package controller;

import java.util.Calendar;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import server.Conversation;
import server.Handler;

public class StudentGetSessieController implements Handler {
	private PrIS informatieSysteem;
	
	public StudentGetSessieController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
	  if (conversation.getRequestedURI().startsWith("/my-absentCollegesKrijgen/collegeinfo")) {
			collegeinfo(conversation);
		}
	}
	
  private void collegeinfo(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
  
  	String lGebruikersnaam = lJsonObjIn.getString("username");
  	String lDatum = lJsonObjIn.getString("datum");
  	
  	
  	// Array gemistencolleges = gekregen.functie(datum, gebruikersnaam)

		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		
		lJsonObjectBuilder 
		
			.add("college", informatieSysteem.getStudent(lGebruikersnaam).getVolledigeAchternaam());

		String lJsonOut = lJsonObjectBuilder.build().toString();
		
		conversation.sendJSONMessage(lJsonOut);										// terugsturen naar de Polymer-GUI!	}
  }
}
