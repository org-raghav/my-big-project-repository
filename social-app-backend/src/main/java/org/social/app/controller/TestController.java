package org.social.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping(path = {"/", "/test-app"})
	public String testApp() {
		return "Hello I Social App Backend!!!";
	}
	
}
