package edu.jnu.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月10日 20:11
 */
@ApiModel("密钥文件存储操作实体对象")
public class SaveKeyFileDto {
    @JSONField(name = "userId")
    @ApiModelProperty("用户Id")
    private String userId;    // 用户id
    @JSONField(name = "file")
    @ApiModelProperty("待存储文件，理论上可以是任何格式的文件，大小上限为3000MB")
    private MultipartFile file;     // 待存储文件
    @JSONField(name = "storageType")
    @ApiModelProperty("存储类型:1为阿里云对象存储OSS")
    private Integer storageType;     //  存储类型:OSS、大数据平台等

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }
}
