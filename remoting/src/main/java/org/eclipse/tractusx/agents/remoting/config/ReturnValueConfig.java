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
 * represents the config of a return value
 */
public class ReturnValueConfig {
    /**
     * path of the return value, defaults to empty
     */
    protected String path = "";
    /**
     * default data type is string
     */
    protected String dataType = "http://www.w3.org/2001/XMLSchema#string";


    @Override
    public String toString() {
        return super.toString() + "/return";
    }

    public void validate(String context) throws SailConfigException {
        switch (dataType) {
            case "http://www.w3.org/2001/XMLSchema#double":
                break;
            case "http://www.w3.org/2001/XMLSchema#int":
                break;
            case "http://www.w3.org/2001/XMLSchema#float":
                break;
            case "http://www.w3.org/2001/XMLSchema#long":
                break;
            case "http://www.w3.org/2001/XMLSchema#string":
                break;
            case "http://www.w3.org/2001/XMLSchema#dateTime":
                break;
            case "https://json-schema.org/draft/2020-12/schema#Object":
                break;
            default:
                throw new SailConfigException(String.format("Data type %s is not supported in return value %s.", dataType, context));
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
