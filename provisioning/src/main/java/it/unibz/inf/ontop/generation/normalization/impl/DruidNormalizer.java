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
package it.unibz.inf.ontop.generation.normalization.impl;

import com.google.inject.Inject;
import it.unibz.inf.ontop.generation.normalization.DialectExtraNormalizer;
import it.unibz.inf.ontop.iq.IQTree;
import it.unibz.inf.ontop.utils.VariableGenerator;

/**
 * Ontop Normalizer which makes the druid optimizer happy.
 * TODO single joins per value binding
 */
public class DruidNormalizer implements DialectExtraNormalizer {

    private final TypingNullsInUnionDialectExtraNormalizer typingNullInUnionNormalizer;
    private final TypingNullsInConstructionNodeDialectExtraNormalizer typingNullInConstructionNormalizer;
    private final SubQueryFromComplexJoinExtraNormalizer complexJoinNormalizer;

    @Inject
    public DruidNormalizer(TypingNullsInUnionDialectExtraNormalizer typingNullInUnionNormalizer,
                           TypingNullsInConstructionNodeDialectExtraNormalizer typingNullInConstructionNormalizer,
                           SubQueryFromComplexJoinExtraNormalizer complexJoinNormalizer) {
        this.typingNullInUnionNormalizer = typingNullInUnionNormalizer;
        this.typingNullInConstructionNormalizer = typingNullInConstructionNormalizer;
        this.complexJoinNormalizer = complexJoinNormalizer;
    }

    @Override
    public IQTree transform(IQTree tree, VariableGenerator variableGenerator) {
        return complexJoinNormalizer.transform(
                typingNullInConstructionNormalizer.transform(
                        typingNullInUnionNormalizer.transform(tree, variableGenerator),
                        variableGenerator),
                variableGenerator);
    }
}