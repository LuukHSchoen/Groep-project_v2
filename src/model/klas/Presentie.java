package model.klas;

import java.util.ArrayList;

import javax.json.JsonObject;

import model.PrIS;
import model.persoon.Student;
import server.Conversation;

public class Presentie {
	private ArrayList<Student> lStudentenVanKlas;
	private Cursus deCursus;
	private PrIS informatieSysteem;

	public Presentie(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void ophalen(Conversation conversation) {
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String lGebruikersnaam = lJsonObjectIn.getString("username");
		Student lStudentZelf = informatieSysteem.getStudent(lGebruikersnaam);
		Klas lKlas = informatieSysteem.getKlasVanStudent(lStudentZelf);
		String lCursus = deCursus.getNaam();
	}
}
	
