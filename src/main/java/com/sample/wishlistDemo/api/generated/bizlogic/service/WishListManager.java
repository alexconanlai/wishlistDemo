package com.sample.wishlistDemo.api.generated.bizlogic.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import com.sample.wishlistDemo.api.generated.TotalPrice;
import com.sample.wishlistDemo.api.generated.Wishlist;
import com.sample.wishlistDemo.api.generated.WishlistItem;
import com.sample.wishlistDemo.api.generated.YaasAwareParameters;
import com.sample.wishlistDemo.api.generated.persist.service.DocumentPersistService;

@ManagedBean
public class WishListManager {
	@Inject
	private DocumentPersistService documentPersistService;

	@Inject
	private AuthorizationHelper authHelper;

	public List<Wishlist> getWishlists(final YaasAwareParameters yaasAware) {
		return authHelper.wrapWithAuthorization(yaasAware,
				token -> documentPersistService.getWishlists(yaasAware, token));

	}

	public String createWishlist(final YaasAwareParameters yaasAware, final Wishlist wishlist) {
		return authHelper.wrapWithAuthorization(yaasAware,
				token -> documentPersistService.createWishlist(yaasAware, token, wishlist));
	}

	public Wishlist getWishlist(final YaasAwareParameters yaasAware, final String wishlistId) {
		return authHelper.wrapWithAuthorization(yaasAware,
				token -> documentPersistService.getWishlist(yaasAware, token, wishlistId));
	}

	public void updateWishlist(final YaasAwareParameters yaasAware, final String wishlistId, final Wishlist wishlist) {
		 authHelper.wrapWithAuthorization(yaasAware,
				token -> {
								documentPersistService.updateWishlist(yaasAware, token, wishlistId, wishlist); 
								return null;
							});
		
	}

	public void deleteByWishlistId(final YaasAwareParameters yaasAware, final String wishlistId) {
		 authHelper.wrapWithAuthorization(yaasAware,
					token -> {
									documentPersistService.deleteByWishlistId(yaasAware, token, wishlistId); 
									return null;
								});
		
	}

	public List<WishlistItem> getWishlistItems(final YaasAwareParameters yaasAware, final String wishlistId) {
		return authHelper.wrapWithAuthorization(yaasAware,
				token -> documentPersistService.getWishlistItems(yaasAware, token, wishlistId));
	}

	public void createWishlistItem(final YaasAwareParameters yaasAware, final String wishlistId, final WishlistItem wishlistItem) {
		 final Wishlist wishlist = getWishlist(yaasAware, wishlistId);
		
		 List<WishlistItem> wishlistItems = wishlist.getItems();
		 if (wishlistItems == null){
			 wishlistItems = Collections.emptyList();
		 }
			 	
		 wishlistItems.add(wishlistItem);
		 wishlist.setItems(wishlistItems);
			 
		 updateWishlist(yaasAware, wishlistId, wishlist);
	}

	public TotalPrice getTotalPrice(final YaasAwareParameters yaasAware, final String wishlistId) {
		final Wishlist wishlist = getWishlist(yaasAware, wishlistId);
		final TotalPrice totalPrice = new TotalPrice();
		
		List<WishlistItem> wishlistItems = wishlist.getItems();
		 if (wishlistItems != null && !wishlistItems.isEmpty()){
			 double price = 0.0d;
			 for(WishlistItem wishlistItem : wishlistItems){
				 price += wishlistItem.getPrice();
			 }
			 
			 totalPrice.setTotalPrice(price);
		 }
		
		 return totalPrice;
	}

}
