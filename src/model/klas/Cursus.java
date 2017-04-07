package model.klas;


public class Cursus {
	private String cursusCode;
	private String vaknaam;
	
	public Cursus (String cC, String vm) {
		cursusCode = cC;
		vaknaam = vm;
	}

	public String getcursusCode() {
		return cursusCode;
	}
	
	public String getvaknaam(){
		return vaknaam;
	}
}
