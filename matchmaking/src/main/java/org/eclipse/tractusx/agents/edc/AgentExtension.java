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
package org.eclipse.tractusx.agents.edc;

import okhttp3.OkHttpClient;
import org.apache.jena.query.Syntax;
import org.apache.jena.sparql.serializer.SerializerRegistry;
import org.apache.jena.sparql.service.ServiceExecutorRegistry;
import org.eclipse.edc.connector.dataplane.http.spi.HttpRequestParamsProvider;
import org.eclipse.edc.connector.dataplane.spi.pipeline.PipelineService;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.runtime.metamodel.annotation.Requires;
import org.eclipse.edc.spi.http.EdcHttpClient;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.spi.types.TypeManager;
import org.eclipse.edc.web.spi.WebService;
import org.eclipse.tractusx.agents.edc.http.AgentController;
import org.eclipse.tractusx.agents.edc.http.DelegationServiceImpl;
import org.eclipse.tractusx.agents.edc.http.HttpClientFactory;
import org.eclipse.tractusx.agents.edc.rdf.RdfStore;
import org.eclipse.tractusx.agents.edc.service.DataManagementImpl;
import org.eclipse.tractusx.agents.edc.service.DataspaceSynchronizer;
import org.eclipse.tractusx.agents.edc.service.EdcSkillStore;
import org.eclipse.tractusx.agents.edc.sparql.DataspaceServiceExecutor;
import org.eclipse.tractusx.agents.edc.sparql.SparqlQueryProcessor;
import org.eclipse.tractusx.agents.edc.sparql.SparqlQuerySerializerFactory;
import org.eclipse.tractusx.agents.edc.validation.SwitchingDataPlaneTokenValidatorController;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;

/**
 * EDC extension that initializes the Agent subsystem (Agent Sources, Agent Endpoint and Federation Callbacks
 */
@Requires(HttpRequestParamsProvider.class)
public class AgentExtension implements ServiceExtension {

    /**
     * static constants
     */
    protected static final String DEFAULT_CONTEXT_ALIAS = "default";
    protected static final String CALLBACK_CONTEXT_ALIAS = "callback";
    public static final Pattern GRAPH_PATTERN = Pattern.compile("((?<url>[^#]+)#)?(?<graph>.*Graph(Asset)?.*)");
    public static final Pattern SKILL_PATTERN = Pattern.compile("((?<url>[^#]+)#)?(?<skill>.*Skill(Asset)?.*)");


    /**
     * dependency injection part
     */
    @Inject
    protected WebService webService;


    @Inject
    protected PipelineService pipelineService;

    @Inject
    protected Vault vault;

    @Inject
    protected TypeManager typeManager;

    // we reuse the http settings of the http transfer
    @Inject
    protected EdcHttpClient edcHttpClient;
    @Inject
    protected OkHttpClient httpClient;

    /**
     * refers a scheduler
     * TODO maybe reuse an injected scheduler
     */
    protected ScheduledExecutorService executorService;

    /**
     * data synchronization service
     */
    protected DataspaceSynchronizer synchronizer;

    /**
     * access
     *
     * @return name of the extension
     */
    @Override
    public String name() {
        return "Knowledge Agents Extension";
    }

    /**
     * runs on extension initialization
     *
     * @param context EDC bootstrap context
     */
    @Override
    public void initialize(ServiceExtensionContext context) {
        Monitor monitor = context.getMonitor();
        
        monitor.debug(String.format("Initializing %s", name()));

        AgentConfig config = new AgentConfig(monitor, context.getConfig());
        Map.Entry<EdcHttpClient, OkHttpClient> instance = HttpClientFactory.create(edcHttpClient, httpClient, pipelineService, config);
        edcHttpClient = instance.getKey();
        httpClient = instance.getValue();

        DataManagementImpl catalogService = new DataManagementImpl(monitor, typeManager, httpClient, config);

        AgreementControllerImpl agreementController = new AgreementControllerImpl(monitor, config, catalogService);
        monitor.debug(String.format("Registering agreement controller %s", agreementController));
        webService.registerResource(CALLBACK_CONTEXT_ALIAS, agreementController);

        RdfStore rdfStore = new RdfStore(config, monitor);

        executorService = Executors.newScheduledThreadPool(config.getThreadPoolSize());
        synchronizer = new DataspaceSynchronizer(executorService, config, catalogService, rdfStore, monitor);

        SwitchingDataPlaneTokenValidatorController validatorController = new SwitchingDataPlaneTokenValidatorController(httpClient, config, monitor);
        if (validatorController.isEnabled()) {
            monitor.debug(String.format("Registering switching validator controller %s", validatorController));
            webService.registerResource(DEFAULT_CONTEXT_ALIAS, validatorController);
        }

        // EDC Remoting Support
        ServiceExecutorRegistry reg = new ServiceExecutorRegistry();
        reg.addBulkLink(new DataspaceServiceExecutor(monitor, agreementController, config, httpClient, executorService, typeManager));
        //reg.add(new DataspaceServiceExecutor(monitor,agreementController,config,httpClient));

        // Ontop and other deep nesting-afraid providers/optimizers
        // should be supported by not relying on the Fuseki syntax graph
        SparqlQuerySerializerFactory arqQuerySerializerFactory = new SparqlQuerySerializerFactory();
        SerializerRegistry.get().addQuerySerializer(Syntax.syntaxARQ, arqQuerySerializerFactory);
        SerializerRegistry.get().addQuerySerializer(Syntax.syntaxSPARQL_10, arqQuerySerializerFactory);
        SerializerRegistry.get().addQuerySerializer(Syntax.syntaxSPARQL_11, arqQuerySerializerFactory);

        // the actual sparql engine inside the EDC
        SparqlQueryProcessor processor = new SparqlQueryProcessor(reg, monitor, config, rdfStore, typeManager);

        // stored procedure store and transport endpoint
        SkillStore skillStore = new EdcSkillStore(catalogService, typeManager, config);
        DelegationServiceImpl delegationService = new DelegationServiceImpl(agreementController, monitor, httpClient, typeManager, config);
        
        // register endpoint /agent
        AgentController agentController = new AgentController(monitor, agreementController, config, processor, skillStore, delegationService);
        monitor.debug(String.format("Registering agent controller %s", agentController));
        webService.registerResource(DEFAULT_CONTEXT_ALIAS, agentController);
        
        // register endpoint /agentsource
        //AgentSourceController agentSourceController = new AgentSourceController(monitor, config, processor, skillStore, delegationService);
        //AgentSourceController agentSourceController = new AgentSourceController(monitor, config, processor, skillStore);
        //monitor.debug(String.format("Registering agent source controller %s", agentSourceController));
        //webService.registerResource(DEFAULT_CONTEXT_ALIAS, agentSourceController);

        monitor.debug(String.format("Initialized %s", name()));

        // mho HttpRequestFactory httpRequestFactory = new HttpRequestFactory();
        // mho AgentSourceFactory sourceFactory = new AgentSourceFactory(edcHttpClient, new AgentSourceRequestParamsSupplier(vault, typeManager, config, monitor), monitor, httpRequestFactory, processor, skillStore);
        // mho pipelineService.registerFactory(sourceFactory);
    }

    /**
     * start scheduled services
     */
    @Override
    public void start() {
        synchronizer.start();
    }

    /**
     * Signals the extension to release resources and shutdown.
     * stop any schedules services
     */
    @Override
    public void shutdown() {
        synchronizer.shutdown();
    }
}