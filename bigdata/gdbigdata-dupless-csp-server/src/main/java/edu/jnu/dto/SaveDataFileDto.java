package edu.jnu.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月15日 14:21
 */
@ApiModel("数据文件存储操作实体对象")
public class SaveDataFileDto {
    @JSONField(name = "userId")
    @ApiModelProperty("用户Id")
    private String userId;    // 用户id
    @JSONField(name = "dataHashValue")
    @ApiModelProperty("文件内容的哈希值(需调用我们提供的前端模块的相关函数产生的hash值，否则方案正确性可能不成立)")
    private String dataHashValue;   // 文件内容的hash值
    @JSONField(name = "strategy")
    @ApiModelProperty("去重策略:1为存储前去重，2为存储后去重")
    private Integer strategy;    // 去重策略:存储时去重、存储后去重
    @JSONField(name = "storageType")
    @ApiModelProperty("存储类型:1为阿里云对象存储OSS")
    private Integer storageType;     //  存储类型:OSS、大数据平台等
    @JSONField(name = "file")
    @ApiModelProperty("待存储文件，理论上可以是任何格式的文件，大小上限为3000MB")
    private MultipartFile file;     // 待存储文件

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDataHashValue() {
        return dataHashValue;
    }

    public void setDataHashValue(String dataHashValue) {
        this.dataHashValue = dataHashValue;
    }

    public Integer getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
