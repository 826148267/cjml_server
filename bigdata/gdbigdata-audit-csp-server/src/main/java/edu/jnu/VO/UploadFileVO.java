package edu.jnu.VO;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月12日 21:52
 */
@ApiModel("上传文件实体类")
public class UploadFileVO {
    @JSONField(name = "userId")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;
    @JSONField(name = "fileStoragePath")
    @ApiModelProperty(value = "文件存储路径", required = true)
    private String fileStoragePath;
    @JSONField(name = "tagFile")
    @ApiModelProperty(value = "标签文件", required = true)
    private MultipartFile tagFile;
    @JSONField(name = "dataFile")
    @ApiModelProperty(value = "数据文件", required = true)
    private MultipartFile dataFile;
    @JSONField(name = "blockNum")
    @ApiModelProperty(value = "数据块数", required = true)
    private Integer blockNum;
    @JSONField(name = "r")
    @ApiModelProperty(value = "验签参数", required = true)
    private String r;
    @JSONField(name = "mimeType")
    @ApiModelProperty(value = "文件格式", required = true)
    private String mimeType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileStoragePath() {
        return fileStoragePath;
    }

    public void setFileStoragePath(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }

    public MultipartFile getTagFile() {
        return tagFile;
    }

    public void setTagFile(MultipartFile tagFile) {
        this.tagFile = tagFile;
    }

    public MultipartFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(MultipartFile dataFile) {
        this.dataFile = dataFile;
    }

    public Integer getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Integer blockNum) {
        this.blockNum = blockNum;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
