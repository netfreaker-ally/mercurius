package com.Mercurius.ApiGateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {
	@RequestMapping("/Support")
	public String fallbackmethod() {
		return "An Error occured in gateway server";
	}

}
