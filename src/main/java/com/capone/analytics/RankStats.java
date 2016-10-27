package com.capone.analytics;

import com.capone.skills.Skill;

public class RankStats {

	private Integer rank;
	private Long count;
	private Double ratingAvg;
	private Skill skill;
	
		
	public RankStats() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RankStats(Integer rank, Long count, Double ratingAvg) {
		super();
		this.rank = rank;
		this.count = count;
		this.ratingAvg = ratingAvg;
	}


	public Integer getRank() {
		return rank;
	}


	public void setRank(Integer rank) {
		this.rank = rank;
	}


	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	public Double getRatingAvg() {
		return ratingAvg;
	}


	public void setRatingAvg(Double ratingAvg) {
		this.ratingAvg = ratingAvg;
	}


	@Override
	public String toString() {
		return "RankStats [rank=" + rank + ", count=" + count + ", ratingAvg=" + ratingAvg + "]";
	}


	public String getSkill() {
		StringBuilder bldr = new StringBuilder();
		bldr.append("id=");
		bldr.append(skill.getId());
		bldr.append(" ");
		bldr.append("name=");
		bldr.append(skill.getName());
		bldr.append(" ");
		bldr.append("rating=");
		bldr.append(skill.getRating());
		return bldr.toString();
	}


	public void setSkill(Skill skill) {
		this.skill = skill;
	}


	
}
