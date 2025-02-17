package com.mtlaa.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtlaa.api.domain.user.entity.Role;
import com.mtlaa.user.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author mtlaa
 * @since 2023-12-22
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
