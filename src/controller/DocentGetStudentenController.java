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
		  if (conversation.getRequestedURI().startsWith("/my-presentieInvoeren/sessiesinfo")) {
				sessiesinfo(conversation);
			}
		}
	

}
