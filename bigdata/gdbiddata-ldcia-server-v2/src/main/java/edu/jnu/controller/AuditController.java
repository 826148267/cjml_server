package edu.jnu.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.model.OSSObject;
import edu.jnu.entity.Challenge;
import edu.jnu.entity.FileListDTO;
import edu.jnu.entity.IntegrityProof;
import edu.jnu.service.FileService;
import edu.jnu.service.OSSService;
import edu.jnu.utils.AuditTool;
import edu.jnu.utils.StringTool;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月05日 21时24分
 * @功能描述: 审计接口
 */
@RestController
public class AuditController {

    @Autowired
    private FileService fileService;

    @Autowired
    private OSSService ossService;

    @GetMapping("/files/{userName}")
    public ResponseEntity<List<FileListDTO>> uploadParams(@PathVariable("userName") String userName) {
        List<FileListDTO> files = fileService.listFiles(userName);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/auditParams/{fileId}")
    public ResponseEntity<String> getAuditParams(@PathVariable("fileId") String fileId) {
        return ResponseEntity.ok(fileService.getAuditParams(fileId));
    }

    @PostMapping("/proof/{fileId}")
    public ResponseEntity<IntegrityProof> proof(@PathVariable("fileId") String fileId, @RequestBody String challengesStr) {
        List<Challenge> challenges = JSONArray.parseArray(JSONObject.parseObject(challengesStr).getString("challenges"), Challenge.class);
        OSSObject dataFileObject = ossService.getObj(fileService.getDataFileId(fileId), "gdbigdata");
        OSSObject tagFileObject = ossService.getObj(fileService.getTagFileId(fileId), "gdbigdata");
        ArrayList<Integer> integerList = new ArrayList<>(challenges.stream()
                .map(Challenge::getIndex)
                .toList());
        Collections.sort(integerList);
        ArrayList<String> dataList = fileService.getListFromPointedLinesArrayInInputStream(dataFileObject.getObjectContent(), integerList);
        ArrayList<String> signOriginList = fileService.getListFromPointedLinesArrayInInputStream(tagFileObject.getObjectContent(), integerList);
        List<String> signList = signOriginList.stream()
                .map(sign -> new String(Base64.getDecoder().decode(sign)))
                .toList();
        String dataAggregation = AuditTool.getDataAggregation(challenges, dataList);
        String signAggregation = AuditTool.getSignAggregation(challenges, signList);
        IntegrityProof proof = IntegrityProof.builder().signAggregation(signAggregation).dataAggregation(dataAggregation).build();
        return ResponseEntity.ok(proof);
    }
}
