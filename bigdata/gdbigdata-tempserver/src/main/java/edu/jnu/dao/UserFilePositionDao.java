package edu.jnu.dao;

import edu.jnu.domain.UserFilePosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author Guo zifan
 * @version 1.0
 * @date 2022年03月01日 20:58
 */
public interface UserFilePositionDao extends JpaRepository<UserFilePosition, Integer> {

    UserFilePosition findByFilePathContains(String fileName);
}
