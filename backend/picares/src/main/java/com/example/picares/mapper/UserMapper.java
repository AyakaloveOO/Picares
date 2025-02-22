package com.example.picares.mapper;

import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.dto.user.UserUpdateDTO;
import com.example.picares.model.entity.User;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Insert("insert into user (userAccount,userPassword,userName) value (#{userAccount},#{userPassword},#{userName})")
    void register(String userAccount,String userPassword,String userName);

    @Select("select * from user where userAccount=#{userAccount} and userPassword=#{userPassword} and isDelete=0")
    LoginUserVO login(String userAccount,String userPassword);

    @Select("select * from user where id=#{id} and isDelete=0")
    LoginUserVO selectById(Long id);

    void updateUser(UserUpdateDTO userUpdateDTO);

    @Update("update user set isDelete=1 where id=#{id}")
    void deleteUser(Long id);

    List<UserVO> selectUserByPage(UserQueryDTO userQueryDTO);

    int countUserByPage(UserQueryDTO userQueryDTO);
}
