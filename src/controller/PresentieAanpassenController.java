package controller;

import javax.json.JsonObject;

import model.PrIS;
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
	}
}