package model.klas;


public class Cursus {
	private String cursusCode;
	private String vaknaam;
	
	public Cursus (String cC, String vm) {
		this.cursusCode = cC;
		this.vaknaam = vm;
	}

	public String getcursusCode() {
		return this.cursusCode;
	}
	
	public String getvaknaam(){
		return this.vaknaam;
	}
}
