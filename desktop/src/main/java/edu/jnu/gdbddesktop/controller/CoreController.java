package edu.jnu.gdbddesktop.controller;

import com.leewyatt.rxcontrols.controls.RXCarousel;
import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.scene.Core;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 核心业务控制器
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 20时05分
 * @功能描述: 控制主业务界面的跳转（）
 */
public class CoreController implements Initializable {

    @FXML
    private Button btnExitFx;

    @FXML
    private RXCarousel mainCarousel;

    @FXML
    private VBox navBar;

    @FXML
    private ToggleGroup navGroup;

    @FXML
    private HBox topBar;

    private double offsetX, offsetY;

    @FXML
    void exitAction(ActionEvent event) {
        ServiceDesk.exit();
    }

    @FXML
    void topBarDraggedAction(MouseEvent event) {
        Window window = topBar.getScene().getWindow();
        window.setX(event.getScreenX() - offsetX);
        window.setY(event.getScreenY() - offsetY);
    }

    @FXML
    void topBarPressedAction(MouseEvent event) {
        offsetX = event.getSceneX();
        offsetY = event.getSceneY();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Core.toAccess(mainCarousel, navGroup);
    }
}