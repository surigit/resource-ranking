package com.capone.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.capone.analytics.RankStats;
import com.capone.skills.ResourceSkill;
import com.capone.skills.Skill;

public class HiringUtils {
	private static Log log = LogFactory.getLog(HiringUtils.class.getName());
	
	
	public static Map<Integer,Integer> getPeerStats(List<Skill> peers) throws Exception{
		
		if(peers==null||peers.isEmpty()) return null;
		Map<Integer,List<Skill>> grouped = peers.stream().collect(Collectors.groupingBy(Skill::getId));

		Map<Integer,Integer> stats = new HashMap();
		Set<Entry<Integer,List<Skill>>> keys = grouped.entrySet();
		keys.forEach(k -> {
			stats.put(k.getKey(),k.getValue().size());
		});
		
		return stats;
	}
	
	
	/**
	 * 
	 * @param hire
	 * @param emp
	 * @throws Exception
	 */
	public static Map<Skill,RankStats> computeRanking(Set<Skill> hire,List<Skill> peers) throws Exception{

		log.info("HiringUtils.computeRanking()|Begins..");
		int capacity = 256;
		
		// safety checks 
		if(hire == null || hire.isEmpty() || peers == null || peers.isEmpty()){
			log.error("HiringUtils.computeRanking()|Failed in Safety checks");
			return null;
		}
		
		int count = peers.size();
		if(count >capacity){
			log.debug("HiringUtils.computeRanking()|Skill Capacity set to 1024");
			capacity = 1024;
		}
		
		//merge both to a list
		List<Skill> allSkills = new ArrayList<Skill>(capacity);

		allSkills.addAll(peers);
		// Filter unwanted skill set - not required for ranking for new hire
		allSkills.retainAll(hire);
		
		//safety check - if all skills is empty 
		if(allSkills.isEmpty()){
			log.info("HiringUtils.computeRanking()|Peer Skills are empty after filtering..");
			Map<Skill,RankStats> resultMap = new HashMap();
			hire.forEach(s-> {
				resultMap.put(s, new RankStats(1, new Long(0), new Double(s.getRating())));
			});
			
			log.info("HiringUtils.computeRanking()|Retuning all skills with Top Rank..");
			return resultMap;
		}
		
		
		allSkills.addAll(hire);
		log.debug("HiringUtils.computeRanking()|Filtered unwanted skill sets");
		
		//get grouped 
		// Skill Id, Unique Grouped Skill Ratings 
		Map<Integer,Set<Integer>> grouped = allSkills.stream().collect(Collectors.groupingBy(Skill::getId,Collectors.mapping(Skill::getRating, Collectors.toSet())));
		
		log.debug("HiringUtils.computeRanking()|Basic Grouped Results "+grouped);
		
		Map<Integer,Skill> ratingMap = new HashMap();
		
		hire.forEach(s -> {
			ratingMap.put(s.getId(), new ResourceSkill(s.getId(), s.getName(), s.getRating()));
		});
		log.debug("HiringUtils.computeRanking()|Prepared Rating Map");		
		
		Map<Skill,RankStats> resultMap = new HashMap();
		
		
		log.info("HiringUtils.computeRanking()|Starting Rank Computation");		
		//sort 
		Set<Entry<Integer,Set<Integer>>> entrySet = grouped.entrySet();
		Iterator<Entry<Integer,Set<Integer>>> itr = entrySet.iterator();
		
		//get Peer stats here 
		Map<Integer, Integer> peerStats = getPeerStats(peers);
		
		while(itr.hasNext()){
			
			Entry<Integer,Set<Integer>> entry = itr.next();
			
			List<Integer> sortList = entry.getValue().stream().sorted((f,s) -> {
				if(f ==s ) return 0;
				if(f<s) return 1;
				return -1;
			}).collect(Collectors.toList());
			log.debug("HiringUtils.computeRanking()|Ranking - Skill.id ["+entry.getKey()+"]");		
			
			//sortList.forEach(System.out::println);
			
			if(ratingMap.containsKey(entry.getKey())){
				Skill sRating = ratingMap.get(entry.getKey());
				//get the index 
				int index = sortList.indexOf(sRating.getRating());
				if(index <1) index = 0;
	
				IntSummaryStatistics rankStats = sortList.stream().collect(Collectors.summarizingInt(a -> a));

				Integer comparedTo = 0;
				if(peerStats.get(sRating.getId()) != null){
					comparedTo = peerStats.get(sRating.getId());
				}
				
				resultMap.put(sRating, new RankStats(index+1, new Long(comparedTo),new Double(rankStats.getAverage())));
			}
			
		}
		
		log.info("HiringUtils.computeRanking()|End Rank Computation | Results -  "+resultMap);
		
		log.info("HiringUtils.computeRanking()|Ends..");		
		return resultMap;
	}
	
	/**
	 * 
	 * @param skills
	 * @return
	 * @throws Exception
	 */
	public static Set<Skill> computeAvgResults(List<Skill> skills) throws Exception{

		log.info("HiringUtils.computeAvgResults()|Begins..");
		if(skills==null || skills.isEmpty()){
			log.warn("HiringUtils.computeAvgResults()|Skills list is NULL or empty. Returning Null");
			return null;
		}
		log.debug(skills);
		
		Map<Integer, Double> avgResults = skills.stream()
				.collect(Collectors.groupingBy(Skill::getId, Collectors.averagingInt(Skill::getRating)));
		
		Set<Skill> finalSkill = null;

		if(avgResults != null && !avgResults.isEmpty()){
		
			log.debug("HiringUtils.computeAvgResults()|Before ["+avgResults+"]");
			finalSkill = new HashSet();
			
			Iterator<Map.Entry<Integer,Double>> itr = avgResults.entrySet().iterator();
			while(itr.hasNext()){
				Map.Entry<Integer, Double> entry = itr.next();
				int index = skills.indexOf(new ResourceSkill(entry.getKey(),null,null));
				Skill skill = skills.get(index);
				Skill cSkill = new ResourceSkill(skill.getId(), skill.getName(), entry.getValue().intValue());
				finalSkill.add(cSkill);
			}
			
		}

		log.debug("HiringUtils.computeAvgResults()|Final:["+finalSkill+"]");
		log.info("HiringUtils.computeAvgResults()|Ends..");		
		return finalSkill;
	}
	
	/**
	 * 
	 * @param mesg
	 * @return
	 */
	public static Map<String,String> putSuccess(String code, String mesg){
		
		Map<String, String> status = new HashMap();
		if(code!=null && !"".equals(code)){
			status.put("code", code);
		}else{
			status.put("code", "200");
		}
		status.put("status", "SUCCESS");
		status.put("mesg", mesg);
		return status;
	}
	
	/**
	 * 
	 * @param mesg
	 * @return
	 */
	public static Map<String,String> putFail(String mesg){
		
		Map<String, String> status = new HashMap();
		status.put("code", "500");
		status.put("status", "Fail");
		status.put("mesg", mesg);
		return status;
	}
}
