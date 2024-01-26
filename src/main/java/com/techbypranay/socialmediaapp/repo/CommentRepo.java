package com.techbypranay.socialmediaapp.repo;

import com.techbypranay.socialmediaapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment,String> {
    List<Comment> findByPostId(int postId);
}
