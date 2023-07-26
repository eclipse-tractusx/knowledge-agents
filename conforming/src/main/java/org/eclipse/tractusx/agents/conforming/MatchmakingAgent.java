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

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Implements a standard matchmaking agent
 */
@Path("/match")
public class MatchmakingAgent extends ConformingAgent {

    public MatchmakingAgent() {
        status=203;
    }

    @Override
    protected Response annotate(Response.ResponseBuilder builder) {
        return builder.header("cx_warnings",warnings)
            .header("Access-Control-Expose-Headers","cx_warnings, content-length, content-type")
            .build();
    }

    @Override
    public Response getAgent(String asset, String queryLn, String query, String _vin, List<String> troubleCode) throws NotFoundException {
        if(query==null && asset==null) {
            return annotate(Response.status(400,"{ \"error\":400, \"reason\":\"KA-MATCH: query or asset parameter must be set\" }"));
        }
        return super.getAgent(asset,queryLn,query,_vin,troubleCode);
    }

    @Override
    public Response postAgent(Object body, String asset, String queryLn, String query, String _vin,  List<String> troubleCode) throws NotFoundException {
        if((body==null || String.valueOf(body).isEmpty()) && asset == null) {
            return annotate(Response.status(400,"{ \"error\":400, \"reason\":\"KA-BIND: body or asset parameter must be set\" }"));
        }
        return super.postAgent(body, asset, queryLn, query, _vin,troubleCode);
    }
}
