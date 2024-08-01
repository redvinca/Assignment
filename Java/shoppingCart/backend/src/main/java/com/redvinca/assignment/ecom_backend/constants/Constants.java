package com.redvinca.assignment.ecom_backend.constants;

public final class Constants {

	// Messages
	public static final String ADD_TO_CART_STARTED = "addToCart method started for productId: {}";
	public static final String PRODUCT_ID_INVALID = "ProductId should be greater than 0";
	public static final String PRODUCT_NOT_FOUND = "Product Not Found";
	public static final String PRODUCT_OUT_OF_STOCK = "Product is out of stock for productId: {}";
	public static final String QUANTITY_EXCEEDS_STOCK = "Quantity exceeds stock for productId: {}";
	public static final String ADD_TO_CART_ENDED = "addToCart method ended for productId: {}";
	public static final String PORDUCT_OUT_OF_STOCK_EXCEPTION = "Product is out of stock";

	public static final String GET_ALL_CART_ITEMS_STARTED = "getAllCartItems method started";
	public static final String GET_ALL_CART_ITEMS_ENDED = "getAllCartItems method ended with {} items found";

	public static final String DELETE_ITEM_TO_CART_STARTED = "deleteItemToCart method started for cartId: {}";
	public static final String CART_ID_INVALID = "CartId should be greater than 0";
	public static final String CART_ID_NOT_FOUND = "Cart id not found";
	public static final String DELETE_ITEM_TO_CART_ENDED = "deleteItemToCart method ended for cartId: {}";
	public static final String CART_ITEM_DELETED_SUCCESSFULLY = "Cart Item deleted Successfully";

	public static final String GET_TOTAL_QUANTITY_STARTED = "getTotalQuantity method started";
	public static final String GET_TOTAL_QUANTITY_ENDED = "getTotalQuantity method ended with total quantity: {}";

	public static final String GET_TOTAL_PRICE_STARTED = "getTotalPrice method started";
	public static final String GET_TOTAL_PRICE_ENDED = "getTotalPrice method ended with total price: {}";

	public static final String UPDATE_QUANTITY_STARTED = "updateQuantityIncreaseDecrease method started for cartItemId: {}";
	public static final String CART_ITEM_NOT_FOUND = "Cart item not found for cartItemId: {}";
	public static final String QUANTITY_LESS_THAN_ZERO = "Quantity cannot be less than zero for cartItemId: {}";
	public static final String INSUFFICIENT_STOCK = "Quantity exceeds stock for productId: {}";
	public static final String QUANTITY_UPDATED_SUCCESSFULLY = "Quantity updated Successfully";
	public static final String UPDATE_QUANTITY_ENDED = "updateQuantityIncreaseDecrease method ended for cartItemId: {}";
	 public static final String CART_ITEM_ADDED_SUCCESSFULLY = "Item added to cart successfully.";

	public static final String CREATE_PRODUCT_STARTED = "createProduct method started for product: {}";
	public static final String PRODUCT_DETAILS_INVALID = "Product Details cannot be empty";
	public static final String CREATE_PRODUCT_ENDED = "createProduct method ended for product: {}";
	public static final String GET_ALL_PRODUCTS_STARTED = "getAllProducts method started";
	public static final String GET_ALL_PRODUCTS_ENDED = "getAllProducts method ended with {} products found";
	public static final String GET_PRODUCT_BY_ID_STARTED = "getProductById method started for productId: {}";
	public static final String PRODUCT_FOUND = "Product found for productId: {}";
	public static final String PRODUCT_NOT_FOUND_WARN = "Product not found for productId: {}";
	public static final String GET_PRODUCT_BY_ID_ENDED = "getProductById method ended for productId: {}";
	
	public static final String CONTROLLER_CREATE_PRODUCT_STARTED = "createProduct method started for product: {}";
    public static final String CONTROLLER_PRODUCT_CREATED_SUCCESSFULLY = "Product created successfully!!";
    public static final String CONTROLLER_FAILED_TO_ADD_ITEM = "Failed to add item";
    public static final String CONTROLLER_GET_ALL_PRODUCTS_STARTED = "getAllProducts method started";
    public static final String CONTROLLER_RETRIEVED_PRODUCTS = "Retrieved {} products";
    public static final String CONTROLLER_GET_PRODUCT_BY_ID_STARTED = "getProductById method started for productId: {}";
    public static final String CONTROLLER_PRODUCT_FOUND = "Product found for productId: {}";
    public static final String CONTROLLER_PRODUCT_NOT_FOUND = "Product not found for productId: {}";

    public static final String CONTROLLER_ADD_TO_CART_STARTED = "addToCart method started for productId: {}";
    public static final String CONTROLLER_ADD_TO_CART_SUCCESS = "Product added to cart successfully for productId: {}";
    public static final String CONTROLLER_ADD_TO_CART_FAILURE = "Failed to add item to cart for productId: {}";
    public static final String CONTROLLER_GET_ALL_CART_ITEMS_STARTED = "getAllCartItems method started";
    public static final String CONTROLLER_GET_ALL_CART_ITEMS_SUCCESS = "Retrieved all cart items successfully, count: {}";
    public static final String CONTROLLER_GET_ALL_CART_ITEMS_FAILURE = "Failed to retrieve cart items";
    public static final String CONTROLLER_GET_TOTAL_PRICE_STARTED = "getTotalPrice method started";
    public static final String CONTROLLER_GET_TOTAL_PRICE_SUCCESS = "Total price calculated successfully: {}";
    public static final String CONTROLLER_GET_TOTAL_PRICE_FAILURE = "Failed to calculate total price";
    public static final String CONTROLLER_GET_TOTAL_QUANTITY_STARTED = "getTotalQuantity method started";
    public static final String CONTROLLER_GET_TOTAL_QUANTITY_SUCCESS = "Total quantity calculated successfully: {}";
    public static final String CONTROLLER_GET_TOTAL_QUANTITY_FAILURE = "Failed to calculate total quantity";
    public static final String CONTROLLER_UPDATE_QUANTITY_STARTED = "updateQuantity method started for cartItemId: {} with quantityChange: {}";
    public static final String CONTROLLER_UPDATE_QUANTITY_ENDED = "updateQuantity method ended for cartItemId: {}";
    public static final String CONTROLLER_DELETE_ITEM_FROM_CART_STARTED = "deleteItemFromCart method started for cartId: {}";
    public static final String CONTROLLER_DELETE_ITEM_FROM_CART_ENDED = "deleteItemFromCart method ended for cartId: {}";

}
