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
 * represents the config of an argument
 */
public class ArgumentConfig {
     /** name of the argument */
     protected String argumentName;
    /**
     * whether it is mandatory
     */
    protected boolean mandatory=true;

    /**
     * an optional default value
     */
     protected Object defaultValue;

    /**
     * whether the argument forms a batch group
     */
    protected boolean formsBatchGroup=false;

    /**
     * the priority with which the argument is processed
     */
    protected int priority = 100;

    /**
     * @return argument reference
     */
    public String getArgumentName() {
        return argumentName;
    }

    /**
     * sets
     * @param argumentName reference
     */
    public void setArgumentName(String argumentName) {
        this.argumentName = argumentName;
    }

    /**
     * @return whether this argument is mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * @return the default value of the argument if not bound
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return whether this argument should form a batch group
     */
    public boolean isFormsBatchGroup() {
        return formsBatchGroup;
    }

    /**
     * @return the processing priority of the argument
     */
    public int getPriority() {
        return priority;
    }

    /**
     * validate the argument
     * @param context validation context
     * @throws SailConfigException if validation was unsuccessful
     */
    public void validate(String context) throws SailConfigException {
         if (argumentName==null || argumentName.length()==0) {
            throw new SailConfigException(String.format("Only support named arguments %s.",context));
         }    
     }

     @Override
     public String toString() {
         return super.toString()+"/argument("+String.valueOf(mandatory)+")";
     }
 
}
