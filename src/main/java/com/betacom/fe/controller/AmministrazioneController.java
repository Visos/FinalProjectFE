package com.betacom.fe.controller;

import java.io.Console;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.betacom.fe.dto.CamiciaDTO;
import com.betacom.fe.dto.MagliettaDTO;
import com.betacom.fe.dto.PantaloneDTO;
import com.betacom.fe.dto.ProdottoDTO;
import com.betacom.fe.dto.ScarpaDTO;
import com.betacom.fe.dto.VestitoDTO;
import com.betacom.fe.request.CamiciaReq;
import com.betacom.fe.request.MagliettaReq;
import com.betacom.fe.request.PantaloneReq;
import com.betacom.fe.request.ProdottoReq;
import com.betacom.fe.request.ScarpaReq;
import com.betacom.fe.request.UtenteReq;
import com.betacom.fe.request.VestitoReq;
import com.betacom.fe.response.Response;
import com.betacom.fe.response.ResponseBase;
import com.betacom.fe.response.ResponseObject;

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
		UtenteReq utente = new UtenteReq();
		mav.addObject("utente", utente);
		return mav;
	}

	@PostMapping("/saveUser")
	public Object saveUser(@ModelAttribute("utente") UtenteReq req) {
		log.debug("Nome utente: " + req.getNome());
		log.debug("Ruolo: " + req.getRuolo());
		
		req.setRuolo("CLIENTE");
		UtenteReq utente = req;
		
		log.debug("Ruolo: " + utente.getRuolo());
		
		URI uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/utente/createOrUpdate")
				.buildAndExpand().toUri();
		
		
		ResponseBase resp = rest.postForEntity(uri, utente, ResponseBase.class).getBody();
		log.debug("Nome: " + utente.getNome());
		log.debug("rc: " + resp.getRc());
		log.debug("rc: " + resp.getMsg());
		
		
		if (!resp.getRc()) {
			ModelAndView mav = new ModelAndView("create-user");
			mav.addObject("utente", req);
			mav.addObject("error", resp.getMsg());
			return mav;
		}
		
		//in questo modo voglio dico che voglio aggiornare la pagina e tornare in listSocio
		return "redirect:/index";
	}

	@GetMapping("/nuovoProdotto")
	public ModelAndView nuovoProdotto() {
		ModelAndView mav = new ModelAndView("nuovo-prodotto");
		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/marca/listAll").buildAndExpand().toUri();

		Response<?> listMarca = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/colore/listAll").buildAndExpand().toUri();

		Response<?> listColore = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/fantasia/listAll").buildAndExpand().toUri();
		Response<?> listFantasia = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/materiale/listAll").buildAndExpand().toUri();

		Response<?> listMateriale = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/lunghezzaManica/listAll").buildAndExpand().toUri();

		Response<?> listLunghezzaManica = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/taglia/listAll").buildAndExpand().toUri();

		Response<?> listTaglia = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/tipoColletto/listAll").buildAndExpand().toUri();

		Response<?> listTipoColletto = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/vestibilita/listAll").buildAndExpand().toUri();
		Response<?> listVestibilita = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/lunghezza/listAll").buildAndExpand().toUri();

		Response<?> listLunghezza = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/chiusura/listAll").buildAndExpand().toUri();

		Response<?> listChiusura = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/tipoScarpa/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listTipoScarpa = rest.getForEntity(uri, Response.class).getBody();

		ProdottoReq p = new ProdottoReq();
		p.setMagliettaReq(new MagliettaReq());
		p.setCamiciaReq(new CamiciaReq());
		p.setPantaloneReq(new PantaloneReq());
		p.setVestitoReq(new VestitoReq());
		p.setScarpaReq(new ScarpaReq());

		mav.addObject("marca", listMarca);
		mav.addObject("colore", listColore);
		mav.addObject("fantasia", listFantasia);
		mav.addObject("materiale", listMateriale);
		mav.addObject("manica", listLunghezzaManica);
		mav.addObject("taglia", listTaglia);
		mav.addObject("colletto", listTipoColletto);
		mav.addObject("vestibilita", listVestibilita);
		mav.addObject("lunghezza", listLunghezza);
		mav.addObject("chiusura", listChiusura);
		mav.addObject("tipoScarpa", listTipoScarpa);
		mav.addObject("prodotto", p);

		return mav;
	}

	@PostMapping("/saveProdotto")
	public Object saveProdotto(@ModelAttribute("prodotto") ProdottoReq req, Model model) {
		
		ProdottoReq p = new ProdottoReq();
		p.setId((req.getId() == null) ? null : req.getId());
		p.setQty(req.getQty());
		p.setSesso(req.getSesso());
		
		
		 String fileName = req.getImg();
		
		p.setImg("../img/" + fileName);
//		log.debug(req.getMarca());
//		log.debug(req.getMateriale());
//		log.debug(req.getColore());
		p.setColore(req.getColore());
		p.setMarca(req.getMarca());
		p.setMateriale(req.getMateriale());
		p.setFantasia(req.getFantasia());
		p.setPrezzo(req.getPrezzo());
		



		if (req.getMagliettaReq().getLunghezzaManica() != "" && req.getMagliettaReq().getTaglia() != ""
				&& req.getMagliettaReq().getTipoColletto() != "" && req.getMagliettaReq().getVestibilita() != "") {
			MagliettaReq m = new MagliettaReq();
	

			m.setLunghezzaManica(req.getMagliettaReq().getLunghezzaManica());
			m.setTaglia(req.getMagliettaReq().getTaglia());
			m.setTipoColletto(req.getMagliettaReq().getTipoColletto());
			m.setVestibilita(req.getMagliettaReq().getVestibilita());
			p.setMagliettaReq(m);

		}
		
		if (req.getCamiciaReq().getLunghezzaManica() != "" && req.getCamiciaReq().getTaglia() != ""
				&& req.getCamiciaReq().getTipoColletto() != "" && req.getCamiciaReq().getVestibilita() != "") {
			CamiciaReq c = new CamiciaReq();
			c.setLunghezzaManica(req.getCamiciaReq().getLunghezzaManica());
			c.setTaglia(req.getCamiciaReq().getTaglia());
			c.setTipoColletto(req.getCamiciaReq().getTipoColletto());
			c.setVestibilita(req.getCamiciaReq().getVestibilita());
			p.setCamiciaReq(c);
		}
		
		if (req.getVestitoReq().getLunghezzaManica() != "" && req.getVestitoReq().getTaglia() != ""
				&& req.getVestitoReq().getLunghezza() != "" && req.getVestitoReq().getVestibilita() != "") {
			
			VestitoReq v = new VestitoReq();
			v.setLunghezzaManica(req.getVestitoReq().getLunghezzaManica());
			v.setTaglia(req.getVestitoReq().getTaglia());
			v.setLunghezza(req.getVestitoReq().getLunghezza());
			v.setVestibilita(req.getVestitoReq().getVestibilita());
			p.setVestitoReq(v);
		}
		
		if (req.getPantaloneReq().getTaglia() != ""
				&& req.getPantaloneReq().getLunghezza() != "" && req.getPantaloneReq().getVestibilita() != "") {
			PantaloneReq pant = new PantaloneReq();
			pant.setTaglia(req.getPantaloneReq().getTaglia());
			pant.setLunghezza(req.getPantaloneReq().getLunghezza());
			pant.setVestibilita(req.getPantaloneReq().getVestibilita());
			p.setPantaloneReq(pant);
		}
		
//		log.debug(req.getScarpaReq().getChiusura() + "Chiusura");
//		log.debug(req.getScarpaReq().getTipoScarpa() + "Tipooo");
//		log.debug(req.getScarpaReq().getTagliaScarpe().toString());
		
		
		
		if (req.getScarpaReq().getChiusura() != "" && req.getScarpaReq().getTipoScarpa() != ""
				&& req.getScarpaReq().getTagliaScarpe() > 0) {
			
			ScarpaReq s = new ScarpaReq();
			s.setChiusura(req.getScarpaReq().getChiusura());
			s.setTagliaScarpe(req.getScarpaReq().getTagliaScarpe());
			s.setTipoScarpa(req.getScarpaReq().getTipoScarpa());
			p.setScarpaReq(s);

		}
		
		
		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/createOrUpdate").buildAndExpand().toUri();
		//log.debug("Uri: " + uri);
		
		ResponseBase resp = rest.postForEntity(uri, p, Response.class).getBody();

		
		log.debug("return code: " + resp.getRc());
		log.debug("Message: " + resp.getMsg());
		
		if (!resp.getRc()) {
			ModelAndView mav = new ModelAndView("nuovo-prodotto");
			mav.addObject("prodotto",p);
			return mav;
		}
		
		return "redirect:/index";	
	}

	@GetMapping("/profilo")
	public ModelAndView profilo() {
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); 
        }
		
		
		ModelAndView mav = new ModelAndView("profilo");
		
		URI uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/utente/searchByMail")
				.queryParam("mail", email)
				.buildAndExpand().toUri();
		
		 ResponseObject<?> respObj = rest.getForEntity(uri, ResponseObject.class).getBody();
		 
		 mav.addObject("utente", respObj);
		return mav;
	}

	@GetMapping("/createAdmin")
	public ModelAndView createAdmin() {
		ModelAndView mav = new ModelAndView("create-admin");
		UtenteReq admin = new UtenteReq();
		mav.addObject("admin", admin);
		return mav;
	}
	
	@PostMapping("/saveAdmin")
	public Object saveAdmin(@ModelAttribute("admin") UtenteReq req) {
		log.debug("Nome utente: " + req.getNome());
		log.debug("Ruolo: " + req.getRuolo());
		
		req.setRuolo("AMMINISTRATORE");
		UtenteReq admin = req;
		
		log.debug("Ruolo: " + admin.getRuolo());
		
		URI uri = UriComponentsBuilder
				.fromHttpUrl(backend + "/utente/createOrUpdate")
				.buildAndExpand().toUri();
		
		
		ResponseBase resp = rest.postForEntity(uri, admin, ResponseBase.class).getBody();
		log.debug("Nome: " + admin.getNome());
		log.debug("rc: " + resp.getRc());
		log.debug("rc: " + resp.getMsg());
		
		
		if (!resp.getRc()) {
			ModelAndView mav = new ModelAndView("create-admin");
			mav.addObject("admin", admin);
			mav.addObject("error", resp.getMsg());
			return mav;
		}
		
		//in questo modo voglio dico che voglio aggiornare la pagina e tornare in listSocio
		return "redirect:/index";
	}

	@GetMapping("/logout")
	public String logoutPage(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
