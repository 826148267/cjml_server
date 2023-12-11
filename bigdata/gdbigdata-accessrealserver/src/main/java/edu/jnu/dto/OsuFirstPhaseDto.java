package edu.jnu.dto;

import edu.jnu.domain.CipherText;

/**
 * @author Guo zifan
 * @date 2022年02月10日 20:36
 */
public class OsuFirstPhaseDto {
    private int[] accessSet;
    private CipherText[] taos;
    private String targetColumn;

    public int[] getAccessSet() {
        return accessSet;
    }

    public void setAccessSet(int[] accessSet) {
        this.accessSet = accessSet;
    }

    public CipherText[] getTaos() {
        return taos;
    }

    public void setTaos(CipherText[] taos) {
        this.taos = taos;
    }

    public String getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
    }
}
