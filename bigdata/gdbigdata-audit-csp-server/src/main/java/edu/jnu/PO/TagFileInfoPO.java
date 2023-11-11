package edu.jnu.PO;

import javax.persistence.*;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月15日 10:46
 */
@Entity(name = "table_tag_file_info")
public class TagFileInfoPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1") //自增
    private int id;
    @Column
    private String fileId;
    @Column
    private String filePath;
    @Column
    private String fileName;
    @Column(length = 400)
    private String R;

    public TagFileInfoPO(String fileId, String filePath, String fileName, String R) {
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileName = fileName;
        this.R = R;
    }

    public TagFileInfoPO() {
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

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }
}
