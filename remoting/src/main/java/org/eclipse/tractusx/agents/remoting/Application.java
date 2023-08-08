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

import org.eclipse.rdf4j.common.iteration.Iterations;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.tractusx.agents.remoting.config.*;

import java.util.Arrays;


/**
 * A sample main application which exposes an RDF4J-SparQL endpoint
 * with a fixed binding. Just to demonstrate how to build custom servers.
 * Usually, this library is added to the rdf4j lib path and the
 * endpoints/repositories using the function binding are then defined
 * declaratively.
 */
public class Application {


    @Override
    public String toString() {
        return super.toString()+"/application";
    }

    /**
     * main logic sets up the rdf4j server programatically.
     * @param args command line args
     */

    public static void main(String[] args) {        
        RemotingSailConfig rsc=new RemotingSailConfig(RemotingSailFactory.SAIL_TYPE);
        ServiceConfig ic=new ServiceConfig();
        rsc.putService("https://www.w3id.org/catenax/ontology/prognosis#Invocation",ic);
        ic.setTargetUri("class:io.catenax.knowledge.agents.remoting.TestFunction#test");
        ArgumentConfig ac=new ArgumentConfig();
        ac.setArgumentName("arg0");
        ic.getArguments().put("https://www.w3id.org/catenax/ontology/prognosis#input-1",ac);
        ac=new ArgumentConfig();
        ac.setArgumentName("arg1");
        ic.getArguments().put("https://www.w3id.org/catenax/ontology/prognosis#input-2",ac);
        ReturnValueConfig rvc=new ReturnValueConfig();
        ResultConfig rc=new ResultConfig();
        rc.getOutputs().put("https://www.w3id.org/catenax/ontology/prognosis#output",rvc);
        ic.setResult(rc);
        ic.setResultName("https://www.w3id.org/catenax/ontology/prognosis#Result");

        rsc.validate();

        Repository rep = new SailRepository(new RemotingSail(rsc));
        try (RepositoryConnection conn = rep.getConnection()) {
            TupleQuery query=(TupleQuery) conn.prepareQuery(QueryLanguage.SPARQL,
            "PREFIX cx: <https://w3id.org/catenax/ontology#> "+
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "+
            "PREFIX prognosis: <https://www.w3id.org/catenax/ontology/prognosis#> "+
            "SELECT ?output "+
            "WHERE { "+
            "?invocation a prognosis:Invocation; "+
            "            prognosis:input-1 \"1\"^^xsd:string; "+
            "            prognosis:input-2 \"2\"^^xsd:string; "+
            "            prognosis:output ?output. "+
            "}");
            final TupleQueryResult result = query.evaluate();
		    final String[] names = result.getBindingNames().toArray(new String[0]);
            System.out.println("Got variables "+Arrays.toString(names));
		    java.util.List<BindingSet> bindings = Iterations.asList(result);
		    System.out.println("Got bindings "+Arrays.toString(bindings.toArray(new BindingSet[0])));
        }
    }
}