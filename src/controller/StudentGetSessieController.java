package controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.klas.Klas;
import model.klas.Presentie;
import model.klas.Sessie;
import model.persoon.Student;
import server.Conversation;
import server.Handler;

public class StudentGetSessieController implements Handler {
	private PrIS informatieSysteem;
	
	public StudentGetSessieController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
	  if (conversation.getRequestedURI().startsWith("/my-absent-CollegeSelecteren/sessiesinfo")) {
			sessiesinfo(conversation);
		}
	  if (conversation.getRequestedURI().startsWith("/my-absent-CollegeSelecteren/sessiesopslaan")) {
			sessiesopslaan(conversation);
	  }
	}
	
	private void sessiesopslaan(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
	  	JsonArray arraysessies = lJsonObjIn.getJsonArray("colleges");
	  	String lGebruikersnaam = lJsonObjIn.getString("username");
	  	String datum = lJsonObjIn.getString("datum");
		String reden = lJsonObjIn.getString("reden");
	  	
	  	Klas deklas = informatieSysteem.getKlasVanStudent(informatieSysteem.getStudent(lGebruikersnaam));
	  	ArrayList<Sessie> desessies = informatieSysteem.getSessiesOpDatumEnKlas(datum, deklas.getKlasCode());
	  	
	  	if (arraysessies != null) { 
	    	for (int i=0;i<arraysessies.size();i++){
	    		JsonObject sessie = arraysessies.getJsonObject(i);
	    		boolean absent = sessie.getBoolean("absent");
	    		if (absent == false) {
	    			for(Sessie getsessie : desessies){
	    				for (Presentie depresentie : getsessie.getCollege().getdePresentie()){
	    					if (depresentie.getStudent().equals(informatieSysteem.getStudent(lGebruikersnaam))){
	    						depresentie.setPresentieDoorStudent(false, reden);
	    					}
	  			}
	    		}
	    		}
	    	}
	  	}
	  	JsonObjectBuilder lJob = Json.createObjectBuilder(); 
	  	lJob.add("errorcode", 0);
	   	//nothing to return use only errorcode to signal: ready!
	  	String lJsonOutStr = lJob.build().toString();
	 		conversation.sendJSONMessage(lJsonOutStr);	
	}
	  	
	
  private void sessiesinfo(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		
  
  	String lGebruikersnaam = lJsonObjIn.getString("username");
  	String datum = lJsonObjIn.getString("datum");
  	

  	Klas deklas = informatieSysteem.getKlasVanStudent(informatieSysteem.getStudent(lGebruikersnaam));
  	ArrayList<Sessie> desessies = informatieSysteem.getSessiesOpDatumEnKlas(datum, deklas.getKlasCode());
  	
  	
  	
  	// Array gemistencolleges = gekregen.functie(datum, gebruikersnaam)

		
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	

		for (Sessie deSessie : desessies) {
				for (Presentie depresentie : deSessie.getCollege().getdePresentie()){
					if (depresentie.getStudent().equals(informatieSysteem.getStudent(lGebruikersnaam))){
						boolean aanwezig = depresentie.getPresentie();
		
				String cursuscode = deSessie.getCursus().getcursusCode();
				String begineneindtijd = deSessie.getCollege().getBeginEnEindTijd();
				JsonObjectBuilder lJsonObjectBuildergeminstesessie = Json.createObjectBuilder(); // maak het JsonObject voor een student
				lJsonObjectBuildergeminstesessie
					.add("cursuscode", cursuscode)																//vul het JsonObject		     
					.add("tijd", begineneindtijd)
					.add("aanwezig", aanwezig);
				
			  lJsonArrayBuilder.add(lJsonObjectBuildergeminstesessie);													//voeg het JsonObject aan het array toe				     
			}
				}
		}
		String lJsonOutGemisteSessie = lJsonArrayBuilder.build().toString();												// maak er een string van
		conversation.sendJSONMessage(lJsonOutGemisteSessie);					
		}
		
										// terugsturen naar de Polymer-GUI!	}

}

