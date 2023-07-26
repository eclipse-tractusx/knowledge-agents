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

# Tractus-X Matchmaking and Inference Agent (Upcoming)

This is a folder providing a FOSS implementations of a Matchmaking Agent.

The Matchmaking Agent is needed by any Agent-Enabled dataspace participant. 

The Matchmaking Agent is able to interact with and reason about the Dataspace/EDC (and hence: other Matchmaking Agents) 
using a Federated Data Catalogue. 

It is also able to delegate work on sub-graphs/asset to tenant-internal agents (Binding Agents) providing actual data and functions. 

For Agent-Enabled dataspace consumers, deploying the Matchmaking Agent alongside an Agent-Enabled EDC is sufficient.

Currently, the Matchmaking Agent (based on Apache Jena Fuseki) is baked into the [EDC data plane (Agent Plane)](https://github.com/eclipse-tractusx/knowledge-agents-edc/tree/main/agent-plane/agent-plane-protocol/src/main/java/org/eclipse/tractusx/agents/edc/sparql). 

This will be refactored in the near future.

## What is Matchmaking

A query/computation engine is federating if parts of the query maybe (recursively) dumped off to separate query/computation engines collocated in the same environment/dataspace.

Usually, there will be an explicit construct in the query language which determines (or even: infers) the required computation target. This is useful when the targets of the computations are unique in the sense that they can be listed/lookedup in advance.

But there maybe cases in which 
* the query designed has no full overview over the dataspace
* the dataspace is rapidly changing
* there are several computation engines providing similar assets, but with a slightly differently profile (contract conditions, intersecting content, performance, ...)
* the integration (union, join) of individual federated computations is costly by itself.

In these cases, so-called Matchmaking agents come into play. They introduce several measures to improve the behaviour of more complex dataspaces:
* Federation decisions in the query (hence a so-called query rewrite) will be made using advanced meta-data and optimization algorithms. This means that the user of such a matchmaking agent will be able to pose a technically simple (but not necessarily business-wise) query which is then elaborated by the dataspace in an optimal fashion.
* the matchmaking agent comes with its additional computing power.
* the matchmaking agent may negotiate with the most common and critical provider agents to migrate a part of their data/functions to the matchmaking agent in order to be able to do most optimal data integration strategies (local union/join, predicate-push, ...)






