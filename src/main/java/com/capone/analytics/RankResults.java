package com.capone.analytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.capone.skills.Resource;
import com.capone.skills.Skill;

public class RankResults {

	private Resource Candidate;
	private Map<Skill,RankStats> rankData;
	private List<RankStats> rankStats;
	private Map<String, String> status;
	
	public RankResults() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RankResults(Resource candidate, Map<Skill, RankStats> rankingResults) {
		super();
		Candidate = candidate;
		this.rankData = rankingResults;
		
		if(rankingResults!=null){
			rankStats = new ArrayList<RankStats>();
			Set<Entry<Skill, RankStats>> keys = rankingResults.entrySet();
			keys.forEach(k -> {
				RankStats stats = k.getValue();
				stats.setSkill(k.getKey());
				rankStats.add(stats);
			});
		}
		
	}
	
	public RankResults(Map<String, String> status) {
		super();
		this.status = status;
	}


	public Map<String, String> getStatus() {
		return status;
	}


	public void setStatus(Map<String, String> status) {
		this.status = status;
	}


	public Resource getCandidate() {
		return Candidate;
	}
	public void setCandidate(Resource candidate) {
		Candidate = candidate;
	}
	public List<RankStats> getRankData() {
		return rankStats;
	}

}
