package com.capone.datasource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.capone.skills.Candidate;
import com.capone.skills.Employee;
import com.capone.skills.Resource;
import com.capone.skills.ResourceSkill;
import com.capone.skills.Skill;

public class TestHireLoadDatasource {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {

		//System.out.println(loadLob());
		
		Map<Resource,Map<Resource,Set<Skill>>> candidates = loadCandidates();
		
		Set<Entry<Resource,Map<Resource,Set<Skill>>>> keySet= candidates.entrySet();
		keySet.forEach(e -> {
			System.out.println(e.getKey());
			
			Set<Entry<Resource, Set<Skill>>> lKeys = e.getValue().entrySet();
			lKeys.forEach(lk -> {
				
				System.out.println("  > "+lk.getKey());
				
				lk.getValue().forEach(s ->{
					System.out.println("     > "+s);
				});
			});
			
		});
		
		//loadCandidates());
	
	}


	private Map<String,List<Skill>> loadLob(){
		
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
					
					System.out.println("Loading Lob["+lob+"]");
					

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
						System.out.println("Loadig Skill ["+intv+"]");
						
					}

				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return lobs;
	}
	
	private Map<Resource,Map<Resource,Set<Skill>>> loadCandidates(){
		String file = "hire.yml";
		
		InputStream is;
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
		Map<Resource,Map<Resource,Set<Skill>>> candMap = null;

		Yaml yml = null;
		try {
			//ConcurrentMap<String, List<PlatformRole>> rlMap = new ConcurrentHashMap<>();

			yml = new Yaml();
			Map<String, Object> map = (Map<String, Object>) yml.load(is);

			candMap = new HashMap<Resource,Map<Resource,Set<Skill>>>();
			
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
					
					candMap.put(cand, new HashMap<Resource,Set<Skill>>());
					
					System.out.println("Loading CAND["+candidate+"]");
					
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
						
						candMap.get(cand).put(em, new HashSet());
						System.out.println("Loadig Int ["+intv+"]");
						
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
							
							candMap.get(cand).get(em).add(skill);
							System.out.println("Loadig Skill ["+sk+"]");
							
							
							
						}						
					}

				}

			}
			
//			if(!rlMap.isEmpty()) policyMap = new ConcurrentHashMap<>(rlMap);

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return candMap;
		
	}
	
}
