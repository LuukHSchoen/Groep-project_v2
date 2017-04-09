package model;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import model.klas.Klas;
import model.klas.Presentie;
import model.klas.Sessie;

public class test {
	public static void main(String[] arg) {
		PrIS infoSysteem = new PrIS();
		String lGebruikersnaam = "robin.vanvlijmen@student.hu.nl";
	  	String datum = "2017-02-06";
	  	boolean aanwezig = false;
	  	
	  	Klas deklas = infoSysteem.getKlasVanStudent(infoSysteem.getStudent(lGebruikersnaam));
	  	ArrayList<Sessie> desessies = infoSysteem.getSessiesOpDatumEnKlas(datum, deklas.getKlasCode());

			for (Sessie deSessie : desessies) {
					for (Presentie depresentie : deSessie.getCollege().getdePresentie()){
						if (depresentie.getStudent().equals(infoSysteem.getStudent(lGebruikersnaam))){
							aanwezig = depresentie.getPresentie();
							
						}
					}
							System.out.println(aanwezig);
									     
				}
					
			
			System.out.println(aanwezig);					
		
			String als = "";
		  	for(Sessie getsessie : desessies){
				for (Presentie depresentie : getsessie.getCollege().getdePresentie()){
					if (depresentie.getStudent().equals(infoSysteem.getStudent(lGebruikersnaam))){
						als = depresentie.getStudent().getGebruikersnaam();
					}
				}
				System.out.println(als);
		  	}
			System.out.println(als);
					// terugsturen naar de Polymer-GUI!	}

	


		
		}
}
		
		
	


