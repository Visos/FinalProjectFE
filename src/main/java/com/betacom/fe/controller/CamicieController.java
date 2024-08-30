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
public class CamicieController {
	
	@Value("${jpa.backend}")
	String backend;
	
	@Autowired
	RestTemplate rest;
	
	public static Logger log = LoggerFactory.getLogger(CamicieController.class);
	
	@GetMapping(value = {"/listCamicie"})
	public ModelAndView listCamicie() {
		ModelAndView mav = new ModelAndView("list-camicie");		
		 URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/marca/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listMarca = rest.getForEntity(uri, Response.class).getBody();
		    
		    uri = UriComponentsBuilder.fromHttpUrl(backend + "/colore/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listColore = rest.getForEntity(uri, Response.class).getBody();
		    
		    uri = UriComponentsBuilder.fromHttpUrl(backend + "/fantasia/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listFantasia = rest.getForEntity(uri, Response.class).getBody();
		    
		    uri = UriComponentsBuilder.fromHttpUrl(backend + "/materiale/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listMateriale = rest.getForEntity(uri, Response.class).getBody();
		    
		    uri = UriComponentsBuilder.fromHttpUrl(backend + "/lunghezzaManica/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listLunghezzaManica = rest.getForEntity(uri, Response.class).getBody();
		    
		    uri = UriComponentsBuilder.fromHttpUrl(backend + "/taglia/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listTaglia = rest.getForEntity(uri, Response.class).getBody();
		    
		    uri = UriComponentsBuilder.fromHttpUrl(backend + "/tipoColletto/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listTipoColletto = rest.getForEntity(uri, Response.class).getBody();
		    
		    uri = UriComponentsBuilder.fromHttpUrl(backend + "/vestibilita/listAll")
		            .buildAndExpand().toUri();
		    log.debug("URI: " + uri);
		    
		    Response<?> listVestibilita = rest.getForEntity(uri, Response.class).getBody();
		
		    uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/prodotto/listAll")
				.buildAndExpand().toUri();
		
		    Response<?> resp = rest.getForEntity(uri, Response.class).getBody();
		
		mav.addObject("listCamicie", resp);
		mav.addObject("marca", listMarca);
	    mav.addObject("colore", listColore);
	    mav.addObject("fantasia", listFantasia);
	    mav.addObject("materiale", listMateriale);
	    mav.addObject("manica", listLunghezzaManica);
	    mav.addObject("taglia", listTaglia);
	    mav.addObject("colletto", listTipoColletto);
	    mav.addObject("vestibilita", listVestibilita);
		
		return mav;
	}
	
	@GetMapping(value = {"/listCamicieAdmin"})
	public ModelAndView listCamicieAdmin() {
		ModelAndView mav = new ModelAndView("list-camicie-admin");		
		
		return mav;
	}

}
