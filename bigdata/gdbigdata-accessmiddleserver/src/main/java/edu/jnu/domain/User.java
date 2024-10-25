package edu.jnu.domain;

/**
 * @author Guo zifan
 * @date 2022年01月30日 20:52
 */
public class User {
    private String userId;
    private String userName;
    private String userAddress;
    private String userOrganization;
    private String userFileNums;

    public User() {
    }

    public User(String userName, String userAddress, String userOrganization, String userFileNums) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userOrganization = userOrganization;
        this.userFileNums = userFileNums;
    }

    public User(String userId, String userName, String userAddress, String userOrganization, String userFileNums) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userOrganization = userOrganization;
        this.userFileNums = userFileNums;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(String userOrganization) {
        this.userOrganization = userOrganization;
    }

    public String getUserFileNums() {
        return userFileNums;
    }

    public void setUserFileNums(String userFileNums) {
        this.userFileNums = userFileNums;
    }
}
