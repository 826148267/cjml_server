package edu.jnu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年04月25日 22时20分
 * @功能描述: 用户信息传输实体类
 */
@ApiModel("用户信息实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserVO {
    @JSONField(name = "userAddress")
    @ApiModelProperty(value = "用户住址", example = "广东省广州市番禺区暨南大学")
    private String userAddress = "无固定住址";
    @JSONField(name = "userOrganization")
    @ApiModelProperty(value = "用户组织", example = "暨南大学")
    private String userOrganization = "无组织";
    @JSONField(name = "userFileNums")
    @ApiModelProperty(value = "用户文件数量", example = "1")
    private String userFileNums = "0";
}
