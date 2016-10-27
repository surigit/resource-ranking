package com.capone.datasource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.capone.skills.Resource;
import com.capone.skills.Skill;

public class DataFactory {

	
	/**
	 * The LOB Maps is a standby - for a full LRU Implementation
	 * A few LOBs can be loaded - just to speed up 
	 * The Lobs Can or Cannot be at a granular level
	 */
	private ConcurrentMap<String,List<Skill>> lobData;
	private ConcurrentMap<Resource,Map<Resource,Set<Skill>>> candData;
	
	//public List<Skill> getLobData(String lob,)
	
}
