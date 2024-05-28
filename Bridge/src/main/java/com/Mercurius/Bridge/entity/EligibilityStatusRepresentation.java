
package com.Mercurius.Bridge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class EligibilityStatusRepresentation {

    private boolean eligible;
    private String message;
	public EligibilityStatusRepresentation(boolean eligible, String message) {
		super();
		this.eligible = eligible;
		this.message = message;
	}
	public EligibilityStatusRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isEligible() {
		return eligible;
	}
	public void setEligible(boolean eligible) {
		this.eligible = eligible;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

  
}
