package edu.jnu.service;

import edu.jnu.config.OSU.OsuProtocolParams;
import edu.jnu.entity.*;
import edu.jnu.service.thirdpart.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

/**
 * @author Guo zifan
 * @date 2022年01月30日 20:12
 */
@Service
@Slf4j
public class UserService {

    @Autowired(required = false)
    private UserInfoService userInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private ThreadPoolTaskExecutor applicationTaskExecutor;

    @Autowired
    private OsuService osuService;

    public boolean isExist(String userName) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(userName));
    }

    public void countUserInfoNum() {
        redisTemplate.opsForValue().increment("userInfoRecordNum");
    }

    public void saveUserNameAndId(Optional<String> userName, Optional<String> nowRecordNum) {
        if (userName.isPresent() && nowRecordNum.isPresent()) {
            redisTemplate.opsForValue().set(userName.get(), nowRecordNum.get());
            redisTemplate.persist(userName.get());
        }
    }

    public boolean addUser(UserDTO userDto) {
        Optional<String> userName = Optional.ofNullable(userDto.getUserName());
        try {
            if (userName.isPresent()) {
                userDto.encryptAllFields2SPlus2(OsuProtocolParams.PK, OsuProtocolParams.s, applicationTaskExecutor);
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error("用户"+userName + "的信息加密失败，存储失败");
            return false;
        }

        // 将用户信息入库，返回用户id
        Optional<String> recordId = Optional.ofNullable(userInfoService.addUser(userDto));
        // 元素非空判断式
        Predicate<String> sqlOperatorIsSuccess = Objects::nonNull;
        // 如果用户id非空，则向redis中插入数据
        if (recordId.filter(sqlOperatorIsSuccess).isPresent()) {
            this.saveUserNameAndId(userName, recordId);
            this.countUserInfoNum();
            return true;
        } else {
            return false;
        }
    }

    @Cacheable("userFileNumsByUserName")
    public String getUserFileNums(String userName) {
        int id = this.getIdByUserName(userName);
        String recordNum = this.getNowRecordNum();
        OsuParam osuParam = new OsuParam(id, "userFileNums", recordNum);
        return osuService.getOneFieldOfTable(osuParam);
    }

    @Cacheable("userAddressByUserName")
    public String getUserAddress(String userName) {
        int id = this.getIdByUserName(userName);
        String recordNum = this.getNowRecordNum();
        OsuParam osuParam = new OsuParam(id, "userAddress", recordNum);
        return osuService.getOneFieldOfTable(osuParam);
    }

    @Cacheable("userOrganizationByUserName")
    public String getUserOrganization(String userName) {
        int id = this.getIdByUserName(userName);
        String recordNum = this.getNowRecordNum();
        OsuParam osuParam = new OsuParam(id, "userOrganization", recordNum);
        return osuService.getOneFieldOfTable(osuParam);
    }

    public UserVO getUser(String userName) {
        int id = this.getIdByUserName(userName);
        CompletableFuture<String> userOrganizationFuture = CompletableFuture.supplyAsync(() -> this.getUserOrganization(userName));
        CompletableFuture<String> userAddressFuture = CompletableFuture.supplyAsync(() -> this.getUserAddress(userName));
        CompletableFuture<String> userFileNumsFuture = CompletableFuture.supplyAsync(() -> this.getUserFileNums(userName));
        CompletableFuture.allOf(userOrganizationFuture, userAddressFuture, userFileNumsFuture);
        return UserVO.builder()
                .userId(String.valueOf(id))
                .userName(userName)
                .userAddress(userAddressFuture.join())
                .userOrganization(userOrganizationFuture.join())
                .userFileNums(userFileNumsFuture.join())
                .build();
    }

    public int getIdByUserName(String userName) {
        return Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get(userName)).toString());
    }

    public String getNowRecordNum() {
        return Objects.requireNonNull(redisTemplate.opsForValue().get("userInfoRecordNum")).toString();
    }

    @CachePut(value = "userOrganizationByUserName", key = "#user.userName")
    public String updateUserOrganization(UpdateOrganizationDTO user) {
        int id = this.getIdByUserName(user.getUserName());
        String recordNum = this.getNowRecordNum();
        OsuParam osuParam = new OsuParam(id, "userOrganization", recordNum);
        return osuService.setOneFieldOfTable(osuParam, user.getUserOrganization());
    }

    @CachePut(value = "userAddressByUserName", key = "#user.userName")
    public String updateUserAddress(UpdateAddressDTO user) {
        int id = this.getIdByUserName(user.getUserName());
        String recordNum = this.getNowRecordNum();
        OsuParam osuParam = new OsuParam(id, "userAddress", recordNum);
        return osuService.setOneFieldOfTable(osuParam, user.getUserAddress());
    }

    @CachePut(value = "userFileNumsByUserName", key = "#user.userName")
    public String updateUserFileNums(UpdateFileNumsDTO user) {
        int id = this.getIdByUserName(user.getUserName());
        String recordNum = this.getNowRecordNum();
        OsuParam osuParam = new OsuParam(id, "userFileNums", recordNum);
        return osuService.setOneFieldOfTable(osuParam, user.getUserFileNums());
    }

    public UserDTO updateUserInfo(UserDTO user) {
        UpdateAddressDTO updateAddressDTO = UpdateAddressDTO.builder()
                .userName(user.getUserName())
                .userAddress(user.getUserAddress())
                .build();
        UpdateOrganizationDTO updateOrganizationDTO = UpdateOrganizationDTO.builder()
                .userName(user.getUserName())
                .userOrganization(user.getUserOrganization())
                .build();
        UpdateFileNumsDTO updateFileNumsDTO = UpdateFileNumsDTO.builder()
                .userName(user.getUserName())
                .userFileNums(user.getUserFileNums())
                .build();
        CompletableFuture<String> userOrganizationFuture = CompletableFuture.supplyAsync(() -> this.updateUserOrganization(updateOrganizationDTO));
        CompletableFuture<String> userAddressFuture = CompletableFuture.supplyAsync(() -> this.updateUserAddress(updateAddressDTO));
        CompletableFuture<String> userFileNumsFuture = CompletableFuture.supplyAsync(() -> this.updateUserFileNums(updateFileNumsDTO));
        CompletableFuture.allOf(userOrganizationFuture, userAddressFuture, userFileNumsFuture);
        return UserDTO.builder()
                .userName(String.valueOf(this.getIdByUserName(user.getUserName())))
                .userName(user.getUserName())
                .userAddress(userAddressFuture.join())
                .userOrganization(userOrganizationFuture.join())
                .userFileNums(userFileNumsFuture.join())
                .build();
    }
}