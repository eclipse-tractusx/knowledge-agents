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
package org.eclipse.tractusx.agents.conforming;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;

public class BindingProfileChecker extends AbstractQueryModelVisitor<Exception> {
    @Override
    public void meet(StatementPattern node) throws Exception {
        if(!node.getPredicateVar().isConstant()) {
            throw new Exception("Predicate must be not constant.");
        }
        if(!node.getPredicateVar().getValue().isIRI()) {
            throw new Exception("Predicate must be IRI.");
        }
        if("http://www.w3.org/1999/02/22-rdf-syntax-ns#type".equals(node.getPredicateVar().getValue().stringValue())) {
            if(!node.getObjectVar().isConstant()) {
                throw new Exception("Object of rdf:type must be constant.");
            }
        }
        super.meet(node);
    }
}
