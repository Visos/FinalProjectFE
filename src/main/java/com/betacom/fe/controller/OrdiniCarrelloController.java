package com.betacom.fe.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.betacom.fe.dto.OrdineDTO;
import com.betacom.fe.request.UtenteReq;
import com.betacom.fe.response.Response;
import com.betacom.fe.response.ResponseObject;
import com.betacom.fe.util.Stato;

@Controller
public class OrdiniCarrelloController {

	@Value("${jpa.backend}")
	String backend;

	@Autowired
	RestTemplate rest;

	public static Logger log = LoggerFactory.getLogger(OrdiniCarrelloController.class);

	@GetMapping("/carrello")
	public ModelAndView carrello() {
		ModelAndView mav = new ModelAndView("carrello");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = null;
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			email = userDetails.getUsername();
		}
		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/utente/searchByMail").queryParam("mail", email)
				.buildAndExpand().toUri();
		ResponseObject<UtenteReq> respObj = rest
				.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseObject<UtenteReq>>() {
				}).getBody();

		Integer utenteId = respObj.getDati().getId();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/list").queryParam("id", utenteId)
				.queryParam("stato", Stato.CARRELLO).buildAndExpand().toUri();

		Response<OrdineDTO> objCarrello = rest.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
		}).getBody();

		Integer ordineId = objCarrello.getDati().get(0).getId();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/listProdOrd").queryParam("idOrd", ordineId)
				.buildAndExpand().toUri();

		Response<?> respProdOrd = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/listAll").buildAndExpand().toUri();

		Response<?> resp = rest.getForEntity(uri, Response.class).getBody();
		
		
		uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/ordine/findById")
				.queryParam("id", ordineId)
				.buildAndExpand().toUri();
		
		ResponseObject<?> respOrdine = rest.getForEntity(uri, ResponseObject.class).getBody();

		mav.addObject("listProd", resp);
		mav.addObject("listProdOrd", respProdOrd);
		mav.addObject("ordine", respOrdine);

//		log.debug(resp.getDati().toString());
//		log.debug(respProdOrd.getDati().toString());
//		log.debug(respOrdine.getDati().toString());
		return mav;
	}

	@GetMapping("/ordini")
	public ModelAndView ordini() {
		ModelAndView mav = new ModelAndView("ordini");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = null;
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			email = userDetails.getUsername();
		}
		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/utente/searchByMail").queryParam("mail", email)
				.buildAndExpand().toUri();
		ResponseObject<UtenteReq> respObj = rest
				.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseObject<UtenteReq>>() {
				}).getBody();

		Integer utenteId = respObj.getDati().getId();
		
		uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/list").queryParam("id", utenteId)
				.queryParam("").buildAndExpand().toUri();

		Response<OrdineDTO> obj = rest.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
		}).getBody();
		
		uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/listAll").buildAndExpand().toUri();

		Response<?> resp = rest.getForEntity(uri, Response.class).getBody();
		
		mav.addObject("ordini", obj);
		mav.addObject("prodotti", resp);
		
		log.debug(obj.getDati().toString());

		return mav;
	}

	@GetMapping("/ordiniAdmin")
	public ModelAndView ordiniAdmin() {
		ModelAndView mav = new ModelAndView("/ordini-admin");

		return mav;
	}

	@GetMapping("/ordiniAdminAll")
	public ModelAndView ordiniAdminAll() {
		ModelAndView mav = new ModelAndView("/ordini-admin-all");

		return mav;
	}

}
