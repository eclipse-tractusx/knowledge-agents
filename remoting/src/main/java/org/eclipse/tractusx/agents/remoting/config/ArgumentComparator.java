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

import java.util.Comparator;
import java.util.Map;

/**
 * A comparator for arguments according to priority
 */
public class ArgumentComparator implements Comparator<Map.Entry<String, ArgumentConfig>> {

    /**
     * use the configs priority
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return comparison according to priority
     */
    public int compare(Map.Entry<String, ArgumentConfig> o1, Map.Entry<String, ArgumentConfig> o2) {
        return Integer.compare(o1.getValue().getPriority(),o2.getValue().getPriority());
    }
}
