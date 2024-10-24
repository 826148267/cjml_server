package edu.jnu.gdbddesktop.entity;

import edu.jnu.gdbddesktop.config.Constant;
import edu.jnu.gdbddesktop.utils.MyHttpTools;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.HttpResponse;

import java.util.Map;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月05日 13时27分
 * @功能描述: 用户身份认证类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAuth {
    private String userName;
    private String password;

    /**
     * 验证用户名和密码是否正确
     * @return 是否正确
     */
    public boolean authentic() {
        if (userName.isEmpty() || password.isEmpty()) {
            System.out.println("用户名或密码不能为空");
            return false;
        }
        Map<String, String> params = Map.of("userName", userName, "password", password);
        HttpResponse response = MyHttpTools.sendHttpPostRequestWithString(Constant.LOGIN_URL.value, params);
        // 如果请求成功
        return response.getStatusLine().getStatusCode() == 200;
    }
}
