package com.tbd.elasticsearch.moduloNeo4j;


import com.tbd.elasticsearch.entities.User;
import com.tbd.elasticsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceNeo4j {

    @Autowired
    private UserRepository userRepository;

    private UserRepositoryNeo4j userRepositoryNeo4j;

    public UserServiceNeo4j(UserRepositoryNeo4j userRepositoryNeo4j) {
        this.userRepositoryNeo4j = userRepositoryNeo4j;
    }
    @Transactional(readOnly = true)
    public UserNode findByName(String name) {
        return userRepositoryNeo4j.findByName(name);
    }

    @Transactional(readOnly = true)
    public UserNode findByScreenName(String screenName){
        return userRepositoryNeo4j.findByScreenName(screenName);
    }

    @Transactional(readOnly = true)
    public Collection<UserNode> tweetedBook(String name) {
        return userRepositoryNeo4j.tweetedBook(name);
    }

    @Transactional(readOnly = true)
    public Collection<UserNode> tweetedGenre(String name) {
        return userRepositoryNeo4j.tweetedGenre(name);
    }

    @Transactional(readOnly = true)
    public  Collection<UserNode> getAllUsers(){
        return userRepositoryNeo4j.findAll();
    }

    public UserNode updateUser(UserNode userNode){
        System.out.println(userNode.getName());
        System.out.println(userNode.getId());
        return this.userRepositoryNeo4j.save(userNode);
    }

    public Collection<UserNode> insertUsers(){

        if(!userRepositoryNeo4j.findAll().isEmpty())
            userRepositoryNeo4j.deleteAll();
        List<User> users = userRepository.findAll();//ByOrderByScoreDesc();
        UserNode userNode;
        int i = 0;
        for (User user:users) {
            i++;
            userNode = new UserNode(user.getId(),user.getName(),user.getScreenName(),user.getFollowersCount(),user.getFriendsCount(),user.getLocation(),user.getIsVerified(),user.getScore());
            userRepositoryNeo4j.save(userNode);
            System.out.printf("Insertados %d usuarios, faltan %d\n",i,+users.size()-i);
        }

        return userRepositoryNeo4j.findAll();
    }



}
