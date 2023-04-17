package com.freelancer.ui.ChatMessaging;

/**
 * this class is used to hold the attributes of a contact
 */
public class UserModel {
    private String userId,userEmail, userName, userPassword;

    /**
     * default constructor
     */
    public UserModel(){

    }

    /**
     * constructor to initialize values
     * @param userName
     * @param userId
     * @param userEmail
     */
    public UserModel(String userName, String userId, String userEmail ) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * return the users name
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * return the users ID
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set the users id
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * return the users email
     * @return
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * set the users name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * return the users password
     * @return
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * set the users password
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
