package edu.jnu.VO;

import com.alibaba.fastjson.annotation.JSONField;
import edu.jnu.DTO.DataFileInfoDTO;
import edu.jnu.PO.DataFileInfoPO;
import edu.jnu.POJO.DataFileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月05日 16:50
 */
public class GetFileListVO {

    @JSONField(name = "totalsElement")
    private String totalsElement;

    @JSONField(name = "dataFileInfoList")
    private List<DataFileInfo> dataFileInfoList;

    public GetFileListVO() {
    }

    public GetFileListVO(DataFileInfoDTO dataFileInfoDTO) {
        this.totalsElement = String.valueOf(dataFileInfoDTO.getTotalsElement());
        this.dataFileInfoList = new ArrayList<>();
        for (DataFileInfoPO tmp: dataFileInfoDTO.getDataFileInfoPOList()) {
            DataFileInfo dataFileInfo = new DataFileInfo();
            dataFileInfo.setFileId(tmp.getFileId());
            dataFileInfo.setFileName(tmp.getFileName());
            dataFileInfo.setFilePath(tmp.getFilePath());
            dataFileInfo.setTagFileId(tmp.getTagFileId());
            dataFileInfo.setBlockNum(tmp.getBlockNum());
            dataFileInfo.setUserId(tmp.getUserId());
            this.dataFileInfoList.add(dataFileInfo);
        }
    }



    public String getTotalsElement() {
        return totalsElement;
    }

    public void setTotalsElement(String totalsElement) {
        this.totalsElement = totalsElement;
    }

    public List<DataFileInfo> getDataFileInfoList() {
        return dataFileInfoList;
    }

    public void setDataFileInfoList(List<DataFileInfo> dataFileInfoList) {
        this.dataFileInfoList = dataFileInfoList;
    }
}
