package com.redvinca.assignment.ecom_backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteItemToCartRequest {
	private Long cartId;

}
