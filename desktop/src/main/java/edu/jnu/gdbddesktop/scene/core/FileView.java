package edu.jnu.gdbddesktop.scene.core;

import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.entity.FileViewTable;
import edu.jnu.gdbddesktop.entity.User;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.Objects;

/**
 * 文件显示模块场景切换
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 20时35分
 * @功能描述: 文件显示模块场景切换
 */
public class FileView {
    public static void initTable(TableColumn<FileViewTable, String> fileId,
                                 TableColumn<FileViewTable, String> fileName,
                                 TableColumn<FileViewTable, String> isFirstSaver,
                                 TableColumn<FileViewTable, String> signFileId,
                                 TableColumn<FileViewTable, String> auditParamsFileId,
                                 TableView<FileViewTable> tableView,
                                 Button refreshBtn) {
        fileId.setCellValueFactory(cellData -> cellData.getValue().getFileId());
        fileName.setCellValueFactory(cellData -> cellData.getValue().getFileName());
        isFirstSaver.setCellValueFactory(cellData -> cellData.getValue().getIsFirstSaver());
        signFileId.setCellValueFactory(cellData -> cellData.getValue().getSignFileId());
        auditParamsFileId.setCellValueFactory(cellData -> cellData.getValue().getAuditParamsFileId());

        User user = ServiceDesk.getInstance().getUserData();
        ObservableList<FileViewTable> data = user.getFileViewTableData();
        tableView.getItems().clear();
        tableView.getItems().addAll(data);
        tableView.getStyleClass().addAll("cf-table-view","cf-scroll-bar-style");

        refreshBtn.setGraphic(new FontIcon());

        tableView.setRowFactory(a -> {
            TableRow<FileViewTable> row = new TableRow<>();
            // 创建菜单对象
            ContextMenu contextMenu = new ContextMenu();
            MenuItem downloadItem = new MenuItem("下载");
            downloadItem.setOnAction(e -> row.getItem().download());
            contextMenu.getItems().add(downloadItem);
            MenuItem auditItem = new MenuItem("审计");
            auditItem.setOnAction(e -> row.getItem().audit());
            contextMenu.getItems().add(auditItem);
            contextMenu.getStyleClass().add("cf-context-menu");
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                    .then(contextMenu).otherwise((ContextMenu) null)
            );
            return row;
        });

    }
}
