package klusje_v3.service;

import java.util.ArrayList;

import klusje_v3.model.Klus;

public interface BiedingenService {
	public ArrayList<String> getGebodenKlusjesmannenUsernameByKlus(Klus k);
}
