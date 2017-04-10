package com.sample.wishlistDemo.api.generated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sample.wishlistDemo.JerseyApplication;
import com.sap.cloud.yaas.servicesdk.patternsupport.traits.YaasAwareTrait;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/applicationContext.xml")
public final class DefaultWishlistsResourceTest extends com.sample.wishlistDemo.api.generated.AbstractResourceTest {
	private static final String TENANT = "caashiring";
	private static final String CLIENT = "l2913671.wish";
	/**
	 * Server side root resource /wishlists, evaluated with some default
	 * value(s).
	 */
	private static final String ROOT_RESOURCE_PATH = "/wishlists";
	private static final String WISHLIST_ITEMS_PATH = "wishlistItems";

	private Wishlist wishlist;

	@Before
	public void before() {
		wishlist = new Wishlist();
		wishlist.setId(UUID.randomUUID().toString());
		wishlist.setOwner("u123");
		wishlist.setTitle("wl1");
		// WishlistItem wishlistItem = new WishlistItem();
		List<WishlistItem> wishlistItems = new ArrayList<>();
		wishlist.setItems(wishlistItems);
		postWishlist(wishlist);
	}

	/* get() /wishlists */
	@Test
	public void testGet() {
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("");

		final Response response = target.request()
				.header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT)
				.get();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(),
				response.getStatus());
	}

	/* post(entity) /wishlists */
	@Test
	public void testPostWithWishlist() {
		Wishlist wishlist = new Wishlist();
		wishlist.setId(UUID.randomUUID().toString());
		wishlist.setTitle("wl2");
		wishlist.setOwner("u123");

       postWishlist(wishlist);

	}

	/* get() /wishlists/wishlistId */
	@Test
	public void testGetByWishlistId() {
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/" + wishlist.getId());

		final Response response = target.request()
				.header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT).get();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(),
				response.getStatus());
	}

	/* put(entity) /wishlists/wishlistId */
	@Test
	public void testPutByWishlistIdWithWishlist() {
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/" + wishlist.getId());
		wishlist.setDescription("good");
		final javax.ws.rs.client.Entity<Wishlist> entity = javax.ws.rs.client.Entity.entity(wishlist,
				"application/json");

		final Response response = target.request()
				.header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT).put(entity);

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(),
				response.getStatus());
	}

	/* delete() /wishlists/wishlistId */
	@Test
	public void testDeleteByWishlistId() {
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/" + wishlist.getId());

		final Response response = target.request()
				.header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT).delete();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.NO_CONTENT.getStatusCode(),
				response.getStatus());
	}

	// get() /wishlists/wishlistId/wishlistItems
	@Test
	public void testGetByWishlistIdWishlistItems() {
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH)
				.path("/" + wishlist.getId() + "/" + WISHLIST_ITEMS_PATH);
		final Response response = target.request().header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT).get();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertNotNull("Response must not be null", response.readEntity(WishlistItem[].class));
	}

	// post() /wishlists/wishlistId/wishlistItems
	@Test
	public void testPostByWishlistIdWishlistItems() {
		final List<WishlistItem> wishlistItems = wishlist.getItems();
		final WishlistItem wishlistItem = new WishlistItem();
		wishlistItem.setCreatedAt(Calendar.getInstance().getTime());
		wishlistItem.setPrice(1.6);
		wishlistItem.setAmount(1);
		wishlistItem.setProduct("botten");
		wishlistItems.add(wishlistItem);

		WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/" + wishlist.getId() + "/" + WISHLIST_ITEMS_PATH);
		final Response response = target.request().header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT)
				.post(Entity.entity(wishlistItem, "application/json"));
		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(),
				response.getStatus());

		target = getRootTarget(ROOT_RESOURCE_PATH).path("/" + wishlist.getId() + "/" + WISHLIST_ITEMS_PATH);
		final Response responseGet = target.request().header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT).get();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(),
				response.getStatus());
		Assert.assertEquals(1, responseGet.readEntity(WishlistItem[].class).length);
	}

	/* get() /wishlist/wishlistId/totalPrice */
	@Test
	public void testGetByWishlistIdTotalPrice() {
		final List<WishlistItem> wishlistItems = wishlist.getItems();
		WishlistItem wishlistItem = new WishlistItem();
		wishlistItem.setCreatedAt(Calendar.getInstance().getTime());
		wishlistItem.setPrice(1.6);
		wishlistItem.setAmount(1);
		wishlistItem.setProduct("botten");
		wishlistItems.add(wishlistItem);

		wishlistItem = new WishlistItem();
		wishlistItem.setAmount(1);
		wishlistItem.setCreatedAt(Calendar.getInstance().getTime());
		wishlistItem.setPrice(300.0);
		wishlistItem.setProduct("computer");
		wishlistItems.add(wishlistItem);

		wishlistItem = new WishlistItem();
		wishlistItem.setAmount(3);
		wishlistItem.setCreatedAt(Calendar.getInstance().getTime());
		wishlistItem.setPrice(5.6);
		wishlistItem.setProduct("notebook");
		wishlistItems.add(wishlistItem);

		Response response = postWishlist(wishlist);

		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("/" + wishlist.getId()).path("/totalPrice");
		response = target.request().header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT).get();

		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.OK.getStatusCode(),
				response.getStatus());
		final TotalPrice totalPrice = response.readEntity(TotalPrice.class);
		Assert.assertEquals("Response does not have expected totalPrice", new BigDecimal(307.2),
				new BigDecimal(totalPrice.getTotalPrice()));
	}

	private Response postWishlist(Wishlist wishlist) {
		final WebTarget target = getRootTarget(ROOT_RESOURCE_PATH).path("");
		final Wishlist entityBody = wishlist;
		final Entity<Wishlist> entity = Entity.entity(entityBody, "application/json");

		Response response = target.request().header(YaasAwareTrait.Headers.CLIENT, CLIENT)
				.header(YaasAwareTrait.Headers.TENANT, TENANT).post(entity);
		
		Assert.assertNotNull("Response must not be null", response);
		Assert.assertEquals("Response does not have expected response code", Status.CREATED.getStatusCode(),
				response.getStatus());
		
		return response;
	}

	@Override
	protected ResourceConfig configureApplication() {
		final ResourceConfig application = new JerseyApplication();
		application.register(DefaultWishlistsResource.class);
		return application;
	}
}
