package com.informatixinc.calnotify.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

/**
 * Auth chain filter
 * 
 * @author Paul Ortiz
 *
 */
public class AuthChain implements Filter {

	private static final Logger logger = Logger.getLogger(AuthChain.class);

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// This is for testing. Comment out this line for production
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");

		if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			httpResponse.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
			httpResponse.addHeader("Access-Control-Allow-Headers",
					"Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization, Account");

			PrintWriter out = response.getWriter();
			out.println("");
			out.close();
			return;
		}

		chain.doFilter(httpRequest, httpResponse);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc} force log off
	 */
	private void doBlock(HttpServletResponse httpResponse) {
		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("forceLogoff", true);

		PrintWriter out;
		try {
			out = httpResponse.getWriter();
			out.println(responseJson.toString());
			out.close();
		} catch (IOException e) {
			logger.error("Error getting print writer", e);
		}

		return;

	}

}
