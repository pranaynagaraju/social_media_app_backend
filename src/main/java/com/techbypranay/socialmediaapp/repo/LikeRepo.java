package com.techbypranay.socialmediaapp.repo;
import com.techbypranay.socialmediaapp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like,String> {
    Like findByPostIdAndFirebaseUserId(int postId, String firebaseUserId);

}
