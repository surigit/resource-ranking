package com.capone.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capone.analytics.RankResults;
import com.capone.service.RankService;
import com.capone.utils.HiringUtils;

@RestController
@RequestMapping("/rank")
public class RatingController {
	private static final Log log = LogFactory.getLog(RatingController.class.getName());

	@Autowired
	RankService rankService;
	
	
	@RequestMapping(value = "/candidate/{candidateid}/lob/{lobid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getRanking(@PathVariable(value = "candidateid") String candidateId,
			@PathVariable("lobid") String lobId, HttpServletRequest request, HttpServletResponse response) {

		
		log.info("RatingController.getRanking()|Begins..");
		
		log.info("RatingController.getRanking()|1| Candidate Id [" + candidateId + "]");
		log.info("RatingController.getRanking()|2| LOB Id [" + lobId + "]");

		log.info("RatingController.getRanking()|3|Invoking Service");
		RankResults results = null;

		Integer candId = null;
		try {
			candId = new Integer(candidateId);
		} catch (NumberFormatException e1) {
			log.error("RatingController.getRanking()|3.1|Error casting String to Integer.",e1);
			results = new RankResults(HiringUtils.putFail("Invalid Candidate id."));
			return results;
		}
		
		
		try {
			results  = rankService.getRanking(candId, lobId);
		} catch (Exception e) {
			log.error("RatingController.getRanking()|4|Exception ["+e.getMessage()+"]", e);
			results = new RankResults(HiringUtils.putFail("Application Encountered an Exception"));
		}
		
		// setDummyData(request);
		log.info("RatingController.getRanking()|9|");
		log.info("RatingController.getRanking()|Ends..");

		return results;
	}

}
