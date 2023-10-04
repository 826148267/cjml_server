package edu.jnu.po;

import edu.jnu.domain.FileInfo;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月15日 18:32
 */
@Entity(name = "table_data_file_info")
public class DataFileInfoPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1")    // 自增
    private int id;

    @Column
    private String userId;

    @Column
    private String fileId;

    @Column
    private String fileLogicPath;

    @Column
    private String fileActualPath;

    @Column
    private String fileLogicName;

    @Column
    private String fileActualName;

    @Column
    private String dataHashValue;

    @Column
    private Integer deduplicateFlag;

    public DataFileInfoPo() {
    }

    public DataFileInfoPo(String userId, String fileLogicPath, String fileActualPath, String fileLogicName, String fileActualName, String dataHashValue) {
        this.userId = userId;
        this.fileId = this.getUniqueId();
        this.fileLogicPath = fileLogicPath;
        this.fileActualPath = fileActualPath;
        this.fileLogicName = fileLogicName;
        this.fileActualName = fileActualName;
        this.dataHashValue = dataHashValue;
    }

    public DataFileInfoPo(FileInfo fileInfo) {
        this.userId = fileInfo.getUserId();
        this.fileId = this.getUniqueId();
        this.fileLogicPath = Objects.requireNonNull(fileInfo.getFile().getOriginalFilename()).split(">>")[0];
        this.fileLogicName = Objects.requireNonNull(fileInfo.getFile().getOriginalFilename()).split(">>")[1];
        this.fileActualName = this.fileLogicName;
        this.fileActualPath = this.fileLogicPath;
        this.dataHashValue = fileInfo.getDataHashValue();
        this.deduplicateFlag = fileInfo.getDeduplicateFlag();
    }

    // 全局唯一id，如果有其他更好的方式也可替换
    private String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFileActualPath() {
        return fileActualPath;
    }

    public void setFileActualPath(String fileActualPath) {
        this.fileActualPath = fileActualPath;
    }

    public String getFileLogicName() {
        return fileLogicName;
    }

    public void setFileLogicName(String fileLogicName) {
        this.fileLogicName = fileLogicName;
    }

    public String getFileActualName() {
        return fileActualName;
    }

    public void setFileActualName(String fileActualName) {
        this.fileActualName = fileActualName;
    }

    public String getDataHashValue() {
        return dataHashValue;
    }

    public void setDataHashValue(String dataHashValue) {
        this.dataHashValue = dataHashValue;
    }

    public Integer getDeduplicateFlag() {
        return deduplicateFlag;
    }

    public void setDeduplicateFlag(Integer deduplicateFlag) {
        this.deduplicateFlag = deduplicateFlag;
    }
}
