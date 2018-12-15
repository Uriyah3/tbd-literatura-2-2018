package com.tbd.mongo.entities;

import java.io.Serializable;
import javax.persistence.*;

//import org.springframework.data.annotation.Id;


@Entity
@Table(name="book")
public class Book implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	@Column(name="name", unique=true, nullable=false) 
	String name;
	
	@Column(name="authorId", nullable=false)
	private Long authorId;
	@Column(name="genreId", nullable=false)
	private Long genreId;
	
	

}
