package com.tbd.elasticsearch.moduloMongo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tbd.elasticsearch.entities.*;

import java.util.Date;


//1065786331274649602
@Document(collection="statusJSONImpl")
public class Tweet {

    @Id
    private Long id;
    private String text;
    private Long favoriteCount;
    private Long retweetCount;
	private Date createdAt;
	private Double negative;
	private Double positive;

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
		this.createdAt = created_at;
	}

	private User user;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
