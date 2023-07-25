//
// Copyright (C) 2022-2023 Catena-X Association and others. 
// 
// This program and the accompanying materials are made available under the
// terms of the Apache License 2.0 which is available at
// http://www.apache.org/licenses/.
//  
// SPDX-FileType: SOURCE
// SPDX-FileCopyrightText: 2022-2023 Catena-X Association
// SPDX-License-Identifier: Apache-2.0
//
package org.eclipse.tractusx.agents.conforming.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.ArrayList;

/**
 * XmlResultsetResultsResult
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2023-03-23T11:28:11.277776230Z[GMT]")public class XmlResultsetResultsResult   {
  @JsonProperty("binding")
  private List<XmlResultsetResultsResultBinding> bindings = new ArrayList<>();

  public XmlResultsetResultsResult addBinding(XmlResultsetResultsResultBinding binding) {
    this.bindings.add(binding);
    return this;
  }

  /**
   * Get binding
   * @return binding
   **/
  public List<XmlResultsetResultsResultBinding> getBindings() {
    return bindings;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XmlResultsetResultsResult xmlResultsetResultsResult = (XmlResultsetResultsResult) o;
    return Objects.equals(this.bindings, xmlResultsetResultsResult.bindings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bindings);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class XmlResultsetResultsResult {\n");
    
    sb.append("    binding: ").append(toIndentedString(bindings)).append("\n");
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
