package controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import model.PrIS;
import model.persoon.Docent;
import model.persoon.Student;
import model.klas.Klas;
import model.klas.Sessie;
import server.Conversation;
import server.Handler;

public class DocentGetStudentenController implements Handler {
	private PrIS informatieSysteem;
	
	public DocentGetStudentenController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	
	public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-presentieInvoeren/ophalen")) {
				collegeophalen(conversation);
			}
		}
	
	private void collegeophalen(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		
		String lGebruikersnaam = lJsonObjIn.getString("username");		
	  	String datum = lJsonObjIn.getString("datum");
	  	
	  	ArrayList<String> colleges = informatieSysteem.getSessieDocent(datum, lGebruikersnaam);
	  	
	  		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
	  		String test = "test1234";
	  		
	  			JsonObjectBuilder lJsonObjectBuildergeminstesessie = Json.createObjectBuilder();
	  			lJsonObjectBuildergeminstesessie.add("college", test);
	  			
	  			lJsonArrayBuilder.add(lJsonObjectBuildergeminstesessie); 			
	  			
	  		
	  		
	  		String lJsonOutGemisteSessie = lJsonArrayBuilder.build().toString();												// maak er een string van
			conversation.sendJSONMessage(lJsonOutGemisteSessie);
	  			
	}
}
