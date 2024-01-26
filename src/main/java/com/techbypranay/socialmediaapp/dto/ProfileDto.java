package com.techbypranay.socialmediaapp.dto;

import java.util.List;

public class ProfileDto {

    private String userName;
    private String email;
    private String userPhotoUrl;

    private List<PostBasicDetailsDto> userPosts;

    private  List<PostBasicDetailsDto> savedPostsList;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public List<PostBasicDetailsDto> getSavedPostsList() {
        return savedPostsList;
    }

    public void setSavedPostsList(List<PostBasicDetailsDto> savedPostsList) {
        this.savedPostsList = savedPostsList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public List<PostBasicDetailsDto> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<PostBasicDetailsDto> userPosts) {
        this.userPosts = userPosts;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }





}
