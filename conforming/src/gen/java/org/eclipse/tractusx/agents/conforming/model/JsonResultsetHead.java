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
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonResultsetHead
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2023-03-23T11:28:11.277776230Z[GMT]")public class JsonResultsetHead   {
  @JsonProperty("vars")
  private List<String> vars = null;

  public JsonResultsetHead vars(List<String> vars) {
    this.vars = vars;
    return this;
  }

  public JsonResultsetHead addVarsItem(String varsItem) {
    if (this.vars == null) {
      this.vars = new ArrayList<String>();
    }
    this.vars.add(varsItem);
    return this;
  }

  /**
   * Get vars
   * @return vars
   **/
  @JsonProperty("vars")
  @Schema(description = "")
  public List<String> getVars() {
    return vars;
  }

  public void setVars(List<String> vars) {
    this.vars = vars;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonResultsetHead jsonResultsetHead = (JsonResultsetHead) o;
    return Objects.equals(this.vars, jsonResultsetHead.vars);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vars);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonResultsetHead {\n");
    
    sb.append("    vars: ").append(toIndentedString(vars)).append("\n");
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
