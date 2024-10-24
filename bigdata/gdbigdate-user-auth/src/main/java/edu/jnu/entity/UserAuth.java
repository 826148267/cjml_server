package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 19时33分
 * @功能描述: TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_auth")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1")
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
}
