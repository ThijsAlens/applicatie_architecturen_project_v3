package klusje_v3.service;

import java.util.ArrayList;

import klusje_v3.model.Klus;

public interface KlusService {

	public void addKlus(Klus klus);
	public ArrayList<Klus> getAllKlussen();
	public ArrayList<Klus> getAllKlussenByKlantUsername(String username);
	public Float getRatingByKlusjesmanUsername(String klusjesmanUsername);
	public Klus getKlusById(int klusId);
	public void updateKlus(Klus k);
	public ArrayList<Klus> getAllAplicableKlussen();
	public ArrayList<Klus> getAllToegewezenKlussenByKlusjesmanUsername(String username);
	public void deleteKlusById(int klusId);
}
