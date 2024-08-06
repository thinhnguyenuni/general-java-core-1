package fa.training.services;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import fa.training.dao.ExperienceDAO;
import fa.training.entities.Candidate;
import fa.training.entities.Experience;

public class Test {


	

	public static void main(String[] args) {
		Set<Experience> hsList = new TreeSet<>();
		hsList=ExperienceDAO.selectEx();
		
		for (Experience experience : hsList) {
			System.out.println(experience.showInfo());
		}
	}
	
	
}
