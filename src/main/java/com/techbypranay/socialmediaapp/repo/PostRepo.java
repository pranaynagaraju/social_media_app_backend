package com.techbypranay.socialmediaapp.repo;

import com.techbypranay.socialmediaapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

   List<Post> findByFirebaseUserIdOrderByCreatedOnDesc(String uid);
}
