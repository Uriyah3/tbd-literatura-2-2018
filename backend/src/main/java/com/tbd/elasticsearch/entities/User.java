package com.tbd.elasticsearch.entities;

//import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
//import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Table(name="user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="screen_name")
	private String screenName;
	@Column(name="location")
	private String location;

	@Column(name="friends_count")
	private Integer friendsCount;
	@Column(name="followers_count")
    private Integer followersCount;
	@Column(name="is_verified")
    private Boolean isVerified;
	@Column(name="score")
	private Double score;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
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
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}
	public Integer getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
    
    
    
    
    

}
