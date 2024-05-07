package edu.jnu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 公开审计的TPA审计算法类
 * @author Guo zifan
 * @version 1.0
 * @date 2022年05月08日 10:36
 */
public class PublicVerifyApi {

    /**
     * tpa客户端调用此算法实现dpos的公开审计.
     * 需要传入公钥、用户的文件路径、用户的标签路径,
     * 此算法通过用户的文件路径、用户的标签路径、和自己产生的质询想CSP索要证明
     * 获取到证明之后，验证证明。最终返回结果.
     * @return 返回审计结果
     */
    @PostMapping("/dpos")
    public ResponseEntity<?> dpos() {

        return null;
    }
}
