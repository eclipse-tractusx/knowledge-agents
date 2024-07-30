<!--
 * Copyright (c) 2022,2024 Contributors to the Eclipse Foundation
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


# Changelog

All notable changes to this product will be documented in this file.

# Released

## [1.13.22] - 2024-07-29

### Added

### Changed

- Matchmaking Agent: Compliant to the TX EDC 0.7.3 management api
- Matchmaking Agent: Add connector allowance patterns to chart
- Matchmaking Agent: Ugrade to jetty 10.0.16
- Remoting Agent: Upgrade to tomcat 9.0.90 and zookeeper 8.1.4. Remove vulnerable solr support.

## [1.12.19] - 2024-05-17

### Added

- Matchmaking Agent: Carve-Out of the SPARQL-Processor from [Tractus-X EDC Agent Plane (KA-EDC)](http://github.com/eclipse-tractusx/knowledge-agents-edc) in order to hide graph data in the internal network.
- Matchmaking Agent: Implements a new API extension to upload/delete graph assets by ttl and csv.

### Changed

- Remoting Agent: Deal with mapping of taxonomical constants to API elements. Support for JSON-serialization of partial results.
- Upgraded to the latest possible version of dependent libraries

### Removed

## [1.11.16] - 2024-02-20

### Added

### Changed

- Remoting Agent upgraded to latest RDF4J distribution
- Provisioning Agent upgrade to latest Ontop release
- Upgraded to the latest possible version of dependent libraries

### Removed

## [1.10.15] - 2023-11-28

### Added

- Provisioning Agent (chart): Enable Integrated H2 Database Sample by correct user und group ids

### Changed

- Provisioning Agent: Upgrade to Ontop 5.1.0
- Codestyle consistent with Tractus-X using checkstyle
- Remoting Agent: Debug the test/sample repository descriptions

### Removed

- Cyclone DX Boms (we have dash)

## [1.9.8] - 2023-09-04

### Added

- Remoting Agent: Asynchronous API calls

### Changed

- Adapted all Catena-X namespaces to https://w3id.org/catenax
- Upgraded to best possible unvulnerable dependencies
- Eclipse Tractus-X standards and migration

### Removed

# Unreleased

## [0.8.6] - 2023-05-19

### Added

- Conforming Agent: Implementation
- Support for SPARQL KA-transfer profile including the cx_warnings header

### Changed

### Removed

## [0.7.4] - 2023-02-20

### Added

- Necessary documentation markdown for Eclipse Standard
- Helm Sub-Charts for Umbrella Embedding
- Postman Collection with Integration Tests
- SparQL Anything, PostgreSQL support

### Changed

### Removed

## [0.6.4] - 2022-12-15

### Added

-

### Changed

- Include depending artifacts via Maven/Docker
- Remoting Agent Batch Mode

### Removed

- 

## [0.5.5] - 2022-08-10

### Added

- Added 2 Agent Implementations (Ontop, RDF4J)

### Changed

- Include depending artifacts via Maven/Docker

### Removed

- Tractus-X and Jena Links
- Spike Data

## [0.4.6] - 2022-05-13

### Added

- Submodules to Apache Jena and Tractus-X
- Source Code and Data Samples for Three tenants
- Postman Collection with Spike Logic

### Changed

### Removed
