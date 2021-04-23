package com.taylorw.DemoHibernate;

import javax.persistence.Embeddable;

@Embeddable
public class AlienName {
	private String lname;
	private String mname;
	private String fname;
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	@Override
	public String toString() {
		return "AlienName [lname=" + lname + ", mname=" + mname + ", fname=" + fname + "]";
	}
	
	
}
