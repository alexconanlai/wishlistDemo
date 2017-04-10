package com.sample.wishlistDemo.api.generated.util.exception;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.yaas.servicesdk.authorization.AccessToken;
import com.sap.cloud.yaas.servicesdk.authorization.integration.AccessTokenInvalidException;
import com.sap.cloud.yaas.servicesdk.authorization.integration.AuthorizedExecutionTemplate;

public class ErrorHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);
	
	public static RuntimeException resolveErrorResponse(final Response response, final AccessToken token) {
		switch (response.getStatus()) {
			case 401:
				LOG.error("Request unauthorized.");
				return new AccessTokenInvalidException("request unauthorized", token);
			case 403:
				LOG.error("Request forbidden.");
				return new AccessTokenInvalidException("request forbidden", token);
			default:
				LOG.error("Internal  server error.");
				return new InternalServerErrorException(response);
		}
	}
}
