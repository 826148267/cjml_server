package edu.jnu.gdbddesktop.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.jnu.gdbddesktop.ServiceDesk;
import edu.jnu.gdbddesktop.components.MyConfirmAlert;
import edu.jnu.gdbddesktop.components.MyErrorAlert;
import edu.jnu.gdbddesktop.config.Constant;
import edu.jnu.gdbddesktop.utils.AuditTool;
import edu.jnu.gdbddesktop.utils.MyHttpTools;
import edu.jnu.gdbddesktop.utils.StringTool;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.FileChooser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月06日 19时28分
 * @功能描述: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileViewTable {

    private SimpleStringProperty fileId = new SimpleStringProperty();

    private SimpleStringProperty fileName = new SimpleStringProperty();

    private SimpleStringProperty isFirstSaver = new SimpleStringProperty();

    private SimpleStringProperty signFileId = new SimpleStringProperty();

    private SimpleStringProperty auditParamsFileId = new SimpleStringProperty();

    /**
     * 下载文件
     */
    public void download() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择文件保存位置");
        fileChooser.setInitialFileName(this.getFileName().getValue());

        File file = fileChooser.showSaveDialog(ServiceDesk.getInstance().getStage());
        // 文件非空且是目录
        if (Objects.nonNull(file)) {
            HttpResponse response = MyHttpTools.sendHttpGetRequestWithString(Constant.DOWNLOAD_FILE_URL.value + "/" + fileId.getValue());
            if (response.getStatusLine().getStatusCode() == 200) {
                try {
                    DownloadFileDTO downloadFileDTO = JSONObject.parseObject(EntityUtils.toString(response.getEntity()), DownloadFileDTO.class);
                    byte[] dataBytes = Base64.getDecoder().decode(downloadFileDTO.getData().replace("\n", "").getBytes());

                    Files.write(file.toPath(), dataBytes);
                    new MyConfirmAlert("下载成功","文件已保存到"+file.getAbsolutePath(),"知道了");
                } catch (IOException e) {
                    System.out.println("文件下载失败:"+e.getMessage());
                }
            }
        }

    }

    /**
     * 审计文件的完整性
     */
    public void audit() {
        User user = ServiceDesk.getInstance().getUserData();
        // 获取审计参数
        AuditParams auditParams = user.getAuditParams(fileId.getValue());
        // 获取挑战
        List<Challenge> challenges = user.getChallenges(auditParams);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("challenges", JSONArray.toJSONString(challenges));
        HttpResponse response = MyHttpTools.sendHttpPostRequestWithString(Constant.POST_CHALLENGES_URL.value+"/"+fileId.getValue(), hashMap);
        if (response.getStatusLine().getStatusCode() == 200) {
            String string = null;
            try {
                string = EntityUtils.toString(response.getEntity());
                IntegrityProof proof = JSONObject.parseObject(string, IntegrityProof.class);
                if (user.verify(auditParams, proof, challenges)) {
                    new MyConfirmAlert("审计成功","此文件通过了公开审计的检验","知道了");
                } else {
                    new MyErrorAlert("警示！审计未通过！服务器给出了证明，但证明并未通过检验", "知道了");
                }
                return;
            } catch (IOException e) {
                new MyConfirmAlert("审计失败","完整性证明格式转化失败", "知道了");
                return;
            }
        }
        new MyConfirmAlert("审计失败","挑战发送失败，请检查网络或者检查服务器连接是否畅通","知道了");
    }
}
