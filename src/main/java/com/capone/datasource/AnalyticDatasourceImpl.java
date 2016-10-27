package com.capone.datasource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import com.capone.skills.Candidate;
import com.capone.skills.Employee;
import com.capone.skills.HiringResourceSkill;
import com.capone.skills.ProspectiveHire;
import com.capone.skills.Resource;
import com.capone.skills.ResourceSkill;
import com.capone.skills.Skill;


/**
 * Technically This should connect to the actual datasource - some database
 * or an another implementation can be written.
 * For the purposes of this exercise - the file load is stored locally.
 * @author sm58496
 *
 */
@Component
public class AnalyticDatasourceImpl implements AnalyticDatasource {
	
	/**
	 * The LOB Maps is a standby - for a full LRU Implementation
	 * A few LOBs can be loaded - just to speed up 
	 * The Lobs Can or Cannot be at a granular level
	 */
	private ConcurrentMap<String,List<Skill>> lobData;
	private ConcurrentMap<Integer,DataContext> candData;
	private static Log log = LogFactory.getLog(AnalyticDatasourceImpl.class.getName());

	public AnalyticDatasourceImpl() {
		super();
		init();
	}

	private void init(){
		log.info("AnalyticDatasourceImpl.init()|Loading LOB Data");
		lobData = new ConcurrentHashMap(loadLob());
		log.info("AnalyticDatasourceImpl.init()|Finished Loading LOB Data");
		log.info("AnalyticDatasourceImpl.init()|Loading Candidate Data");
		candData = new ConcurrentHashMap(loadCandidates());
		log.info("AnalyticDatasourceImpl.init()|Finished Loading Candidate Data");
	}
	
	
	@Override
	public List<Skill> getSkillsFromLob(String lobId) throws Exception {
		log.info("AnalyticDatasourceImpl.getSkillsFromLob()|Begins");
		return lobData.get(lobId);
	}

	@Override
	public HiringResourceSkill getCandidateData(Integer candidateId) throws Exception {
		
		if(candData.get(candidateId)!= null){
		
			DataContext dataCtx = candData.get(candidateId);
			HiringResourceSkill candidate = new ProspectiveHire(dataCtx.getCandidate(),dataCtx.getCandidateResults());
			
			return candidate;
		}
		
		return null;
	}

	/**
	 * LOB Data
	 * @return
	 */
	private Map<String,List<Skill>> loadLob(){
		log.info("AnalyticDatasourceImpl.loadLob()|Begins..");
		String file = "lob.yml";
		
		InputStream is;
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);

		Map<String, List<Skill>> lobs = new HashMap();;
		
