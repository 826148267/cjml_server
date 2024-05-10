package edu.jnu.po;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月10日 20:30
 */
@Entity(name = "table_key_file_info")
public class KeyFileInfoPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1")    // 自增
    private int id;

    @Column
    private String userId;

    @Column
    private String fileId;

    @Column
    private String filePath;

    @Column
    private String fileName;

    public KeyFileInfoPo(String userId, MultipartFile file) {
        this.userId = userId;
        this.fileId = this.getUniqueId();
        this.filePath = Objects.requireNonNull(file.getOriginalFilename()).split(">>")[0];
        this.fileName = Objects.requireNonNull(file.getOriginalFilename()).split(">>")[1];
    }

    public KeyFileInfoPo() {

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
}
