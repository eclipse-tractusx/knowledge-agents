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
package org.eclipse.tractusx.agents.remoting.callback;

/**
 * A callback token that may used to
 * synchronized and analyze asynchronous
 * callbacks
 */
public class CallbackToken {

    protected String responsePath;
    protected String callId;

    /**
     * create a new token
     * @param responsePath the path where we expect call id in the response
     * @param callId the callid to look for
     */
    public CallbackToken(String responsePath, String callId) {
        this.responsePath=responsePath;
        this.callId=callId;
    }

    /**
     * @return associated call id
     */
    public String getCallId() {
        return callId;
    }

    /**
     * @return associated response path
     */
    public String getResponsePath() {
        return responsePath;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || (obj instanceof CallbackToken &&
            responsePath.equals(((CallbackToken) obj).responsePath) && callId.equals(((CallbackToken) obj).callId));
    }

    @Override
    public int hashCode() {
        return responsePath.hashCode()*callId.hashCode();
    }
}
