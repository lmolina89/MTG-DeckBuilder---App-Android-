package com.example.tfglorenzo_mtgdeckbuilder.models.userData;

import java.util.ArrayList;
import java.util.List;

public class UsersList {
    private List<User> userList;

    public UsersList() {
        this.userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
