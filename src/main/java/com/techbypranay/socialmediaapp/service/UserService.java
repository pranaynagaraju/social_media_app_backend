package com.techbypranay.socialmediaapp.service;

import com.techbypranay.socialmediaapp.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface UserService {

UserDto getUserDetailsByToken(String token) throws IOException;

ProfileDto getUserProfile(String token, String uid);

List<SearchUserDto> searchUser(String token, String search);

}
