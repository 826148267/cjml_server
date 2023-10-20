package edu.jnu.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月09日 21:18
 */
public class GetUserFileListDto {
    @JSONField(name = "总记录数量")
    private Long recordTotals;
    @JSONField(name = "fileInfoList")
    private List<FileInfo> fileInfoList;

    public Long getRecordTotals() {
        return recordTotals;
    }

    public void setRecordTotals(Long recordTotals) {
        this.recordTotals = recordTotals;
    }

    public List<FileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<FileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }
}
