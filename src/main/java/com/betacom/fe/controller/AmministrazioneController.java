package com.betacom.fe.controller;

import java.io.Console;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
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
import com.betacom.fe.request.VestitoReq;
import com.betacom.fe.response.Response;
import com.betacom.fe.response.ResponseBase;

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

		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/createOrUpdate").buildAndExpand().toUri();
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
		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/marca/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listMarca = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/colore/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listColore = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/fantasia/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listFantasia = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/materiale/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listMateriale = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/lunghezzaManica/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listLunghezzaManica = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/taglia/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listTaglia = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/tipoColletto/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listTipoColletto = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/vestibilita/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listVestibilita = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/lunghezza/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

		Response<?> listLunghezza = rest.getForEntity(uri, Response.class).getBody();

		uri = UriComponentsBuilder.fromHttpUrl(backend + "/chiusura/listAll").buildAndExpand().toUri();
		log.debug("URI: " + uri);

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
		ProdottoDTO p = new ProdottoDTO();
		p.setId((req.getId() == null) ? null : req.getId());
		p.setQty(req.getQty());
		p.setSesso(req.getSesso());
		p.setImg("../img/" + req.getImg());
		log.debug(req.getColore());
		p.setColore(req.getColore());
		p.setMarca(req.getMarca());
		p.setMateriale(req.getMateriale());
		p.setFantasia(req.getFantasia());
		p.setPrezzo(req.getPrezzo());


		if (req.getMagliettaReq().getLunghezzaManica() != "" && req.getMagliettaReq().getTaglia() != ""
				&& req.getMagliettaReq().getTipoColletto() != "" && req.getMagliettaReq().getVestibilita() != "") {
			MagliettaDTO m = new MagliettaDTO();

			m.setLunghezzaManica(req.getMagliettaReq().getLunghezzaManica());
			m.setTaglia(req.getMagliettaReq().getTaglia());
			m.setTipoColletto(req.getMagliettaReq().getTipoColletto());
			m.setVestibilita(req.getMagliettaReq().getVestibilita());
			p.setMagliettaDto(m);
		}
		
		if (req.getCamiciaReq().getLunghezzaManica() != "" && req.getCamiciaReq().getTaglia() != ""
				&& req.getCamiciaReq().getTipoColletto() != "" && req.getCamiciaReq().getVestibilita() != "") {
			CamiciaDTO c = new CamiciaDTO();
			c.setLunghezzaManica(req.getCamiciaReq().getLunghezzaManica());
			c.setTaglia(req.getCamiciaReq().getTaglia());
			c.setTipoColletto(req.getCamiciaReq().getTipoColletto());
			c.setVestibilita(req.getCamiciaReq().getVestibilita());
			p.setCamiciaDto(c);
		}
		
		if (req.getVestitoReq().getLunghezzaManica() != "" && req.getVestitoReq().getTaglia() != ""
				&& req.getVestitoReq().getLunghezza() != "" && req.getVestitoReq().getVestibilita() != "") {
			
			VestitoDTO v = new VestitoDTO();
			v.setLunghezzaManica(req.getVestitoReq().getLunghezzaManica());
			v.setTaglia(req.getVestitoReq().getTaglia());
			v.setLunghezza(req.getVestitoReq().getLunghezza());
			v.setVestibilita(req.getVestitoReq().getVestibilita());
			p.setVestitoDto(v);
		}
		
		if (req.getPantaloneReq().getTaglia() != ""
				&& req.getPantaloneReq().getLunghezza() != "" && req.getPantaloneReq().getVestibilita() != "") {
			PantaloneDTO pant = new PantaloneDTO();
			pant.setTaglia(req.getPantaloneReq().getTaglia());
			pant.setLunghezza(req.getPantaloneReq().getLunghezza());
			pant.setVestibilita(req.getPantaloneReq().getVestibilita());
			p.setPantaloneDto(pant);
		}
		
		if (req.getScarpaReq().getChiusura() != "" && req.getScarpaReq().getTipoScarpa() != ""
				&& req.getScarpaReq().getTagliaScarpe() > 0) {
			ScarpaDTO s = new ScarpaDTO();
			s.setChiusura(req.getScarpaReq().getChiusura());
			s.setTagliaScarpe(req.getScarpaReq().getTagliaScarpe());
			s.setTipoScarpa(req.getScarpaReq().getTipoScarpa());
			p.setScarpaDto(s);

		}
		
		URI uri = UriComponentsBuilder.fromHttpUrl(backend + "/prodotto/createOrUpdate").buildAndExpand().toUri();
		log.debug("Uri: " + uri);
		
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
