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

import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.tractusx.agents.conforming.api.NotFoundException;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Implements a standard binding agent
 */
@Path("/bind")
public class BindingAgent extends ConformingAgent {

    public BindingAgent() {

    }

    @Override
    public Response getAgent(String asset, String queryLn, String query, String vin, List<String> troubleCode) throws NotFoundException {
        if (query == null) {
            return annotate(Response.status(400, "{ \"error\":400, \"reason\":\"KA-BIND: query parameter must be set\" }"));
        }
        SPARQLParser parser = new SPARQLParser();
        ParsedQuery sparql = parser.parseQuery(query, uri.getAbsolutePath().toString());

        try {
            sparql.getTupleExpr().visit(new BindingProfileChecker());
        } catch (Exception e) {
            return annotate(Response.status(400, "{ \"error\":400, \"reason\":\"" + e.getMessage() + "\" }"));
        }
        return super.getAgent(asset, queryLn, query, vin, troubleCode);
    }

    @Override
    public Response postAgent(Object body, String asset, String queryLn, String query, String vin, List<String> troubleCode) throws NotFoundException {
        if (body == null || String.valueOf(body).isEmpty()) {
            return annotate(Response.status(400, "{ \"error\":400, \"reason\":\"KA-BIND: query parameter must be set\" }"));
        }
        return super.postAgent(body, asset, queryLn, query, vin, troubleCode);
    }

    @Override
    public Response postSkill(String body, @NotNull String asset) throws NotFoundException {
        return annotate(Response.status(404, "{ \"error\":404, \"reason\":\"KA-BIND: does not support skills\" }"));
    }
}