		Yaml yml = null;
		try {

			yml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yml.load(is);

			// lob level
			for (String key : map.keySet()) {

				List<Map<String, Object>> lobList = (List<Map<String, Object>>) map.get(key);
				// id level
				for (Map<String, Object> rmap : lobList) {

					String lob = (String)rmap.get("id");
					
					lobs.put(lob,new ArrayList<Skill>());
					
					//System.out.println("Loading Lob["+lob+"]");
					List<Map<String, Object>> skillList = (List<Map<String, Object>>) rmap.get("skills");
//					// skills Level
					for (Map<String, Object> panmap : skillList) {

						String sk = (String) panmap.get("sk");
						String[] skArr = sk.split(",");
						ResourceSkill skill = new ResourceSkill(null,null,null);
						for (String x: skArr){

							String[] flds = x.split("=");
							if("id".equals(flds[0])){
								skill.setId(new Integer(flds[1]));
								continue;
							}
							if("name".equals(flds[0])){
								skill.setName(flds[1]);
								continue;
							}
							if("rating".equals(flds[0])){
								skill.setRating(new Integer(flds[1]));
								continue;
							}
						}
						
						lobs.get(lob).add(skill);					
						
						String intv = (String) panmap.get("sk");
						//System.out.println("Loadig Skill ["+intv+"]");
						
					}

				}

			}
			
		} catch (Exception e) {
			log.error("AnalyticDatasourceImpl.loadLob()|Exception..",e);
			return null;
		}		
		log.info("AnalyticDatasourceImpl.loadLob()|Ends..");
		log.info("AnalyticDatasourceImpl.loadLob()|Lob Data++++++++++++++..");
		printLobData(lobs);
		log.info("AnalyticDatasourceImpl.loadLob()|Lob Data End++++++++++..");
		return lobs;
	}
	
	/**
	 * Candidates Data
	 * @return
	 */
	private Map<Integer,DataContext> loadCandidates(){
		log.info("AnalyticDatasourceImpl.loadCandidates()|Begins..");
		String file = "hire.yml";
		
		InputStream is;
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
		Map<Integer,DataContext> candMap = null;

		Yaml yml = null;
		try {
			//ConcurrentMap<String, List<PlatformRole>> rlMap = new ConcurrentHashMap<>();

			yml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yml.load(is);

			candMap = new HashMap<Integer,DataContext>();
			
			// hire level
			for (String key : map.keySet()) {

				List<Map<String, Object>> candList = (List<Map<String, Object>>) map.get(key);
				// cand level
				for (Map<String, Object> rmap : candList) {

					String candidate = (String)rmap.get("cand");
					String[] candArr = candidate.split(",");
					Candidate cand = new Candidate();
					for(String c: candArr){
						String[] flds = c.split("=");

						if("id".equals(flds[0])){
							cand.setId(new Integer(flds[1]));
							continue;
						}
						if("name".equals(flds[0])){
							cand.setName(flds[1]);
							continue;
						}
						if("title".equals(flds[0])){
							cand.setTitle(flds[1]);
							continue;
						}					
						if("lob".equals(flds[0])){
							cand.setLob(flds[1]);
							continue;
						}
					}
					
					DataContext dataCtx = new DataContext(cand,new HashMap<Resource,Set<Skill>>());
					candMap.put(cand.getId(), dataCtx);
					
					//System.out.println("Loading CAND["+candidate+"]");
					
					List<Map<String, Object>> panelList = (List<Map<String, Object>>) rmap.get("panel");
//					// panel Level
					for (Map<String, Object> panmap : panelList) {

						String intv = (String) panmap.get("int");
						
						String[] intArr = intv.split(",");
						Employee em = new Employee();
						for (String c: intArr){
							String[] flds = c.split("=");

							if("id".equals(flds[0])){
								em.setId(new Integer(flds[1]));
								continue;
							}
							if("name".equals(flds[0])){
								em.setName(flds[1]);
								continue;
							}
							if("title".equals(flds[0])){
								em.setTitle(flds[1]);
								continue;
							}					
							if("lob".equals(flds[0])){
								em.setLob(flds[1]);
								continue;
							}						
							
						}
						
						candMap.get(cand.getId()).getCandidateResults().put(em, new HashSet());
						//System.out.println("Loadig Int ["+intv+"]");
						
						//
						List<Map<String, Object>> skillList = (List<Map<String, Object>>) panmap.get("skills");
						for (Map<String, Object> skillM : skillList) {

							String sk = (String) skillM.get("sk");
							String[] skArr = sk.split(",");
							ResourceSkill skill = new ResourceSkill(null, null, null);

							for (String x: skArr){

								String[] flds = x.split("=");
								if("id".equals(flds[0])){
									skill.setId(new Integer(flds[1]));
									continue;
								}
								if("name".equals(flds[0])){
									skill.setName(flds[1]);
									continue;
								}
								if("rating".equals(flds[0])){
									skill.setRating(new Integer(flds[1]));
									continue;
								}
							}
							
							candMap.get(cand.getId()).getCandidateResults().get(em).add(skill);
							//System.out.println("Loadig Skill ["+sk+"]");
							
						}						
					}

				}

			}
			
		} catch (Exception e) {
			log.error("AnalyticDatasourceImpl.loadCandidates()|Exception..",e);
			e.printStackTrace();
		}		
		log.info("AnalyticDatasourceImpl.loadCandidates()|Ends..");
		
		log.info("AnalyticDatasourceImpl.loadCandidates()|CANDIDATE Data++++++++++++++..");
		printCandData(candMap);
		log.info("AnalyticDatasourceImpl.loadCandidates()|CANDIDATE Data End++++++++++..");
		
		return candMap;
		
	}

	/**
	 * 
	 * @param candidates
	 */
	private void printCandData(Map<Integer,DataContext> candidates){
		
		//Set<Entry<Resource,Map<Resource,Set<Skill>>>> keySet= candidates.entrySet();
		Set<Entry<Integer,DataContext>> keySet= candidates.entrySet();
		keySet.forEach(e -> {
			log.debug(e.getKey());
			
			Map<Resource,Set<Skill>> candResults= e.getValue().getCandidateResults();
						
			Set<Entry<Resource, Set<Skill>>> lKeys = candResults.entrySet();
			lKeys.forEach(lk -> {
				
				log.debug("  > "+lk.getKey());
				
				lk.getValue().forEach(s ->{
					log.debug("     > "+s);
				});
			});
			
		});
	}

	/**
	 * 
	 * @param candidates
	 */
	private void printLobData(Map<String,List<Skill>> lobData){
		
		Set<Entry<String,List<Skill>>> keySet= lobData.entrySet();
		keySet.forEach(e -> {
			log.debug(e.getKey());
				
				e.getValue().forEach(s ->{
					log.debug(" > "+s);
				});
			
		});
	}
}
