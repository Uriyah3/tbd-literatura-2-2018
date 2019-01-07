package com.tbd.elasticsearch.moduloNeo4j;


import com.tbd.elasticsearch.entities.User;
import com.tbd.elasticsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.neo4j.ogm.session.Utils.map;

@Service
public class UserServiceNeo4j {

    @Autowired
    private UserRepository userRepository;

    private UserRepositoryNeo4j userRepositoryNeo4j;

    @Autowired
    private BookRepositoryNeo4j bookRepositoryNeo4j;

    @Autowired
    private GenreRepositoryNeo4j genreRepositoryNeo4j;

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


    private Map<String, Object> toD3Format(Collection<UserNode> userNodes,String type) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<UserNode> result = userNodes.iterator();
        while (result.hasNext()) {
            UserNode userNode = result.next();
            nodes.add(map("name", userNode.getScreenName(), "label", "user","id",i ,"_size", userNode.getScore()));
            int target = i;
            i++;
            if (type.equals("book")) {
                System.out.println(userNode.getBooks().size());
                for (BookNode bookNode : userNode.getBooks()) {
                    Map<String, Object> book = map("name", bookNode.getTitle(), "label", "book","id",i);
                    int source = nodes.indexOf(book);
                    if (source == -1) {
                        nodes.add(book);
                        source = i++;
                    }
                    rels.add(map("sid", target, "tid", source));
                }
            }
            else{
                for (GenreNode genreNode : userNode.getGenres()) {
                    Map<String, Object> genre = map("name", genreNode.getName(), "label", "genre","id",i);
                    int source = nodes.indexOf(genre);
                    if (source == -1) {
                        nodes.add(genre);
                        source = i++;
                    }
                    rels.add(map("sid", target, "tid", source));
                }
            }
        }
        return map("nodes", nodes, "links", rels);
    }


    public Map<String, Object>  graph(int limit,String type) {
        Collection<UserNode> result = userRepositoryNeo4j.graphBook(limit);
        bookRepositoryNeo4j.graphBook(limit);
        genreRepositoryNeo4j.graph(limit);
        return toD3Format(result,type);
    }


}
