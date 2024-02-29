package edu.jnu.controller;

import edu.jnu.dao.SqlshowDao;
import edu.jnu.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月06日 10:39
 */
@RestController
public class SqlshowController {

    @Autowired
    private SqlshowDao sqlshowDao;

    @GetMapping(value = "/show-sql-data")
    public ResponseEntity<List<UserInfo>> listAllUserInfo() {
        List<UserInfo> allUserInfo = sqlshowDao.findAll();
        return ResponseEntity.ok(allUserInfo);
    }
}
