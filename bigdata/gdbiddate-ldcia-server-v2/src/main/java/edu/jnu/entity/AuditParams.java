package edu.jnu.entity;

import edu.jnu.utils.AuditTool;
import it.unisa.dia.gas.jpbc.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月04日 13时32分
 * @功能描述: 审计参数
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditParams {
    private Integer n;
    private String fa;
    private String h;
    private String h1;
    public Element getFaConcatH() {
        return AuditTool.hashTwo(this.fa+h.toString());
    }

    public Element getFaConcatIndex(Integer index) {
        return AuditTool.hashOne(fa+ index.toString());
    }
}
