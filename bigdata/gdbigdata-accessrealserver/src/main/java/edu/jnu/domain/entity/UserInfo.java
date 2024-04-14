package edu.jnu.domain.entity;

import javax.persistence.*;

@Entity(name = "table_user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1") //自增
    private int id;
    @Column(length = 2500)
    private String userName;
    @Column(length = 2500)
    private String userOrganization;
    @Column(length = 2500)
    private String userAddress;
    @Column(length = 2500)
    private String userFileNums;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(String userOrganization) {
        this.userOrganization = userOrganization;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserFileNums() {
        return userFileNums;
    }

    public void setUserFileNums(String userFileNums) {
        this.userFileNums = userFileNums;
    }
}
