package com.techbypranay.socialmediaapp.service;

import com.techbypranay.socialmediaapp.dto.PostDetailsDto;
import com.techbypranay.socialmediaapp.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    String uploadPost(String token, MultipartFile image, String postText) throws IOException;
    List<PostDto> getAllPosts(String token) throws IOException;

    String likePost(String token,int postId);

    String savePost(String token,int postId);

    String addComment(String token,int postId, String comment);
    PostDetailsDto getPostDetails(String token, int postId);
}

