// Copyright (c) 2024 Contributors to the Eclipse Foundation
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

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.jena.query.Syntax;
import org.apache.jena.sparql.serializer.SerializerRegistry;
import org.apache.jena.sparql.service.ServiceExecutorRegistry;
import org.eclipse.edc.spi.monitor.ConsoleMonitor;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.configuration.Config;
import org.eclipse.edc.spi.system.configuration.ConfigFactory;
import org.eclipse.edc.spi.types.TypeManager;
import org.eclipse.tractusx.agents.edc.AgentConfig;
import org.eclipse.tractusx.agents.edc.AgreementControllerImpl;
import org.eclipse.tractusx.agents.edc.SkillStore;
import org.eclipse.tractusx.agents.edc.http.AgentController;
import org.eclipse.tractusx.agents.edc.http.DelegationServiceImpl;
import org.eclipse.tractusx.agents.edc.http.GraphController;
import org.eclipse.tractusx.agents.edc.rdf.RdfStore;
import org.eclipse.tractusx.agents.edc.service.DataManagementImpl;
import org.eclipse.tractusx.agents.edc.service.DataspaceSynchronizer;
import org.eclipse.tractusx.agents.edc.service.EdcSkillStore;
import org.eclipse.tractusx.agents.edc.sparql.DataspaceServiceExecutor;
import org.eclipse.tractusx.agents.edc.sparql.SparqlQueryProcessor;
import org.eclipse.tractusx.agents.edc.sparql.SparqlQuerySerializerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SharedObjectManager {
    private static final SharedObjectManager INSTANCE = new SharedObjectManager();
    private Monitor monitor;
    private TypeManager typeManager;
    private AgentConfig agentConfig;
    private RdfStore rdfStore;
    private ServiceExecutorRegistry reg;
    private SparqlQueryProcessor processor;
    private DataManagementImpl catalogService;
    private SkillStore skillStore;
    private AgreementControllerImpl agreementController;
    private AgentController agentController;
    private GraphController graphController;
    private DelegationServiceImpl delegationService;
    private DataspaceSynchronizer synchronizer;
    private OkHttpClient httpClient;
    

    private SharedObjectManager() {
        // Initialize shared objects
        this.monitor = new ConsoleMonitor();
        this.typeManager = new TypeManager();
        Properties props = new Properties();
        FileInputStream input = null;
        try {
            if (System.getProperty("property.file.location") == null) {
                input = new FileInputStream("/app/configuration.properties");
            } else {
                input = new FileInputStream(System.getProperty("property.file.location"));
            }
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Config emptyConfig = ConfigFactory.fromProperties(props);
        this.agentConfig = new AgentConfig(monitor, emptyConfig);
        this.httpClient = new OkHttpClient();
        this.catalogService = new DataManagementImpl(monitor, typeManager, httpClient, agentConfig);
        agreementController = new AgreementControllerImpl(monitor, agentConfig, catalogService);
        this.rdfStore = new RdfStore(agentConfig, monitor);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(agentConfig.getThreadPoolSize());
        synchronizer = new DataspaceSynchronizer(executorService, agentConfig, catalogService, rdfStore, monitor);
        this.reg = new ServiceExecutorRegistry();   
        reg.addBulkLink(new DataspaceServiceExecutor(monitor, agreementController, agentConfig, httpClient, executorService, typeManager));
        SparqlQuerySerializerFactory arqQuerySerializerFactory = new SparqlQuerySerializerFactory();
        SerializerRegistry.get().addQuerySerializer(Syntax.syntaxARQ, arqQuerySerializerFactory);
        SerializerRegistry.get().addQuerySerializer(Syntax.syntaxSPARQL_10, arqQuerySerializerFactory);
        SerializerRegistry.get().addQuerySerializer(Syntax.syntaxSPARQL_11, arqQuerySerializerFactory);
        this.processor = new SparqlQueryProcessor(reg, monitor, agentConfig, rdfStore, typeManager);
        this.skillStore = new EdcSkillStore(catalogService, typeManager, agentConfig);
        this.delegationService = new DelegationServiceImpl(agreementController, monitor, httpClient, typeManager, agentConfig);
        this.agentController = new AgentController(monitor, agreementController, agentConfig, processor, skillStore, delegationService);
        this.graphController = new GraphController(monitor, rdfStore, catalogService, agentConfig);
        
    }
    
    public static String convertToCurl(Request request) {
        StringBuilder curlCommand = new StringBuilder("curl");

        // Add method
        curlCommand.append(" -X ").append(request.method());

        // Add headers
        request.headers().toMultimap().forEach((name, values) -> {
            values.forEach(value -> curlCommand.append(" -H '").append(name).append(": ").append(value).append("'"));
        });

        // Add request body if present
        if (request.body() != null) {
            Gson gson = new Gson();
            String bodyString = gson.toJson(request.body().toString());
            curlCommand.append(" -d '").append(bodyString).append("'");
        }

        // Add URL
        curlCommand.append(" '").append(request.url()).append("'");

        return curlCommand.toString();
    }

    public static SharedObjectManager getInstance() {
        return INSTANCE;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public TypeManager getTypeManager() {
        return typeManager;
    }

    public AgentConfig getAgentConfig() {
        return agentConfig;
    }

    public RdfStore getRdfStore() {
        return rdfStore;
    }

    public ServiceExecutorRegistry getReg() {
        return reg;
    }

    public SparqlQueryProcessor getProcessor() {
        return processor;
    }

    public DataManagementImpl getCatalogService() {
        return catalogService;
    }

    public SkillStore getSkillStore() {
        return skillStore;
    }

    public AgreementControllerImpl getAgreementController() {
        return agreementController;
    }

    public DelegationServiceImpl getDelegationService() {
        return delegationService;
    }

    public DataspaceSynchronizer getSynchronizer() {
        return synchronizer;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public AgentController getAgentController() {
        return agentController;
    }

    public GraphController getGraphController() {
        return graphController;
    }
    
}