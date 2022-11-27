package hu.webuni.hr.saca.model;

public class AvgOfSalary {

	private Double avg;
	private String jobname;
	
	public AvgOfSalary(Double avg, String jobname) {
		this.avg = avg;
		this.jobname = jobname;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	
	
}
