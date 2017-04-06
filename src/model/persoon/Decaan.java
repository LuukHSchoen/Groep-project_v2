package model.persoon;

public class Decaan extends Persoon {
	private int decaanNummer;

	public Decaan(String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String gebruikersnaam,
			int decNr) {
		super(voornaam, tussenvoegsel, achternaam, wachtwoord, gebruikersnaam);
		decaanNummer = 0;
	}

	//public String getProbleemGeval() {
		//return probleemGeval;	}

	public int getDecaanNummer() {
		return decaanNummer;
	}

	public void setDecaanNummer(int decNr) {
		this.decaanNummer = decNr;
	}
}
