package com.techbypranay.socialmediaapp.controller;
import com.techbypranay.socialmediaapp.dto.ProfileDto;
import com.techbypranay.socialmediaapp.dto.SearchUserDto;
import com.techbypranay.socialmediaapp.dto.UserDto;
import com.techbypranay.socialmediaapp.service.UserService;
import com.techbypranay.socialmediaapp.service.impl.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserContoller {
    @Autowired
    UserService userService;
    @GetMapping("/user-details")
    public ResponseEntity<UserDto> user(@RequestParam String token) throws IOException {
        UserDto userDto = userService.getUserDetailsByToken(token);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userDto, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user-profile")
    public ResponseEntity<ProfileDto> userProfile(@RequestParam String token,@RequestParam String uid) throws IOException {
        ProfileDto profileDto = userService.getUserProfile(token,uid);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<SearchUserDto>> searchUserProfile(@RequestParam String token,@RequestParam String q) throws IOException {
        q=q.toLowerCase();
        List<SearchUserDto> searchUserDto = userService.searchUser(token,q);
        return new ResponseEntity<>(searchUserDto, HttpStatus.OK);
    }
}