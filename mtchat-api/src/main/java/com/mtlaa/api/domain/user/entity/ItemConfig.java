package com.mtlaa.api.domain.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 功能物品配置表
 * </p>
 *
 * @author mtlaa
 * @since 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("item_config")
public class ItemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId("id")
    private Long id;

    /**
     * 物品类型 1改名卡 2徽章
     */
    @TableField("type")
    private Integer type;

    /**
     * 物品图片
     */
    @TableField("img")
    private String img;

    /**
     * 物品功能描述
     */
    @TableField("`describe`")
    private String describe;

    /**
     * 创建时间
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
      @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
