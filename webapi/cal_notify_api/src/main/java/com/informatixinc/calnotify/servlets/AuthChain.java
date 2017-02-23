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

public class AuthChain implements Filter{

	private static final Logger logger = Logger.getLogger(AuthChain.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		//This is for testing.  Comment out this line for production
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");

		if(httpRequest.getMethod().equalsIgnoreCase("OPTIONS")){
			httpResponse.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
			httpResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization, Account");


			PrintWriter out = response.getWriter(); 
			out.println("");
			out.close();
			return;
		}

		//		 String[] urlParams = httpRequest.getRequestURI().split("/");
		//		 
		//		 //Login is always allowed
		//		 if(urlParams[urlParams.length - 1].equals("login")){
		//			 chain.doFilter(httpRequest, httpResponse);
		//			 return;
		//		 }
		//		 
		//		 //Check for active session
		//		 if(!AuthMap.isAuthenticated(httpRequest.getHeader("Authorization"))){
		//			 doBlock(httpResponse);
		//			 return;
		//		 }

		chain.doFilter( httpRequest, httpResponse);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private void doBlock(HttpServletResponse httpResponse){
		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("forceLogoff", true);

		PrintWriter out;
		try {
			out = httpResponse.getWriter();
			out.println(responseJson.toString());
			out.close();
		} catch (IOException e) {
			logger.error("Error getting print writer",e);
		} 

		return;

	}

}
