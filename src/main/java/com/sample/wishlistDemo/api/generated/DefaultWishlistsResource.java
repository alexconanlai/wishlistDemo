
package com.sample.wishlistDemo.api.generated;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sample.wishlistDemo.api.generated.bizlogic.service.WishListManager;

/**
 * Resource class containing the custom logic. Please put your logic here!
 */
@Component("apiWishlistsResource")
@Singleton
public class DefaultWishlistsResource implements com.sample.wishlistDemo.api.generated.WishlistsResource {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultWishlistsResource.class);

	@javax.ws.rs.core.Context
	private javax.ws.rs.core.UriInfo uriInfo;

	@Inject
	private WishListManager wishListManager;

	/* GET / */
	@Override
	public Response get(final YaasAwareParameters yaasAware) {
		if (null == yaasAware) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}

		List<Wishlist> wishlists = wishListManager.getWishlists(yaasAware);

		return Response.ok().entity(wishlists).build();
	}

	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final Wishlist wishlist) {
		if (null == yaasAware || null == wishlist) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}

		final String id = wishListManager.createWishlist(yaasAware, wishlist);

		final URI createdLocation = uriInfo.getRequestUriBuilder().path(id).build();

		return Response.created(createdLocation).build();
	}

	/* GET /{wishlistId} */
	@Override
	public Response getByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId) {
		if (isWishlistIdEmpty(yaasAware, wishlistId)) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}

		final Wishlist wishlist = wishListManager.getWishlist(yaasAware, wishlistId);

		// if (null == wishlist) {
		// LOG.debug("No wishlist been got for id " + wishlistId + " .");
		// }

		return Response.ok().entity(wishlist).build();
	}

	/* PUT /{wishlistId} */
	@Override
	public Response putByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId,
			final Wishlist wishlist) {
		if (isWishlistIdEmpty(yaasAware, wishlistId) || null == wishlist) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}

		wishListManager.updateWishlist(yaasAware, wishlistId, wishlist);

		return Response.ok().build();
	}

	/* DELETE /{wishlistId} */
	@Override
	public Response deleteByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId) {
		if (isWishlistIdEmpty(yaasAware, wishlistId)) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}

		wishListManager.deleteByWishlistId(yaasAware, wishlistId);
		return Response.noContent().build();
	}

	@Override
	public Response getByWishlistIdWishlistItems(final YaasAwareParameters yaasAware,
			final java.lang.String wishlistId) {
		if (isWishlistIdEmpty(yaasAware, wishlistId)) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}

		final List<WishlistItem> wishlistItems = wishListManager.getWishlistItems(yaasAware, wishlistId);

		return Response.ok().entity(wishlistItems).build();
	}

	@Override
	public Response postByWishlistIdWishlistItems(final YaasAwareParameters yaasAware,
			final java.lang.String wishlistId, final WishlistItem wishlistItem) {
		if (isWishlistIdEmpty(yaasAware, wishlistId) || null == wishlistItem) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}

		wishListManager.createWishlistItem(yaasAware, wishlistId, wishlistItem);

		return Response.created(uriInfo.getRequestUri()).build();
	}

	private boolean isWishlistIdEmpty(final YaasAwareParameters yaasAware, final java.lang.String wishlistId) {
		return null == yaasAware || null == wishlistId || wishlistId.trim().isEmpty();
	}

	@Override
	public Response getByWishlistIdTotalPrice(YaasAwareParameters yaasAware, String wishlistId) {
		if (isWishlistIdEmpty(yaasAware, wishlistId)) {
			LOG.error("Request error.");
			return Response.status(Status.BAD_REQUEST).entity("request error.").type(MediaType.TEXT_PLAIN).build();
		}
		
		final TotalPrice totalPrice = wishListManager.getTotalPrice(yaasAware, wishlistId);
		
		return Response.ok().entity(totalPrice).build();
	}

	@Override
	public Response putByWishlistIdTotalPrice(String wishlistId, TotalPrice totalPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteByWishlistIdTotalPrice(String wishlistId) {
		// TODO Auto-generated method stub
		return null;
	}
}
