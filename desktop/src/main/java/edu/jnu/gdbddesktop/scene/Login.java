package edu.jnu.gdbddesktop.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 14时09分
 * @功能描述: 登陆场景加载类，此类只做场景加载
 */
public class Login {
    /**
     * 加载登陆场景
     * @param stage 登陆场景
     */
    public static void load(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Login.class.getResource("/edu.jnu.gdbddesktop/fxml/login.fxml")));
        } catch (IOException e) {
            System.out.println("加载登陆页面失败");
            e.printStackTrace();
        }
        stage.getScene().setRoot(root);
    }
}
