package com.jerry.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: UserMapper
 * Package: com.jerry.reggie.mapper
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-21 17:58
 * @Version 1.0
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
