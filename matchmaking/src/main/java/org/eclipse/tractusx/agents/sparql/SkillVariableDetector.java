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
package org.eclipse.tractusx.agents.sparql;

import org.apache.jena.graph.Node;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.TransformSingle;
import org.apache.jena.sparql.algebra.op.OpExtend;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.ExprVar;
import org.apache.jena.sparql.expr.NodeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * a pseudo transform which visits every graph node
 * to list all variables. helps us to
 * only serialize the needed portion from
 * consumer to producer
 */
public class SkillVariableDetector extends TransformSingle {

    HashMap<String, Node> variables = new HashMap<>();
    Set<String> allowed;

    public SkillVariableDetector(Set<String> allowed) {
        this.allowed = allowed;
    }

    @Override
    public Op transform(OpExtend opExtend, Op subOp) {
        opExtend.getVarExprList().forEachExpr((assignment, expr) -> {
            String varName = assignment.getVarName();
            if (!variables.containsKey(varName)) {
                if (expr.isVariable()) {
                    Var var = (Var) ((ExprVar) expr).getAsNode();
                    if (allowed.contains(var.getVarName())) {
                        variables.put(varName, var);
                    }
                } else if (expr.isConstant()) {
                    Node node = ((NodeValue) expr).getNode();
                    variables.put(varName, node);
                }
            }
        });
        return opExtend;
    }

    public Map<String, Node> getVariables() {
        return variables;
    }
}
