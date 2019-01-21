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
	@Column(name="genre_id", nullable=false)
	private Long genreId;
	@Column(name="hits")
	private Integer hits;

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
	
	
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
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
	public Long getGenreId() {
		return genreId;
	}
	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}
	
	
	
}
