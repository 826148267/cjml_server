package edu.jnu.PO;

import javax.persistence.*;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 20:52
 */
@Entity(name = "table_data_file_info")
public class DataFileInfoPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1") //自增
    private int id;
    @Column
    private String fileId;
    @Column
    private String filePath;
    @Column
    private String fileName;
    @Column
    private Integer blockNum;
    @Column
    private String tagFileId;
    @Column
    private String userId;
    @Column
    private String mimeType;

    public DataFileInfoPO(String fileId, String filePath, String fileName, Integer blockNum, String tagFileId, String userId, String mimeType) {
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileName = fileName;
        this.blockNum = blockNum;
        this.tagFileId = tagFileId;
        this.userId = userId;
        this.mimeType = mimeType;
    }

    public DataFileInfoPO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
