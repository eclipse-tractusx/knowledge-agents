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
package org.eclipse.tractusx.agents.conforming;

import org.eclipse.tractusx.agents.conforming.api.NotFoundException;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;


import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Implements a standard transfer agent
 */
@Path("/transfer")
public class TransferAgent extends ConformingAgent {

    /** the actual delegator to use */
    ConformingAgent delegator=new MatchmakingAgent();

    @PostConstruct
    public void init() {
        delegator.application = application;
        delegator.securityContext = securityContext;
        delegator.headers = headers;
        delegator.request=request;
        delegator.response=response;
        delegator.uri=uri;
    }

    @Override
    protected MediaType getDefaultResultType() {
        if(this.uri.getQueryParameters().containsKey("cx_accepts")) {
            List<String> allCompartments=this.uri.getQueryParameters().get("cx_accepts");
            for(String compartments : allCompartments) {
                for (String compartment : compartments.split(",")) {
                    String[] qualifiers = compartment.split(";");
                    try {
                        MediaType wantedEncoding = MediaType.valueOf(qualifiers[0]);
                        if (wantedEncoding.isCompatible(srj)) {
                            return srj;
                        } else if (wantedEncoding.isCompatible(srx)) {
                            return srx;
                        }
                    } catch (IllegalArgumentException iae) {
                    }
                }
            }
            return null;
        }
        return srj;
    }

    /** produces a standard response */
    protected Response compute(Response delegateResponse) {
        Response.ResponseBuilder response= Response.status(delegateResponse.getStatus()).type(MediaType.MULTIPART_FORM_DATA);
        FormDataMultiPart mpe = new FormDataMultiPart();
        if(delegateResponse.hasEntity()) {
            mpe.bodyPart(delegateResponse.getEntity(), delegateResponse.getMediaType());
        }
        if(delegateResponse.getHeaders().containsKey("cx_warnings")) {
            mpe.bodyPart(delegateResponse.getHeaderString("cx_warnings"), MediaType.APPLICATION_JSON_TYPE);
        }
        if(mpe.getBodyParts().size()>0) {
            response=response.entity(mpe);
        }
        return response.build();
    }

    @Override
    public Response getAgent(String asset, String queryLn, String query, String _vin, List<String> troubleCode) throws NotFoundException {
        Response intermediate=delegator.getAgent(asset,queryLn,query,_vin,troubleCode);
        return compute(intermediate);
    }

    @Override
    public Response postAgent(Object body, String asset,  String queryLn, String query, String _vin,  List<String> troubleCode) throws NotFoundException {
        Response intermediate=delegator.postAgent(body, asset,  queryLn, query, _vin,  troubleCode);
        return compute(intermediate);
    }

    @Override
    public Response postSkill(String body, @NotNull String asset) throws NotFoundException {
        return Response.status(404,"{ \"error\":404, \"reason\":\"KA-BIND: does not support skills\" }").build();
    }

}
