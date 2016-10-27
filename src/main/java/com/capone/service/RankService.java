package com.capone.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capone.analytics.RankResults;
import com.capone.analytics.RankStats;
import com.capone.datasource.AnalyticDatasource;
import com.capone.skills.Candidate;
import com.capone.skills.HiringResourceSkill;
import com.capone.skills.Resource;
import com.capone.skills.Skill;
import com.capone.utils.HiringUtils;
@Component
public class RankService {

	@Autowired
	AnalyticDatasource analyticDatasource;
	
	private static final Log log = LogFactory.getLog(RankService.class.getName());

	public RankService() {
		super();
	}
	
	
	public RankResults getRanking(Integer candidateId, String lobId) throws Exception{
		
		log.info("RankService.getRanking()|Begins..");
		
		log.info("RankService.getRanking()|1|.. Get Candidate Data first");
		RankResults results = null;
		HiringResourceSkill candSkills = analyticDatasource.getCandidateData(candidateId);
		
		if (candSkills==null){
			results = new RankResults(HiringUtils.putSuccess("404", "invalid Candidate Id - "+candidateId));
			log.error("RankService.getRanking()|1.1|.. invalid Candidate Id ["+candidateId+"]");
			return results;
		}
		Resource candidate =  new Candidate(candSkills.getId(),candSkills.getName(),candSkills.getTitle(),candSkills.getLob());
		
		Set<Skill> candAvg = null;;
		try {
			log.info("RankService.getRanking()|2|.. Calculate average of the Ratings");
			candAvg = HiringUtils.computeAvgResults(candSkills.getAllSubmittedResults());
			log.info("RankService.getRanking()|3|.. Finished average of the Ratings");
		} catch (Exception e) {
			log.info("RankService.getRanking()|4|Exception in Calculate average - "+e.getMessage(),e);
			throw new Exception ("RankService.getRanking()|4|Exception in Calculate average - ",e);
		
		}
		
		log.info("RankService.getRanking()|5|.. Get Lob Data");
		
		List<Skill> peers = analyticDatasource.getSkillsFromLob(lobId);
		
		if (peers==null || peers.isEmpty()){
			results = new RankResults(HiringUtils.putSuccess("404", "invalid LOB - "+lobId+" or NO resources under the LOB"));
			log.error("RankService.getRanking()|5.1|.. invalid LOB ["+lobId+"] or NO resources under the LOB");
			return results;
		}
		
		log.info("RankService.getRanking()|6|.. Computing Ranking now");

		Map<Skill,RankStats> rankResults = null;
		try {
			log.info("RankService.getRanking()|7|.. Computing Ranking");
			rankResults = HiringUtils.computeRanking(candAvg, peers);
			log.info("RankService.getRanking()|8|.. Finished Computing Ranking");
		} catch (Exception e) {
			log.info("RankService.getRanking()|9|Exception in Computing Ranking"+e.getMessage(),e);
			throw new Exception ("RankService.getRanking()|9|Exception in Computing Ranking",e);
		}
		
		//prepare the final results 
		results = new RankResults(candidate, rankResults);
		results.setStatus(HiringUtils.putSuccess(null, "COMPLETED"));
		
		
		log.info("RankService.getRanking()|Ends..");

		return results;
	}
	
}
