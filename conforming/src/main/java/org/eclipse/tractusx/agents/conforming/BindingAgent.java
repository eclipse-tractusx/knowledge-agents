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

import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;

/**
 * Implements a standard binding agent
 */
@Path("/bind")
public class BindingAgent extends ConformingAgent {

    public BindingAgent() {

    }

    @Override
    public Response getAgent(String asset, String queryLn, String query, String _vin, List<String> troubleCode) throws NotFoundException {
        if(query==null) {
            return annotate(Response.status(400,"{ \"error\":400, \"reason\":\"KA-BIND: query parameter must be set\" }"));
        }
        SPARQLParser parser = new SPARQLParser();
        ParsedQuery sparql=parser.parseQuery(query,uri.getAbsolutePath().toString());

        try {
            sparql.getTupleExpr().visit(new BindingProfileChecker());
        } catch(Exception e) {
            return annotate(Response.status(400,"{ \"error\":400, \"reason\":\""+e.getMessage()+"\" }"));
        }
        return super.getAgent(asset,queryLn, query,_vin,troubleCode);
    }

    @Override
    public Response postAgent(Object body, String asset,  String queryLn, String query, String _vin,  List<String> troubleCode) throws NotFoundException {
        if(body==null || String.valueOf(body).isEmpty()) {
            return annotate(Response.status(400,"{ \"error\":400, \"reason\":\"KA-BIND: query parameter must be set\" }"));
        }
        return super.postAgent(body,asset,queryLn,query,_vin,troubleCode);
    }

    @Override
    public Response postSkill(String body, @NotNull String asset) throws NotFoundException {
        return annotate(Response.status(404,"{ \"error\":404, \"reason\":\"KA-BIND: does not support skills\" }"));
    }
}
