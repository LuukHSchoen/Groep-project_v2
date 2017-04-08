package controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.klas.Klas;
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
	  if (conversation.getRequestedURI().startsWith("/my-absent/sessiesinfo")) {
			sessiesinfo(conversation);
		}
	}
	
  private void sessiesinfo(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
  
  	String lGebruikersnaam = lJsonObjIn.getString("username");
  	String datum = lJsonObjIn.getString("datum");
  	String reden = lJsonObjIn.getString("reden");
  	
  	Klas deklas = informatieSysteem.getKlasVanStudent(informatieSysteem.getStudent(lGebruikersnaam));
  	ArrayList<Sessie> desessies = informatieSysteem.getSessiesOpDatumEnKlas(datum, deklas.getKlasCode());
  	
  	
  	// Array gemistencolleges = gekregen.functie(datum, gebruikersnaam)

		
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	

		for (Sessie deSessie : desessies) {									        // met daarin voor elke medestudent een JSON-object... 
				String cursuscode = deSessie.getCursus().getcursusCode();
				String begineneindtijd = deSessie.getCollege().getBeginEnEindTijd();
				JsonObjectBuilder lJsonObjectBuildergeminstesessie = Json.createObjectBuilder(); // maak het JsonObject voor een student
				lJsonObjectBuildergeminstesessie
					.add("cursuscode", cursuscode)																//vul het JsonObject		     
					.add("tijd", begineneindtijd);	
			  lJsonArrayBuilder.add(lJsonObjectBuildergeminstesessie);													//voeg het JsonObject aan het array toe				     
			}
		String lJsonOutGemisteSessie = lJsonArrayBuilder.build().toString();												// maak er een string van
		conversation.sendJSONMessage(lJsonOutGemisteSessie);					
		}
		
										// terugsturen naar de Polymer-GUI!	}
  }

