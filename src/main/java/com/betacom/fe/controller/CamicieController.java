package com.betacom.fe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CamicieController {
	
	@Value("${jpa.backend}")
	String backend;
	
	@Autowired
	RestTemplate rest;
	
	public static Logger log = LoggerFactory.getLogger(CamicieController.class);
	
	@GetMapping(value = {"/listCamicie"})
	public ModelAndView listCamicie() {
		ModelAndView mav = new ModelAndView("list-camicie");		
		
		return mav;
	}

}
