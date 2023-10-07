package edu.jnu.POJO;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月15日 15:56
 */
public class DataFileInfo {
    @JSONField(name = "fileId")
    private String fileId;
    @JSONField(name = "filePath")
    private String filePath;
    @JSONField(name = "fileName")
    private String fileName;
    @JSONField(name = "blockNum")
    private Integer blockNum;
    @JSONField(name = "tagFileId")
    private String tagFileId;
    @JSONField(name = "userId")
    private String userId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Integer blockNum) {
        this.blockNum = blockNum;
    }

    public String getTagFileId() {
        return tagFileId;
    }

    public void setTagFileId(String tagFileId) {
        this.tagFileId = tagFileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
