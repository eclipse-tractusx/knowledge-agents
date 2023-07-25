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

import org.eclipse.rdf4j.query.MutableBindingSet;

import java.util.Collection;
import java.util.Set;

/**
 * Interface to any intermediate stpre
 * which holds information about the bound
 * variables and the incoming/outgoing tuples
 */
public interface IBindingHost {

    Set<String> getVariables();
    Collection<MutableBindingSet> getBindings();

}
