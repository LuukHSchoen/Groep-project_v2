
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

public class PresentieAanpassenvervolgController implements Handler {
	private PrIS informatieSysteem;
	
	public PresentieAanpassenvervolgController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-PresentieAanpassen-vervol/studenten")) {
				studentenabsenties(conversation);
			}
	}
	
	private void studentenabsenties(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		
		String lGebruikersnaam = lJsonObjIn.getString("username");
		String destring = lJsonObjIn.getString("destring");	
	  	Docent dedocent = informatieSysteem.getDocent(lGebruikersnaam);	
	  	Sessie desessie = informatieSysteem.vergelijkDeSessie(destring);
	  	
	  	JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	
	  	
	  	for(Presentie depresenties : desessie.getCollege().getdePresentie()){
	  		int studentid = depresenties.getStudent().getStudentNummer();
	  		String voornaam = depresenties.getStudent().getVoornaam();
	  		String achternaam = depresenties.getStudent().getVolledigeAchternaam();
	  		String reden = "" + depresenties.getredenAbsentie();
	  		boolean absentie = depresenties.getPresentie();
	  		JsonObjectBuilder lJsonObjectBuilderstudent = Json.createObjectBuilder(); // maak het JsonObject voor een student
	  		lJsonObjectBuilderstudent
			.add("studentid", studentid)
	  		.add("voornaam", voornaam)
	  		.add("achternaam", achternaam)
	  		.add("reden", reden)
	  		.add("absentie", absentie);
	  lJsonArrayBuilder.add(lJsonObjectBuilderstudent);	
		
		
	}
	String lJsonOutstudenten = lJsonArrayBuilder.build().toString();												// maak er een string van
	conversation.sendJSONMessage(lJsonOutstudenten);

	  	}
	}	

