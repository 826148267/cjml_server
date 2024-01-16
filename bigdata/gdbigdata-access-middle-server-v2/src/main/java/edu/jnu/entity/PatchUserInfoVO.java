package edu.jnu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月17日 16:28
 */
@ApiModel("用户信息单字段操作实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchUserInfoVO {
    @ApiModelProperty(value = "用户操作类型", allowableValues = "update,get", example = "get", dataType = "String")
    @NotBlank(message = "操作不得为空或空串，可以为update、get")
    private String op;
    @ApiModelProperty(value = "属性值", allowableValues = "/user-organization,/user-address,/user-file-nums", example = "/user-organization", dataType = "String")
    @NotBlank(message = "资源的属性值不得为空或空串，格式如/attribute")
    private String path;
    @ApiModelProperty(value = "该属性值的新值", dataType = "String", example = "我是一个占位字符串")
    @NotNull(message = "更新值不得为空")
    private String value;
}
