package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年04月26日 16时43分
 * @功能描述: 添加用户信息的接口实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserVO {
    @NotBlank(message = "用户名至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "字符串的长度应该是1~2500")
    private String userName;
    @NotBlank(message = "用户住址至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "字符串的长度应该是1~2500")
    private String userAddress;
    @NotBlank(message = "用户组织至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "用户组织字符串的长度应该是1~2500")
    private String userOrganization;
    @NotBlank(message = "用户文件数量至少需要有一个非空白字符")
    @Length(min = 1, max = 2500, message = "用户文件数量字符串的长度应该是1~2500")
    private String userFileNums;
}
