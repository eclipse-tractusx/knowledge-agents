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
