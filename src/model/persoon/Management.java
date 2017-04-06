package model.persoon;

public class Management extends Persoon {
	private int managementNummer;

	public Management(String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String gebruikersnaam,
			int manNr) {
		super(voornaam, tussenvoegsel, achternaam, wachtwoord, gebruikersnaam);
		this.setManagementNummer(manNr);
	}

	public int getManagementNummer() {
		return managementNummer;
	}

	public void setManagementNummer(int manNr) {
		this.managementNummer = manNr;
	}
}