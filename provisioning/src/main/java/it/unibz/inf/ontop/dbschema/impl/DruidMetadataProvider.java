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
package it.unibz.inf.ontop.dbschema.impl;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import it.unibz.inf.ontop.dbschema.RelationID;
import it.unibz.inf.ontop.exception.MetadataExtractionException;
import it.unibz.inf.ontop.injection.CoreSingletons;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ontop MetadataProvider which adapts the
 * JDBC meta-data provided by the Avatica/Calcite
 * driver such that schema/tablenames are fitting.
 * Activate by adding
 * org.apache.calcite.avatica.remote.Driver-metadataProvider = it.unibz.inf.ontop.dbschema.impl.DruidMetadataProvider
 * org.apache.calcite.avatica.remote.Driver-typeFactory = it.unibz.inf.ontop.model.type.impl.DefaultSQLDBTypeFactory
 * org.apache.calcite.avatica.remote.Driver-symbolFactory = it.unibz.inf.ontop.model.term.functionsymbol.db.impl.DefaultSQLDBFunctionSymbolFactory
 */
public class DruidMetadataProvider extends DefaultDBMetadataProvider {

    @AssistedInject
    public DruidMetadataProvider(@Assisted Connection connection, CoreSingletons coreSingletons) throws MetadataExtractionException {
        super(connection, coreSingletons);
    }

    /**
     * we do not use schema and catalogue names in the OBDA defitions
     *
     * @param rs resultset
     * @param catalogNameColumn catalog - will be named "druid"
     * @param schemaNameColumn schema - will be named "druid"
     * @param tableNameColumn table - only component that is relevant
     * @return final name
     * @throws SQLException in case something happens
     */
    @Override
    protected RelationID getRelationID(ResultSet rs, String catalogNameColumn, String schemaNameColumn, String tableNameColumn) throws SQLException {
        return rawIdFactory.createRelationID(rs.getString(tableNameColumn));
    }

}
