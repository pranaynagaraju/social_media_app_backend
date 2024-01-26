package com.techbypranay.socialmediaapp.dto;

import java.util.List;

public class PostDetailsDto {

    private int postId;
    private String postImageUrl;

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    private Boolean saved;
    private boolean liked;
    public int getPostId() {
        return postId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostUploadedByUserName() {
        return postUploadedByUserName;
    }

    public void setPostUploadedByUserName(String postUploadedByUserName) {
        this.postUploadedByUserName = postUploadedByUserName;
    }

    public String getPostUploadedByUserPhoto() {
        return postUploadedByUserPhoto;
    }

    public void setPostUploadedByUserPhoto(String postUploadedByUserPhoto) {
        this.postUploadedByUserPhoto = postUploadedByUserPhoto;
    }

    public List<CommentDto> getAllComments() {
        return allComments;
    }

    public void setAllComments(List<CommentDto> allComments) {
        this.allComments = allComments;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    private  String postUploadedByUserName;

    private String postUploadedByUserPhoto;

    private List<CommentDto> allComments;

    private  int totalLikes;
    private  int totalComments;

}
