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
import model.klas.Sessie;
import server.Conversation;
import server.Handler;

public class PresentieAanpassenController implements Handler {
	private PrIS informatieSysteem;
	
	public PresentieAanpassenController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	//"/my-presentiesAanpassen/sessie"
	public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-presentiesAanpassen/ophalen")) {
				presentiesaanpassen(conversation);
			}
		  else{
				sessiesaanpassen(conversation);
		  }
		}
	
	private void presentiesaanpassen(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		
		String lGebruikersnaam = lJsonObjIn.getString("username");
	  	String datum = lJsonObjIn.getString("datum");
	  	Docent dedocent = informatieSysteem.getDocent(lGebruikersnaam);
	  	ArrayList<String> deklassen = informatieSysteem.getKlassenvanDocent(dedocent);
	  	
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	
		
		for(String klascode : deklassen){
		JsonObjectBuilder lJsonObjectBuilderklascodes = Json.createObjectBuilder(); // maak het JsonObject voor een student
		lJsonObjectBuilderklascodes
			.add("klascode", klascode);	     
		
	  lJsonArrayBuilder.add(lJsonObjectBuilderklascodes);													//voeg het JsonObject aan het array toe				     
	}
		
String lJsonOutklascodes = lJsonArrayBuilder.build().toString();												// maak er een string van
conversation.sendJSONMessage(lJsonOutklascodes);					
}
	private void sessiesaanpassen(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		
		Klas gekozenklas = null;
		
		String lGebruikersnaam = lJsonObjIn.getString("username");
	  	String datum = lJsonObjIn.getString("datum");
	  	String klascode = lJsonObjIn.getString("klas");
	  	
	  	Docent dedocent = informatieSysteem.getDocent(lGebruikersnaam);
	  	ArrayList<Klas> deklassen = informatieSysteem.getKlassenvanDocentmetKlas(dedocent);
	  	ArrayList<String> deklascodes = informatieSysteem.getKlassenvanDocent(dedocent);
		for (Klas dejuisteklas : deklassen){
			if (dejuisteklas.getKlasCode().equals(klascode)){
				gekozenklas = dejuisteklas;
				
			}
		}
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	
		ArrayList<Sessie> desessies = informatieSysteem.getSessiesOpDatumEnKlas(datum, gekozenklas.getKlasCode());
		for (Sessie deSessie : desessies) {
			String decursuscode = deSessie.getCursus().getcursusCode();
			String deklascode = deSessie.getKlas().getKlasCode();
			String detijd = deSessie.getCollege().getBeginEnEindTijd();
			String samen = decursuscode + " - " + deklascode + " - " + detijd;
			JsonObjectBuilder lJsonObjectBuilderString = Json.createObjectBuilder(); // maak het JsonObject voor een student
			lJsonObjectBuilderString
				.add("destring", samen);																//vul het JsonObject		     

		  lJsonArrayBuilder.add(lJsonObjectBuilderString);	
			
			
		}
	}
}