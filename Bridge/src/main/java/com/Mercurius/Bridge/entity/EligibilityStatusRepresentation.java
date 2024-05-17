
package com.Mercurius.Bridge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EligibilityStatusRepresentation {
	private String userId;
	private String productId;
	private boolean eligible;
	private AccountRepresentation account;
	private ProductRepresentation product;

}
