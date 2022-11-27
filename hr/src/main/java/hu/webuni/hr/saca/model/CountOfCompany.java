package hu.webuni.hr.saca.model;

public class CountOfCompany {

	private Long cnt;
	private String name;
	
	public CountOfCompany(Long cnt, String name) {
		this.cnt = cnt;
		this.name = name;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
