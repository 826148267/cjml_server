package edu.jnu.controller;

import edu.jnu.entity.TransParams;
import edu.jnu.entity.dto.DownloadFileDTO;
import edu.jnu.entity.dto.UploadDataFileDTO;
import edu.jnu.entity.dto.UploadParamsDTO;
import edu.jnu.entity.dto.UploadSignFileAndParamsDTO;
import edu.jnu.service.FileService;
import edu.jnu.service.OSSService;
import edu.jnu.utils.AuditTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月05日 21时24分
 * @功能描述: 去重接口
 */
@RestController
public class DedupController {

    @Autowired
    private FileService fileService;


    /**
     * 上传数据文件
     * @return ResponseEntity<TransParams>
     */
    @PostMapping("/uploadDataFile")
    public ResponseEntity<TransParams> uploadDataFile(@RequestBody UploadDataFileDTO uploadDataFileDTO) {
        String fileAbstract = AuditTool.getFileAbstract(uploadDataFileDTO.getData().replace("\n", ""));
        // 存储文件之前，先判断是否有相同文件
        if (fileService.existFile(fileAbstract, uploadDataFileDTO.getMimeType())) {
            if (fileService.isSaveDupBySelf(fileAbstract, uploadDataFileDTO.getMimeType(), uploadDataFileDTO.getUserName(), uploadDataFileDTO.getFileName())) {
                return ResponseEntity.status(200).body(null);
            }
            TransParams transParams = fileService.addDataFileInfo(uploadDataFileDTO.getUserName(),
                                            uploadDataFileDTO.getFileName(),
                                            uploadDataFileDTO.getMimeType(),
                                            fileAbstract);
            return ResponseEntity.status(201).body(transParams);
        }
        // 如果没有相同文件， 则将怎见存储到对象存储中
        String fileId = fileService.saveDataFile(uploadDataFileDTO.getData());
        fileService.saveDataFileInfo(fileId,
                uploadDataFileDTO.getUserName(),
                uploadDataFileDTO.getFileName(),
                uploadDataFileDTO.getMimeType(),
                fileAbstract);
        return ResponseEntity.status(202).body(null);
    }

    @PostMapping("/uploadSignFile/noDedup")
    public ResponseEntity<String> uploadSignFileAndParams(@RequestBody UploadSignFileAndParamsDTO uploadSignFileAndParamsDTO) {
        String signFileId = fileService.saveSignFile(uploadSignFileAndParamsDTO.getSigns());
        String auditParamsFileId = fileService.saveAuditParamsFile(uploadSignFileAndParamsDTO.getAuditParams());
        String transParamsFileId = fileService.saveTransParamsFile(uploadSignFileAndParamsDTO.getTransParams());
        fileService.perfectFileInfo(uploadSignFileAndParamsDTO.getUserName(), uploadSignFileAndParamsDTO.getFileName(), signFileId, transParamsFileId, auditParamsFileId);
        return ResponseEntity.status(200).body(null);
    }

    @PostMapping("/uploadSignFile/canDedup")
    public ResponseEntity<String> uploadParams(@RequestBody UploadParamsDTO uploadParamsDTO) {
        String transParamsFileId = fileService.saveTransParamsFile(uploadParamsDTO.getTransParams());
        String auditParamsFileId = fileService.saveAuditParamsFile(uploadParamsDTO.getAuditParams());
        String firstSaverSignFileId = fileService.getSignFileIdByUserNameAndFileName(uploadParamsDTO.getUserName(), uploadParamsDTO.getFileName());
        fileService.perfectFileInfo(uploadParamsDTO.getUserName(), uploadParamsDTO.getFileName(), firstSaverSignFileId, transParamsFileId, auditParamsFileId);
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<DownloadFileDTO> downloadFile(@PathVariable String fileId) {
        String data = fileService.getFileAsString(fileId);
        String mimeType = fileService.getMimeTypeByFileId(fileId);
        DownloadFileDTO downloadFileDTO = DownloadFileDTO.builder()
                .data(data).mimeType(mimeType).build();
        return ResponseEntity.ok(downloadFileDTO);
    }
}
