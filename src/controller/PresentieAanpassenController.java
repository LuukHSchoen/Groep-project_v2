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
	
	public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-presentiesAanpassen/ophalen")) {
				presentiesaanpassen(conversation);
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
}