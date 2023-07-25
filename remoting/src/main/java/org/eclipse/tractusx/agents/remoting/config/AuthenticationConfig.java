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
