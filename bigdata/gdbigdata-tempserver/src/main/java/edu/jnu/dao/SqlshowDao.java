package edu.jnu.dao;

import edu.jnu.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月06日 10:40
 */
public interface SqlshowDao extends JpaRepository<UserInfo, Integer> {

}
