package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月06日 19时54分
 * @功能描述: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileListDTO {
    private String fileId;
    private String fileName;
    private String isFirstSaver;
    private String signFileId;
    private String auditParamsFileId;
}
