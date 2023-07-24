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
package org.eclipse.tractusx.agents.remoting;

import org.eclipse.rdf4j.sail.Sail;
import org.eclipse.rdf4j.sail.config.SailFactory;
import org.eclipse.rdf4j.sail.config.SailImplConfig;
import org.eclipse.rdf4j.sail.config.SailConfigException;
import org.eclipse.tractusx.agents.remoting.config.RemotingSailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to create remoting sails
 */

public class RemotingSailFactory implements SailFactory {

    /**
     * our logger
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * hardcoded sail type
     */
    public static final String SAIL_TYPE = "org.eclipse.tractusx.agents:Remoting";
    
    /**
     * createst the factory
     */
    public RemotingSailFactory() {
        if(logger.isDebugEnabled()) {
            logger.debug(String.format("Creating a Remoting SAIL factory for SAIL type %s. Usually OSGI configuration has been successful at this point.",SAIL_TYPE));
        }
    }

    @Override
    public String toString() {
        return super.toString()+"/factory";
    }

    /**
     * return the sail type IRI
     */
    @Override
    public String getSailType() {
        return SAIL_TYPE;
    }

    /**
     * provide a SAIL specific configuration implementation
     */
    @Override
    public SailImplConfig getConfig() {
        if(logger.isTraceEnabled()) {
            logger.trace("Creating a Remoting SAIL configuration.");
        }
        return new RemotingSailConfig();
    }

    /**
     * the actual factory method
     */
    @Override
    public Sail getSail(SailImplConfig originalConfig) throws SailConfigException {
        if(logger.isDebugEnabled()) {
            logger.debug("About to creating a Remoting SAIL from configuration "+originalConfig);
        }
        if (!(originalConfig instanceof RemotingSailConfig)) {
            throw new SailConfigException(
                    "Wrong config type: " 
                            + originalConfig.getClass().getCanonicalName() + ". ");
        }
        RemotingSailConfig config = (RemotingSailConfig)originalConfig;
        RemotingSail sail = new RemotingSail(config);
        return sail;
       
    }
}