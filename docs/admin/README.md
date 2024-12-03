<!--
 * Copyright (c) 2022,2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0

-->

# Tractus-X Knowledge Agents Reference Implementation (KA-RI) Administration Guide

## Deployment

Deployment can be done
* via [JAR libraries](https://github.com/orgs/eclipse-tractusx/packages?repo_name=knowledge-agents&ecosystem=maven) copied into your Java runtime
* via [Docker images](https://hub.docker.com/r/tractusx) 
* via [Helm Charts (Stable Versions)](https://eclipse-tractusx.github.io/charts/stable) or [Helm Charts (Dev Versions)](https://eclipse-tractusx.github.io/charts/stable)

## Helm Chart for Conforming Agent

A helm chart for deploying the remoting agent can be found under [this folder](../../charts/remoting-agent).

It can be added to your umbrella chart.yaml by the following snippet

```console
dependencies:
  - name: conforming-agent
    repository: https://eclipse-tractusx.github.io/charts/dev
    version: 1.14.24-SNAPSHOT
    alias: my-conforming-agent
```

and then installed using

```console
helm dependency update
```

In your values.yml, you configure your specific instance of the conforming agent like this

```console
##############################################################################################
# Conforming Agent
##############################################################################################

my-conforming-agent:
  securityContext: *securityContext
  nameOverride: my-conforming-agent
  fullnameOverride: my-conforming-agent
  ingresses:
    - enabled: true
      # -- The hostname to be used to precisely map incoming traffic onto the underlying network service
      hostname: "my-conforming-agent.public-ip"
      annotations:
        nginx.ingress.kubernetes.io/rewrite-target: /$1
        nginx.ingress.kubernetes.io/use-regex: "true"
      # -- Agent endpoints exposed by this ingress resource
      endpoints:
        - default
      tls:
        enabled: true
        secretName: my-conforming-tls
```

## Helm Chart for Matchmaking Agent

A helm chart for deploying the matchmaking agent can be found under [this folder](../../charts/matchmaking-agent).

It can be added to your umbrella chart.yaml by the following snippet

```console
dependencies:
  - name: matchmaking-agent
    repository: https://eclipse-tractusx.github.io/charts/dev
    version: 1.14.24-SNAPSHOT
    alias: my-matchmmaking-agent
```

and then installed using

```console
helm dependency update
```

In your values.yml, you configure your specific instance of the matchmaking agent like this

```console
##############################################################################################
# Matchmaking Agent
##############################################################################################

my-matchmaking-agent:
  securityContext: *securityContext
  nameOverride: my-matchmaking-agent
  fullnameOverride: my-matchmaking-agent
  ingresses:
    - enabled: true
      # -- The hostname to be used to precisely map incoming traffic onto the underlying network service
      hostname: "my-matchmaking-agent.public-ip"
      annotations:
        nginx.ingress.kubernetes.io/rewrite-target: /$1
        nginx.ingress.kubernetes.io/use-regex: "true"
      # -- Agent endpoints exposed by this ingress resource
      endpoints:
        - default
      tls:
        enabled: true
        secretName: my-conforming-tls
```

## Helm Chart for Provisioning Agent

A helm chart for deploying the remoting agent can be found under [this folder](../../charts/provisioning-agent).

It can be added to your umbrella chart.yaml by the following snippet

```console
dependencies:
  - name: provisioning-agent
    repository: https://eclipse-tractusx.github.io/charts/dev
    version: 1.14.24-SNAPSHOT
    alias: my-provider-agent
```

and then installed using

```console
helm dependency update
```

In your values.yml, you configure your specific instance of the remoting agent like this

```console
#######################################################################################
# Data Binding Agent
#######################################################################################

my-provider-agent:
  securityContext: *securityContext
  nameOverride: my-provider-agent
  fullnameOverride: my-provider-agent
  resources:
    requests:
      cpu: 500m
      # you should employ 512Mi per endpoint
      memory: 1Gi
    limits:
      cpu: 500m
      # you should employ 512Mi per endpoint
      memory: 1Gi
  bindings:
    # disables the default sample binding
    dtc: null
    # real production mapping
    telematics2:
      port: 8081
      path: /t2/(.*)
      settings:
        jdbc.url: 'jdbcurl'
        jdbc.user: <path:vaultpath#username>
        jdbc.password: <path:vaultpath#password>
        jdbc.driver: 'org.postgresql.Driver'
      ontology: cx-ontology.xml
      mapping: |-
        [PrefixDeclaration]
        cx-common:          https://w3id.org/catenax/ontology/common#
        cx-core:            https://w3id.org/catenax/ontology/core#
        cx-vehicle:         https://w3id.org/catenax/ontology/vehicle#
        cx-reliability:     https://w3id.org/catenax/ontology/reliability#
        uuid:		            urn:uuid:
        bpnl:		            bpn:legal:
        owl:		            http://www.w3.org/2002/07/owl#
        rdf:		            http://www.w3.org/1999/02/22-rdf-syntax-ns#
        xml:		            http://www.w3.org/XML/1998/namespace
        xsd:		            http://www.w3.org/2001/XMLSchema#
        json:               https://json-schema.org/draft/2020-12/schema#
        obda:		            https://w3id.org/obda/vocabulary#
        rdfs:		            http://www.w3.org/2000/01/rdf-schema#
        oem:                urn:oem:

        [MappingDeclaration] @collection [[
        mappingId	vehicles
        target		<{vehicle_id}> rdf:type cx-vehicle:Vehicle ; cx-vehicle:vehicleIdentificationNumber {van}^^xsd:string; cx-vehicle:worldManufaturerId bpnl:{localIdentifiers_manufacturerId}; cx-vehicle:productionDate {production_date}^^xsd:date.
        source		SELECT vehicle_id, van, 'BPNL0000000DUMMY' as localIdentifiers_manufacturerId, production_date FROM vehicles

        mappingId	partsvehicle
        target		<{gearbox_id}> cx-vehicle:isPartOf <{vehicle_id}> .
        source		SELECT vehicle_id, gearbox_id FROM vehicles

        mappingId	vehicleparts
        target		<{vehicle_id}> cx-vehicle:hasPart <{gearbox_id}> .
        source		SELECT vehicle_id, gearbox_id FROM vehicles

        ]]
  ingresses:
    - enabled: true
      # -- The hostname to be used to precisely map incoming traffic onto the underlying network service
      hostname: "my-provider-agent.public.ip"
      annotations:
        nginx.ingress.kubernetes.io/rewrite-target: /$1
        nginx.ingress.kubernetes.io/use-regex: "true"
      # -- Agent endpoints exposed by this ingress resource
      endpoints:
        - telematics2
      tls:
        enabled: true
        secretName: my-provider-tls
```

