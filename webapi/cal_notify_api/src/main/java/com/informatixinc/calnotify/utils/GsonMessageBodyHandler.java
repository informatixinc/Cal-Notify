package com.informatixinc.calnotify.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/*
 * Util for working with GSON
 */
public class GsonMessageBodyHandler<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

	private static final String PRETTY_PRINT = "pretty-print";

	private final Gson gson;
	private final Gson prettyGson;

	@Context
	private UriInfo ui;

	/**
	 * Construct on instance
	 */
	public GsonMessageBodyHandler() {
		GsonBuilder builder = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization();

		this.gson = builder.create();
		this.prettyGson = builder.setPrettyPrinting().create();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		InputStreamReader reader = new InputStreamReader(entityStream, "UTF-8");
		try {
			return gson.fromJson(reader, type);
		} finally {
			reader.close();
		}
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {

		PrintWriter printWriter = new PrintWriter(entityStream);
		try {
			String json;
			if (ui.getQueryParameters().containsKey(PRETTY_PRINT)) {
				json = prettyGson.toJson(t);
			} else {
				json = gson.toJson(t);
			}
			printWriter.write(json);
			printWriter.flush();
		} finally {
			printWriter.close();
		}
	}
}