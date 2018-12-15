package com.tbd.mongo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tbd.entities.User;

//1065786331274649602
@Document(collection="statusJSONImpl")
public class Tweet {
    @Id
    private Long id;
    private String text;
    private Long favoriteCount;
    private Long retweetCount;
    
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
