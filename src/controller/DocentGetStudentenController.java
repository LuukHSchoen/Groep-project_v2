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
import model.klas.Presentie;
import model.klas.Sessie;
import server.Conversation;
import server.Handler;

public class DocentGetStudentenController implements Handler {
	private PrIS informatieSysteem;
	
	public DocentGetStudentenController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	
	public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-presentiesInvoeren/ophalen")) {
				collegeophalen(conversation); 
			}
		  if (conversation.getRequestedURI().startsWith("/my-presentiesInvoeren/studenten")){
			  ophalenstudenten(conversation);
		  }
		  if (conversation.getRequestedURI().startsWith("/my-presentiesInvoeren/opslaan")){
	opslaanstudenten(conversation);
		}
}
	private void opslaanstudenten(Conversation conversation){
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
	
	private void ophalenstudenten(Conversation conversation){
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
		


	
	private void collegeophalen(Conversation conversation) {
       JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		
		
		String lGebruikersnaam = lJsonObjIn.getString("username");
	  	String datum = "" + lJsonObjIn.getString("datum");
	  	
	  	Docent dedocent = informatieSysteem.getDocent(lGebruikersnaam);
	  	ArrayList<Sessie> desessies = new ArrayList<Sessie>();
	  	ArrayList<Klas> deklassen = informatieSysteem.getKlassenvanDocentmetKlas(dedocent);
	  	for (Klas deklas : deklassen){
	  		for (Sessie deSessie : informatieSysteem.getSessiesOpDatumEnKlas(datum, deklas.getKlasCode())){
	  			desessies.add(deSessie);
	  		}
	  	}
	  	
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	
	
		for (Sessie des : desessies) {
			String decursuscode = des.getCursus().getcursusCode();
			String deklascode = des.getKlas().getKlasCode();
			String detijd = des.getCollege().getBeginEnEindTijd();
			String samen = decursuscode + " - " + deklascode + " - " + detijd;
			JsonObjectBuilder lJsonObjectBuilderString = Json.createObjectBuilder(); // maak het JsonObject voor een student
			lJsonObjectBuilderString
				.add("destring", samen);																//vul het JsonObject		     

		  lJsonArrayBuilder.add(lJsonObjectBuilderString);	
			
			
		}
		String lJsonOutDesessies = lJsonArrayBuilder.build().toString();												// maak er een string van
		conversation.sendJSONMessage(lJsonOutDesessies);
	}
	  			
	}

