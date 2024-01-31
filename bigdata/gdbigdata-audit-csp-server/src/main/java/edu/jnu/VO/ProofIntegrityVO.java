package edu.jnu.VO;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月04日 20:56
 */
@ApiModel("完整性证明实体类")
public class ProofIntegrityVO {
    @JSONField(name = "filePath")
    @ApiModelProperty(value = "文件路径", required = true)
    private String filePath;
    @JSONField(name = "fileName")
    @ApiModelProperty(value = "文件名", required = true)
    private String fileName;
    @JSONField(name = "tagFileId")
    @ApiModelProperty(value = "标签文件id", required = true)
    private String tagFileId;
    @JSONField(name = "iList")
    @ApiModelProperty(value = "有序无重复随机下标数组（从小到大）", required = true)
    private ArrayList<Integer> iList;
    @JSONField(name = "vList")
    @ApiModelProperty(value = "随机数数组", required = true)
    private ArrayList<BigInteger> vList;

    public ProofIntegrityVO() {
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

    public String getTagFileId() {
        return tagFileId;
    }

    public void setTagFileId(String tagFileId) {
        this.tagFileId = tagFileId;
    }

    public ArrayList<Integer> getiList() {
        return iList;
    }

    public void setiList(ArrayList<Integer> iList) {
        this.iList = iList;
    }

    public ArrayList<BigInteger> getvList() {
        return vList;
    }

    public void setvList(ArrayList<BigInteger> vList) {
        this.vList = vList;
    }
}
