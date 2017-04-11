package model;

import model.persoon.Student;

public class Main {
	public static void main(String[] arg) {
		Student s1 = new Student("Falco", "de", "Beer", "geheim", "falco.debeer@student.hu.nl", 1694547);
		PrIS p1 = new PrIS();
		System.out.println(p1.getKlasVanStudent(s1));

	}

}
