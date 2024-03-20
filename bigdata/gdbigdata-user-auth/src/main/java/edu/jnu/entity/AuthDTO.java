package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 19时29分
 * @功能描述: 用户认证传输实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    private String userName;
    private String password;
}
