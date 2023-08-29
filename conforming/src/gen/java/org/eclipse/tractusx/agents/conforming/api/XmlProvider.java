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
package org.eclipse.tractusx.agents.conforming.api;

import org.eclipse.tractusx.agents.conforming.ConformingAgent;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class XmlProvider implements MessageBodyReader, MessageBodyWriter {

    @Override
    public boolean isReadable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.isCompatible(ConformingAgent.srx);
    }

    @Override
    public Object readFrom(Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        try {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder parser= factory.newDocumentBuilder();
            return parser.parse(new InputSource(inputStream));
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Could not xml parse message body",e);
        }
    }

    @Override
    public boolean isWriteable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.isCompatible(ConformingAgent.srx);
    }

    @Override
    public void writeTo(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD","");
            factory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet","");
            Transformer transformer = factory.newTransformer();
            transformer.transform(new DOMSource((Document) o),new StreamResult(outputStream));
        } catch (TransformerException e) {
            throw new IOException("Cannot render xml body",e);
        }
    }
}
