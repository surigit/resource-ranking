package com.capone.utils;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.capone.skills.ResourceSkill;
import com.capone.skills.Skill;

public class TestHiringUtilsAverage {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAvgUtils_Case1() {
		
		Skill s1 = new ResourceSkill(101, "Java", 6);
		Skill s2 = new ResourceSkill(101, "Java", 6);
		
		List<Skill> sList = new ArrayList();
		sList.add(s1);
		sList.add(s2);
		
		try {
			Set<Skill> fList = HiringUtils.computeAvgResults(sList);
			Assert.assertSame(1, fList.size());;
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed with Exception");
		}
		
	}

	@Test
	public void testAvgUtils_Case2() {
		
		Skill s1 = new ResourceSkill(101, "Java", 6);
		Skill s2 = new ResourceSkill(102, "Spring", 6);
		
		List<Skill> sList = new ArrayList();
		sList.add(s1);
		sList.add(s2);
		
		try {
			Set<Skill> fList = HiringUtils.computeAvgResults(sList);
			Assert.assertSame(2, fList.size());;
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed with Exception");
		}
		
	}


	@Test
	public void testAvgUtils_Case3() {
		
		// fail cases
		
		Skill s1 = new ResourceSkill(101, "Java", 6);
		Skill s2 = new ResourceSkill(102, "Spring", 6);
		
		List<Skill> sList = new ArrayList();
		sList.add(s1);
		sList.add(s2);
		
		try {
			Set<Skill> fList = HiringUtils.computeAvgResults(null);
			Assert.assertNull(fList);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed with Exception");
		}
		
	}


	@Test
	public void testAvgUtils_Case4() {
		
		// fail cases
		
		Skill s1 = new ResourceSkill(101, "Java", 6);
		Skill s2 = new ResourceSkill(102, "Spring", 6);
		
		List<Skill> sList = new ArrayList();
		
		try {
			Set<Skill> fList = HiringUtils.computeAvgResults(sList);
			Assert.assertNull(fList);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed with Exception");
		}
		
	}

}
