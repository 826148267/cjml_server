package edu.jnu.entity;

import edu.jnu.config.OSU.PublicKey;
import edu.jnu.utils.Action;
import edu.jnu.utils.Tools;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年04月26日 13时50分
 * @功能描述: 传输类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String userName;
    private String userAddress;
    private String userOrganization;
    private String userFileNums;


    /**
     * 加密全部字段到s+2层。
     * @return 加密成功返回true，否则返回false
     */
    public void encryptAllFields2SPlus2(PublicKey pk, int sValue, ThreadPoolTaskExecutor executor) throws ExecutionException, InterruptedException {
        this.userName = Tools.encode(this.getUserName());
        this.userAddress = Tools.encode(this.getUserAddress());
        this.userOrganization = Tools.encode(this.getUserOrganization());
        this.userFileNums = Tools.encode(this.getUserFileNums());
        // 解析出接受到的实例
        // 对userDto实例中的每个字段都加密
        CompletableFuture<Void> userNameFuture = CompletableFuture.runAsync(() -> {
            CipherText cUserName = null;
            try {
                cUserName = Action.Enc2SPlus2(pk, sValue, this.userName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.userName = cUserName.getCp().toString();
        }, executor);

        CompletableFuture<Void> userOrganizationFuture = CompletableFuture.runAsync(() -> {
            CipherText cUserOrganization = null;
            try {
                cUserOrganization = Action.Enc2SPlus2(pk, sValue, this.userOrganization);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.userOrganization = cUserOrganization.getCp().toString();
        }, executor);

        CompletableFuture<Void> userAddressFuture = CompletableFuture.runAsync(() -> {
            CipherText cUserAddress = null;
            try {
                cUserAddress = Action.Enc2SPlus2(pk, sValue, this.userAddress);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.userAddress = cUserAddress.getCp().toString();
        }, executor);

        CompletableFuture<Void> userFileNumsFuture = CompletableFuture.runAsync(() -> {
            CipherText cUserFileNums = null;
            try {
                cUserFileNums = Action.Enc2SPlus2(pk, sValue, this.userFileNums);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.userFileNums = cUserFileNums.getCp().toString();
        }, executor);

        CompletableFuture.allOf(userNameFuture, userOrganizationFuture, userAddressFuture, userFileNumsFuture).get();
    }
}
