package com.mtlaa.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtlaa.api.domain.user.entity.Black;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 黑名单 Mapper 接口
 * </p>
 *
 * @author mtlaa
 * @since 2023-12-22
 */
@Mapper
public interface BlackMapper extends BaseMapper<Black> {

}
