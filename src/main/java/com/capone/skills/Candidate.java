package com.capone.skills;

public class Candidate implements Resource {

	private Integer id = null;
	private String name = null;
	private String title = null;
	private String lob = null;
	
	
	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Candidate(Integer id, String name, String title, String lob) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.lob = lob;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	@Override
	public String toString() {
		return "Candidate [id=" + id + ", name=" + name + ", title=" + title + ", lob=" + lob + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidate other = (Candidate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
