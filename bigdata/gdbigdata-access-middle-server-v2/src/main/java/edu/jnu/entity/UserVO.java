package edu.jnu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Guo zifan
 * @date 2022年01月30日 20:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    private String userId;
    private String userName;
    private String userAddress;
    private String userOrganization;
    private String userFileNums;
}
