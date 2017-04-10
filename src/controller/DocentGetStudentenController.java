package controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
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
	  	
	  	Klas deklas = informatieSysteem.getDocentKlass(informatieSysteem.getStudent(datum,lGebruikersnaam));
	  	
	  	JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();	
	  	
	  	JsonObjectBuilder lJsonObjectBuildergeminstesessie = Json.createObjectBuilder();
	  	
	  	String cursuscode = "TICT-V1OODC-15_2016";
	  	String klascode = "TICT-SIE-V1D";
	  	String tijd = "11:00 tot 12:00";
	  	
	  	lJsonObjectBuildergeminstesessie
		.add("cursuscode", cursuscode)																//vul het JsonObject		     
		.add("klascode", klascode)
		.add("tijd", tijd);
	
	  	lJsonArrayBuilder.add(lJsonObjectBuildergeminstesessie);	
	  	
	  	String lJsonOutGemisteSessie = lJsonArrayBuilder.build().toString();												// maak er een string van
		conversation.sendJSONMessage(lJsonOutGemisteSessie);
		
		
		
	}
}
