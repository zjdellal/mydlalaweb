package com.dlalaweb.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import com.dlalacore.dlala.entities.Fiche;
import com.dlalaweb.service.client.RestClient;

public class FicheService {
	private String						fiches			= "fichesbyid/";
	private String						addFiche		= "addFiche/";
	private String						deleteFiche	= "deleteFiche/";
	private RestClient<Fiche>	client;

	public FicheService() {
		client = new RestClient<>();
	}

	public List<Fiche> getFichesByIdPhone(int idPhone) {

		List<Fiche> ficheListe = client.getAll(fiches + idPhone, new ParameterizedTypeReference<List<Fiche>>() {
		});
		if (client.getStatus().equals(HttpStatus.NOT_FOUND)) {
			return null;
		}
		return ficheListe;

	}

	public Fiche save(Fiche fiche) {

		Fiche ficheSer = client.post(addFiche, fiche, new ParameterizedTypeReference<Fiche>() {
		});
		return ficheSer;
	}

	@SuppressWarnings("static-access")
	public HttpStatus deleteFiche(Integer idFiche) {
		@SuppressWarnings("unused")
		Fiche ficheSer = client.get(deleteFiche+idFiche, new ParameterizedTypeReference<Fiche>() {
		});
		if (client.getStatus().equals(HttpStatus.NOT_FOUND)) {
			return client.getStatus().NOT_FOUND;
		}
		return client.getStatus().OK;
	}
}