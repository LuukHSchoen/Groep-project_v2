package model.klas;

public class Cursus {
	private String naam;
	
	private Cursus (String nm) {
		naam = nm;
	}

	public void setNaam(String nm) {
		naam = nm;
	}

	public String getNaam() {
		return naam;
	}
}
