package edu.jnu.gdbddesktop.controller.core;

import com.leewyatt.rxcontrols.controls.RXTranslationButton;
import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.entity.User;
import edu.jnu.gdbddesktop.utils.MyHttpTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传场景中的业务控制器
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 20时41分
 * @功能描述: 文件上传场景中的业务控制器
 */
public class FileUploadClientController {

    @FXML
    private RXTranslationButton openBtn;

    @FXML
    private ProgressBar progressBar;
    public void openFileAction(ActionEvent actionEvent) {
        File file = new FileChooser().showOpenDialog(ServiceDesk.getInstance().getStage());
        if (Objects.nonNull(file) && file.exists()) {
            uploadFile(file);
        }
    }

    /**
     * 客户端去重
     * @param file
     */
    private void uploadFile(File file) {


    }
}
