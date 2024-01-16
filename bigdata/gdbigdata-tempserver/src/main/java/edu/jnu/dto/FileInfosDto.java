package edu.jnu.dto;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月15日 17:08
 */
public class FileInfosDto {
    private String fileName;
    private String filePath;
    private int duplicateNum;

    public FileInfosDto() {
    }

    public FileInfosDto(String fileName, String filePath, int duplicateNum) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.duplicateNum = duplicateNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
