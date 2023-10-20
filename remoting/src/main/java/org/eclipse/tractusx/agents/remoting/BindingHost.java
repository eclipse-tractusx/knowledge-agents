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
package org.eclipse.tractusx.agents.remoting;

import org.eclipse.rdf4j.query.MutableBindingSet;

import java.util.Collection;
import java.util.Set;

/**
 * Interface to any intermediate stpre
 * which holds information about the bound
 * variables and the incoming/outgoing tuples
 */
public interface BindingHost {

    Set<String> getVariables();

    Collection<MutableBindingSet> getBindings();

}
