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
package org.eclipse.tractusx.agents.remoting.util;

import java.util.Arrays;

/**
 * implements a compound key based on individual hash codes
 */
public class BatchKey<TargetClass> {

    TargetClass[] components;

    public TargetClass[] getComponents() {
        return components;
    }

    /**
     * create a new batch key
     * @param theComponents of the batch key
     */
    public BatchKey(TargetClass... theComponents) {
        this.components=theComponents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchKey<TargetClass> batchKey = (BatchKey<TargetClass>) o;
        return Arrays.equals(components, batchKey.components);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }
}
