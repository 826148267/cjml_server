package edu.jnu.domain;

import edu.jnu.constant.StorageType;
import edu.jnu.dto.SaveDataFileDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月15日 21:49
 */
public class FileInfo {
    private String userId;    // 用户id
    private String dataHashValue;   // 文件内容的hash值
    private Integer storageType;     //  存储类型:OSS、大数据平台等
    private MultipartFile file;     // 待存储文件
    private Integer deduplicateFlag;    // 去重标志位：0代表未去重，1代表已去重

    public FileInfo(SaveDataFileDto saveDataFileDto) {
        this.userId = saveDataFileDto.getUserId();
        this.dataHashValue = saveDataFileDto.getDataHashValue();
        this.storageType = saveDataFileDto.getStorageType();
        this.file = saveDataFileDto.getFile();
    }

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

    public Integer getDeduplicateFlag() {
        return deduplicateFlag;
    }

    public void setDeduplicateFlag(Integer deduplicateFlag) {
        this.deduplicateFlag = deduplicateFlag;
    }
}
