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

import java.util.Map;

/**
 * class to represent a single result description
 * from the config graph of the remoting SAIL repo.
 */
public class ResultConfig {

    /**
     * map of outputs
     */
    protected Map<String, ReturnValueConfig> outputs = new java.util.HashMap<>();

    /**
     * prefix for all the output paths
     */
    String outputProperty = null;
    /**
     * where the id of the result can be found
     */
    String resultIdProperty = null;

    public Map<String, ReturnValueConfig> getOutputs() {
        return outputs;
    }

    public String getOutputProperty() {
        return outputProperty;
    }

    public String getResultIdProperty() {
        return resultIdProperty;
    }

    public String getCorrelationInput() {
        return correlationInput;
    }

    public String getCallbackProperty() {
        return callbackProperty;
    }

    String correlationInput = null;
    String callbackProperty = null;


    @Override
    public String toString() {
        return super.toString() + "/service";
    }

    /**
     * Validates the invocation config
     *
     * @throws SailConfigException if validation is not successful
     */
    public void validate(String context) throws SailConfigException {
        if (context == null) {
            throw new SailConfigException("Result Config must have a context");
        }
        for (Map.Entry<String, ReturnValueConfig> arg : outputs.entrySet()) {
            arg.getValue().validate(arg.getKey());
        }
    }
}
