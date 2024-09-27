package edu.jnu.gdbddesktop.components;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import edu.jnu.gdbddesktop.ServiceDesk;
import javafx.scene.control.Label;
import javafx.stage.Modality;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月05日 19时28分
 * @功能描述: 我的弹框
 */
public class MyConfirmAlert {

    public MyConfirmAlert(String title, String message, String confirmButtonText) {
        JFXAlert< String> alert = new JFXAlert<>(ServiceDesk.getInstance().getStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(message));
        JFXButton closeButton = new JFXButton(confirmButtonText);
        closeButton.getStyleClass().addAll("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }
}
