package com.cognizant.pas.quotes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Authorization-Microservice", url = "http://localhost:8400/auth")
public interface AuthorisingClient {

	@PostMapping("/authorize")
	public boolean authorizeTheRequest(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
}