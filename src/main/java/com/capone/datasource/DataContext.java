package com.capone.datasource;

import java.util.Map;
import java.util.Set;

import com.capone.skills.Resource;
import com.capone.skills.Skill;

public class DataContext {

	private Resource candidate;
	private Map<Resource,Set<Skill>> candidateResults;
	public DataContext(Resource candidate, Map<Resource, Set<Skill>> candidateResults) {
		super();
		this.candidate = candidate;
		this.candidateResults = candidateResults;
	}
	public DataContext() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Resource getCandidate() {
		return candidate;
	}
	public void setCandidate(Resource candidate) {
		this.candidate = candidate;
	}
	public Map<Resource, Set<Skill>> getCandidateResults() {
		return candidateResults;
	}
	public void setCandidateResults(Map<Resource, Set<Skill>> candidateResults) {
		this.candidateResults = candidateResults;
	}

	
}
