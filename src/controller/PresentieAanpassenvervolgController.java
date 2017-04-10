
package controller;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
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

public class PresentieAanpassenvervolgController implements Handler {
	private PrIS informatieSysteem;
	
	public PresentieAanpassenvervolgController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-PresentieAanpassen-vervolg/studenten")) {
				studentenabsenties(conversation);
			}
		  if (conversation.getRequestedURI().startsWith("/my-PresentieAanpassen-vervolg/studentenopslaan")){
			  studentenopslaan(conversation);
		  }
	}
	private void studentenopslaan(Conversation conversation){
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
	  	String lGebruikersnaam = lJsonObjIn.getString("username");
	  	String destring = lJsonObjIn.getString("destring");
	  	String dedatum = lJsonObjIn.getString("dedatum");
	  	JsonArray arraystudenten = lJsonObjIn.getJsonArray("studenten");
	  	
	  	Sessie desessies = informatieSysteem.vergelijkDeSessie(destring,dedatum);
	    int studentid = 0;

	  	
		
	  	if (arraystudenten != null) { 
	  		
	    	for (int i=0;i<arraystudenten.size();i++){
	    		JsonObject sessie = arraystudenten.getJsonObject(i);
	    		studentid = sessie.getInt("studentid");
	    		boolean absent = sessie.getBoolean("absentie");
	    		for (Presentie depresentie : desessies.getCollege().getdePresentie()){
	    			if (depresentie.getStudent().getStudentNummer()== studentid){
	    			depresentie.setPresentieDoorDocent(absent);
	    					}
	  			}
	    	}
	}
	}
	
	
	private void studentenabsenties(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		
		String lGebruikersnaam = lJsonObjIn.getString("username");
		String destring = lJsonObjIn.getString("destring");
		String dedatum = lJsonObjIn.getString("dedatum");
	  	Docent dedocent = informatieSysteem.getDocent(lGebruikersnaam);	
	  	
	  	Sessie desessie = informatieSysteem.vergelijkDeSessie(destring,dedatum);
	  	
	  	JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	
	  	
	  	for(Presentie depresenties : desessie.getCollege().getdePresentie()){
	  		int studentid = depresenties.getStudent().getStudentNummer();
	  		String voornaam = depresenties.getStudent().getVoornaam();
	  		String achternaam = depresenties.getStudent().getVolledigeAchternaam();
	  		String reden = "" + depresenties.getredenAbsentie();
	  		String naam = voornaam + "" + achternaam;
	  		boolean absentie = depresenties.getPresentie();
	  		JsonObjectBuilder lJsonObjectBuilderstudent = Json.createObjectBuilder(); // maak het JsonObject voor een student
	  		lJsonObjectBuilderstudent
			.add("studentid", studentid)
	  		.add("voornaam", naam)
	  		.add("reden", reden)
	  		.add("absentie", absentie);
	  lJsonArrayBuilder.add(lJsonObjectBuilderstudent);	
		
	}
	String lJsonOutstudenten = lJsonArrayBuilder.build().toString();												// maak er een string van
	conversation.sendJSONMessage(lJsonOutstudenten);

	  	}
	}	

