package com.freelancer.ui.ChatMessaging;

public class UserModel {
    private String userId,userEmail, userName, userPassword;

    public UserModel(){

    }

    public UserModel(String userName, String userId, String userEmail ) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
