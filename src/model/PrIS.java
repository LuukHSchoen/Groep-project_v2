package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.klas.College;
import model.klas.Cursus;
import model.klas.Klas;
import model.klas.Presentie;
import model.persoon.Decaan;
import model.persoon.Docent;
import model.persoon.Management;
import model.persoon.Student;
import model.klas.Sessie;
//test
public class PrIS {
	private ArrayList<Docent> deDocenten;
	private ArrayList<Student> deStudenten;
	private ArrayList<Klas> deKlassen;
	private ArrayList<Decaan> deDecaan;
	private ArrayList<Management> hetManagement;
	private ArrayList<Cursus> deCursussen;
	private ArrayList<Sessie> deSessies;
	private ArrayList<Presentie> dePresenties;
	
	

	
	/**
	 * De constructor maakt een set met standaard-data aan. Deze data
	 * moet nog uitgebreidt worden met rooster gegevens die uit een bestand worden
	 * ingelezen, maar dat is geen onderdeel van deze demo-applicatie!
	 * 
	 * De klasse PrIS (PresentieInformatieSysteem) heeft nu een meervoudige
	 * associatie met de klassen Docent, Student, Vakken en Klassen Uiteraard kan dit nog veel
	 * verder uitgebreid en aangepast worden! 
	 * 
	 * De klasse fungeert min of meer als ingangspunt voor het domeinmodel. Op
	 * dit moment zijn de volgende methoden aanroepbaar:
	 * 
	 * String login(String gebruikersnaam, String wachtwoord)
	 * Docent getDocent(String gebruikersnaam)
	 * Student getStudent(String gebruikersnaam)
	 * ArrayList<Student> getStudentenVanKlas(String klasCode)
	 * 
	 * Methode login geeft de rol van de gebruiker die probeert in te loggen,
	 * dat kan 'student', 'docent' of 'undefined' zijn! Die informatie kan gebruikt 
	 * worden om in de Polymer-GUI te bepalen wat het volgende scherm is dat getoond 
	 * moet worden.
	 * 
	 */
	public PrIS() {
		deDocenten = new ArrayList<Docent>();
		deStudenten = new ArrayList<Student>();
		deKlassen = new ArrayList<Klas>();
		deDecaan = new ArrayList<Decaan>();
		hetManagement = new ArrayList<Management>();
		deCursussen = new ArrayList<Cursus>();
		deSessies = new ArrayList<Sessie>();

		// Inladen klassen
		vulKlassen(deKlassen);

		// Inladen studenten in klassen
		vulStudenten(deStudenten, deKlassen);

		// Inladen docenten
		vulDocenten(deDocenten);
		
		// Inladen decaan 
		vulDecaan(deDecaan);
		
		// Inladen management
		vulManagement(hetManagement);
		
		//Inladen curussen
		vulCursussen(deCursussen);
		
		//Inlanden Sessies
		vulSessies(deSessies, deDocenten, deKlassen, deCursussen);
	
	} //Einde Pris constructor
	
