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
