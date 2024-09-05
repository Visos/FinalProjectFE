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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.betacom.fe.dto.OrdineDTO;
import com.betacom.fe.request.OrdineReq;
import com.betacom.fe.request.ProdottiOrdiniReq;
import com.betacom.fe.request.ProdottoReq;
import com.betacom.fe.request.UtenteReq;
import com.betacom.fe.response.Response;
import com.betacom.fe.response.ResponseBase;
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
	public ModelAndView carrello(@ModelAttribute("prodotto") ProdottoReq req) {
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

		Response<OrdineDTO> objCarrello = rest
				.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
				}).getBody();

		Integer ordineId = objCarrello.getDati().get(0).getId();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/listProdOrd").queryParam("idOrd", ordineId)
				.buildAndExpand().toUri();

		Response<?> respProdOrd = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/listAll").buildAndExpand().toUri();

		Response<?> resp = rest.postForEntity(uri, req, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/findById").queryParam("id", ordineId).buildAndExpand()
				.toUri();

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
	public ModelAndView ordini(@ModelAttribute("prodotto") ProdottoReq req) {
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

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/list").queryParam("id", utenteId).queryParam("")
				.buildAndExpand().toUri();

		Response<OrdineDTO> obj = rest
				.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
				}).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/listAll").buildAndExpand().toUri();

		Response<?> resp = rest.postForEntity(uri, req, Response.class).getBody();

		mav.addObject("ordini", obj);
		mav.addObject("prodotti", resp);

		return mav;
	}

	@GetMapping("/ordiniAdmin")
	public ModelAndView ordiniAdmin(@ModelAttribute("prodotto") ProdottoReq req) {
		ModelAndView mav = new ModelAndView("/ordini-admin");

		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/list").queryParam("stato", Stato.ELABORAZIONE)
				.buildAndExpand().toUri();

		Response<OrdineDTO> obj = rest
				.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
				}).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/listAll").buildAndExpand().toUri();

		Response<?> resp = rest.postForEntity(uri, req, Response.class).getBody();

		mav.addObject("ordini", obj);
		mav.addObject("prodotti", resp);

		return mav;
	}

	@GetMapping("/ordiniAdminAll")
	public ModelAndView ordiniAdminAll(@ModelAttribute("prodotto") ProdottoReq req) {
		ModelAndView mav = new ModelAndView("/ordini-admin-all");

		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/list").buildAndExpand().toUri();

		Response<OrdineDTO> obj = rest
				.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
				}).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/listAll").buildAndExpand().toUri();

		Response<?> resp = rest.postForEntity(uri, req, Response.class).getBody();

		mav.addObject("ordini", obj);
		mav.addObject("prodotti", resp);

		return mav;
	}

	@GetMapping("/createModelProdOrd")
	public Object createModelProdOrd(@RequestParam Integer idProdotto, @RequestParam Integer qty) {

		log.debug("prodottoId: " + idProdotto);
		log.debug("qty: " + qty);

		ModelAndView mav = new ModelAndView("prodotto");

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

		Response<OrdineDTO> objCarrello = rest
				.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
				}).getBody();

		OrdineReq reqOrd = new OrdineReq();

		Integer ordineId = null;

		if (!objCarrello.getDati().isEmpty()) {
			ordineId = objCarrello.getDati().get(0).getId();
			reqOrd.setIdUtente(utenteId);
			reqOrd.setStato("CARRELLO");
		} else {
			reqOrd.setIdUtente(utenteId);
			reqOrd.setStato("CARRELLO");
			uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/create").buildAndExpand().toUri();

			ResponseBase respCarrello = rest.postForEntity(uri, reqOrd, ResponseBase.class).getBody();

			uri = UriComponentsBuilder.fromHttpUrl(backend + "/ordine/list").queryParam("id", utenteId)
					.queryParam("stato", Stato.CARRELLO).buildAndExpand().toUri();

			Response<OrdineDTO> objCarrello2 = rest
					.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Response<OrdineDTO>>() {
					}).getBody();
			ordineId = objCarrello2.getDati().get(0).getId();

			log.debug(respCarrello.toString());

		}

		URI uriProd = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/searchById").queryParam("id", idProdotto)
				.buildAndExpand().toUri();

		ResponseObject<?> respObjProdotto = rest.getForEntity(uriProd, ResponseObject.class).getBody();

		mav.addObject("prodotto", respObjProdotto);
		mav.addObject("prodottoID", idProdotto);

		ProdottiOrdiniReq req = new ProdottiOrdiniReq();
		req.setIdOrdine(ordineId);
		req.setIdProdotto(idProdotto);
		req.setQty(qty);

		uri = UriComponentsBuilder.fromHttpUrl(backend + "ordine/addProd").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		ResponseBase att = rest.postForEntity(uri, req, ResponseBase.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "ordine/update").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		ResponseBase up = rest.postForEntity(uri, reqOrd, ResponseBase.class).getBody();

		log.debug("rc " + att.getRc());
//		mav.addObject("prodottoOrdineReq", req);
		return "redirect:/carrello";
	}

//	@PostMapping("/addProdOrd")
//	public Object addProd(@ModelAttribute("prodOrdReq") ProdottiOrdiniReq req) {
//		log.debug("idOrdine: " + req.getIdOrdine() + " IdProdotto: " + req.getIdProdotto());
//
//		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "ordine/addProd").buildAndExpand().toUri();
//		log.debug("URI: " + uri);
//
//		ResponseBase att = rest.postForEntity(uri, req, ResponseBase.class).getBody();
//
//		log.debug("rc " + att.getRc());
//
//		// in questo modo voglio dico che voglio aggiornare la pagina e tornare in
//		// listSocio
//		return "redirect:/carrello";
//	}

	@GetMapping("/acquistaOrdine")
	public Object acquistaOrdine(@RequestParam Integer idOrdine) {
		log.debug("ID ORDINE: " + idOrdine);
		return "redirect:/carrello";
	}

}