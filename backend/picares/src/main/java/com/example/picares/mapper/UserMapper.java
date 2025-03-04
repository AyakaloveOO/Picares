package com.example.picares.mapper;

import com.example.picares.model.entity.user.UserLogin;
import com.example.picares.model.entity.user.UserQuery;
import com.example.picares.model.entity.user.UserRegister;
import com.example.picares.model.entity.user.UserUpdate;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.UserAdminVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Insert("insert into user (userAccount,userPassword,userName) value (#{userAccount},#{userPassword},#{userName})")
    void register(UserRegister userRegister);

    @Select("select * from user where userAccount=#{userAccount} and userPassword=#{userPassword} and isDelete=0")
    LoginUserVO login(UserLogin userLogin);

    @Select("select * from user where id=#{id} and isDelete=0")
    LoginUserVO selectById(Long id);

    void updateUser(UserUpdate userUpdate);

    @Update("update user set isDelete=1 where id=#{id}")
    void deleteUser(Long id);

    List<UserAdminVO> selectUserByPage(UserQuery userQuery);

    int countUserByPage(UserQuery userQuery);
}
