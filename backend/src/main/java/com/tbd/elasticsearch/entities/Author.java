package com.tbd.elasticsearch.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="author")
public class Author implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	@Column(name="name", unique=true, nullable=false)
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
