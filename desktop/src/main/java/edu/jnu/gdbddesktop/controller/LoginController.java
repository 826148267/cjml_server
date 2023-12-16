package edu.jnu.gdbddesktop.controller;

import com.leewyatt.rxcontrols.controls.RXPasswordField;
import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.controls.RXTranslationButton;
import com.leewyatt.rxcontrols.event.RXActionEvent;
import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.entity.User;
import edu.jnu.gdbddesktop.entity.UserAuth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;


/**
 * 登陆场景中的业务控制器
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年5月2日 下午14:46:14
 * @功能描述: 根据用户在场景中的输入进行业务处理（类中的方案相当于银行柜台的不同业务）
 */
public class LoginController {
    @FXML
    private RXTextField usernameField;
    @FXML
    private RXPasswordField passwordField;
    @FXML
    private MaskerPane maskerPane;
    @FXML
    private RXTranslationButton loginBtn;
    @FXML
    private RXTranslationButton registerBtn;


    @FXML
    void deleteText(RXActionEvent event) {
        RXTextField tf = (RXTextField) event.getSource();
        tf.clear();
    }

    @FXML
    void exitAction(ActionEvent event) {
        ServiceDesk.exit();
    }

    @FXML
    void register(ActionEvent actionEvent) {
        ServiceDesk.getInstance().register();
    }

    @FXML
    void loginIn(ActionEvent actionEvent) {
        // 打开加载动画
        maskerPane.setVisible(false);
        // 初始化用户
        UserAuth userAuth = new UserAuth();
        userAuth.setUserName(usernameField.getText());
        userAuth.setPassword(passwordField.getText());
        // 如果用户进行登陆验证
        if (userAuth.authentic()) {
            User user = new User();
            // 初始化用户名和用户密钥
            user.setUserName(userAuth.getUserName());
            if (user.initInfo(userAuth.getPassword())) {
                // 记录用户信息 方便其他场景调用
                ServiceDesk.getInstance().getStage().setUserData(user);
            }
            System.out.println("登陆成功");
            maskerPane.setVisible(true);
            try {
                ServiceDesk.getInstance().core();
            } catch (IOException e) {
                System.out.println("切换至主场景失败");
            }
        } else {
            maskerPane.setVisible(false);
            System.out.println("账号密码不匹配");
        }
    }
}
