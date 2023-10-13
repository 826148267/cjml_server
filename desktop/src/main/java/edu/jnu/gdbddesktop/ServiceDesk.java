package edu.jnu.gdbddesktop;

import com.leewyatt.rxcontrols.animation.carousel.AnimFlip;
import com.leewyatt.rxcontrols.controls.RXCarousel;
import com.leewyatt.rxcontrols.pane.RXCarouselPane;
import edu.jnu.gdbddesktop.entity.User;
import edu.jnu.gdbddesktop.scene.Core;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;
import java.util.Objects;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 13时32分
 * @功能描述: 服务总台。进行业务场景切换，其作用是切换场景scene。
 *           相当于银行的向导，根据用户指令指引用户到不同的柜台（scene）。
 */
public class ServiceDesk {

    private Stage stage = null;

    // 单例模式
    private static final ServiceDesk INSTANCE = new ServiceDesk();
    private ServiceDesk() {}
    public static ServiceDesk getInstance() {
        return INSTANCE;
    }

    /**
     * 退出程序
     */
    public static void exit() {
        Platform.exit();
        System.exit(0);
    }


    /**
     * @功能描述: 软件打开后首个界面
     * @param stage 主窗口
     */
    public void init(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/edu.jnu.gdbddesktop/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("用户登陆");
        stage.show();
        this.stage = stage;
    }

    /**
     * 给控制器或者场景类调用的接口
     */
    public void login() {
        try {
            ServiceDesk.getInstance().login(stage);
        } catch (IOException e) {
            System.out.println("切换至登陆场景失败");
        }
    }


    /**
     * 用户登陆场景
     * @param stage 主窗口
     * @throws IOException 场景加载异常
     */
    public void login(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/edu.jnu.gdbddesktop/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("用户登陆");
    }

    /**
     * 用户注册场景
     */
    public void register() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/edu.jnu.gdbddesktop/fxml/register.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("切换至注册场景失败");
        }
        stage.setTitle("用户注册");
        stage.setScene(scene);
    }

    public void core() throws IOException {
        Core.load(stage);
    }

    /**
     * 暴露舞台对象，让其他类可以直接做场景切换之外的操作
     * 如设置缓存数据
     * @return stage 主窗口
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * 窗口中用户信息
     * @return Object 用户的数据
     */
    public User getUserData() {
        return (User) stage.getUserData();
    }
}
