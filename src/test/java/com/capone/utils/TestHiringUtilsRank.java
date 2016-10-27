package com.capone.utils;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.capone.analytics.RankStats;
import com.capone.skills.ResourceSkill;
import com.capone.skills.Skill;

public class TestHiringUtilsRank {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_Case1() {
		
		List<Skill> peers = new ArrayList();
		peers.add(new ResourceSkill(101, "Java", 7));
		peers.add(new ResourceSkill(102, "Spring", 3));
		peers.add(new ResourceSkill(103, "SQL", 3));
		peers.add(new ResourceSkill(101, "Java", 9));
		peers.add(new ResourceSkill(102, "Spring", 7));
		peers.add(new ResourceSkill(103, "SQL", 5));
		peers.add(new ResourceSkill(101, "Java", 5));
		peers.add(new ResourceSkill(102, "Spring", 9));
		peers.add(new ResourceSkill(103, "SQL", 7));
		peers.add(new ResourceSkill(101, "Java", 3));

		Set<Skill> cand = new HashSet();
		cand.add(new ResourceSkill(101,"Java",2));
		cand.add(new ResourceSkill(102,"Spring",2));
		cand.add(new ResourceSkill(103,"SQL",9));
		
		try {
			Map<Skill,RankStats> rMap = HiringUtils.computeRanking(cand, peers);
			System.out.println(rMap);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed with Exception");
		}

	}

}
