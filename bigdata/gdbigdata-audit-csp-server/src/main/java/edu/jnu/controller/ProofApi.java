package edu.jnu.controller;

import edu.jnu.VO.IntegerityProof;
import edu.jnu.VO.ProofIntegrityVO;
import edu.jnu.service.SepdpProofService;
import edu.jnu.service.TagFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年05月28日 10:47
 */
@RestController
@Api(tags = "完整性证明接口")
public class ProofApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProofApi.class);

    @Autowired
    private SepdpProofService sepdpProofService;

    @Autowired
    private TagFileService tagFileService;

    /**
     * 返回完整性证明材料
     * @param proofIntegrityVO 文件路径，质询集合，随机数集合，bucketName等
     * @return  返回 α和γ
     */
    @PostMapping("/sepdp/proofs/")
    @ApiOperation("完整性证明接口")
    public ResponseEntity<IntegerityProof> proofIntegrity(@RequestBody ProofIntegrityVO proofIntegrityVO) {
        // 通过文件路径获取文件
        String dataFullPath = "audit/data/"+proofIntegrityVO.getFilePath()+"/"+proofIntegrityVO.getFileName();    //  获取文件全路径
        String tagFullPath = "audit/tag/"+tagFileService.getTagFullPathByTagFileId(proofIntegrityVO.getTagFileId());    //  获取文件全路径
        // 读取数据文件，根据下标集合读取mi集合，计算beta
        // 读取标签文件，根据下标集合读取si集合，计算alpha
        ArrayList<BigInteger> mList = sepdpProofService.getMList(dataFullPath, proofIntegrityVO.getiList());    // 最后一块是文件格式
        ArrayList<BigInteger> sList = sepdpProofService.getSList(tagFullPath, proofIntegrityVO.getiList());   //最后一块是R
        if (mList.size() != sList.size()) {
            LOGGER.error("标签数量与数据块数量关系错误");
            return ResponseEntity.internalServerError().body(null);
        }
        // 获取R
        BigInteger R = tagFileService.getRByFileId(proofIntegrityVO.getTagFileId());
        // 通过beta计算gamma
        BigInteger gamma = sepdpProofService.getGamma(proofIntegrityVO.getvList(), mList);
        BigInteger alpha = sepdpProofService.getAlpha(R, proofIntegrityVO.getvList(), sList);
        // 将gamma和alpha封装返回
        LOGGER.info("成功返回证明,其中:");
        LOGGER.info("alpha:"+String.valueOf(alpha));
        LOGGER.info("gamma:"+String.valueOf(gamma));
        LOGGER.info("R:"+String.valueOf(R));
        return ResponseEntity.ok(new IntegerityProof(String.valueOf(alpha), String.valueOf(gamma), String.valueOf(R)));
    }

}
