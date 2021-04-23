package com.taylorw.DemoHibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="alien")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Alien {

	@Id
	private int aid;
//	@Transient
	private AlienName aname;
	@Column(name="alien_color")
	private String acolor;
	@OneToMany(mappedBy="alien", fetch=FetchType.LAZY)
	private List<Weapon> weapons = new ArrayList<Weapon>();
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public AlienName getAname() {
		return aname;
	}
	public void setAname(AlienName aname) {
		this.aname = aname;
	}
	public String getAcolor() {
		return acolor;
	}
	public void setAcolor(String acolor) {
		this.acolor = acolor;
	}
	
	public List<Weapon> getWeapon() {
		return weapons;
	}
	public void setWeapon(List<Weapon> weapons) {
		this.weapons = weapons;
	}
	@Override
	public String toString() {
		return "Alien [aid=" + aid + ", aname=" + aname + ", acolor=" + acolor 
//				+ ", weapon=" + weapons 
				+ "]";
	}

	
	
}
