package com.capone.skills;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.capone.analytics.RankStats;
import com.capone.utils.HiringUtils;

public class TestProspectiveRankComputation {
	ProspectiveHire ph = new ProspectiveHire(999,"Martin","SVP","Capital Markets");

	@Before
	public void setUp() throws Exception {
		
		// Prep List of Interviewers (who are Employees) 
	    Resource intv1 = new Employee(101,"Mike");
	    
	    Skill s1 = new ResourceSkill(91, "Java", 9);
	    Skill s2 = new ResourceSkill(92, "Spring", 6);
	    
	    Set<Skill> skills = new HashSet();
	    skills.add(s1);
	    skills.add(s2);
	    
	    ph.add(intv1, skills);
	    
	    Resource intv2 = new Employee(102,"Rudolph");
	    Skill s3 = new ResourceSkill(91, "Java", 8);
	    Skill s4 = new ResourceSkill(92, "Spring", 1);
	    
	    Set<Skill> skills1 = new HashSet();
	    skills1.add(s3);
	    skills1.add(s4);
	    
	    ph.add(intv2, skills1);
	    
	    Resource intv3 = new Employee(103,"John");
	    Skill s5 = new ResourceSkill(91, "Java", 5);
	    Skill s6 = new ResourceSkill(92, "Spring", 7);
	    Skill s7 = new ResourceSkill(95, "Hibernate", 7);
	    
	    Set<Skill> skills2 = new HashSet();
	    skills2.add(s5);
	    skills2.add(s6);
	    skills2.add(s7);

	    
	    ph.add(intv3, skills2);
	    
	    
	}

	@Test
	public void testSetFinalResults() {

	
		try {
			Set<Skill> finalResults = HiringUtils.computeAvgResults(ph.getAllSubmittedResults());
			System.out.println(finalResults);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testRanking(){
		
	    Skill s1 = new ResourceSkill(91, "Java", 7);
	    Skill s2 = new ResourceSkill(92, "Spring", 6);
	    Skill s3 = new ResourceSkill(93, "AWS", 6);
	    
	    Set<Skill> newHire = new HashSet();
	    newHire.add(s1);
	    newHire.add(s2);
	    newHire.add(s3);
	    
	    try {
			Map<Skill,RankStats> rankMap = HiringUtils.computeRanking(newHire, ph.getAllSubmittedResults());
			System.out.println(rankMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}

}
