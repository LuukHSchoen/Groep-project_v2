package controller;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.persoon.Docent;
import model.persoon.Student;
import model.klas.Klas;
import model.klas.Presentie;
import model.klas.Sessie;
import server.Conversation;
import server.Handler;

public class PresentieInzienController implements Handler {
	private PrIS informatieSysteem;
	
	public PresentieInzienController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-overzicht/ophalen")) {
				presentiesaanpassen(conversation);
			}
		}
	
	private void presentiesaanpassen(Conversation conversation) {
	JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
  	String lGebruikersnaam = lJsonObjIn.getString("username");
  	String begindatum = lJsonObjIn.getString("datumbegin");
  	String eindatum = lJsonObjIn.getString("datumeind");
  	
  	ArrayList<Klas> deklassen = informatieSysteem.getKlassen();
  	ArrayList<Sessie> desessies = informatieSysteem.getSessies();
  	ArrayList<Sessie> degoedeses = new ArrayList<Sessie>();
  	
  	boolean checkeind = false;
  	
  	
	JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	
	
	for (Sessie desessie : desessies){
		
		if (desessie.getCollege().getDatum().equals(begindatum)){
		
			
			while (checkeind == false){
				
				for (Sessie cesessie : desessies){
					
				if (cesessie.getCollege().getDatum().equals(eindatum)){
				checkeind = true;
				break;
				
			
			}
				else{
					degoedeses.add(cesessie);
				}
		}
	}
}
}
	
	for (Klas deklas : deklassen){
		int present = 0;
		int absent = 0;
		ArrayList<Sessie> klassessies = informatieSysteem.filterOpSessieOpKlas(deklas.getKlasCode(),degoedeses);
		for (Sessie sels : klassessies){
			present = present + informatieSysteem.aantalPresentieSessies(sels);
			absent = absent + informatieSysteem.aantalAbsentiesSessies(sels);
		}
  		JsonObjectBuilder lJsonObjectBuilderspresentie = Json.createObjectBuilder(); // maak het JsonObject voor een student
  		lJsonObjectBuilderspresentie
		.add("present", present)
  		.add("absent", absent)
  		.add("klascode", deklas.getKlasCode());
  lJsonArrayBuilder.add(lJsonObjectBuilderspresentie);	
	}
	String lJsonOutDesessies = lJsonArrayBuilder.build().toString();												// maak er een string van
	conversation.sendJSONMessage(lJsonOutDesessies);
}
}