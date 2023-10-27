package edu.jnu.dto;

import edu.jnu.valid.IsANum;
import edu.jnu.valid.NotContainSpace;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * 创建用户时的数据传输封装对象.
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月12日 15:46
 */
public class CreateUserDto {
    @NotContainSpace(message = "用户名中不可以含有是空格")
    @NotBlank(message = "用户名至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "字符串的长度应该是1~2500")
    @IsANum(message = "输入的用户名字符串必须是数字")
    private String userName;
    @NotContainSpace(message = "用户住址中不可以含有是空格")
    @NotBlank(message = "用户住址至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "字符串的长度应该是1~2500")
    @IsANum(message = "输入的用户住址符串必须是数字")
    private String userAddress;
    @NotContainSpace(message = "用户组织中不可以含有是空格")
    @NotBlank(message = "用户组织至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "用户组织字符串的长度应该是1~2500")
    @IsANum(message = "输入的用户组织符串必须是数字")
    private String userOrganization;
    @NotContainSpace(message = "用户文件数量中不可以含有是空格")
    @NotBlank(message = "用户文件数量至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "用户文件数量字符串的长度应该是1~2500")
    @IsANum(message = "输入的用户文件数量字符串必须是数字")
    private String userFileNums;

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
