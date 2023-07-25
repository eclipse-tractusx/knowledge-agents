# Dependencies of Tractus-X Knowledge Agents Reference Implementations (KA-RI)

We provide an [Eclipse Dash License File](DEPENDENCIES) for each release.

You may recreate an up-to-date DEPENDENCIES file by invoking

```shell
./mvnw org.eclipse.dash:license-tool-plugin:license-check -Ddash.summary=DEPENDENCIES
```
We provide Software-Bill-Of-Material (SBOM) documents for each KA module for each release:
* [Conforming Agent](conforming/conforming-agent-1.9.5-SNAPSHOT-sbom.json)
* [Provisioning Agent](provisioning/provisioning-agent-1.9.5-SNAPSHOT-sbom.json)
* [Remoting Agent](remoting/remoting-agent-1.9.5-SNAPSHOT-sbom.json)

You may recreate up-to-date SBOMs by invoking

```shell
./mvnw package -DskipTests
```

Afterwards, you find the current documents under:
* [Conforming Agent](conforming/target/conforming-agent-1.9.5-SNAPSHOT-sbom.json)
* [Provisioning Agent](provisioning/target/provisioning-agent-1.9.5-SNAPSHOT-sbom.json)
* [Remoting Agent](remoting/target/remoting-agent-1.9.5-SNAPSHOT-sbom.json)

The KA-RI build and runtime platform is relying on:
* [Java Runtime Environment (JRE >=11 - license depends on chosen provider)](https://de.wikipedia.org/wiki/Java-Laufzeitumgebung)
* [Java Development Kit (JDK >=11 - license depends on chosen provider)](https://de.wikipedia.org/wiki/Java_Development_Kit) 
* [Apache Maven >=3.8 (Apache License 2.0)](https://maven.apache.org) 
* [Eclipse Dash (Eclipse Public License 2.0)](https://github.com/eclipse/dash-licenses)
* [CycloneDX 1.4 (Apache License 2.0)](https://github.com/CycloneDX)
* [Docker Engine >= 20.10.17 (Apache License 2.0)]() 
* [Helm (Apache License 2.0)](https://helm.sh/) 
