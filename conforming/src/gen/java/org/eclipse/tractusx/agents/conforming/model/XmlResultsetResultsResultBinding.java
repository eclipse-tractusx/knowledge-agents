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
package org.eclipse.tractusx.agents.conforming.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * XmlResultsetResultsResultBinding
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2023-03-23T11:28:11.277776230Z[GMT]")
public class XmlResultsetResultsResultBinding {
    @JsonProperty("name")
    private String name = null;

    @JsonProperty("literal")
    private XmlResultsetResultsResultBindingLiteral literal = null;

    @JsonProperty("uri")
    private String uri = null;

    public XmlResultsetResultsResultBinding name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    @JsonProperty("name")
    @Schema(description = "")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XmlResultsetResultsResultBinding literal(XmlResultsetResultsResultBindingLiteral literal) {
        this.literal = literal;
        return this;
    }

    /**
     * Get literal
     *
     * @return literal
     **/
    @JsonProperty("literal")
    @Schema(description = "")
    public XmlResultsetResultsResultBindingLiteral getLiteral() {
        return literal;
    }

    public void setLiteral(XmlResultsetResultsResultBindingLiteral literal) {
        this.literal = literal;
    }

    public XmlResultsetResultsResultBinding uri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Get uri
     *
     * @return uri
     **/
    @JsonProperty("uri")
    @Schema(description = "")
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, literal, uri);
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        XmlResultsetResultsResultBinding xmlResultsetResultsResultBinding = (XmlResultsetResultsResultBinding) o;
        return Objects.equals(this.name, xmlResultsetResultsResultBinding.name) &&
                Objects.equals(this.literal, xmlResultsetResultsResultBinding.literal) &&
                Objects.equals(this.uri, xmlResultsetResultsResultBinding.uri);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class XmlResultsetResultsResultBinding {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    literal: ").append(toIndentedString(literal)).append("\n");
        sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
