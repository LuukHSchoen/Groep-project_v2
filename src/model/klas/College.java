package model.klas;

import java.util.ArrayList;

public class College {
	private String datum;
	private String begintijd, eindtijd;
	private ArrayList<Presentie> deAanwezige;
	
	public College(String lD, String bd, String ed, ArrayList<Presentie> dA){
		this.datum = lD;
		this.begintijd = bd;
		this.eindtijd = ed;
		this.deAanwezige = dA;
	}
	
	public String getDatum(){
		return datum;
	}
	
	public String getBeginEnEindTijd(){
		return (begintijd + "-" + eindtijd);
	}
	
	public ArrayList<Presentie> getdePresentie(){
		return deAanwezige;
	}
	
	//De studenten van de Sessie
	public void setdeAanwezige (ArrayList<Presentie> dA){
		deAanwezige = dA;
	}
}