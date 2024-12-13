package edu.jnu.DTO;

import edu.jnu.PO.DataFileInfoPO;

import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年07月15日 15:45
 */
public class DataFileInfoDTO {
    private Long totalsElement;
    private List<DataFileInfoPO> dataFileInfoPOList;

    public DataFileInfoDTO() {
    }

    public DataFileInfoDTO(Long totalsElement, List<DataFileInfoPO> dataFileInfoPOList) {
        this.totalsElement = totalsElement;
        this.dataFileInfoPOList = dataFileInfoPOList;
    }

    public Long getTotalsElement() {
        return totalsElement;
    }

    public void setTotalsElement(Long totalsElement) {
        this.totalsElement = totalsElement;
    }

    public List<DataFileInfoPO> getDataFileInfoPOList() {
        return dataFileInfoPOList;
    }

    public void setDataFileInfoPOList(List<DataFileInfoPO> dataFileInfoPOList) {
        this.dataFileInfoPOList = dataFileInfoPOList;
    }
}
