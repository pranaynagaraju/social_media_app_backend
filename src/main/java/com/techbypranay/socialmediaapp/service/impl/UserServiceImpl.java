package com.techbypranay.socialmediaapp.service.impl;
import com.google.firebase.auth.*;

import com.techbypranay.socialmediaapp.dto.PostBasicDetailsDto;
import com.techbypranay.socialmediaapp.dto.ProfileDto;
import com.techbypranay.socialmediaapp.dto.SearchUserDto;
import com.techbypranay.socialmediaapp.dto.UserDto;
import com.techbypranay.socialmediaapp.entity.Post;
import com.techbypranay.socialmediaapp.entity.Save;
import com.techbypranay.socialmediaapp.repo.PostRepo;
import com.techbypranay.socialmediaapp.repo.SaveRepo;
import com.techbypranay.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
public class UserServiceImpl implements UserService {

@Autowired
    PostRepo postRepo;

    @Autowired
    SaveRepo saveRepo;
    private Future<?> currentSearchTask;


    @Override
    public UserDto getUserDetailsByToken(String idToken) throws IOException {
        UserDto user = new UserDto();
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            user.setId(decodedToken.getUid());
            user.setEmail(decodedToken.getEmail());
            user.setName(decodedToken.getName());
            user.setPhotoURL(decodedToken.getPicture());
            return user;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public ProfileDto getUserProfile(String token, String uid) {
        ProfileDto profileDto =new ProfileDto();
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
            profileDto.setEmail(userRecord.getEmail());
            profileDto.setUserName(userRecord.getDisplayName());
            profileDto.setUserPhotoUrl(userRecord.getPhotoUrl());
            List<Post> posts = postRepo.findByFirebaseUserIdOrderByCreatedOnDesc(userRecord.getUid());
            List<PostBasicDetailsDto> postBasicDetailsDtoList=new ArrayList<>();
            for(Post post:posts)
            {
                PostBasicDetailsDto postBasicDetailsDto = new PostBasicDetailsDto();
                Optional<Post> postDetails= postRepo.findById(post.getPostId());
                if(postDetails.isPresent())
                {
                    postBasicDetailsDto.setPostId(postDetails.get().getPostId());
                    postBasicDetailsDto.setPostImageUrl(postDetails.get().getImageUrl());
                    postBasicDetailsDto.setTotalLikes(postDetails.get().getTotalLikes());
                    postBasicDetailsDto.setTotalComments(postDetails.get().getTotalComments());
                }

               postBasicDetailsDtoList.add(postBasicDetailsDto);
            }
            profileDto.setUserPosts(postBasicDetailsDtoList);
            List <PostBasicDetailsDto>savedpostsBasicDetailsDtoList=new ArrayList<>();
            List<Save> saveList = new ArrayList<>();
            if(decodedToken.getUid().equals(userRecord.getUid())) {
                saveList=saveRepo.findByFirebaseUserIdOrderByCreatedOnDesc(decodedToken.getUid());
                if (saveList != null) {
                    for (Save savePost : saveList) {
                        PostBasicDetailsDto postBasicDetailsDto = new PostBasicDetailsDto();
                        postBasicDetailsDto.setPostId(savePost.getPostId());
                        Optional<Post> savedPostDetails = postRepo.findById(postBasicDetailsDto.getPostId());
                        if (savedPostDetails.isPresent()) {
                            postBasicDetailsDto.setPostImageUrl(savedPostDetails.get().getImageUrl());
                            postBasicDetailsDto.setTotalLikes(savedPostDetails.get().getTotalLikes());
                            postBasicDetailsDto.setTotalComments(savedPostDetails.get().getTotalComments());
                            savedpostsBasicDetailsDtoList.add(postBasicDetailsDto);
                        }
                    }
                }
            }
            profileDto.setSavedPostsList(savedpostsBasicDetailsDtoList);
            return profileDto;
        }
        catch (Exception ignored)
        {
            return null;
        }

    }

    @Override
    public List<SearchUserDto> searchUser(String token, String search) {
        List <SearchUserDto> searchResults =new ArrayList<>();
            try {
                ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
                while (page != null) {
                    for (ExportedUserRecord user : page.getValues()) {
                        SearchUserDto searchUserDto= new SearchUserDto();
                        searchUserDto.setUid(user.getUid());
                        searchUserDto.setUserName(user.getDisplayName());
                        searchUserDto.setEmail(user.getEmail());
                        searchUserDto.setUserPhotoUrl(user.getPhotoUrl());
                        if(user.getDisplayName()!=null && user.getDisplayName().toLowerCase().contains(search))
                        {
                            searchResults.add(searchUserDto);
                        }
                    }
                    page = page.getNextPage();
                }
                return searchResults;
            }
            catch (Exception ignored) {
                return searchResults;
            }
        }



    }



