package edu.jnu.domain;

import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 20:52
 */
@Entity(name = "table_user_file_position")
public class UserFilePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "1") //自增
    private int id;
    @Column
    private String filePath;
    @Column
    private int duplicateNum;

    public UserFilePosition() {
    }

    public UserFilePosition(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getDuplicateNum() {
        return duplicateNum;
    }

    public void setDuplicateNum(int duplicateNum) {
        this.duplicateNum = duplicateNum;
    }
}
