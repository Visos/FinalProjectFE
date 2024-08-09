package com.betacom.fe.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class AmministrazioneController {
	
	@Value("${jpa.backend}")
	String backend;
	
	@Autowired
	RestTemplate rest;
	
	public static Logger log = LoggerFactory.getLogger(AmministrazioneController.class);
	
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@GetMapping("/createUser")
	public ModelAndView createUser() { 
		ModelAndView mav = new ModelAndView("create-user");
		
		URI uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/createUser")
				.buildAndExpand().toUri();
		log.debug("URI: " + uri);
        return mav; 
	}
	
	@PostMapping("/saveUser")
	public Object saveUser() {

		return null;
	}

}
