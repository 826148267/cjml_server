package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月05日 20时43分
 * @功能描述: 标签所有权转移参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransParams {
    // 在Zp群上
    private String r;
    // 在Zp群上
    private String aux;
    // 在G群上
    private String v;
}
