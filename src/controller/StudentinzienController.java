package controller;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


import model.PrIS;
import model.klas.Klas;
import model.klas.Presentie;
import model.klas.Sessie;
import server.Conversation;
import server.Handler;

public class StudentinzienController implements Handler {
		private PrIS informatieSysteem;
		public StudentinzienController(PrIS infoSys) {
			informatieSysteem = infoSys;
		}

		public void handle(Conversation conversation) {
		  if (conversation.getRequestedURI().startsWith("/my-presentiesinzien/ophalen")) {
				ophalen(conversation);
			}
		  }
		
		private void ophalen(Conversation conversation) {
			JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
			
			boolean aanwezig = true;
		  	String lGebruikersnaam = lJsonObjIn.getString("username");
		  	String datum = lJsonObjIn.getString("datum");
		  	

		  	Klas deklas = informatieSysteem.getKlasVanStudent(informatieSysteem.getStudent(lGebruikersnaam));
		  	ArrayList<Sessie> desessies = informatieSysteem.getSessiesOpDatumEnKlas(datum, deklas.getKlasCode());
		  	
		  	
		  	
		  	// Array gemistencolleges = gekregen.functie(datum, gebruikersnaam)

				
				JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	

				for (Sessie deSessie : desessies) {
						for (Presentie depresentie : deSessie.getCollege().getdePresentie()){
							if (depresentie.getStudent().equals(informatieSysteem.getStudent(lGebruikersnaam))){
								aanwezig = depresentie.getPresentie();
							}
						}
				
						String cursuscode = deSessie.getCursus().getcursusCode();
						String begineneindtijd = deSessie.getCollege().getBeginEnEindTijd();
						JsonObjectBuilder lJsonObjectBuildergeminstesessie2 = Json.createObjectBuilder(); // maak het JsonObject voor een student
						lJsonObjectBuildergeminstesessie2
							.add("cursuscode", cursuscode)																//vul het JsonObject		     
							.add("tijd", begineneindtijd)
							.add("aanwezig", aanwezig);
						
					  lJsonArrayBuilder.add(lJsonObjectBuildergeminstesessie2);													//voeg het JsonObject aan het array toe				     
					}
						
				String lJsonOutGemisteSessie2 = lJsonArrayBuilder.build().toString();												// maak er een string van
				conversation.sendJSONMessage(lJsonOutGemisteSessie2);					
				}
				
												

		}
