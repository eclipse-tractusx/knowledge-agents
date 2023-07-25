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
package org.eclipse.tractusx.agents.remoting.test;

/**
 * a simple test function to expose
 */
public class TestFunction {


    @Override
    public String toString() {
        return super.toString()+"/test";
    }

    /**
     * test method to expose
     */
    public int test(int operator1,int operator2) {
        return operator1+operator2;
    }    
}
