package com.jerry.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: AddressBookMapper
 * Package: com.jerry.reggie.mapper
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-21 20:39
 * @Version 1.0
 */

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
