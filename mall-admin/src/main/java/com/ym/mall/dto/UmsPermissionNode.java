package com.ym.mall.dto;

import com.ym.mall.model.UmsPermission;
import lombok.Data;

import java.util.List;

/**
 * @author matao
 * @create 2019-08-15 18:35
 */
@Data
public class UmsPermissionNode extends UmsPermission {

    private List<UmsPermissionNode> children;
}
