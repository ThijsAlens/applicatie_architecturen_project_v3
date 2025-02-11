package klusje_v3.service;

import java.util.ArrayList;

import klusje_v3.model.Biedingen;
import klusje_v3.model.Klus;

public interface BiedingenService {
	public ArrayList<Biedingen> getAllBiedingen();
	public ArrayList<String> getGebodenKlusjesmannenUsernameByKlus(Klus k);
	public void removeBiedingenByKlusId(int klusId);
	public void addBieding(Biedingen bieding);
	public void removeBiedingenByKlusIdAndKlusjesmanUsername(int klusId, String username);
}
