package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 18时39分
 * @功能描述: 账户注册类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterVO {
    private String userName;
    private String password;
    private String userAddress;
    private String userOrganization;
    private String userFileNums;
}
