package edu.jnu.gdbddesktop;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        // 服务总台初始化
        try {
            ServiceDesk.getInstance().init(stage);
        } catch (IOException e) {
            System.out.println("页面加载失败");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}