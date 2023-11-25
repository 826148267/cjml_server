package edu.jnu.service;

import edu.jnu.dao.UserFilePositionDao;
import edu.jnu.domain.UserFilePosition;
import edu.jnu.dto.FileInfosDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 获取文件相对位置.
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 21:19
 */
@Service
public class UserFilePositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFilePositionService.class);

    @Autowired
    private UserFilePositionDao userFilePositionDao;

    /**
     * 无重复记录文件位置.
     * 先查询数据库中是否存在此文件，如果没有就插入，有就不插入
     * @param userFilePosition  用户文件存放在OSS中的位置
     */
    public void putFilePosition(UserFilePosition userFilePosition) {
        // 查询数据库中所有userId的文件位置
        List<UserFilePosition> positions = userFilePositionDao.findAll();
        // 如果查询出来的集合中，有和属性filePath的值相同的，就不插入，
        String filePath = userFilePosition.getFilePath();
        List<UserFilePosition> positionList = positions.stream()
                .filter(position -> position.getFilePath().equals(filePath)).toList();
        // 否则插入
        if (positionList.size() == 0) {
            userFilePosition.setDuplicateNum(0);
            userFilePositionDao.save(userFilePosition);
        } else {
            // 如果文件路径已经存在于数据库中,将去重数+1，然后根据记录的id来更新去重次数
            UserFilePosition ufp = positionList.get(0);
            ufp.setDuplicateNum(ufp.getDuplicateNum()+1);
            userFilePositionDao.save(ufp);
            LOGGER.info("文件已去重");
        }
    }

    /**
     * 获取文件相对路径集合.
     * 获取用户存放在OSS中的文件信息.
     * @return  该用户所有文件的文件路径集合
     */
    public List<FileInfosDto> listUserFilePosition(Integer pageOffset, Integer size) {
        PageRequest pageRequest = PageRequest.of(pageOffset, size);
        Page<UserFilePosition> ufps = userFilePositionDao.findAll(pageRequest);

        List<FileInfosDto> res = ufps.stream().map(ufp -> {
            FileInfosDto fid = new FileInfosDto();
            fid.setFilePath(ufp.getFilePath().split("/", 2)[1]);
            int len = ufp.getFilePath().split("/").length;
            fid.setFileName(ufp.getFilePath().split("/")[len-1]);
            fid.setDuplicateNum(ufp.getDuplicateNum());
            return fid;
        }).toList();
        return res;
    }

    /**
     * 统计表中记录数.
     * @return 返回总记录数
     */
    public Integer getTotals() {
        return Math.toIntExact(userFilePositionDao.count());
    }

    public void deleteFilePosition(String fileName) {
        UserFilePosition ufp = userFilePositionDao.findByFilePathContains(fileName);
        if (ufp.getDuplicateNum() == 0) {
            userFilePositionDao.deleteById(ufp.getId());
        } else {
            ufp.setDuplicateNum(ufp.getDuplicateNum()-1);
            userFilePositionDao.save(ufp);
        }
    }
}
