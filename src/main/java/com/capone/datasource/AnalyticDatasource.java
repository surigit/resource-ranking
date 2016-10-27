package com.capone.datasource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.capone.skills.HiringResourceSkill;
import com.capone.skills.Resource;
import com.capone.skills.Skill;

public interface AnalyticDatasource {

	public List<Skill> getSkillsFromLob(String lobId) throws Exception;
	
	public HiringResourceSkill getCandidateData(Integer candidateId) throws Exception;
	
}
