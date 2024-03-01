// Copyright (c) 2022,2024 Contributors to the Eclipse Foundation
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

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the standard matchmaking agent. this is not a test for conformance! +
 * It is rather a test for "exactness" such that the implementation tested can serve
 * as a conformance tool.
 */

public class MatchmakingAgentTest extends ConformingAgentTest {

    @Test
    public void testMatchSkillGet() throws IOException {
        Response response = target(getPath()).queryParam("asset", "urn:cx:SkillAsset#Test").request().get();
        assertTrue(response.getStatus() >= 200 && response.getStatus() < 300, "Successful get json request");
        testJsonResultSet(response);
    }

    @Test
    public void testMatchSkillPost() throws IOException {
        Response response = target(getPath()).queryParam("asset", "urn:cx:SkillAsset#Test").request().
                post(Entity.entity(ConformingAgent.emptyJson, "application/sparql-results+json"));
        assertTrue(response.getStatus() >= 200 && response.getStatus() < 300, "Successful post skill request");
        testJsonResultSet(response);
    }

    @Test
    public void testMatchSkillPostXml() throws IOException {
        Response response = target(getPath()).queryParam("asset", "urn:cx:SkillAsset#Test").request()
                .accept("application/sparql-results+xml")
                .post(Entity.entity(ConformingAgent.emptyXml, "application/sparql-results+xml"));
        assertTrue(response.getStatus() >= 200 && response.getStatus() < 300, "Successful post skill xml request");
        testXmlResultSet(response);
    }

    @Test
    public void testMatchSkillPostJsonXml() throws IOException {
        Response response = target(getPath()).queryParam("asset", "urn:cx:SkillAsset#Test").request()
                .accept("application/sparql-results+json")
                .post(Entity.entity(ConformingAgent.emptyXml, "application/sparql-results+xml"));
        assertTrue(response.getStatus() >= 200 && response.getStatus() < 300, "Successful post skill json request");
        testJsonResultSet(response);
    }

    @Test
    public void testMatchSkillPostXmlJson() throws IOException {
        Response response = target(getPath()).queryParam("asset", "urn:cx:SkillAsset#Test").request()
                .accept("application/sparql-results+xml")
                .post(Entity.entity(ConformingAgent.emptyJson, "application/sparql-results+json"));
        assertTrue(response.getStatus() >= 200 && response.getStatus() < 300, "Successful post skill xml request");
        testXmlResultSet(response);
    }

    @Override
    protected String getPath() {
        return "/match";
    }

    @Override
    protected int getNumberVars() {
        return 3;
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(MatchmakingAgent.class);
    }

}
