package com.capone.skills;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capone.analytics.RankStats;

public interface HiringResourceSkill extends Resource{

	public void add(Resource emp, Set<Skill> skills);

	public Set<Skill> getRatings();
	public void setRatings(Set<Skill> skills);
	public List<Skill> getAllSubmittedResults();
	//public Map<Skill,RankStats> getRankSummary();
	//public void setRankSummary(Map<Skill,RankStats> statsMap);
	
	
}
