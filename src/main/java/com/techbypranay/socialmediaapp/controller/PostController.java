package com.techbypranay.socialmediaapp.controller;

import com.techbypranay.socialmediaapp.dto.PostDetailsDto;
import com.techbypranay.socialmediaapp.dto.PostDto;
import com.techbypranay.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    @Autowired
    PostService postService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("token") String token, @RequestParam("file") MultipartFile file, @RequestParam("postText") String postText) throws IOException {
        String result = postService.uploadPost(token,file,postText);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get-all-posts")
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam("token") String token) throws IOException {
        return ResponseEntity.ok( postService.getAllPosts(token));
    }

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestParam("token") String token,@RequestParam("postId") int postId) throws IOException {
        return ResponseEntity.ok( postService.likePost(token,postId));
    }
    @PostMapping("/save")
    public ResponseEntity<String> savePost(@RequestParam("token") String token,@RequestParam("postId") int postId) throws IOException {
        return ResponseEntity.ok( postService.savePost(token,postId));
    }
    @GetMapping("/get-post-details")
    public ResponseEntity<PostDetailsDto> getPostDetails(@RequestParam("token") String token, @RequestParam("postId") int postId) throws IOException {
        return ResponseEntity.ok( postService.getPostDetails(token,postId));
    }
    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(@RequestParam("token") String token, @RequestParam("postId") int postId, @RequestParam("comment") String comment) throws IOException {
        return ResponseEntity.ok( postService.addComment(token,postId,comment));
    }
}
