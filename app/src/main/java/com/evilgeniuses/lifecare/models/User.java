package com.evilgeniuses.lifecare.models;

public class User {

    public String userID;
    public String userUsername;
    public String userProfileImageURL;
    public String userName;
    public String userLastname;
    public String userEmail;
    public String userPassword;
    public String userCategory;
    public String userExperience;
    public String userAge;

    public User(String userID, String userUsername, String userProfileImageURL, String userName, String userLastname, String userEmail, String userPassword, String userCategory, String userExperience, String userAge) {
        this.userID = userID;
        this.userUsername = userUsername;
        this.userProfileImageURL = userProfileImageURL;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userCategory = userCategory;
        this.userExperience = userExperience;
        this.userAge = userAge;
    }

    public User() {
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserProfileImageURL() {
        return userProfileImageURL;
    }

    public void setUserProfileImageURL(String userProfileImageURL) {
        this.userProfileImageURL = userProfileImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(String userExperience) {
        this.userExperience = userExperience;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

}
