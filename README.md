<!--
 * Copyright (C) 2022-2023 Catena-X Association and others. 
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Apache License 2.0 which is available at
 * http://www.apache.org/licenses/.
 * 
 * SPDX-FileType: DOCUMENTATION
 * SPDX-FileCopyrightText: 2022-2023 Catena-X Association
 * SPDX-License-Identifier: Apache-2.0
-->

# Tractus-X Knowledge Agents Reference Implementations (KA)

![GitHub contributors](https://img.shields.io/github/contributors/eclipse-tractusx/knowledge-agents)
![GitHub Org's stars](https://img.shields.io/github/stars/catenax-ng)
![GitHub](https://img.shields.io/github/license/eclipse-tractusx/knowledge-agents)
![GitHub all releases](https://img.shields.io/github/downloads/eclipse-tractusx/knowledge-agents/total)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=eclipse-tractusx_knowledge-agents&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=eclipse-tractusx_knowledge-agents)

Tractus-X Knowledge Agents Reference Implementations (KA) is a product of the [Catena-X Knowledge Agents Kit](https://catenax-ng.github.io/product-knowledge) implementing the "binding" modules of the CX-0084 standard (Federated Queries in Dataspaces).

* See the [copyright notice](COPYRIGHT.md)
* See the [authors file](AUTHORS.md)
* See the [license file](LICENSE.md)
* See the [code of conduct](CODE_OF_CONDUCT.md)
* See the [contribution guidelines](CONTRIBUTING.md)
* See the [dependencies](DEPENDENCIES.md)

## About the Project

This repository provides FOSS implementations for so-called Agents. 

An "Agent" is a (re- as well as pro-active) component which understands, partially elaborates and possibly delegates declarative scripts or queries (the so-called "Skills") over individual data and service assets ("Knowledge Graphs") of the dataspace.

Agents speak [Semantic Web Based](https://www.w3.org/2001/sw/wiki/Main_Page) dataspace protocols (such as [SPARQL](https://www.w3.org/2001/sw/wiki/SPARQL)) which are negotiated and 
transferred by the [Tractus-X Knowledge Agent Extensions for the Eclipse Dataspace Components (KA-EDC)](https://github.com/eclipse-tractusx/knowledge-agents-edc).
Binding Agents translate these protocols to your backend data storage or API.

We provide several of agent implementations in this product. 

- [Matchmaking Agent (KA-MATCH upcoming)](matchmaking) An agent implementation is needed by any Agent-Enabled dataspace participant to reason about the Dataspace/EDC (and hence: other Matchmaking Agents). It is also able to delegate work on sub-graphs/asset to tenant-internal agents (Binding Agents) providing actual data and functions. For Agent-Enabled dataspace consumers, deploying the Matchmaking Agent alongside an Agent-Enabled EDC is sufficient.
- Binding Agents are needed by any Agent-Enabled dataspace provider in order to link the Dataspace/Protocol layer to the actual Data and Functional Resources. Binding (sometimes: Bridging) is a technology which rather translates between protocols rather than translating data (Mapping).
  - [Provisioning Agent (KA-PROV)](provisioning) An agent implementation which binds typical SQL-based backend data sources to SPARQL.
  - [Remoting Agent (KA-RMT)](remoting) An agent implementation which binds typical REST services to SPARQL.
- [Conforming Agent (KA-CONF)](conforming) An agent implementation which may play any role (Matchmaking Agent, Binding Agent, EDC Transfer) in order to test the conformity of all other parts of the architecture/standard.

Included in this repository are ready-made [Helm charts](charts). 
They can be installed from the [Catena-X Knowledge Agents Kit Helm Repository](https://docs.catenax-ng.github.io/product-knowledge/infrastructure).

## Getting Started

### Build

To compile, package and containerize the binary artifacts (includes running the unit tests)

```shell
./mvnw package -Pwith-docker-image
```

To publish the binary artifacts (environment variables GITHUB_ACTOR and GITHUB_TOKEN must be set)

```shell
./mvnw -s settings.xml publish
```

### Deployment

Deployment can be done
* via [JAR libraries](https://github.com/orgs/eclipse-tractusx/packages?repo_name=knowledge-agents&ecosystem=maven) copied into your Java runtime
* via [Docker images](https://github.com/orgs/eclipse-tractusx/packages?repo_name=knowledge-agents&ecosystem=docker) 
* vial [Helm Charts](https://catenax-ng.github.io/product-knowledge/infrastructure/index.yaml)

* See the individual agent documentations
  * [Provisioning Agent](provisioning/README.md)
  * [Remoting Agent](remoting/README.md)
  * [Conforming Agent](conforming/README.md)



