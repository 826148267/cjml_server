package edu.jnu.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月06日 13时25分
 * @功能描述: TODO
 */
@Entity(name = "table_data_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataFilePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1")    // 自增
    private Integer id;

    @Column
    private String fileId;

    @Column
    private String userName;

    @Column
    private String fileName;

    @Column
    private String fileActualId;

    @Column
    private String fileAbstract;

    @Column
    private String keyFileId;

    @Column
    private String tagFileId;

    @Column
    private String mimeType;

    @Column
    private String auditParamsFileId;

    @Column
    private String transParamsFileId;

    @Column
    private Integer firstSaveFlag;
}
