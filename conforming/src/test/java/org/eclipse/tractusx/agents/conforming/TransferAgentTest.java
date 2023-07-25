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

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the standard transfer agent. this is not a test for conformance! +
 * It is rather a test for "exactness" such that the implementation tested can serve
 * as a conformance tool.
 */

public class TransferAgentTest extends MatchmakingAgentTest {

    @Override protected String getEntity(Response response) {
        return response.readEntity(FormDataMultiPart.class).getBodyParts().get(0).getEntityAs(String.class);
    }

    @Override
    protected String getPath() {
        return "/transfer";
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(TransferAgent.class);
    }

}
