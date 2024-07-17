package com.mtlaa.user.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtlaa.api.domain.user.entity.Black;
import com.mtlaa.user.mapper.BlackMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author mtlaa
 * @since 2023-12-22
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> {

}
