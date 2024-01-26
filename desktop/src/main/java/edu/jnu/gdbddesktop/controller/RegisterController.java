package edu.jnu.gdbddesktop.controller;

import com.leewyatt.rxcontrols.controls.RXPasswordField;
import com.leewyatt.rxcontrols.controls.RXTextField;
import com.leewyatt.rxcontrols.controls.RXTranslationButton;
import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.utils.MyHttpTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 注册业务控制器
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 15时05分
 * @功能描述: 主要为注册
 */
public class RegisterController {
    @FXML
    private RXPasswordField confirmPwdField;

    @FXML
    private RXPasswordField pwdField;

    @FXML
    private RXTranslationButton submitBtn;

    @FXML
    private RXTranslationButton toLoginBtn;

    @FXML
    private RXTextField userAddressField;

    @FXML
    private RXTextField userFileNumsField;

    @FXML
    private RXTextField userNameField;

    @FXML
    private RXTextField userOrganizationField;

    public RegisterController() {
    }

    @FXML
    public void register(ActionEvent actionEvent) throws IOException {
        if (!confirmPwdField.getText().equals(pwdField.getText())) {
            System.out.println("密码不一致");
            return;
        }
        Map<String, String> map = Map.of(
                "userName", userNameField.getText(),
                "password", pwdField.getText(),
                "userAddress", userAddressField.getText(),
                "userOrganization", userOrganizationField.getText(),
                "userFileNums", userFileNumsField.getText());
        HttpResponse response = MyHttpTools.sendHttpPostRequestWithString("http://localhost:10003/register", map);
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("注册成功");
            ServiceDesk.getInstance().login();
        } else {
            System.out.println(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8));
        }
    }

    @FXML
    void exitAction(ActionEvent event) {
        ServiceDesk.exit();
    }

    @FXML
    public void toLogin(ActionEvent actionEvent) {
        ServiceDesk.getInstance().login();
    }
}
