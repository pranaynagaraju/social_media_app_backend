package com.techbypranay.socialmediaapp.repo;

import com.techbypranay.socialmediaapp.entity.Post;
import com.techbypranay.socialmediaapp.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveRepo extends JpaRepository<Save,String> {
    Save findByPostIdAndFirebaseUserId(int postId, String firebaseUserId);
    List<Save> findByFirebaseUserIdOrderByCreatedOnDesc(String uid);
}
