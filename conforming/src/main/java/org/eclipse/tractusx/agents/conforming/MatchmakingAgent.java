//
// Copyright (C) 2022-2023 Catena-X Association and others. 
// 
// This program and the accompanying materials are made available under the
// terms of the Apache License 2.0 which is available at
// http://www.apache.org/licenses/.
//  
// SPDX-FileType: SOURCE
// SPDX-FileCopyrightText: 2022-2023 Catena-X Association
// SPDX-License-Identifier: Apache-2.0
//
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
