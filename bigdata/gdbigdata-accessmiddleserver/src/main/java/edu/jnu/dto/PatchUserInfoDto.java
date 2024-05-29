package edu.jnu.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月17日 16:28
 */
@ApiModel("用户信息单字段操作实体")
public class PatchUserInfoDto<T> {
    @JSONField(name = "op")
    @ApiModelProperty(value = "用户操作类型", allowableValues = "update,get", example = "get", dataType = "String")
    @NotBlank(message = "操作不得为空或空串，可以为update、get")
    private String op;
    @JSONField(name = "path")
    @ApiModelProperty(value = "属性值", allowableValues = "/user-organization,/user-address,/user-file-nums", example = "/user-organization", dataType = "String")
    @NotBlank(message = "资源的属性值不得为空或空串，格式如/attribute")
    private String path;
    @JSONField(name = "value")
    @ApiModelProperty(value = "该属性值的新值", dataType = "String", example = "我是一个占位字符串")
    @NotNull(message = "更新值不得为空")
    private T value;

    public PatchUserInfoDto() {
    }

    public PatchUserInfoDto(String op, String path, T value) {
        this.op = op;
        this.path = path;
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
