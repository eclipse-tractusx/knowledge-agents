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
package org.eclipse.tractusx.agents.remoting.config;

import org.eclipse.rdf4j.sail.config.SailConfigException;

/**
 * class to represent an authentication description
 * from the config graph of the remoting SAIL repo.
 */
public class AuthenticationConfig {
 
    /** auth key (defaults to Authorization) */
    protected String authKey = "Authorization";

    /** actual auth code */
    protected String authCode = null;
   
    @Override
    public String toString() {
        return super.toString()+"/authentication";
    }   

    /**
     * Validates the authentication config
     * @throws SailConfigException in case validation is unsuccessful
     */
    public void validate(String context) throws SailConfigException {
        if (authCode==null || authCode.length() == 0) {
            throw new SailConfigException(String.format("Authentication code in %s is not provided",context));
        }
    }

    /**
     * @return configured auth key
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     * @return configured auth code
     */
    public String getAuthCode() {
        return authCode;
    }

}
