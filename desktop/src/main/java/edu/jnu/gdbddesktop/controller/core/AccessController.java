package edu.jnu.gdbddesktop.controller.core;


import com.leewyatt.rxcontrols.controls.RXAvatar;
import com.leewyatt.rxcontrols.controls.RXTranslationButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * 隐私访问场景中的业务控制器
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 20时40分
 * @功能描述: 隐私访问场景中的业务控制器
 */
public class AccessController {

    @FXML
    private RXAvatar avatar;

    @FXML
    private RXTranslationButton loginBtn;

    @FXML
    private Label userName;


    @FXML
    void refreshInfo(MouseEvent event) {
        System.out.println("refreshing");
    }

    @FXML
    void updateInfo(MouseEvent event) {
        System.out.println("updateInfo");
    }
}
