package com.techbypranay.socialmediaapp.dto;

public class CommentDto {

    private String commentedUserName;
    private String commentedUserPhoto;
    private String comment;
    private String commentId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentedUserName() {
        return commentedUserName;
    }

    public void setCommentedUserName(String commentedUserName) {
        this.commentedUserName = commentedUserName;
    }

    public String getCommentedUserPhoto() {
        return commentedUserPhoto;
    }

    public void setCommentedUserPhoto(String commentedUserPhoto) {
        this.commentedUserPhoto = commentedUserPhoto;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
