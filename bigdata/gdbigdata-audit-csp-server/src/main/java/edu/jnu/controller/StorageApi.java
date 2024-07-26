package edu.jnu.controller;

import edu.jnu.DTO.DataFileInfoDTO;
import edu.jnu.VO.GetFileListVO;
import edu.jnu.VO.UploadFileVO;
import edu.jnu.PO.DataFileInfoPO;
import edu.jnu.service.DataFileService;
import edu.jnu.service.FilePositionService;
import edu.jnu.service.OSSService;
import edu.jnu.service.TagFileService;
import edu.jnu.utils.Tools;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年05月28日 10:47
 */
@RestController
@Api(tags = "文件服务接口")
public class StorageApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageApi.class);

    @Autowired
    private OSSService ossService;

    @Autowired
    private FilePositionService filePositionService;

    @Autowired
    private DataFileService dataFileService;

    @Autowired
    private TagFileService tagFileService;

    /**
     * 上传文件.
     * @param uploadFileVO 上传文件时的数据传输对象
     * @return  返回上传结果描述信息
     */
    @PostMapping(value = "/files")
    @ApiOperation("文件上传接口")
    @ApiResponse(code = 500, message = "文件存储失败，服务器内部错误")
    public ResponseEntity<String> uploadFile(UploadFileVO uploadFileVO) {
        MultipartFile tagFile = uploadFileVO.getTagFile();
        MultipartFile dataFile = uploadFileVO.getDataFile();
        try {
            ossService.uploadObj2OSS("audit/data/"+ uploadFileVO.getFileStoragePath()+"/"+dataFile.getOriginalFilename(),
                    "gdbigdata", dataFile.getInputStream());
            ossService.uploadObj2OSS("audit/tag/"+ uploadFileVO.getFileStoragePath()+"/"+tagFile.getOriginalFilename(),
                    "gdbigdata", tagFile.getInputStream());
        } catch (IOException e) {
            LOGGER.error("文件在数据流获取操作阶段出错："+e);
            return ResponseEntity.internalServerError().body("文件"+dataFile.getOriginalFilename()+"存储失败");
        }
        LOGGER.info("数据文件和标签文件存储成功。");
        filePositionService.recordDataAndTagFileInfo(
                uploadFileVO.getUserId(),
                uploadFileVO.getFileStoragePath(),
                dataFile.getOriginalFilename(),
                tagFile.getOriginalFilename(),
                uploadFileVO.getBlockNum(),
                uploadFileVO.getR(),
                uploadFileVO.getMimeType()
        );
        LOGGER.info("已将数据文件信息和标签文件信息记录到数据库中。");
        return ResponseEntity.ok("数据文件和标签文件存储成功");
    }

    /**
     * 获取所有文件列表.
     * 根据用户id分页获取文件信息列表.
     * @param page 当前页
     * @param size 页容量
     * @return  文件列表
     */
    @GetMapping(value = "/{userId}/files/info-list")
    @ApiOperation("获取文件信息列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前处于第几页", defaultValue = "0", paramType = "query", dataType = "Integer", example = "0"),
            @ApiImplicitParam(name = "size", value = "页容量", defaultValue = "8", paramType = "query", dataType = "Integer", example = "8"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "path", dataType = "String", example = "1")
    })
    public ResponseEntity<GetFileListVO> getFileInfoList(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                         @RequestParam(value = "size", defaultValue = "8", required = false) Integer size,
                                                         @PathVariable(value = "userId") String userId) {
        DataFileInfoDTO dataFileInfoDTO = dataFileService.listFileInfoByUserId(page, size, userId);
        GetFileListVO result = new GetFileListVO(dataFileInfoDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据用户给定的fileId下载文件对应的密钥文件.
     * @param fileId    数据文件的唯一标识符fileId
     * @return  返回byte[]形式的密钥文件正文
     */
    @GetMapping("/oss/data-files/{fileId}")
    @ApiOperation("数据文件下载接口")
    @ApiImplicitParam(name = "fileId", value = "数据文件id", required = true, paramType = "path", dataType = "String")
    public ResponseEntity<ByteArrayResource> downloadKeyFile(@PathVariable("fileId") String fileId) {
        try {
            // 通过数据文件的fileId获取密钥文件，以数组keyFileByteArray的形式返回文件内容
            ByteArrayResource fileContent = new ByteArrayResource(dataFileService.getFileByFileId(fileId));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.empty());  // 由于客户端接收到文件还要做预处理，所以此处不向浏览器提下载或者是打开的建议
            headers.setLastModified(new Date().getTime());
            headers.setETag("\"" + System.currentTimeMillis() + "\"");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            LOGGER.info("文件fileId:{}的密钥文件下载成功", fileId);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileContent.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileContent);
        } catch (Exception e) {
            LOGGER.info("文件fileId:{}的密钥文件下载失败，cause:{}", fileId, e);
        }
        return ResponseEntity.internalServerError().body(null);
    }

    /**
     * 通过fileId获取文件的媒体类型
     * @param fileId 文件id
     * @return  文件的媒体类型
     */
    @GetMapping("/files/{fileId}/MimeType")
    @ApiOperation("根据文件id获取文件格式接口")
    @ApiImplicitParam(name = "fileId", value = "数据文件id", required = true, paramType = "path", dataType = "String")
    public ResponseEntity<String> getMimeTypeByFileId(@PathVariable(value = "fileId") String fileId) {
        String mimeType = dataFileService.getMimeTypeByFileId(fileId);
        return ResponseEntity.ok(mimeType);
    }

    @DeleteMapping("/oss/data-files/{fileId}")
    @ApiOperation("根据文件id删除数据文件")
    @ApiImplicitParam(name = "fileId", value = "数据文件id", required = true, paramType = "path", dataType = "String")
    public ResponseEntity<String> deleteDataFileByDataFileId(@PathVariable(value = "fileId") String dataFileId) {
        dataFileService.deleteFileByFileId(dataFileId);
        dataFileService.deleteFileInfoByFileId(dataFileId);
        LOGGER.info("成功删除数据文件:{}"+dataFileId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/oss/tag-files/{fileId}")
    @ApiOperation("根据文件id删除标签文件")
    @ApiImplicitParam(name = "fileId", value = "标签文件id", required = true, paramType = "path", dataType = "String")
    public ResponseEntity<String> deleteTagFileByDataFileId(@PathVariable(value = "fileId") String tagFileId) {
        tagFileService.deleteFileByFileId(tagFileId);
        tagFileService.deleteFileInfoByFileId(tagFileId);
        LOGGER.info("成功删除标签文件:{}"+tagFileId);
        return ResponseEntity.ok(null);
    }
}
