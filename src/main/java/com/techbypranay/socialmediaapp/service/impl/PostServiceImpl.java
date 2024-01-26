package com.techbypranay.socialmediaapp.service.impl;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.techbypranay.socialmediaapp.dto.CommentDto;
import com.techbypranay.socialmediaapp.dto.PostDetailsDto;
import com.techbypranay.socialmediaapp.dto.PostDto;
import com.techbypranay.socialmediaapp.entity.Comment;
import com.techbypranay.socialmediaapp.entity.Like;
import com.techbypranay.socialmediaapp.entity.Post;
import com.techbypranay.socialmediaapp.entity.Save;
import com.techbypranay.socialmediaapp.repo.CommentRepo;
import com.techbypranay.socialmediaapp.repo.LikeRepo;
import com.techbypranay.socialmediaapp.repo.PostRepo;
import com.techbypranay.socialmediaapp.repo.SaveRepo;
import com.techbypranay.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private FirebaseApp firebaseApp;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private SaveRepo saveRepo;
    @Override
    public String uploadPost(String token, MultipartFile image, String postText) throws IOException {

        try {
            String cloudinaryUrl = cloudinaryService.uploadImage(image);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            Post post = new Post();
            post.setFirebaseUserId(decodedToken.getUid());
            post.setCreatedOn(Instant.now());
            post.setPostText(postText);
            post.setImageUrl(cloudinaryUrl);
            postRepo.save(post);
            return "Post uploaded";
        } catch (Exception e) {
            System.out.println(e);
            return "Error uploading post";
        }
    }

    @Override
    public List<PostDto> getAllPosts(String token) throws IOException {
        List<Post> postsList = postRepo.findAll(Sort.by(Sort.Direction.DESC, "postId"));
        List<PostDto> postsDtoList = new ArrayList<>();

        for (int i = 0; i < postsList.size(); i++) {
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                PostDto postDto = new PostDto();
                postDto.setName(FirebaseAuth.getInstance().getUser(postsList.get(i).getFirebaseUserId()).getDisplayName());
                postDto.setProfilePicture(FirebaseAuth.getInstance().getUser(postsList.get(i).getFirebaseUserId()).getPhotoUrl());
                postDto.setPostId(postsList.get(i).getPostId());
                postDto.setImageUrl(postsList.get(i).getImageUrl());
                postDto.setPostText(postsList.get(i).getPostText());
                postDto.setTotalLikes(postsList.get(i).getTotalLikes());
                postDto.setCreatedOn(postsList.get(i).getCreatedOn());
                postDto.setTotalComments(postsList.get(i).getTotalComments());
                postDto.setLiked(likeRepo.findByPostIdAndFirebaseUserId(postsList.get(i).getPostId(), decodedToken.getUid()) != null);
                postDto.setSaved(saveRepo.findByPostIdAndFirebaseUserId(postsList.get(i).getPostId(), decodedToken.getUid()) != null);
                postsDtoList.add(postDto);
            }
            catch (Exception ignored) {

            }
        }
        return postsDtoList;
    }

    @Override
    public String likePost(String token,int postId) {
        System.out.println("LikePost service started");
        String response = "";
        Optional<Post> existingPost = postRepo.findById(postId);
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            Like existingLike = likeRepo.findByPostIdAndFirebaseUserId(postId, decodedToken.getUid());
            if (existingLike == null) {
                Like newLike = new Like();
                newLike.setPostId(postId);
                newLike.setFirebaseUserId(decodedToken.getUid());
                likeRepo.save(newLike);
                if (existingPost.isPresent()) {
                    Post post = existingPost.get();
                    post.setTotalLikes(post.getTotalLikes()+1);
                    postRepo.save(post);
                }
                response="post liked";
            } else {
                likeRepo.delete(existingLike);
                if (existingPost.isPresent()) {
                    Post post = existingPost.get();
                    post.setTotalLikes(post.getTotalLikes()-1);
                    postRepo.save(post);
                }
                response="post disliked";
            }
;
        }
        catch (Exception e)
        {
        System.out.println(e);
        }
        return response;
    }

    @Override
    public String savePost(String token, int postId) {
          try
          {
              FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
         Save existingSave = saveRepo.findByPostIdAndFirebaseUserId(postId, decodedToken.getUid());
        if (existingSave == null) {
            Save save = new Save();
            save.setPostId(postId);
            save.setCreatedOn(Instant.now());
            save.setFirebaseUserId(decodedToken.getUid());
            saveRepo.save(save);

            return "Post saved";
        }
        else {
         saveRepo.delete(existingSave);
            return "Post unsaved";
        }
    }
        catch (Exception ignored)
        {
            return "error";
        }

    }

    @Override
    public String addComment(String token, int postId, String comment) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            Comment commentObj = new Comment();
            commentObj.setFirebaseUserId(decodedToken.getUid());
            commentObj.setPostId(postId);
            commentObj.setComment(comment);
            commentRepo.save(commentObj);
            postRepo.findById(postId)
                    .ifPresent(post -> {
                        post.setTotalComments(post.getTotalComments() + 1);
                        postRepo.save(post);
                    });

            return "Comment Saved";
        }
        catch (Exception e)
        {
            return "error:" +e;
        }
    }

    @Override
    public PostDetailsDto getPostDetails(String token, int postId) {
        PostDetailsDto postDetailsDto= new PostDetailsDto();
        List<CommentDto> commentDtoList= new ArrayList<>();
        List<Comment> comments= commentRepo.findByPostId(postId);

        try {
            postRepo.findById(postId)
                    .ifPresent(post -> {
                        postDetailsDto.setPostId(postId);
                        postDetailsDto.setPostImageUrl(post.getImageUrl());
                        postDetailsDto.setTotalComments(post.getTotalComments());
                        postDetailsDto.setTotalLikes(post.getTotalLikes());
                        try {
                            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                            postDetailsDto.setLiked(likeRepo.findByPostIdAndFirebaseUserId(postId, decodedToken.getUid()) != null);
                            postDetailsDto.setSaved(saveRepo.findByPostIdAndFirebaseUserId(postId, decodedToken.getUid()) != null);
                            UserRecord user = FirebaseAuth.getInstance().getUser(post.getFirebaseUserId());
                            postDetailsDto.setPostUploadedByUserName(user.getDisplayName());
                            postDetailsDto.setPostUploadedByUserPhoto(user.getPhotoUrl());
                        } catch (FirebaseAuthException e) {
                            throw new RuntimeException("Firebase Exception");
                        }

                    });
            for (Comment comment : comments) {
                CommentDto commentDto =new CommentDto();
                try {
                    UserRecord user = FirebaseAuth.getInstance().getUser(comment.getFirebaseUserId());
                    commentDto.setCommentedUserName(user.getDisplayName());
                    commentDto.setCommentedUserPhoto(user.getPhotoUrl());
                }
                catch (FirebaseAuthException e) {
                    throw new RuntimeException("Firebase Exception");
                }
                commentDto.setComment(comment.getComment());
                commentDto.setCommentId(comment.getCommentId());
                commentDtoList.add(commentDto);
            }
            postDetailsDto.setAllComments(commentDtoList);
        }
        catch (Exception ignored)
        {

        }
        return postDetailsDto;
    }

}
