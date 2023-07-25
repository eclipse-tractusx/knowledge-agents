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
package org.eclipse.tractusx.agents.conforming.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.tractusx.agents.conforming.ConformingAgent;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

@Provider
public class SparqlProvider implements MessageBodyReader, MessageBodyWriter {

    ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public boolean isReadable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.isCompatible(ConformingAgent.sq);
    }

    @Override
    public Object readFrom(Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
    }

    @Override
    public boolean isWriteable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.isCompatible(ConformingAgent.sq);
    }

    @Override
    public void writeTo(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        new OutputStreamWriter(outputStream).append(String.valueOf(o));
    }
}
