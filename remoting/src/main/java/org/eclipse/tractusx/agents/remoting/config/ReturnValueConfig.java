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
 * represents the config of a return value
 */
public class ReturnValueConfig {
     /** path of the return value, defaults to empty */
     protected String path="";
     /**
      * default data type is string
      */
     protected String dataType="http://www.w3.org/2001/XMLSchema#string";


     @Override
     public String toString() {
         return super.toString()+"/return";
     }

     public void validate(String context) throws SailConfigException {
        switch(dataType) {
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
                throw new SailConfigException(String.format("Data type %s is not supported in return value %s.",dataType,context));
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
