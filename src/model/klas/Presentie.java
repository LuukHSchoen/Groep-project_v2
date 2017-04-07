package model.klas;

public class Presentie {
	private ArrayList<Student> lStudentenVanKlas;
	private ArrayList<College> deColleges;
	private PrIS informatieSysteem;

	public Presentie(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void ophalen(Conversation conversation) {
		Student lStudentZelf = informatieSysteem.getStudent(lGebruikersnaam);
		Klas lKlas = informatieSysteem.getKlasVanStudent(lStudentZelf);
		College lCollege = 
	}

	}
	
}
