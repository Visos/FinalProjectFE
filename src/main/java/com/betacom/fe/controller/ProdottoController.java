package com.betacom.fe.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.betacom.fe.response.ResponseObject;

@Controller
public class ProdottoController {
	@Value("${jpa.backend}")
	String backend;
	
	@Autowired
	RestTemplate rest;
	
	public static Logger log = LoggerFactory.getLogger(ProdottoController.class);
	
	@GetMapping("/prodotto")
	public ModelAndView showProdotto(@RequestParam Integer prodottoID) {
		ModelAndView mav = new ModelAndView("prodotto");	
		
		URI uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/prodotto/searchById")
				.queryParam("id", prodottoID)
				.buildAndExpand().toUri();
		
		ResponseObject<?> respObj = rest.getForEntity(uri, ResponseObject.class).getBody();
		
		mav.addObject("prodotto", respObj);
		mav.addObject("prodottoID", prodottoID);
		
		return mav;
	}
	
	@GetMapping("/prodottoAdmin")
	public ModelAndView showProdottoAdmin() {
		ModelAndView mav = new ModelAndView("prodotto-admin");		
		
		return mav;
	}
}