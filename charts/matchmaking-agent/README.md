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
# matchmaking-agent

![Version: 1.12.19](https://img.shields.io/badge/Version-1.12.19--SNAPSHOT-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 1.12.19](https://img.shields.io/badge/AppVersion-1.12.19--SNAPSHOT-informational?style=flat-square)

A Helm chart for the Tractus-X Matchmaking Agent which is a container encompassing data storage capabilities accessible from the dataplane by a REST API

This chart has no prerequisites.

**Homepage:** <https://github.com/eclipse-tractusx/knowledge-agents/>

## TL;DR
```shell
$ helm repo add eclipse-tractusx https://eclipse-tractusx.github.io/charts/dev
$ helm install my-release eclipse-tractusx/matchmaking-agent --version 1.12.19
```

## Maintainers

| Name | Email | Url |
| ---- | ------ | --- |
| Tractus-X Knowledge Agents Team |  |  |

## Source Code

* <https://github.com/eclipse-tractusx/knowledge-agents/tree/main/matchmaking>

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| affinity | object | `{}` | [Affinity](https://kubernetes.io/docs/concepts/scheduling-eviction/assign-pod-node/#affinity-and-anti-affinity) constrains which nodes the Pod can be scheduled on based on node labels. |
| agent.connectors | object | `{}` | A map of partner ids to remote connector IDS URLs to synchronize with |
| agent.default[0] | string | `"dataspace.ttl"` |  |
| agent.default[1] | string | `"https://w3id.org/catenax/ontology.ttl"` |  |
| agent.endpoints.callback.auth | object | `{}` | An auth object for default security |
| agent.endpoints.callback.path | string | `"/callback"` | The path mapping the "callback" api is going to be exposed by |
| agent.endpoints.callback.port | string | `"8087"` | The network port, which the "callback" api is going to be exposed by the container, pod and service |
| agent.endpoints.callback.regex | string | `"/(.*)"` | An optional regex path match (whose match groups could be used in an nginx-annotation of the ingress) |
| agent.endpoints.default.auth | object | `{}` | An auth object for default security |
| agent.endpoints.default.path | string | `"/api"` | The path mapping the "default" api is going to be exposed by |
| agent.endpoints.default.port | string | `"8082"` | The network port, which the "default" api is going to be exposed by the container, pod and service |
| agent.endpoints.default.regex | string | `"/(.*)"` | An optional regex path match (whose match groups could be used in an nginx-annotation of the ingress) |
| agent.endpoints.internal.auth | object | `{}` | An auth object for default security |
| agent.endpoints.internal.path | string | `"/agentsource"` | The path mapping the "source" api is going to be exposed by |
| agent.endpoints.internal.port | string | `"8080"` | The network port, which the "source" api is going to be exposed by the container, pod and service |
| agent.endpoints.internal.regex | string | `"/(.*)"` | An optional regex path match (whose match groups could be used in an nginx-annotation of the ingress) |
| agent.graphcontract | string | `"Contract?partner=Graph"` | Names the visible contract under which new graphs are published (if not otherwise specified) |
| agent.maxbatchsize | string | `"9223372036854775807"` | Sets the maximal batch size when delegating to agents and services |
| agent.services | object | `{"allow":"(edcs?://.*)|(https://query\\\\.wikidata\\\\.org/sparql)","asset":{"allow":"(edcs?://.*)","deny":"https?://.*"},"deny":"http://.*"}` | A set of configs for regulating outgoing service calls |
| agent.services.allow | string | `"(edcs?://.*)|(https://query\\\\.wikidata\\\\.org/sparql)"` | A regular expression which outgoing service URLs must match (unless overwritten by a specific asset property) |
| agent.services.asset | object | `{"allow":"(edcs?://.*)","deny":"https?://.*"}` | A set of configs for regulating outgoing service calls when providing an asset (when no specific asset property is given) |
| agent.services.asset.allow | string | `"(edcs?://.*)"` | A regular expression which outgoing service URLs must match (unless overwritten by a specific asset property) |
| agent.services.asset.deny | string | `"https?://.*"` | A regular expression which outgoing service URLs must not match (unless overwritten by a specific asset property) |
| agent.services.deny | string | `"http://.*"` | A regular expression which outgoing service URLs must not match (unless overwritten by a specific asset property) |
| agent.skillcontract | string | `"Contract?partner=Skill"` | Names the visible contract under which new skills are published (if not otherwise specified) |
| agent.synchronization | int | `-1` | The synchronization interval in ms to update the federated data catalogue |
| automountServiceAccountToken | bool | `false` | Whether to [automount kubernetes API credentials](https://kubernetes.io/docs/tasks/configure-pod-container/configure-service-account/#use-the-default-service-account-to-access-the-api-server) into the pod |
| autoscaling.enabled | bool | `false` | Enables [horizontal pod autoscaling](https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale/https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale/) |
| autoscaling.maxReplicas | int | `100` | Maximum replicas if resource consumption exceeds resource threshholds |
| autoscaling.minReplicas | int | `1` | Minimal replicas if resource consumption falls below resource threshholds |
| autoscaling.targetCPUUtilizationPercentage | int | `80` | targetAverageUtilization of cpu provided to a pod |
| autoscaling.targetMemoryUtilizationPercentage | int | `80` | targetAverageUtilization of memory provided to a pod |
| configs | object | `{"dataspace.ttl":"#################################################################\n# Catena-X Agent Bootstrap Graph in TTL/RDF/OWL FORMAT\n#################################################################\n@prefix : <GraphAsset?local=Dataspace> .\n@base <GraphAsset?local=Dataspace> .\n"}` | A set of additional configuration files |
| configs."dataspace.ttl" | string | `"#################################################################\n# Catena-X Agent Bootstrap Graph in TTL/RDF/OWL FORMAT\n#################################################################\n@prefix : <GraphAsset?local=Dataspace> .\n@base <GraphAsset?local=Dataspace> .\n"` | An example of an empty graph in ttl syntax |
| connector | string | `""` | Name of the connector deployment |
| controlplane | object | `{"endpoints":{"control":{"path":"/control","port":8083},"management":{"authKey":"","path":"/management","port":8081},"protocol":{"path":"/api/v1/dsp","port":8084}},"ingresses":[{"enabled":false}]}` | References to the control plane deployment |
| controlplane.endpoints.control | object | `{"path":"/control","port":8083}` | control api, used for internal control calls. can be added to the internal ingress, but should probably not |
| controlplane.endpoints.control.path | string | `"/control"` | path for incoming api calls |
| controlplane.endpoints.control.port | int | `8083` | port for incoming api calls |
| controlplane.endpoints.management | object | `{"authKey":"","path":"/management","port":8081}` | data management api, used by internal users, can be added to an ingress and must not be internet facing |
| controlplane.endpoints.management.authKey | string | `""` | authentication key, must be attached to each 'X-Api-Key' request header |
| controlplane.endpoints.management.path | string | `"/management"` | path for incoming api calls |
| controlplane.endpoints.management.port | int | `8081` | port for incoming api calls |
| controlplane.endpoints.protocol | object | `{"path":"/api/v1/dsp","port":8084}` | dsp api, used for inter connector communication and must be internet facing |
| controlplane.endpoints.protocol.path | string | `"/api/v1/dsp"` | path for incoming api calls |
| controlplane.endpoints.protocol.port | int | `8084` | port for incoming api calls |
| customLabels | object | `{}` | Additional custom Labels to add |
| env | object | `{}` | Container environment variables e.g. for configuring [JAVA_TOOL_OPTIONS](https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/envvars002.html) Ex.:   JAVA_TOOL_OPTIONS: >     -Dhttp.proxyHost=proxy -Dhttp.proxyPort=80 -Dhttp.nonProxyHosts="localhost|127.*|[::1]" -Dhttps.proxyHost=proxy -Dhttps.proxyPort=443 |
| envSecretName | string | `nil` | [Kubernetes Secret Resource](https://kubernetes.io/docs/concepts/configuration/secret/) name to load environment variables from |
| fullnameOverride | string | `""` | Overrides the releases full name |
| image.digest | string | `""` | Overrides the image digest |
| image.pullPolicy | string | `"IfNotPresent"` |  |
| image.pullSecrets | list | `[]` |  |
| image.registry | string | `"docker.io/"` | target registry |
| image.repository | string | `"tractusx/matchmaking-agent"` | Which derivate of agent to use |
| image.tag | string | `""` | Overrides the image tag whose default is the chart appVersion |
| ingresses[0].annotations | string | `nil` | Additional ingress annotations to add, for example when implementing more complex routings you may set { nginx.ingress.kubernetes.io/rewrite-target: /$1, nginx.ingress.kubernetes.io/use-regex: "true" } |
| ingresses[0].certManager.clusterIssuer | string | `""` | If preset enables certificate generation via cert-manager cluster-wide issuer |
| ingresses[0].certManager.issuer | string | `""` | If preset enables certificate generation via cert-manager namespace scoped issuer |
| ingresses[0].className | string | `""` | Defines the [ingress class](https://kubernetes.io/docs/concepts/services-networking/ingress/#ingress-class)  to use |
| ingresses[0].enabled | bool | `false` |  |
| ingresses[0].endpoints | list | `["default","source","callback"]` | Agent endpoints exposed by this ingress resource |
| ingresses[0].hostname | string | `"matchmaking-agent.local"` | The hostname to be used to precisely map incoming traffic onto the underlying network service |
| ingresses[0].prefix | string | `""` | Optional prefix that will be prepended to the paths of the endpoints |
| ingresses[0].tls | object | `{"enabled":false,"secretName":""}` | TLS [tls class](https://kubernetes.io/docs/concepts/services-networking/ingress/#tls) applied to the ingress resource |
| ingresses[0].tls.enabled | bool | `false` | Enables TLS on the ingress resource |
| ingresses[0].tls.secretName | string | `""` | If present overwrites the default secret name |
| livenessProbe.enabled | bool | `true` | Whether to enable kubernetes [liveness-probe](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/) |
| livenessProbe.failureThreshold | int | `3` | Minimum consecutive failures for the probe to be considered failed after having succeeded |
| livenessProbe.periodSeconds | int | `60` | Number of seconds each period lasts. |
| livenessProbe.timeoutSeconds | int | `5` | number of seconds until a timeout is assumed |
| logging.configuration | string | `"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<configuration debug=\"false\" scan=\"true\" scanPeriod=\"30 seconds\">\n  <appender name=\"STDOUT\" class=\"ch.qos.logback.core.ConsoleAppender\">\n    <encoder>\n      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>\n    </encoder>\n  </appender>\n  <root>\n    <level value=\"FINEST\"/>\n    <appender-ref ref=\"STDOUT\" />\n  </root>\n</configuration>"` | Logback Xml |
| nameOverride | string | `""` | Overrides the charts name |
| nodeSelector | object | `{}` | [Node-Selector](https://kubernetes.io/docs/concepts/scheduling-eviction/assign-pod-node/#nodeselector) to constrain the Pod to nodes with specific labels. |
| participant.id | string | `""` | BPN Number |
| podAnnotations | object | `{}` | [Annotations](https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/) added to deployed [pods](https://kubernetes.io/docs/concepts/workloads/pods/) |
| podSecurityContext.fsGroup | int | `30000` | The owner for volumes and any files created within volumes will belong to this guid |
| podSecurityContext.runAsGroup | int | `30000` | Processes within a pod will belong to this guid |
| podSecurityContext.runAsUser | int | `10001` | Runs all processes within a pod with a special uid |
| podSecurityContext.seccompProfile.type | string | `"RuntimeDefault"` | Restrict a Container's Syscalls with seccomp |
| readinessProbe.enabled | bool | `true` | Whether to enable kubernetes readiness-probes |
| readinessProbe.failureThreshold | int | `3` | Minimum consecutive failures for the probe to be considered failed after having succeeded |
| readinessProbe.periodSeconds | int | `300` | Number of seconds each period lasts. |
| readinessProbe.timeoutSeconds | int | `5` | number of seconds until a timeout is assumed |
| replicaCount | int | `1` | Specifies how many replicas of a deployed pod shall be created during the deployment Note: If horizontal pod autoscaling is enabled this setting has no effect |
| resources | object | `{"limits":{"cpu":"250m","memory":"768Mi"},"requests":{"cpu":"250m","memory":"768Mi"}}` | [Resource management](https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/) applied to the deployed pod We recommend 25% of a cpu, 512MB per server and 256MB per endpoint |
| securityContext.allowPrivilegeEscalation | bool | `false` | Controls [Privilege Escalation](https://kubernetes.io/docs/concepts/security/pod-security-policy/#privilege-escalation) enabling setuid binaries changing the effective user ID |
| securityContext.capabilities.add | list | `["NET_BIND_SERVICE"]` | Specifies which capabilities to add to issue specialized syscalls |
| securityContext.capabilities.drop | list | `["ALL"]` | Specifies which capabilities to drop to reduce syscall attack surface |
| securityContext.readOnlyRootFilesystem | bool | `true` | Whether the root filesystem is mounted in read-only mode |
| securityContext.runAsGroup | int | `30000` | Processes within a pod will belong to this guid |
| securityContext.runAsNonRoot | bool | `true` | Requires the container to run without root privileges |
| securityContext.runAsUser | int | `10001` | The container's process will run with the specified uid |
| service.type | string | `"ClusterIP"` | [Service type](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services-service-types) to expose the running application on a set of Pods as a network service. |
| serviceAccount.annotations | object | `{}` | [Annotations](https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/) to add to the service account |
| serviceAccount.create | bool | `true` | Specifies whether a [service account](https://kubernetes.io/docs/tasks/configure-pod-container/configure-service-account/) should be created per release |
| serviceAccount.name | string | `""` | The name of the service account to use. If not set and create is true, a name is generated using the release's fullname template |
| startupProbe.enabled | bool | `true` | Whether to enable kubernetes startup-probes |
| startupProbe.failureThreshold | int | `18` | Minimum consecutive failures for the probe to be considered failed after having succeeded |
| startupProbe.initialDelaySeconds | int | `60` | Number of seconds after the container has started before liveness probes are initiated. |
| startupProbe.periodSeconds | int | `30` | Number of seconds each period lasts. |
| startupProbe.timeoutSeconds | int | `5` | number of seconds until a timeout is assumed |
| tolerations | list | `[]` | [Tolerations](https://kubernetes.io/docs/concepts/scheduling-eviction/taint-and-toleration/) are applied to Pods to schedule onto nodes with matching taints. |

----------------------------------------------
Autogenerated from chart metadata using [helm-docs v1.11.2](https://github.com/norwoodj/helm-docs/releases/v1.11.2)
