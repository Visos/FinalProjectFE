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

import jakarta.servlet.http.HttpSession;

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
				.fromHttpUrl(backend + "/createOrUpdate")
				.buildAndExpand().toUri();
		log.debug("URI: " + uri);
        return mav; 
	}
	
	@PostMapping("/saveUser")
	public Object saveUser() {

		return null;
	}
	
	@GetMapping("/nuovoProdotto")
	public ModelAndView nuovoProdotto() {
		ModelAndView mav = new ModelAndView("nuovo-prodotto");
		return mav;
	}
	
	@GetMapping("/profilo")
	public ModelAndView profilo() {
		ModelAndView mav = new ModelAndView("profilo");
		return mav;
	}
	
	
	@GetMapping("/profiloAdmin")
	public ModelAndView profiloAdmin() {
		ModelAndView mav = new ModelAndView("profilo-admin");
		return mav;
	}
	
	@GetMapping("/createAdmin")
	public ModelAndView createAdmin() {
		ModelAndView mav = new ModelAndView("create-admin");
		return mav;
	}

	@GetMapping("/logout")
    public String logoutPage(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
