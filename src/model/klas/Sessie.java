package model.klas;

import model.persoon.Docent;

public class Sessie {
	private Klas deKlas;
	private College hetCollege;
	private Docent deDocent;
	private Cursus deCursus;

	public Sessie(College hC, Klas dK, Docent dD, Cursus dC) {
		hetCollege = hC;
		deKlas = dK;
		deDocent = dD;
		deCursus = dC;
	}

	public Docent getDocent() {
		return deDocent;
	}

	public Cursus getCursus() {
		return deCursus;
	}

	public Klas getKlas() {
		return deKlas;
	}

	public College getCollege() {
		return hetCollege;
	}
}