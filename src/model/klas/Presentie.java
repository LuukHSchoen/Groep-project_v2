package model.klas;

import java.util.ArrayList;

import javax.json.JsonObject;

import model.PrIS;
import model.persoon.Student;
import server.Conversation;

public class Presentie {
	private Student deStudent;
	private Sessie deSessie;
	private boolean present;
	private String redenAbsentie;

	public Presentie(Student dSt) {
		deStudent = dSt;
	}

	public Presentie(Student dSt, boolean pt) {
		deStudent = dSt;
		present = pt;
	}

	public void setPresentieDoorDocent(boolean pt) {
		present = pt;
	}

	// Voor de eerste Use Case
	public void setPresentieDoorStudent(boolean pt, String re) {
		present = pt;
		redenAbsentie = re;
	}

	public boolean getPresentie() {
		return present;
	}

	public String getredenAbsentie() {
		return redenAbsentie;
	}

	public Student getStudent() {
		return deStudent;
	}

	public Sessie getSessie() {
		return deSessie;
	}
}
