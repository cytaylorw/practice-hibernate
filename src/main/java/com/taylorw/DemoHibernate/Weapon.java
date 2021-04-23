package com.taylorw.DemoHibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="weapon")
public class Weapon {
	
	@Id
	private int id;
	private String name;
	@ManyToOne
	private Alien alien;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Alien getAlien() {
		return alien;
	}
	public void setAlien(Alien alien) {
		this.alien = alien;
	}
	@Override
	public String toString() {
		return "Weapon [id=" + id + ", name=" + name + ", alien=" + alien.getAid() + "]";
	}

	
	
	
}
