package edu.jnu.dto;

import com.alibaba.fastjson.annotation.JSONField;
import edu.jnu.po.DataFileInfoPo;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月09日 21:59
 */
public class FileInfo {
    @JSONField(name = "userId")
    private String userId;    // 用户id
    @JSONField(name = "dataHashValue")
    private String dataHashValue;   // 文件内容的hash值
    @JSONField(name = "fileId")
    private String fileId;  // 文件Id
    @JSONField(name = "fileLogicPath")
    private String fileLogicPath;   // 文件逻辑地址
    @JSONField(name = "fileLogicName")
    private String fileLogicName;   // 文件逻辑名
    @JSONField(name = "deduplicateFlag")
    private Integer deduplicateFlag; // 去重标志

    public FileInfo(DataFileInfoPo dataFileInfoPo) {
        this.userId = dataFileInfoPo.getUserId();
        this.dataHashValue = dataFileInfoPo.getDataHashValue();
        this.fileId = dataFileInfoPo.getFileId();
        this.fileLogicPath = dataFileInfoPo.getFileLogicPath();
        this.fileLogicName = dataFileInfoPo.getFileLogicName();
        this.deduplicateFlag = dataFileInfoPo.getDeduplicateFlag();
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileLogicPath() {
        return fileLogicPath;
    }

    public void setFileLogicPath(String fileLogicPath) {
        this.fileLogicPath = fileLogicPath;
    }

    public String getFileLogicName() {
        return fileLogicName;
    }

    public void setFileLogicName(String fileLogicName) {
        this.fileLogicName = fileLogicName;
    }

    public Integer getDeduplicateFlag() {
        return deduplicateFlag;
    }

    public void setDeduplicateFlag(Integer deduplicateFlag) {
        this.deduplicateFlag = deduplicateFlag;
    }
}
