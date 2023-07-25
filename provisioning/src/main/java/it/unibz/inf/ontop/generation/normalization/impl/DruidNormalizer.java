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