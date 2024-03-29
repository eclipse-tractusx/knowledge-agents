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
package org.eclipse.tractusx.agents.remoting;

import org.eclipse.rdf4j.common.iteration.AbstractCloseableIteration;
import org.eclipse.rdf4j.common.iteration.CloseableIteration;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.Dataset;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.algebra.TupleExpr;
import org.eclipse.rdf4j.query.impl.ListBindingSet;
import org.eclipse.rdf4j.sail.SailException;
import org.eclipse.rdf4j.sail.helpers.AbstractSailConnection;
import org.eclipse.tractusx.agents.remoting.util.BatchKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implements a connection to a remote service
 * This is the main magic for hiding functions as graphs.
 */
public class RemotingSailConnection extends AbstractSailConnection {
    /**
     * logger
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * namespaces map
     */
    protected Map<String, String> namespaces = new HashMap<>();
    /**
     * link to the sail / service wrapper
     */
    protected RemotingSail remotingSail;

    /**
     * creates a new connection
     *
     * @param remotingSail the remoting sail (just a wrapper around the config, since it keeps no state)
     */
    public RemotingSailConnection(RemotingSail remotingSail) {
        super(remotingSail);
        this.remotingSail = remotingSail;
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Creating a new connection to the remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
    }


    @Override
    public String toString() {
        return super.toString() + "/connection";
    }

    /**
     * clear namespaces
     */
    @Override
    protected void clearNamespacesInternal() {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Clearing connection namespaces to the remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
        namespaces.clear();
    }

    /**
     * unset namespace
     */
    @Override
    protected void removeNamespaceInternal(String prefix) {
        String oldValue = namespaces.remove(prefix);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Removed namespace prefix %s (previous value %s) to the remoting sail %s wrapping config %s", prefix, oldValue, remotingSail, remotingSail.config));
        }
    }

    /**
     * set namespace
     */
    @Override
    protected void setNamespaceInternal(String prefix, String value) {
        String oldValue = namespaces.put(prefix, value);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Set namespace prefix %s to value %s (previous value was %s) to the remoting sail %s wrapping config %s", prefix, value, oldValue, remotingSail, remotingSail.config));
        }
    }

    /**
     * resolve namespace
     */
    @Override
    protected String getNamespaceInternal(String prefix) {
        String value = namespaces.get(prefix);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Resolve namespace prefix %s to value %s to the remoting sail %s wrapping config %s", prefix, value, remotingSail, remotingSail.config));
        }
        return value;
    }

    /**
     * return namespace list
     */
    @Override
    protected CloseableIteration<? extends Namespace, SailException> getNamespacesInternal() {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Iterate namespaces to the remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
        return new AbstractCloseableIteration<>() {
            protected final java.util.Iterator<java.util.Map.Entry<String, String>> iterator = namespaces.entrySet().iterator();

            @Override
            public void remove() {
                iterator.remove();
            }

            @Override
            public Namespace next() {
                java.util.Map.Entry<String, String> nextEntry = iterator.next();
                return new SimpleNamespace(nextEntry.getKey(), nextEntry.getValue());
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
        };
    }

    /**
     * close the connection
     */
    @Override
    protected void closeInternal() throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Closing connection to the remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
    }

    /**
     * is that the actual query part?
     */
    @Override
    protected CloseableIteration<? extends BindingSet, QueryEvaluationException> evaluateInternal(
            TupleExpr tupleExpr, Dataset dataset, BindingSet bindings, boolean includeInferred) throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Evaluating tuples %s on dataset %s and bindings %s (including inferred %b) " +
                    "connection to the remoting sail %s wrapping config %s", tupleExpr, dataset, bindings, includeInferred, remotingSail, remotingSail.config));
        }
        QueryExecutor visitor = new QueryExecutor(this);
        tupleExpr.visit(visitor);
        final Set<BatchKey<Value>> distincts = new HashSet<>();
        visitor.bindings.forEach(binding -> {
            BatchKey<Value> key = new BatchKey<>(visitor.outputVariables.values().stream().map(binding::getValue).toArray(Value[]::new));
            distincts.add(key);
        });
        List<String> keys = new ArrayList<String>(visitor.outputVariables.keySet());
        final Iterator<ListBindingSet> allBindings = distincts.stream().map(key -> new ListBindingSet(keys, key.getComponents())).iterator();

        return new AbstractCloseableIteration<BindingSet, QueryEvaluationException>() {

            @Override
            public void remove() {
                allBindings.remove();
            }

            @Override
            public BindingSet next() {
                return allBindings.next();
            }

            @Override
            public boolean hasNext() {
                return allBindings.hasNext();
            }
        };
    }

    /**
     * iterate resources (graphs?)
     */
    @Override
    protected CloseableIteration<? extends Resource, SailException> getContextIDsInternal()
            throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Iterating resources for connection on remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
        return new AbstractCloseableIteration<>() {

            @Override
            public void remove() {
            }

            @Override
            public Resource next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        };
    }

    /**
     * return all statements
     */
    @Override
    protected CloseableIteration<? extends Statement, SailException> getStatementsInternal(Resource subj,
                                                                                           IRI pred, Value obj, boolean includeInferred, Resource... contexts) throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Returning all statements on subject %s for predicate %s and object %s (including inferred %b) " +
                    "in contexts %s for connection to the remoting sail %s wrapping config %s", subj, pred, obj, includeInferred, Arrays.toString(contexts), remotingSail, remotingSail.config));
        }
        return new AbstractCloseableIteration<>() {

            @Override
            public void remove() {
            }

            @Override
            public Statement next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        };
    }


    /**
     * compute graph sizes
     */
    @Override
    protected long sizeInternal(Resource... contexts) throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Returning siteo for contexts %s for connection to the remoting sail %s wrapping config %s", Arrays.toString(contexts), remotingSail, remotingSail.config));
        }
        return -1;
    }

    /**
     * compute graph sizes
     */
    @Override
    protected void startTransactionInternal() throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Start transaction on connection to the remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
    }

    /**
     * compute graph sizes
     */
    @Override
    protected void commitInternal() throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Commit transaction on connection to the remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
    }

    /**
     * compute graph sizes
     */
    @Override
    protected void rollbackInternal() throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Rollback transaction on connection to the remoting sail %s wrapping config %s", remotingSail, remotingSail.config));
        }
    }

    /**
     * adds a statement
     */
    @Override
    protected void addStatementInternal(Resource subj, IRI pred, Value obj, Resource... contexts)
            throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Adding statements on subject %s for predicate %s and object %s in contexts %s " +
                    "for connection to the remoting sail %s wrapping config %s", subj, pred, obj, Arrays.toString(contexts), remotingSail, remotingSail.config));
        }
    }

    @Override
    protected void removeStatementsInternal(Resource subj, IRI pred, Value obj, Resource... contexts)
            throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Remove statements on subject %s for predicate %s and object %s in contexts %s " +
                    "for connection to the remoting sail %s wrapping config %s", subj, pred, obj, Arrays.toString(contexts), remotingSail, remotingSail.config));
        }
    }

    @Override
    protected void clearInternal(Resource... contexts) throws SailException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Clearcontexts %s for connection to the remoting sail %s wrapping config %s", Arrays.toString(contexts), remotingSail, remotingSail.config));
        }
    }

}
