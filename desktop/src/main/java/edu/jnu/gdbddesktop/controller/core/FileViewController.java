package edu.jnu.gdbddesktop.controller.core;

import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.entity.FileViewTable;
import edu.jnu.gdbddesktop.entity.User;
import edu.jnu.gdbddesktop.scene.core.FileView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 文件显示场景中的业务控制器
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 20时42分
 * @功能描述: 文件显示场景中的业务控制器
 */
public class FileViewController implements Initializable {
    @FXML
    private TableColumn<FileViewTable, String> auditParamsFileId;

    @FXML
    private TableColumn<FileViewTable, String> fileId;

    @FXML
    private TableColumn<FileViewTable, String> fileName;

    @FXML
    private TableView<FileViewTable> filesTable;

    @FXML
    private TableColumn<FileViewTable, String> isFirstSaver;

    @FXML
    private TableColumn<FileViewTable, String> signFileId;

    @FXML
    private Button refreshBtn;

    public void fileIdOnEdit(TableColumn.CellEditEvent cellEditEvent) {
    }
    @FXML
    void auditParamsFileIdOnEdit(TableColumn.CellEditEvent event) {

    }

    @FXML
    void fileNameOnEdit(TableColumn.CellEditEvent event) {

    }

    @FXML
    void firstSaverOnEdit(TableColumn.CellEditEvent event) {

    }

    @FXML
    void signFileIdOnEdit(TableColumn.CellEditEvent event) {

    }

    @FXML
    void refresh(ActionEvent event) {
        User user = ServiceDesk.getInstance().getUserData();
        ObservableList<FileViewTable> data = user.getFileViewTableData();
        filesTable.getItems().clear();
        filesTable.getItems().addAll(data);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FileView.initTable(fileId, fileName, isFirstSaver, signFileId, auditParamsFileId, filesTable, refreshBtn);
    }


}
