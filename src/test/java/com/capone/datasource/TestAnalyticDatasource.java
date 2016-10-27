package com.capone.datasource;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.capone.skills.HiringResourceSkill;
import com.capone.skills.Skill;


public class TestAnalyticDatasource {

	AnalyticDatasource datasrc;
	
	@Before
	public void setUp() throws Exception {
		datasrc = new AnalyticDatasourceImpl();
	}

	@Test
	public void testNotNull() {
		Assert.assertNotNull(datasrc);
	}

	@Test
	public void testGetCandidatesResults_Case1() {

		try {
			HiringResourceSkill pHire = datasrc.getCandidateData(801);
			
			Assert.assertNotNull(pHire);

			List<Skill> subSkills = pHire.getAllSubmittedResults();
			
			Assert.assertNotNull(subSkills);
			
			Assert.assertSame(9, subSkills.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed in Exception");
		}
	
	}


	@Test
	public void testLobResults_Case1() {

		try {
			HiringResourceSkill pHire = datasrc.getCandidateData(801);
			
			Assert.assertNotNull(pHire);

			List<Skill> subSkills = pHire.getAllSubmittedResults();
			
			Assert.assertNotNull(subSkills);
			
			Assert.assertSame(9, subSkills.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed in Exception");
		}
	
	}

	
}
