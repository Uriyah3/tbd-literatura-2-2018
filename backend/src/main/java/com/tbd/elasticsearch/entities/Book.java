package com.tbd.elasticsearch.entities;

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
	@Column(name="hits") 
	Integer hits;
	
	@Column(name="authorId", nullable=false)
	private Long authorId;
	@Column(name="genreId", nullable=false)
	private Long genreId;


	@Column(name="positivo")
	private Integer positivo;
	@Column(name="negativo")
	private Integer negativo;
	@Column(name="neutro")
	private Integer neutro;

	public Integer getPositivo() {
		return positivo;
	}

	public void setPositivo(Integer positivo) {
		this.positivo = positivo;
	}

	public Integer getNegativo() {
		return negativo;
	}

	public void setNegativo(Integer negativo) {
		this.negativo = negativo;
	}

	public Integer getNeutro() {
		return neutro;
	}

	public void setNeutro(Integer neutro) {
		this.neutro = neutro;
	}

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
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public Long getGenreId() {
		return genreId;
	}
	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
