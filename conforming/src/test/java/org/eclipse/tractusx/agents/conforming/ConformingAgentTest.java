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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.tractusx.agents.conforming.api.JsonProvider;
import org.eclipse.tractusx.agents.conforming.api.SparqlProvider;
import org.eclipse.tractusx.agents.conforming.api.XmlProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.internal.MultiPartReaderClientSide;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the conforming agents themselves
 * common test setup and tests shared by KA-MATCH and KA-BIND profiles
 */
public abstract class ConformingAgentTest extends JerseyTest {

    protected ObjectMapper objectMapper = new ObjectMapper();

    /**
     * you cannot invoke without query or skill asset definition
     */
    @Test
    public void testUnderspecifiedGet() {
        final Response response = target(getPath()).request().get();
        assertTrue(response.getStatus() >= 400 && response.getStatus() < 500, "KA-BIND/KA-MATCH: Should not be possible to get information without query or skill asset");
    }

    /**
     * you cannot invoke without query or skill asset definition
     */
    @Test
    public void testUnderspecifiedPost() {
        final Response response = target(getPath()).request().post(Entity.entity("", "application/sparql-results+json"));
        assertTrue(response.getStatus() >= 400 && response.getStatus() < 500, "KA-BIND/KA-MATCH: Should not be possible to post information without query or skill asset");
    }

    /**
     * you can always get with a simple sparql
     */
    @Test
    public void testBindGet() throws IOException {
        Response response = target(getPath())
                .queryParam("query", "SELECT%20%3Fsubject%20%3Fpredicate%20%3Fobject%20WHERE%20%7B%20%3Fsubject%20<cx:test>%20%3Fobject.%7D")
                .request()
                .get();
        assertTrue(response.getStatus() >= 200 && response.getStatus() < 300, "Successful get json request");
        testJsonResultSet(response);
    }

    /**
     * you can always get with a simple sparql
     */
    @Test
    public void testBindGetXml() throws IOException, ParserConfigurationException, SAXException {
        Response response = target(getPath())
                .queryParam("query", "SELECT%20%3Fsubject%20%3Fpredicate%20%3Fobject%20WHERE%20%7B%20%3Fsubject%20<cx:test>%20%3Fobject.%7D")
                .request()
                .accept("application/sparql-results+xml")
                .get();
        assertTrue(response.getStatus() >= 200 && response.getStatus() < 300, "Successful get xml request");
        testXmlResultSet(response);
    }

    protected abstract String getPath();

    @Override
    protected DeploymentContext configureDeployment() {
        DeploymentContext context = super.configureDeployment();
        context.getResourceConfig().register(JsonProvider.class);
        context.getResourceConfig().register(XmlProvider.class);
        context.getResourceConfig().register(SparqlProvider.class);
        context.getResourceConfig().packages("org.glassfish.jersey.examples.multipart")
                .register(MultiPartFeature.class);
        return context;
    }

    @Override
    protected void configureClient(ClientConfig config) {
        super.configureClient(config);
        config.register(MultiPartReaderClientSide.class);
    }

    protected String getEntity(Response response) {
        return response.readEntity(String.class);
    }

    protected int getNumberVars() {
        return 3;
    }

    protected void testJsonResultSet(Response response) throws IOException {
        String content = getEntity(response);
        JsonNode node = objectMapper.readTree(content);
        assertEquals(getNumberVars(), node.get("head").get("vars").size(), "Got three variables");
        assertEquals(1, node.get("results").get("bindings").size(), "got one result");
        assertEquals(getNumberVars(), node.get("results").get("bindings").get(0).size(), "got 3 bindings");
    }

    protected void testXmlResultSet(Response response) throws IOException {
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IOException("Could not get Xml parser", e);
        }
        String content = getEntity(response);
        Document document = null;
        try {
            document = builder.parse(new InputSource(new StringReader(content)));
        } catch (SAXException e) {
            throw new IOException("Cannot parse XML", e);
        }
        assertEquals(getNumberVars(), ((Element) document.getDocumentElement().getElementsByTagName("head").item(0)).getElementsByTagName("variable").getLength(), "Got three variables");
        assertEquals(1, ((Element) document.getDocumentElement().getElementsByTagName("results").item(0)).getElementsByTagName("result").getLength(), "got one result");
        assertEquals(getNumberVars(), ((Element) ((Element) document.getDocumentElement().getElementsByTagName("results").item(0)).getElementsByTagName("result").item(0)).getElementsByTagName("binding").getLength(), "got 3 bindings");
    }

    /**
     * you can always get with a simple sparql
     */


}