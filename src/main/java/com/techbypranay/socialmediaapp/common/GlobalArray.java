package com.techbypranay.socialmediaapp.common;
import com.techbypranay.socialmediaapp.dto.SearchUserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalArray {

    private List<SearchUserDto> allUsers;

    public List<SearchUserDto> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<SearchUserDto> allUsers) {
        this.allUsers = allUsers;
    }
}