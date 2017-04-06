package model.klas;

import java.time.LocalDate;
import java.util.ArrayList;
import model.persoon.Docent;
import model.persoon.Student;

public class College {
	private LocalDate datum;
	private ArrayList<Presentie> collegePresentie = new ArrayList <Presentie>();
	private ArrayList<Klas> deKlassen = new ArrayList<Klas>();
	private Docent deDocent;
	
	public College(LocalDate dt){
		this.datum = dt;
	}
	
	public void setDatum(LocalDate dt){
		this.datum = dt;
	}
	
	public void setDocent(Docent d){
		this.deDocent = d;
	}
	
	public LocalDate getDatum(){
		return datum;
	}
	
	public Docent getDocent(){
		return deDocent;
	}
	public Klas getKlasVanStudent(Student pStudent) {
	  //used
		for (Klas lKlas : deKlassen) {
			if (lKlas.bevatStudent(pStudent)){
				return (lKlas);
			}
		}
		return null;
	}
	
	public void presentieWijzigen(ArrayList<Presentie> pPresentie){
		collegePresentie = pPresentie;
	}
	public ArrayList<Presentie> presentieInzien(){
		return collegePresentie; }
}