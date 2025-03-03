package com.example.picares.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.example.picares.common.BusinessException;
import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import com.example.picares.common.UserAccess;
import com.example.picares.constant.UserConstant;
import com.example.picares.mapper.UserMapper;
import com.example.picares.model.dto.picture.PictureUploadDTO;
import com.example.picares.model.dto.user.UserLoginDTO;
import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.dto.user.UserRegisterDTO;
import com.example.picares.model.dto.user.UserUpdateDTO;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.PageVO;
import com.example.picares.model.vo.UserVO;
import com.example.picares.service.PictureService;
import com.example.picares.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void userRegister(UserRegisterDTO userRegisterDTO) {
        String userAccount = userRegisterDTO.getUserAccount();
        String userPassword = userRegisterDTO.getUserPassword();

        //加密
        String encodePassword = getEncodePassword(userPassword);

        //获取用户名，注册
        String userName = "游客" + RandomUtil.randomNumbers(6);
        try {
            userMapper.register(userAccount, encodePassword, userName);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public LoginUserVO userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        String userAccount = userLoginDTO.getUserAccount();
        String userPassword = userLoginDTO.getUserPassword();

        String encodePassword = getEncodePassword(userPassword);
        LoginUserVO loginUserVO;
        //登录
        try {
            loginUserVO = userMapper.login(userAccount, encodePassword);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        ThrowUtils.throwIf(loginUserVO == null, ErrorCode.OPERATION_ERROR, "账号或密码不正确");

        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN, loginUserVO);

        UserAccess.putLoginSession(loginUserVO.getId(),session);

        return loginUserVO;
    }

    @Override
    public LoginUserVO getLoginUser(HttpServletRequest request) {
        LoginUserVO loginUser = (LoginUserVO) request.getSession().getAttribute(UserConstant.USER_LOGIN);
        ThrowUtils.throwIf(loginUser==null,ErrorCode.NOT_LOGIN_ERROR);

        String id = request.getSession().getId();
        System.out.println("sessionId："+id);
        return loginUser;
    }

    @Override
    public void userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUserVO loginUser = (LoginUserVO) session.getAttribute(UserConstant.USER_LOGIN);
        ThrowUtils.throwIf(loginUser==null,ErrorCode.NOT_LOGIN_ERROR);

        Long id = loginUser.getId();
        session.removeAttribute(UserConstant.USER_LOGIN);
        UserAccess.removeLoginSession(id,session);
    }

    @Override
    public PageVO<UserVO> getUserByPage(UserQueryDTO userQueryDTO) {
        PageVO<UserVO> pageVO=new PageVO<>();
        int current = userQueryDTO.getCurrent();
        int pageSize = userQueryDTO.getPageSize();
        userQueryDTO.setCurrent((current-1)*pageSize);

        try {
            List<UserVO> userVO = userMapper.selectUserByPage(userQueryDTO);
            int count = userMapper.countUserByPage(userQueryDTO);
            pageVO.setRecords(userVO);
            pageVO.setTotal(count);
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return pageVO;
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        try {
            userMapper.updateUser(userUpdateDTO);

            UserAccess.deleteLoginSession(userUpdateDTO.getId());
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void updateUser(MultipartFile multipartFile, UserUpdateDTO userUpdateDTO) {
        try {
            transactionTemplate.execute(status -> {
                try {
                    String avatarPath = getAvatar(multipartFile, userUpdateDTO.getUserAccount());
                    userUpdateDTO.setUserAvatar(File.separator+avatarPath);
                    userMapper.updateUser(userUpdateDTO);
                    uploadAvatar(multipartFile,avatarPath);
                } catch (Exception e) {
                    log.error("用户更新失败，", e);
                    status.setRollbackOnly();
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                return null;
            });
            userMapper.updateUser(userUpdateDTO);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    private String getAvatar(MultipartFile multipartFile,String userAccount) {
        String uploadDir = "avatar"+ File.separator+userAccount;
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean mkdir = dir.mkdirs();
            ThrowUtils.throwIf(!mkdir,ErrorCode.SYSTEM_ERROR);
        }

        String originalFilename = multipartFile.getOriginalFilename();
        ThrowUtils.throwIf(originalFilename==null,ErrorCode.SYSTEM_ERROR);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName= RandomUtil.randomNumbers(4)+Instant.now().toEpochMilli()+suffix;
        return uploadDir+File.separator+fileName;
    }

    private void uploadAvatar(MultipartFile multipartFile,String avatarPath) {
        try {
            File dest = new File(avatarPath);
            System.out.println(dest);
            multipartFile.transferTo(dest.getAbsoluteFile());
        } catch (Exception e) {
            log.error("头像上传失败，",e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            userMapper.deleteUser(id);

            UserAccess.deleteLoginSession(id);
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    private String getEncodePassword(String password) {
        final String SALT = "picares";
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes());
    }
}
