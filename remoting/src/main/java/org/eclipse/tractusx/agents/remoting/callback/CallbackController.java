// Copyright (c) 2022,2023 Contributors to the Eclipse Foundation
//
// See the NOTICE file(s) distributed with this work for additional
// information regarding copyright ownership.
//
// This program and the accompanying materials are made available under the
// terms of the Apache License, Version 2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0.
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// License for the specific language governing permissions and limitations
// under the License.
//
// SPDX-License-Identifier: Apache-2.0
package org.eclipse.tractusx.agents.remoting.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.eclipse.tractusx.agents.remoting.Invocation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * a controller for receiving and synchronizing on
 * asynchronous callbacks
 */
@Controller
@RequestMapping("/callback")
public class CallbackController implements org.springframework.web.servlet.mvc.Controller {

    public static ObjectMapper objectMapper=new ObjectMapper();

    public static final Map<CallbackToken, AtomicReference<Object>> pending=new HashMap<>();

    /**
     * registers a new asynchronous call
     * @param responsePath the path where to look for call ids in the response
     * @param callId id of the call/response
     * @return an atomic reference for the result
     */
    public static CallbackToken register(String responsePath, String callId) {
        CallbackToken token=new CallbackToken(responsePath,callId);
        synchronized (pending) {
            AtomicReference<Object> result = pending.get(token);
            if(result==null) {
                result=new AtomicReference<>();
                pending.put(token,result);
            }
        }
        return token;
    }

    /**
     * synchronizes on the given asynchronous call
     * @param token of the call
     * @return asynchronous result
     */
    public static Object synchronize(CallbackToken token) {
        AtomicReference<Object> result;
        synchronized(pending) {
            result=pending.get(token);
        }
        if(result==null) {
            return null;
        }
        int maxrounds=2;
        synchronized(result) {
            while(result.get()==null && 0<maxrounds--) {
                try {
                    result.wait(30000);
                } catch (InterruptedException ignored) {
                }
            }
            return result.get();
        }
    }

    /**
     * the actual request handler
     * @param request http request
     * @param response http response
     * @return an empty redirection
     */
    @Override
    @PostMapping
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)  {
        try {
            Object callback;
            if(request.getContentType().contains("json")) {
                callback=objectMapper.readTree(request.getInputStream());
            } else if(request.getContentType().contains("xml")) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                callback=builder.parse(request.getInputStream()).getDocumentElement();
            } else {
                callback= IOUtils.toString(request.getInputStream());
            }
            synchronized(pending) {
                for(Map.Entry<CallbackToken,AtomicReference<Object>> callbacks : pending.entrySet()) {
                    String[] paths=callbacks.getKey().getResponsePath().split("\\.");
                    String callId= Invocation.convertObjectToString(Invocation.traversePath(callback, paths));
                    if(callbacks.getKey().getCallId().equals(callId)) {
                        callbacks.getValue().set(callback);
                        synchronized(callbacks.getValue()) {
                            callbacks.getValue().notify();
                        }
                    }
                }
            }
            response.setStatus(200);
        } catch(IOException | ParserConfigurationException | SAXException jpe) {
            response.setStatus(400);
        }
        return null;
    }
}
