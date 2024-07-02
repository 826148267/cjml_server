package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年04月27日 22时06分
 * @功能描述: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAddressDTO {
    private String userName;
    private String userAddress;
}
