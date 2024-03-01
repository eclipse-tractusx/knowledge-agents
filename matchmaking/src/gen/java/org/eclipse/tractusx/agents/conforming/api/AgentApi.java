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
package org.eclipse.tractusx.agents.conforming.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.tractusx.agents.conforming.model.CxWarning;
import org.eclipse.tractusx.agents.conforming.model.JsonResultset;
import org.eclipse.tractusx.agents.conforming.model.XmlResultset;

import java.util.List;


@Path("/")
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2023-03-23T11:28:11.277776230Z[GMT]")
public abstract class AgentApi {

    @Context
    public SecurityContext securityContext;
    @Context
    public Application application;
    @Context
    public HttpHeaders headers;
    @Context
    public Request request;
    @Context
    public Response response;
    @Context
    public UriInfo uri;

    @GET
    @Produces({ "application/sparql-results+json",
            "application/sparql-results+xml"
    })
    @Operation(summary = "Invoke a Skill or Query (Simple)", description = "", tags = { "agent" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The SparQL query has been processed successfully.",
                    content = {
                            @Content(mediaType = "application/sparql-results+json", schema = @Schema(implementation = JsonResultset.class)),
                            @Content(mediaType = "application/sparql-results+xml", schema = @Schema(implementation = XmlResultset.class))
                    }),
            @ApiResponse(responseCode = "203", description = "The SparQL query has been processed successfully but warnings did occur.",
                    headers = { @Header(name = "cx_warnings", schema = @Schema(type = "array", implementation = CxWarning.class)) },
                    content = {
                            @Content(mediaType = "application/sparql-results+json", schema = @Schema(implementation = JsonResultset.class)),
                            @Content(mediaType = "application/sparql-results+xml", schema = @Schema(implementation = XmlResultset.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request or malformed SPARQL"),
            @ApiResponse(responseCode = "500", description = "Fatal error")
    })
    public abstract Response getAgent(
            @Parameter(in = ParameterIn.QUERY, description = "The Target Asset of the Query (targets the complete dataspace if empty)")
            @QueryParam("asset") String asset,
            @Parameter(in = ParameterIn.QUERY, description = "The Query language (fixed: SPARQL)")
            @QueryParam("queryLn") String queryLn,
            @Parameter(in = ParameterIn.QUERY, description = "The SPARQL query")
            @QueryParam("query") String query,
            @Parameter(in = ParameterIn.QUERY, description = "A sample bound parameter 'vin' which opens a new input tuple")
            @QueryParam("(vin") String vin,
            @Parameter(in = ParameterIn.QUERY, description = "A sample multi-bound parameter 'troubleCode' which closes the tuple")
            @QueryParam("troubleCode") List<String> troubleCode)
            throws org.eclipse.tractusx.agents.conforming.api.NotFoundException;

    @POST
    @Consumes({ "application/sparql-results+json", "application/sparql-results+xml", "application/sparql-query" })
    @Produces({ "application/sparql-results+json", "application/sparql-results+xml" })
    @Operation(summary = "Invoke a Skill or Query (Flexible)", description = "", tags = { "agent" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The SparQL query has been processed successfully.",
                    content = {
                            @Content(mediaType = "application/sparql-results+json", schema = @Schema(implementation = JsonResultset.class)),
                            @Content(mediaType = "application/sparql-results+xml", schema = @Schema(implementation = XmlResultset.class))
                    }),
            @ApiResponse(responseCode = "203", description = "The SparQL query has been processed successfully but warnings did occur.",
                    headers = { @Header(name = "cx_warnings", schema = @Schema(type = "array", implementation = CxWarning.class)) },
                    content = {
                            @Content(mediaType = "application/sparql-results+json", schema = @Schema(implementation = JsonResultset.class)),
                            @Content(mediaType = "application/sparql-results+xml", schema = @Schema(implementation = XmlResultset.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request or malformed SPARQL"),
            @ApiResponse(responseCode = "500", description = "Fatal error")
    })
    public abstract Response postAgent(
            @Parameter(in = ParameterIn.DEFAULT, description = "The body either contains the query or a binding data set when a skill is invoked", required = true)
            Object body,
            @Parameter(in = ParameterIn.QUERY, description = "The Target Asset of the Query (targets the complete dataspace if empty)")
            @QueryParam("asset") String asset,
            @Parameter(in = ParameterIn.QUERY, description = "The Query language (fixed: SPARQL)")
            @QueryParam("queryLn") String queryLn,
            @Parameter(in = ParameterIn.QUERY, description = "The SPARQL query")
            @QueryParam("query") String query,
            @Parameter(in = ParameterIn.QUERY, description = "A sample bound parameter 'vin' which opens a new input tuple")
            @QueryParam("(vin") String vin,
            @Parameter(in = ParameterIn.QUERY, description = "A sample multi-bound parameter 'troubleCode' which closes the tuple")
            @QueryParam("troubleCode") List<String> troubleCode)
            throws org.eclipse.tractusx.agents.conforming.api.NotFoundException;

    @POST
    @Path("/skill")
    @Consumes({ "application/sparql-query" })
    @Operation(summary = "Register a Skill", description = "", tags = { "agent" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skill has been registered"),
            @ApiResponse(responseCode = "204", description = "Skill has been updated"),
            @ApiResponse(responseCode = "400", description = "Bad request or malformed SPARQL"),
            @ApiResponse(responseCode = "500", description = "Fatal error") })
    public abstract Response postSkill(
            @Parameter(in = ParameterIn.DEFAULT, description = "The body either contains the parameterized query", required = true)
            String body,
            @Parameter(in = ParameterIn.QUERY, description = "The Target Asset of the Query (targets the complete dataspace if empty)", required = true)
            @QueryParam("asset") String asset)
            throws org.eclipse.tractusx.agents.conforming.api.NotFoundException;

}
