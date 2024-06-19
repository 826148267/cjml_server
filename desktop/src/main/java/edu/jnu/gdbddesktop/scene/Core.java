package edu.jnu.gdbddesktop.scene;

import com.leewyatt.rxcontrols.animation.carousel.AnimFlip;
import com.leewyatt.rxcontrols.controls.RXCarousel;
import com.leewyatt.rxcontrols.pane.RXCarouselPane;
import edu.jnu.gdbddesktop.HelloApplication;
import edu.jnu.gdbddesktop.ServiceDesk;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.MaskerPane;

import java.io.IOException;
import java.util.Objects;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 19时34分
 * @功能描述: 核心业务场景加载类，此类只做场景加载
 */
public class Core {
    /**
     * 加载核心业务界面场景
     * @param stage 场景使用的舞台
     */
    public static void load(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/edu.jnu.gdbddesktop/fxml/core.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // 添加色彩  css
        scene.getStylesheets().addAll(
                Objects.requireNonNull(ServiceDesk.class.getResource("/edu.jnu.gdbddesktop/css/color.css")).toExternalForm(),
                Objects.requireNonNull(ServiceDesk.class.getResource("/edu.jnu.gdbddesktop/css/core.css")).toExternalForm()
        );
        scene.setFill(Color.TRANSPARENT);
        scene.setCamera(new PerspectiveCamera());
        stage.setScene(scene);
    }

    public static void toAccess(RXCarousel rxCarousel, ToggleGroup toggleGroup) {
        Pane p1 = null;
        Pane p2 = null;
        Pane p3 = null;
        Pane p4 = null;
        try {
            p1 = FXMLLoader.load(Objects.requireNonNull(Core.class.getResource("/edu.jnu.gdbddesktop/fxml/access.fxml")));
            p2 = FXMLLoader.load(Objects.requireNonNull(Core.class.getResource("/edu.jnu.gdbddesktop/fxml/file_view.fxml")));
            p3 = FXMLLoader.load(Objects.requireNonNull(Core.class.getResource("/edu.jnu.gdbddesktop/fxml/file_upload_client.fxml")));
            p4 = FXMLLoader.load(Objects.requireNonNull(Core.class.getResource("/edu.jnu.gdbddesktop/fxml/file_upload_server.fxml")));
        } catch (IOException e) {
            System.out.println("子窗口创建失败");
            e.printStackTrace();
        }
        RXCarouselPane rp2 = new RXCarouselPane(p2);
        RXCarouselPane rp3 = new RXCarouselPane(p3);
        RXCarouselPane rp4 = new RXCarouselPane(p4);

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(0, 0, 0, 0));
        MaskerPane maskerPane = new MaskerPane();
        maskerPane.setText("加载信息中");
        maskerPane.setVisible(false);
        stackPane.getChildren().addAll(p1, maskerPane);

        RXCarouselPane rp1 = new RXCarouselPane(stackPane);
        rxCarousel.setPaneList(rp1, rp2, rp3, rp4);
        rxCarousel.setCarouselAnimation(new AnimFlip());
        toggleGroup.selectedToggleProperty().addListener(((observableValue, toggle, t1) -> rxCarousel.setSelectedIndex(toggleGroup.getToggles().indexOf(t1))));
    }
}
