package edu.jnu.gdbddesktop.controller.core;

import com.alibaba.fastjson.JSONObject;
import com.leewyatt.rxcontrols.controls.RXTranslationButton;
import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.components.MyConfirmAlert;
import edu.jnu.gdbddesktop.components.MyErrorAlert;
import edu.jnu.gdbddesktop.config.Constant;
import edu.jnu.gdbddesktop.entity.TransParams;
import edu.jnu.gdbddesktop.entity.User;
import edu.jnu.gdbddesktop.utils.FileTool;
import edu.jnu.gdbddesktop.utils.MyHttpTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * 文件上传场景中的业务控制器
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月02日 20时41分
 * @功能描述: 文件上传场景中的业务控制器
 */
public class FileUploadServerController {

    @FXML
    private RXTranslationButton openBtn;

    @FXML
    private ProgressBar progressBar;
    public void openFileAction(ActionEvent actionEvent) {
        File file = new FileChooser().showOpenDialog(ServiceDesk.getInstance().getStage());
        if (Objects.nonNull(file) && file.exists()) {
            try {
                uploadFile(file);
            } catch (IOException e) {
                System.out.println("文件上传失败"+e.getMessage());
            }
        }
    }

    /**
     * 服务器端去重策略上传文件
     * @param file 文件
     */
    private void uploadFile(File file) throws IOException {
        // 获取用户信息
        User user = ServiceDesk.getInstance().getUserData();
        // 对文件进行读取，并切分成List<String>的格式
        List<String> dataList = FileTool.readFileAsList(file);
        if (dataList == null) {
            return;
        }
        // 发送用户名，文件名，文件内容
        HttpResponse response = user.uploadDataFile(dataList, file.getName(), Files.probeContentType(file.toPath()));
        // 第二个上传阶段时需要的参数
        HashMap<String, String> params;

        if (response.getStatusLine().getStatusCode() == 201) {
            new MyConfirmAlert("无需对文件进行签名", "已经去重，存在重复文件，文件已经存储成功，将上传审计参数用于用户数据的完整性检验", "继续");
            TransParams oldTransParams = JSONObject.parseObject(EntityUtils.toString(response.getEntity()), TransParams.class);
            // 加载参数
            params = user.loadParams(dataList, oldTransParams, file.getName());
            HttpResponse response2 = MyHttpTools.sendHttpPostRequestWithString(Constant.UPLOAD_PARAMS_URL.value, params);
            if (response2.getStatusLine().getStatusCode() == 200) {
                new MyConfirmAlert("存储成功","您可以对文件进行审计了","知道了");
            } else {
                new MyErrorAlert("上传失败", "服务器返回失败，请稍后再试");
            }
        } else if (response.getStatusLine().getStatusCode() == 202) {
            new MyConfirmAlert("需要对文件进行签名", "不可去重，不存在重复文件，将根据数据文件计算标签文件和审计参数，用于用户数据的完整性检验", "继续");
            // 加载参数
            params = user.loadParams(dataList, file.getName());
            HttpResponse response2 = MyHttpTools.sendHttpPostRequestWithString(Constant.UPLOAD_SIGN_AND_PARAMS_URL.value, params);
            if (response2.getStatusLine().getStatusCode() == 200) {
                new MyConfirmAlert("存储成功","您可以对文件进行审计了","知道了");
            } else {
                new MyErrorAlert("上传失败", "服务器返回失败，请稍后再试");
            }
        } else if (response.getStatusLine().getStatusCode() == 200) {
            new MyConfirmAlert("数据文件已存在", "请勿重复上传", "知道了");
        } else {
            new MyConfirmAlert("数据文件上传失败", "请检查网络是否通畅", "知道了");
        }
    }
}
