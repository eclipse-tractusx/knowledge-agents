// Copyright (c) 2022,2024 Contributors to the Eclipse Foundation
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
package org.eclipse.tractusx.agents.conforming;

import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;

public class BindingProfileChecker extends AbstractQueryModelVisitor<Exception> {
    @Override
    public void meet(StatementPattern node) throws Exception {
        if (!node.getPredicateVar().isConstant()) {
            throw new Exception("Predicate must be not constant.");
        }
        if (!node.getPredicateVar().getValue().isIRI()) {
            throw new Exception("Predicate must be IRI.");
        }
        if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#type".equals(node.getPredicateVar().getValue().stringValue())) {
            if (!node.getObjectVar().isConstant()) {
                throw new Exception("Object of rdf:type must be constant.");
            }
        }
        super.meet(node);
    }
}
