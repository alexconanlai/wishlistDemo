package com.sample.wishlistDemo.api.generated.persist.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.wishlistDemo.api.generated.Wishlist;
import com.sample.wishlistDemo.api.generated.WishlistItem;
import com.sample.wishlistDemo.api.generated.YaasAwareParameters;
import com.sample.wishlistDemo.api.generated.util.exception.ErrorHandler;
import com.sample.wishlistDemo.client.document.DocumentClient;
import com.sample.wishlistDemo.client.document.builder.TenantClientDataTypeBuilder.GetActionBuilder;
import com.sample.wishlistDemo.client.document.builder.TenantClientDataTypeDataIdBuilder.PostActionBuilder;
import com.sap.cloud.yaas.servicesdk.authorization.AccessToken;
import com.sap.cloud.yaas.servicesdk.patternsupport.schemas.ResourceLocation;

@ManagedBean
public class DocumentPersistService {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentPersistService.class);

	private final String WISH_LIST_TYPE = "wishlist";

	@Inject
	private DocumentClient documentClient;

	public List<Wishlist> getWishlists(final YaasAwareParameters yaasAware, final AccessToken token) {
		GetActionBuilder gb = documentClient.tenant(yaasAware.getHybrisTenant()).client(yaasAware.getHybrisClient())
				.dataType(WISH_LIST_TYPE).prepareGet().withTotalCount(true).withAuthorization(token.toAuthorizationHeaderValue());
		final Response response = gb.execute();

		if (response.getStatus() == Status.OK.getStatusCode()) {
			return Arrays.stream(response.readEntity(Wishlist[].class)).collect(Collectors.toList());
		} else if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			return Collections.emptyList();
		}

		throw ErrorHandler.resolveErrorResponse(response, token);
	}

	public String createWishlist(final YaasAwareParameters yaasAware, final AccessToken token,
			final Wishlist wishlist) {
		final Response response =documentClient.tenant(yaasAware.getHybrisTenant()).client(yaasAware.getHybrisClient())
				.dataType(WISH_LIST_TYPE).dataId(wishlist.getId()).preparePost().withPayload(Entity.json(wishlist))
				.withAuthorization(token.toAuthorizationHeaderValue()).execute();

		if (response.getStatus() == Status.CREATED.getStatusCode()) {
			return response.readEntity(ResourceLocation.class).getId();
		} else if (response.getStatus() == Status.CONFLICT.getStatusCode()) {
			LOG.warn("Duplicate ID: " + wishlist.getId() + ". The wishlist can't be created.");
			throw new WebApplicationException("Duplicate ID. Please provide another ID for the wishlist.", response);
		}

		throw ErrorHandler.resolveErrorResponse(response, token);
	}

	public Wishlist getWishlist(final YaasAwareParameters yaasAware, final AccessToken token, final String wishlistId) {
		final Response response = documentClient.tenant(yaasAware.getHybrisTenant()).client(yaasAware.getHybrisClient())
				.dataType(WISH_LIST_TYPE).dataId(wishlistId).prepareGet()
				.withAuthorization(token.toAuthorizationHeaderValue()).execute();

		if (response.getStatus() == Status.OK.getStatusCode()) {
			final Wishlist wishlist = response.readEntity(Wishlist.class);
			return wishlist;
		} else if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			LOG.warn("Cannot find wishlist with ID " + wishlistId);
			throw new NotFoundException("Cannot find wishlist with ID " + wishlistId, response);
		}

		throw ErrorHandler.resolveErrorResponse(response, token);
	}

	public void updateWishlist(final YaasAwareParameters yaasAware, final AccessToken token, final String wishlistId,
			final Wishlist wishlist) {
		final Response response = documentClient.tenant(yaasAware.getHybrisTenant()).client(yaasAware.getHybrisClient())
				.dataType(WISH_LIST_TYPE).dataId(wishlistId).preparePut()
				.withAuthorization(token.toAuthorizationHeaderValue()).withPayload(Entity.json(wishlist)).execute();

		if (response.getStatus() == Status.OK.getStatusCode()) {
			return;
		} else if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			LOG.warn("No wishlist with ID " + wishlistId + " to be updated.");
			throw new NotFoundException("Cannot find wishlist with ID " + wishlistId, response);
		}

		throw ErrorHandler.resolveErrorResponse(response, token);
	}

	public void deleteByWishlistId(YaasAwareParameters yaasAware, AccessToken token, String wishlistId) {
		final Response response = documentClient.tenant(yaasAware.getHybrisTenant()).client(yaasAware.getHybrisClient())
				.dataType(WISH_LIST_TYPE).dataId(wishlistId).prepareDelete()
				.withAuthorization(token.toAuthorizationHeaderValue()).execute();

		if (response.getStatus() == Status.NO_CONTENT.getStatusCode()) {
			return;
		} else if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			LOG.warn("No wishlist with ID " + wishlistId + " to be deleted.");
			throw new NotFoundException("Cannot find wishlist with ID " + wishlistId, response);
		}
		throw ErrorHandler.resolveErrorResponse(response, token);

	}

	public List<WishlistItem> getWishlistItems(YaasAwareParameters yaasAware, AccessToken token, String wishlistId) {
		final Response response = documentClient.tenant(yaasAware.getHybrisTenant()).client(yaasAware.getHybrisClient())
				.dataType(WISH_LIST_TYPE).dataId(wishlistId).prepareGet()
				.withAuthorization(token.toAuthorizationHeaderValue()).execute();

		if (response.getStatus() == Status.OK.getStatusCode()) {
			return response.readEntity(Wishlist.class).getItems();
		} else if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
			LOG.warn("Cannot find wishlist item with ID " + wishlistId + " .");
			return Collections.emptyList();
		}
		
		throw ErrorHandler.resolveErrorResponse(response, token);
	}

}
