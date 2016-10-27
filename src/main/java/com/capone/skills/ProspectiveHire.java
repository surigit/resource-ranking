package com.capone.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.capone.analytics.RankStats;

public class ProspectiveHire implements HiringResourceSkill {
	private Log log = LogFactory.getLog(getClass());

	private Map<Resource, Set<Skill>> resultMap = new HashMap();
	private List<Skill> skillsFromAll = new ArrayList<Skill>(20);
	private Set<Skill> finalSkillRating = new HashSet<Skill>();
	private Map<Skill,RankStats> rankStats = null;
	
	private Integer id;
	private String name;
	private String title;
	private String lob;
	
	
	
	public ProspectiveHire() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProspectiveHire(Integer id, String name, String title, String lob) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.lob = lob;
	}

	
	public ProspectiveHire(Resource resource,Map<Resource, Set<Skill>> results) {
		super();
		this.id = resource.getId();
		this.name = resource.getName();
		this.title = resource.getTitle();
		this.lob = resource.getLob();
		
		this.resultMap = results;
		
		Set<Map.Entry<Resource, Set<Skill>>> resEntry = results.entrySet();
		
		resEntry.forEach(e -> {
			
			Set<Skill> skillSet = e.getValue();
			
			skillSet.forEach(s -> {skillsFromAll.add(s);});
		});
		
	}

	@Override
	public void add(Resource emp, Set<Skill> skills) {
		resultMap.put(emp, skills);
		skills.forEach(s-> skillsFromAll.add(s));
	}
	
	@Override
	public Set<Skill> getRatings() {
			return new HashSet(finalSkillRating);
	}

	@Override
	public void setRatings(Set<Skill> skills) {
		this.finalSkillRating = new HashSet<Skill>(skills);
		
	}

	@Override
	public List<Skill> getAllSubmittedResults(){
	
		return new ArrayList<Skill>(this.skillsFromAll);
	}
	

/*	@Override
	public Map<Skill,RankStats> getRankSummary() {
		// TODO Auto-generated method stub
		return this.rankStats;
	}

	@Override
	public void setRankSummary(Map<Skill,RankStats> statsMap) {
		// TODO Auto-generated method stub
		this.rankStats = statsMap;
	}*/

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	@Override
	public String getLob() {
		// TODO Auto-generated method stub
		return this.lob;
	}

	
	
}