	//deze method is static onderdeel van PrIS omdat hij als hulp methode 
	//in veel controllers gebruikt wordt
	//een standaardDatumString heeft formaat YYYY-MM-DD
	public static Calendar standaardDatumStringToCal(String pStadaardDatumString) {
		Calendar lCal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			lCal.setTime(sdf.parse(pStadaardDatumString));
		}  catch (ParseException e) {
			e.printStackTrace();
			lCal=null;
		}
		return lCal;
	}
	
	public static Calendar getEersteles() {
		Date hoogste = null;
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String csvFile = "././CSV/" + "rooster" + ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String SplitBy = "-";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] element = line.split(cvsSplitBy);
				String tijd = (element[0]);
				
				String[] newtijd = tijd.split(SplitBy);
				String detijd = newtijd[0] + "-" + newtijd[1] + "-" + newtijd[2];
				Date date = format.parse(detijd);
				
				if (hoogste == null){
					hoogste = date;
			}
				else {
					if (date.before(hoogste)){
						hoogste = date;
					}
				}
		}
			cal.setTime(hoogste);
		}

		 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return cal;
	}
	
	public static Calendar geLaasteles() {
		Date hoogste = null;
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String csvFile = "././CSV/" + "rooster" + ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String SplitBy = "-";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] element = line.split(cvsSplitBy);
				String tijd = (element[0]);
				String[] newtijd = tijd.split(SplitBy);
				String detijd = newtijd[0] + "-" + newtijd[1] + "-" + newtijd[2];
				Date date = format.parse(detijd);
				if (hoogste == null){
					hoogste = date;
			}
				else {
					if (hoogste.before(date)){
						hoogste = date;
					}
				}
		}
			cal.setTime(hoogste);
		}

		 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return cal;
	}

	//deze method is static onderdeel van PrIS omdat hij als hulp methode 
	//in veel controllers gebruikt wordt
	//een standaardDatumString heeft formaat YYYY-MM-DD
	public static String calToStandaardDatumString (Calendar pCalendar) {
		int lJaar = pCalendar.get(Calendar.YEAR);
		int lMaand= pCalendar.get(Calendar.MONTH) + 1;
		int lDag  = pCalendar.get(Calendar.DAY_OF_MONTH);

		String lMaandStr = Integer.toString(lMaand);
		if (lMaandStr.length() == 1) {
			lMaandStr = "0"+ lMaandStr;
		}
		String lDagStr = Integer.toString(lDag);
		if (lDagStr.length() == 1) {
			lDagStr = "0"+ lDagStr;
		}
		String lString = 
				Integer.toString(lJaar) + "-" +
				lMaandStr + "-" +
				lDagStr;
		return lString;
	}
	//Om sessies te krijgen via een datum en een klas
	public ArrayList<Sessie> getSessiesOpDatumEnKlas(String dm, String ks){
		ArrayList<Sessie> getsessies = new ArrayList<Sessie>();
		for(Sessie pSessie : deSessies){
			if (pSessie.getCollege().getDatum().equals(dm) && pSessie.getKlas().getKlasCode().equals(ks)){
				getsessies.add(pSessie);
			}
		}
		return getsessies;
		
	}
	
	//Om een sessie te krijgen via datum, tijd en klas;
	public Sessie getSessieOpDatumEnKlasEntijd (String dm, String ks, String tijd){
		for(Sessie pSessie : deSessies){
			if (pSessie.getCollege().getDatum().equals(dm) && pSessie.getKlas().getKlasCode().equals(ks) && pSessie.getCollege().getBeginEnEindTijd().equals(tijd)){
				return pSessie;
			}
		}
		return null;
	}
	public ArrayList<Sessie> getSessieDocent(String gebruikersnaam, String dm) {
		ArrayList<Sessie> getDocentSessies = new ArrayList<Sessie>();
		for(Sessie pSessie : deSessies){
			if (pSessie.getCollege().getDatum().equals(dm) && pSessie.getDocent().getGebruikersnaam().equals(gebruikersnaam)){
				getDocentSessies.add(pSessie);//hier moet nog iets
			}
		}
		return null;	
	}
	public Docent getDocent(String gebruikersnaam) {
		Docent resultaat = null;
		
		for (Docent d : deDocenten) {
			if (d.getGebruikersnaam().equals(gebruikersnaam)) {
				resultaat = d;
				break;
			}
		}
		
		return resultaat;
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
	
	
	public Klas getKlasOpCode(String pKlascode){
		for (Klas pklas : deKlassen){
			if(pklas.getKlasCode().equals(pKlascode)){
				return pklas;
			}
		}
		return null;
	}
	
	public Cursus getCursusOpCode(String pCursuscode){
		for (Cursus pcursus : deCursussen){
			if(pcursus.getcursusCode().equals(pCursuscode)){
				return pcursus;
			}
		}
		return null;
	}
	
	public Student getStudent(String pGebruikersnaam) {
		// used
		Student lGevondenStudent = null;
		
		for (Student lStudent : deStudenten) {
			if (lStudent.getGebruikersnaam().equals(pGebruikersnaam)) {
				lGevondenStudent = lStudent;
				break;
			}
		}
		
		return lGevondenStudent;
	}
		public Student getStudent(int pStudentNummer) {
		// used
		Student lGevondenStudent = null;
		
		for (Student lStudent : deStudenten) {
		if (lStudent.getStudentNummer()==(pStudentNummer)) {
				lGevondenStudent = lStudent;
				break;
			}
		}
		
		return lGevondenStudent;
	}
	
		public Decaan getDecaan(String gebruikersnaam) {
		// used
		Decaan lGevondenDecaan = null;
		
		for (Decaan lDecaan : deDecaan) {
		if (lDecaan.getGebruikersnaam().equals(gebruikersnaam)) {
				lGevondenDecaan = lDecaan;
				break;
			}
		}
		
		return lGevondenDecaan;
	}
		
		public Decaan getDecaan(int decNr) {
		// used
		Decaan lGevondenDecaan = null;
		
		for (Decaan lDecaan : deDecaan) {
		if (lDecaan.getDecaanNummer()==(decNr)) {
				lGevondenDecaan = lDecaan;
				break;
			}
		}
		
		return lGevondenDecaan;
		
	}
	public Management getManagement(int manNr) {
		// used
		Management lGevondenManagement = null;
		
		for (Management lManagement : hetManagement) {
		if (lManagement.getManagementNummer()==(manNr)) {
				lGevondenManagement = lManagement;
				break;
			}
		}
		
		return lGevondenManagement;
	}
	public Management getManagement(String gebruikersnaam) {
		// used
		Management lGevondenManagement = null;
		
		for (Management lManagement : hetManagement) {
		if (lManagement.getGebruikersnaam().equals(gebruikersnaam)) {
				lGevondenManagement = lManagement;
				break;
			}
		}
		
		return lGevondenManagement;
	}
		
	
	
	
	public String login(String gebruikersnaam, String wachtwoord) {
		for (Docent d : deDocenten) {
			if (d.getGebruikersnaam().equals(gebruikersnaam)) {
				if (d.komtWachtwoordOvereen(wachtwoord)) {
					return "docent";
				}
			}
		}
		
		for (Student s : deStudenten) {
			if (s.getGebruikersnaam().equals(gebruikersnaam)) {
				if (s.komtWachtwoordOvereen(wachtwoord)) {
					return "student";
				}
			}
		}
		
		for (Decaan dc : deDecaan) {
			if (dc.getGebruikersnaam().equals(gebruikersnaam)) {
				if (dc.komtWachtwoordOvereen(wachtwoord)) {
					return "decaan";
				}
			}
		}
		for (Management m : hetManagement) {
			if (m.getGebruikersnaam().equals(gebruikersnaam)) {
				if (m.komtWachtwoordOvereen(wachtwoord)) {
					return "management";
				}
			}
		}
		return "undefined";
	}
	private void vulDocenten(ArrayList<Docent> pDocenten) {
		String csvFile = "././CSV/docenten.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
			
		try {
	
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	
			        // use comma as separator
				String[] element = line.split(cvsSplitBy);
				String gebruikersnaam = element[0].toLowerCase();
				String voornaam = element[1];
				String tussenvoegsel = element[2];
				String achternaam = element[3];
				pDocenten.add(new Docent(voornaam, tussenvoegsel, achternaam, "geheim", gebruikersnaam, 1));
				
				//System.out.println(gebruikersnaam);
		
			}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void vulKlassen(ArrayList<Klas> pKlassen) {
		//TICT-SIE-VIA is de klascode die ook in de rooster file voorkomt
		//V1A is de naam van de klas die ook als file naam voor de studenten van die klas wordt gebruikt
		Klas k1 = new Klas("TICT-SIE-V1A", "V1A");
		Klas k2 = new Klas("TICT-SIE-V1B", "V1B");
		Klas k3 = new Klas("TICT-SIE-V1C", "V1C");
		Klas k4 = new Klas("TICT-SIE-V1D", "V1D");
		Klas k5 = new Klas("TICT-SIE-V1E", "V1E");
		Klas k6 = new Klas("TICT-SIE-V1F", "V1F");
		
		pKlassen.add(k1);
		pKlassen.add(k2);
		pKlassen.add(k3);
		pKlassen.add(k4);
		pKlassen.add(k5);
		pKlassen.add(k6);
	}	
	
	private void vulCursussen(ArrayList<Cursus> pCursussen) {
		
		String csvFile = "././CSV/" + "vakken" + ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try {

			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {

			    // use comma as separator
				String[] element = line.split(cvsSplitBy);
				String cursuscode = (element[0]);
				String vaknaam = (element[1]);
				Cursus lCursus = new Cursus(cursuscode,vaknaam);
				pCursussen.add(lCursus);
		
			}
		}

		 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	private void vulSessies(ArrayList<Sessie> dS, ArrayList<Docent> dD, ArrayList<Klas> dK, ArrayList<Cursus> dC){
		//Vult de klasse Sessie, College en Presentie

		String csvFile = "././CSV/" + "rooster" + ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				ArrayList<Student> destudent = new ArrayList<Student>();
				ArrayList<Presentie> depresentie = new ArrayList<Presentie>();
				Klas deklas = null;
				
				String[] element = line.split(cvsSplitBy);
				String datum = element[0];
				String begintijd = element[1];
				String eindtijd = element[2];
				String vakcode = element[3];
				String gebruikersnaam = element[4];
				String klas = element[6];
				
				for (Klas degoedeklas : deKlassen){
					if (degoedeklas.getKlasCode().equals(klas)){
						deklas = degoedeklas;
				}
				}
				 
				destudent = deklas.getStudenten();
				
				Docent dedocent = getDocent(gebruikersnaam);
			
				for (Student student : destudent){
				depresentie.add(new Presentie(student, true));
				}
				
				College deCollege = new College(datum,begintijd,eindtijd,depresentie);
				dS.add(new Sessie(deCollege, deklas, dedocent,getCursusOpCode(vakcode)));
				
				

				

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	

				
		
		
	private void vulStudenten(
			ArrayList<Student> pStudenten,
			ArrayList<Klas> pKlassen) {
		Student lStudent;
		for (Klas k : pKlassen) {			
			String csvFile = "././CSV/" + k.getNaam() + ".csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			try {

				br = new BufferedReader(new FileReader(csvFile));
				
				while ((line = br.readLine()) != null) {

				    // use comma as separator
					String[] element = line.split(cvsSplitBy);
					String gebruikersnaam = (element[3] + "." + element[2] + element[1] + "@student.hu.nl").toLowerCase();
					// verwijder spaties tussen  dubbele voornamen en tussen bv van der 
					gebruikersnaam = gebruikersnaam.replace(" ","");
					String lStudentNrString  = element[0];
					int lStudentNr = Integer.parseInt(lStudentNrString);
					lStudent = new Student(element[3], element[2], element[1], "geheim", gebruikersnaam, lStudentNr); //Volgorde 3-2-1 = voornaam, tussenvoegsel en achternaam
					pStudenten.add(lStudent);
					k.voegStudentToe(lStudent);
					
					//System.out.println(gebruikersnaam);
			
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}	
		}
	}	
	private void vulDecaan(ArrayList<Decaan> pDecaan) {
		String csvFile = "././CSV/decaan.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
			
		try {
	
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	
			        // use comma as separator
				String[] element = line.split(cvsSplitBy);
				String gebruikersnaam = element[0].toLowerCase();
				String voornaam = element[1];
				String tussenvoegsel = element[2];
				String achternaam = element[3];
				pDecaan.add(new Decaan(voornaam, tussenvoegsel, achternaam, "geheim", gebruikersnaam, 1));
				
				//System.out.println(gebruikersnaam);
		
			}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void vulManagement(ArrayList<Management> pManagement) {
		String csvFile = "././CSV/management.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
			
		try {
	
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	
			        // use comma as separator
				String[] element = line.split(cvsSplitBy);
				String gebruikersnaam = element[0].toLowerCase();
				String voornaam = element[1];
				String tussenvoegsel = element[2];
				String achternaam = element[3];
				pManagement.add(new Management(voornaam, tussenvoegsel, achternaam, "geheim", gebruikersnaam, 1));
				
				//System.out.println(gebruikersnaam);
		
			}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
