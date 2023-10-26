package edu.jnu.controller;

import com.aliyun.oss.OSS;
import edu.jnu.domain.UserFilePosition;
import edu.jnu.dto.FileInfosDto;
import edu.jnu.dto.UploadFileDto;
import edu.jnu.enums.ResponseEnum;
import edu.jnu.exception.ConditionException;
import edu.jnu.service.OSSService;
import edu.jnu.service.UserFilePositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * 用户文件操作.
 * 接受用户上传的文件，对文件做相应的处理，然后进行存储，
 * 或者根据用户指令取出相应的文件或者文件夹.
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 20:03
 */
@RestController
public class UserFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFileController.class);

    @Autowired
    private OSSService ossService;

    @Autowired
    private UserFilePositionService userFilePositionService;

    /**
     * 上传文件.
     * 根据用户上传的密钥和
     * @param uploadFileDto
     * @return
     */
    @PostMapping(value = "/files")
    public ResponseEntity<?> uploadFile(UploadFileDto uploadFileDto,
                                        @RequestParam("file") MultipartFile multipartFile) {
        // 将文件上传到OSS服务器中
        OSS oss = ossService.createOss(uploadFileDto.getEndpoint(), uploadFileDto.getAccessKeyId(), uploadFileDto.getAccessKeySecret());
        try {
            ossService.uploadObj2OSS(oss, "dupless/"+uploadFileDto.getFileStoragePath()+"/"+multipartFile.getOriginalFilename(),
                    uploadFileDto.getBucketName(), multipartFile.getInputStream());
        } catch (IOException e) {
            LOGGER.info("文件在数据流操作阶段出错："+e);
            throw new ConditionException(ResponseEnum.FAIL);
        }
        // 如果存储成功
        UserFilePosition userFilePosition = new UserFilePosition("dupless/"+uploadFileDto.getFileStoragePath()+"/"+multipartFile.getOriginalFilename());
        // 记录用户文件位置
        userFilePositionService.putFilePosition(userFilePosition);
        LOGGER.info("文件:"+multipartFile.getOriginalFilename()+"已存在于服务器中");
        return ResponseEntity.ok("文件已经上传成功");
    }

    /**
     * 获取用户的所有文件路径.
     * 根据用户ID获取该用户在OSS上的所有文件路径.
     * @return  该用户所有的文件路径
     */
    @GetMapping(value = "/fileInfos")
    public ResponseEntity<List<FileInfosDto>> getUserFilePaths(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageOffset,
                                                               @RequestParam(value = "size", defaultValue = "8", required = false) Integer size) {
        List<FileInfosDto> page = userFilePositionService.listUserFilePosition(pageOffset, size);
        // 从数据库中获取用户id所拥有的所有文件的相对路径
        return ResponseEntity.ok(page);
    }
}
