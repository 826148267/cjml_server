package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月04日 15时54分
 * @功能描述: 完整性证明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntegrityProof {
    private String signAggregation;
    private String dataAggregation;
}
