package com.sample.wishlistDemo.api.generated.bizlogic.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;

import com.sample.wishlistDemo.api.generated.YaasAwareParameters;
import com.sap.cloud.yaas.servicesdk.authorization.AuthorizationScope;
import com.sap.cloud.yaas.servicesdk.authorization.DiagnosticContext;
import com.sap.cloud.yaas.servicesdk.authorization.integration.AuthorizedExecutionCallback;
import com.sap.cloud.yaas.servicesdk.authorization.integration.AuthorizedExecutionTemplate;

@ManagedBean
public class AuthorizationHelper {

	@Value("${SCOPE}")
	private String SCOPE;

	@Inject
	private AuthorizedExecutionTemplate authorizedExecutionTemplate;

	public <T> T wrapWithAuthorization(final YaasAwareParameters yaasAware,
			final AuthorizedExecutionCallback<T> callback) {

		return authorizedExecutionTemplate.executeAuthorized(
				createAuthorizationScope(yaasAware.getHybrisTenant(), Arrays.asList(SCOPE)),
				new DiagnosticContext(yaasAware.getHybrisRequestId(), yaasAware.getHybrisHop()), callback);
	}

	public AuthorizationScope createAuthorizationScope(final String tenant, final List<String> scopes) {

		return new AuthorizationScope(tenant, scopes);

	}
}
