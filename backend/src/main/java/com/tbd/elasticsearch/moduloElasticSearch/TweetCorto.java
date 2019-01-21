package com.tbd.elasticsearch.moduloElasticSearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tbd.elasticsearch.entities.User;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TweetCorto {

    private String id;
    private String text;
    private String text_lower;
	private Long favoriteCount;
	private Long retweetCount;
	private Date createdAt;
	private java.sql.Date date;
	private Double negative;
	private Double positive;
	private String fecha;

	private Long book_id;

	public Long getBook_id() {
		return book_id;
	}

	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}



	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public Double getNegative() {
		return negative;
	}

	public void setNegative(Double negative) {
		this.negative = negative;
	}

	public Double getPositive() {
		return positive;
	}

	public void setPositive(Double positive) {
		this.positive = positive;
	}

	public Date getCreated_at() {
		return createdAt;
	}

	public void setCreated_at(Date created_at) {

		//Pasar a instante



		this.createdAt = created_at;
	}

	private User user;


	public String getText_lower() {
		return text_lower;
	}

	public void setText_lower(String text_lower) {
		this.text_lower = text_lower;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Long getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Long getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Long retweetCount) {
		this.retweetCount = retweetCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
