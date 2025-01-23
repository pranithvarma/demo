package com.practice.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Tag(name="user controller")
public class Controller {

	
	@GetMapping("/hello")
	public String helloworld(HttpServletRequest httpservletrequest) {
		return "hello world"+httpservletrequest.getSession().getId();
	}
}
