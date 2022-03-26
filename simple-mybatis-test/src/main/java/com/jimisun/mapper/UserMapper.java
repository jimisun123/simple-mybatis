package com.jimisun.mapper;

import com.jimisun.entity.User;

public interface UserMapper {

    User selectOne(User user);

    int updateUserByUserId(User user);

}
