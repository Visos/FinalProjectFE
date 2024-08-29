package com.betacom.fe.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.betacom.fe.response.Response;

@Controller
public class MaglietteController {
	@Value("${jpa.backend}")
	String backend;
	
	@Autowired
	RestTemplate rest;
	
	public static Logger log = LoggerFactory.getLogger(MaglietteController.class);
	
	@GetMapping(value = {"/listMagliette"})
	public ModelAndView listMagliette() {
		ModelAndView mav = new ModelAndView("list-magliette");		
		
		URI uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/prodotto/listAll")
				.buildAndExpand().toUri();
		
		Response<?> resp = rest.getForEntity(uri, Response.class).getBody();
		
		mav.addObject("listMagliette", resp);
		
		return mav;
	}
	
	@GetMapping(value = {"/listMaglietteAdmin"})
	public ModelAndView listMaglietteAdmin() {
		ModelAndView mav = new ModelAndView("list-magliette-admin");		
		
		return mav;
	}
}
