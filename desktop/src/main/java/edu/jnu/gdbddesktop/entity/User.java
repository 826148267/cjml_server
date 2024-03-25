package edu.jnu.gdbddesktop.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.jnu.gdbddesktop.components.MyConfirmAlert;
import edu.jnu.gdbddesktop.config.Constant;
import edu.jnu.gdbddesktop.utils.AuditTool;
import edu.jnu.gdbddesktop.utils.FileTool;
import edu.jnu.gdbddesktop.utils.MyHttpTools;
import it.unisa.dia.gas.jpbc.Element;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 21时52分
 * @功能描述: 用户信息（用于存储用户在软件使用期间的信息）
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private static User instance = null;

    private String userName;
    private String userAddress;
    private String userOrganization;
    private String userFileNums;
    // 私钥
    private Element sk;
    // 公钥
    private Element pk;

    public void initSk(String pwd) {
        if (this.sk == null) {
            // 通过seed获得确定性的sk
            this.sk = AuditTool.hashTwo(pwd);
        }
    }

    public Element getSk() {
        if (this.sk == null) {
            String pwd = MyHttpTools.getHttpGet(Constant.GET_SK_URL.value);
            // 通过seed获得确定性的sk
            initSk(pwd);
        }
        return this.sk;
    }

    public Element getPk() {
        if (this.pk == null) {
            this.pk = AuditTool.g.duplicate().powZn(this.sk);
        }
        return this.pk;
    }

    public Element getR(String fa) {
        if (this.sk == null) {
            getSk();
        }
        return AuditTool.hashTwo(this.sk.toString()+fa);
    }

    public Element getH(Element r) {
        return AuditTool.g.duplicate().powZn(r);
    }

    public Element getH1(Element h, Element sk) {
        return h.duplicate().powZn(sk);
    }

    /**
     * 将文件进行签名
     * @param path 进行base64编码后的File实例的path
     * @return 返回两个List，第一个是按行分割的数据文件数组，第二个是按行分割的标签文件数组（最后一行是审计的参数AuditParams对象的json字符串）
     */
    public List<List<String>> sign(Path path) throws IOException {
        List<List<String>> result = new ArrayList<>();
        // 按行读出文件
        List<String> lines = Arrays.stream(
                new String(
                        Base64.getEncoder().encode(Files.readAllBytes(path))
                ).split("(?<=\\G.{" + 10000 + "})")
        ).toList();
        result.add(lines);

        // 按行进行签名
        String fileAbstract =AuditTool.getFileAbstract(path);
        Element r = this.getR(fileAbstract);
        Element h = this.getH(r);
        // 为并行处理做准备
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            hashMap.put(i, lines.get(i));
        }
        // 并行进行签名
        List<String> signList = new ArrayList<>(
                hashMap.entrySet().stream()
                        .parallel()
                        .map(entry -> Base64.getEncoder().encodeToString(AuditTool.g1Element2Str(sign(r, h, fileAbstract, entry.getKey(), entry.getValue().getBytes())).getBytes()))
                        .toList());
        Element h1 = this.getH1(h, this.sk);
        // 插入审计材料
        AuditParams auditParams = AuditParams.builder()
                .h(AuditTool.g1Element2Str(h))
                .h1(AuditTool.g1Element2Str(h1))
                .fa(fileAbstract)
                .n(signList.size())
                .build();
        signList.add(JSONObject.toJSONString(auditParams));
        result.add(signList);
        return result;
    }

    public List<String> sign(List<String> dataList) {
        // 按行进行签名
        String fileAbstract =AuditTool.getFileAbstract(dataList.toString().substring(1, dataList.toString().length() - 1).replace(",", ""));
        Element r = this.getR(fileAbstract);
        Element h = this.getH(r);
        // 为并行处理做准备
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < dataList.size(); i++) {
            hashMap.put(i, dataList.get(i));
        }
        // 并行进行签名
        List<String> signList = new ArrayList<>(
                hashMap.entrySet().stream()
                        .parallel()
                        .map(entry -> Base64.getEncoder().encodeToString(AuditTool.g1Element2Str(sign(r, h, fileAbstract, entry.getKey(), entry.getValue().getBytes())).getBytes()))
                        .toList());
        Element h1 = this.getH1(h, this.sk);
        // 插入审计材料
        AuditParams auditParams = AuditParams.builder()
                .h(AuditTool.g1Element2Str(h))
                .h1(AuditTool.g1Element2Str(h1))
                .fa(fileAbstract)
                .n(signList.size())
                .build();
        signList.add(JSONObject.toJSONString(auditParams));
        return signList;
    }

    public Element sign(Element r, Element h, String fileAbstract, String index, byte[] mBytes) {
        Element left = AuditTool.hashOne(fileAbstract+index).powZn(r);
        Element low = AuditTool.u.duplicate().powZn(AuditTool.hashTwo(mBytes));
        Element high = (sk.duplicate().add(AuditTool.hashTwo(fileAbstract+h.toString()))).invert();
        return (low.powZn(high)).mul(left);
    }

    public Element sign(Element r, Element h, String fileAbstract, Integer index, byte[] mBytes) {
        return sign(r, h, fileAbstract, index.toString(), mBytes);
    }

    public Element sign(Element r, Element h, byte[] fileAbstractAndIndex, byte[] fileAbstractAndH, byte[] mBytes) {
        Element left = AuditTool.hashOne(fileAbstractAndIndex).powZn(r);
        Element low = AuditTool.u.duplicate().powZn(AuditTool.hashTwo(mBytes));
        Element high = this.sk.duplicate().add(AuditTool.hashTwo(fileAbstractAndH)).invert();
        return low.powZn(high).mulZn(left);
    }

    public boolean verify(Element pk, Element sign, Element data, String fileAbstract, Element h, Element h1, List<Challenge> challenges) {
        String fileAbstractAndH = fileAbstract + h.toString();
        Element value = getMutil(fileAbstract, challenges);
        Element left = AuditTool.BP.pairing(sign, pk.mul(AuditTool.g.duplicate().powZn(AuditTool.hashTwo(fileAbstractAndH))));
        Element right1 = AuditTool.BP.pairing(value, h1.mul(h.powZn(AuditTool.hashTwo(fileAbstractAndH))));
        Element right2 = AuditTool.BP.pairing(AuditTool.u.duplicate().powZn(data), AuditTool.g);
        Element right = right1.mul(right2);
        return left.equals(right);
    }
    public boolean verify(AuditParams auditParams, IntegrityProof proof, List<Challenge> challenges) {
        return this.verify(AuditTool.str2G1Element(proof.getSignAggregation()), AuditTool.str2ZpElement(proof.getDataAggregation()), auditParams, challenges);
    }
    public boolean verify(Element sign, Element data, AuditParams auditParams, List<Challenge> challenges) {
        Element pk = this.getPk();
        return this.verify(pk, sign, data, auditParams.getFa(), AuditTool.str2G1Element(auditParams.getH()), AuditTool.str2G1Element(auditParams.getH1()), challenges);
    }

    private Element getMutil(String fileAbstract, List<Challenge> challenges) {
        return challenges.stream()
                .map(challenge -> AuditTool.hashOne(fileAbstract+ challenge.getIndex()).powZn(AuditTool.hashTwo(String.valueOf(challenge.getRandom()))))
                .reduce(AuditTool.getG1One(), Element::mul);
    }

    /**
     * 加载自身数据信息
     */
    public boolean initInfo(String pwd) {
        this.sk = AuditTool.hashTwo(pwd);
        this.pk = getPk();
        // 初始化用户私钥
        HttpResponse response = MyHttpTools.sendHttpGetRequestWithString(Constant.GET_USER_INFO_URL.value+"/"+this.userName);
        if (response.getStatusLine().getStatusCode() == 200) {
            // 从in中解析出响应结果
            String result = null;
            try {
                InputStream in = response.getEntity().getContent();
                result = IOUtils.toString(in, StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.out.println("读取响应结果时失败，用户信息获取失败");
                return false;
            }
            JSONObject jsonObject = JSON.parseObject(result);
            this.userAddress = jsonObject.getString("userAddress");
            this.userOrganization = jsonObject.getString("userOrganization");
            this.userFileNums = jsonObject.getString("userFileNums");
            return true;
        }
        return false;
    }

    /**
     * 上传数据文件
     * @param dataList 数据文件内容
     * @return 返回响应结果
     */
    public HttpResponse uploadDataFile(List<String> dataList, String fileName, String fileType) {
        // 发送用户名，文件名，文件内容
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("data", dataList.toString().substring(1, dataList.toString().length() - 1).replace(", ", "\n"));
        hashMap.put("userName", this.getUserName());
        hashMap.put("fileName", fileName);
        hashMap.put("mimeType", fileType);
        return MyHttpTools.sendHttpPostRequestWithString(Constant.UPLOAD_DATA_FILE_URL.value, hashMap);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userOrganization='" + userOrganization + '\'' +
                ", userFileNums='" + userFileNums + '\'' +
                ", sk=" + AuditTool.g1Element2Str(sk) +
                ", pk=" + AuditTool.zpElement2Str(pk) +
                '}';
    }

    public AuditParams getAuditParams(List<String> dataList) {
        String fileAbstract = AuditTool.getFileAbstract(dataList.toString().substring(1, dataList.toString().length() - 1).replace(",", ""));
        Element r = this.getR(fileAbstract);
        Element h = this.getH(r);
        Element h1 = this.getH1(h, this.sk);
        return AuditParams.builder()
                .fa(fileAbstract)
                .n(dataList.size())
                .h(AuditTool.g1Element2Str(h))
                .h1(AuditTool.g1Element2Str(h1))
                .build();
    }

    public HashMap<String, String> loadParams(List<String> dataList, TransParams oldTransParams, String fileName) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", this.getUserName());
        params.put("fileName", fileName);

        String fileAbstract = AuditTool.getFileAbstract(dataList.toString().substring(1, dataList.toString().length() - 1).replace(",", ""));
        Element r = this.getR(fileAbstract).sub(AuditTool.str2ZpElement(oldTransParams.getR()));

        Element h = this.getH(r);
        Element h1 = this.getH1(h, this.sk);

        TransParams transParams = new TransParams();

        transParams.setR(AuditTool.zpElement2Str(r));

        Element x = AuditTool.getRandomZpElement();
        Element aux = this.sk.duplicate().add(AuditTool.hashTwo(fileAbstract+h)).invert().sub(x);
        aux.add(aux);
        transParams.setAux(AuditTool.zpElement2Str(aux));

        Element v = AuditTool.u.duplicate().powZn(x).mul(AuditTool.str2G1Element(oldTransParams.getV()));
        transParams.setV(AuditTool.g1Element2Str(v));
        params.put("transParams", JSONObject.toJSONString(transParams));


        // 插入审计材料
        AuditParams auditParams = AuditParams.builder()
                .h(AuditTool.g1Element2Str(h))
                .h1(AuditTool.g1Element2Str(h1))
                .fa(fileAbstract)
                .n(dataList.size())
                .build();
        params.put("auditParams", JSONObject.toJSONString(auditParams));

        return params;
    }

    public HashMap<String, String> loadParams(List<String> dataList, String fileName) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", this.getUserName());
        params.put("fileName", fileName);

        // 按行进行签名
        String fileAbstract =AuditTool.getFileAbstract(dataList.toString().substring(1, dataList.toString().length() - 1).replace(",", ""));
        Element r = this.getR(fileAbstract);
        Element h = this.getH(r);
        // 为并行处理做准备
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i < dataList.size(); i++) {
            hashMap.put(i, dataList.get(i));
        }
        // 并行进行签名
        List<String> signList = new ArrayList<>(
                hashMap.entrySet().stream()
                        .parallel()
                        .map(entry -> Base64.getEncoder().encodeToString(AuditTool.g1Element2Str(sign(r, h, fileAbstract, entry.getKey(), entry.getValue().getBytes())).getBytes()))
                        .toList());
        Element h1 = this.getH1(h, this.sk);
        params.put("signs", signList.toString().substring(1, signList.toString().length()-1).replace(", ", "\n"));


        // 插入审计材料
        AuditParams auditParams = AuditParams.builder()
                .h(AuditTool.g1Element2Str(h))
                .h1(AuditTool.g1Element2Str(h1))
                .fa(fileAbstract)
                .n(signList.size())
                .build();
        params.put("auditParams", JSONObject.toJSONString(auditParams));

        TransParams transParams = new TransParams();
        Element x = AuditTool.getRandomZpElement();
        transParams.setR(AuditTool.zpElement2Str(AuditTool.hashTwo(fileAbstract+this.sk.toString())));
        transParams.setV(AuditTool.g1Element2Str(AuditTool.u.duplicate().powZn(x)));
        Element aux = AuditTool.getZpZero().sub(this.sk.duplicate().add(AuditTool.hashTwo(fileAbstract+h)).invert()).sub(x);
        transParams.setAux(AuditTool.zpElement2Str(aux));
        params.put("transParams", JSONObject.toJSONString(transParams));

        return params;
    }

    /**
     * 通过用户名获取文件列表
     * @return 返回文件列表
     */
    public ObservableList<FileViewTable> getFileViewTableData() {
        ObservableList<FileViewTable> fileViewTableData = FXCollections.observableArrayList();
        HttpResponse response = MyHttpTools.sendHttpGetRequestWithString(Constant.GET_USER_FILE_LIST_URL.value+"/"+this.getUserName());
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                String content = EntityUtils.toString(response.getEntity());
                JSONArray objects = JSONArray.parseArray(content);
                for (Object object : objects) {
                    FileViewTable fileViewTable = new FileViewTable();
                    fileViewTable.getFileId().setValue(((JSONObject) object).getString("fileId"));
                    fileViewTable.getFileName().setValue(((JSONObject) object).getString("fileName"));
                    fileViewTable.getIsFirstSaver().setValue(((JSONObject) object).getString("isFirstSaver"));
                    fileViewTable.getSignFileId().setValue(((JSONObject) object).getString("signFileId"));
                    fileViewTable.getAuditParamsFileId().setValue(((JSONObject) object).getString("auditParamsFileId"));
                    fileViewTableData.add(fileViewTable);
                }
            } catch (IOException e) {
                System.out.println("读取文件列表时候，参数转化失败："+e.getMessage());
            }
        }
        return fileViewTableData;
    }

    /**
     * 从服务器获取审计参数的字符串形式
     * @param fileId 文件id
     * @return 返回审计参数
     */
    public AuditParams getAuditParams(String fileId) {
        HttpResponse response = MyHttpTools.sendHttpGetRequestWithString(Constant.GET_AUDIT_PARAMS_URL.value+"/"+fileId);
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                String auditParamsString = EntityUtils.toString(response.getEntity());
                return JSONObject.parseObject(auditParamsString, AuditParams.class);
            } catch (IOException e) {
                System.out.println("获取审计参数失败："+e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取挑战
     * @param auditParams 审计参数
     * @return 获取挑战
     */
    public List<Challenge> getChallenges(AuditParams auditParams) {
        return AuditTool.yieldChallenges(460, auditParams.getN());
    }


}
