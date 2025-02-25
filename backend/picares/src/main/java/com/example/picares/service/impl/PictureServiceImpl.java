package com.example.picares.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.example.picares.common.*;
import com.example.picares.constant.UserConstant;
import com.example.picares.mapper.PictureMapper;
import com.example.picares.model.dto.picture.PictureUploadDTO;
import com.example.picares.model.entity.Picture;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.service.PictureService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Iterator;
import java.util.Locale;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {
    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void updateUser(MultipartFile multipartFile, PictureUploadDTO pictureUploadDTO, HttpServletRequest request) {
        LoginUserVO loginUser = (LoginUserVO) request.getSession().getAttribute(UserConstant.USER_LOGIN);
        Long id = loginUser.getId();
        String userAccount = loginUser.getUserAccount();
        try(InputStream inputStream = multipartFile.getInputStream();) {
            long size = multipartFile.getSize();

            BufferedImage image=ImageIO.read(inputStream);

            int width = image.getWidth();
            int height = image.getHeight();
            double scale = width * 1.0 / height;

            String type = getImageType(multipartFile.getInputStream());

            String uploadDir = "images"+File.separator+userAccount;
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean mkdir = dir.mkdirs();
                ThrowUtils.throwIf(!mkdir,ErrorCode.SYSTEM_ERROR);
            }

//            Path uploadDir= Paths.get("images",userAccount);
//            File dir=uploadDir.toFile();
//            if (!dir.exists()) {
//                boolean mkdir = dir.mkdirs();
//                ThrowUtils.throwIf(!mkdir,ErrorCode.SYSTEM_ERROR);
//            }

            // /images/账号/4位随机数+时间戳.type
            long nowTime = Instant.now().toEpochMilli();
            String fileName= RandomUtil.randomNumbers(4)+nowTime;

            String url=File.separator+uploadDir+File.separator+fileName+"."+type;

//            Path dest=uploadDir.resolve(fileName+"."+type);
            File dest = new File(dir.getAbsoluteFile()+File.separator+fileName+"."+type);

            Picture picture=new Picture();
            picture.setUrl(url);
            picture.setName(pictureUploadDTO.getName());
            picture.setIntroduction(pictureUploadDTO.getIntroduction());
            picture.setCategory(pictureUploadDTO.getCategory());
            picture.setTags(pictureUploadDTO.getTags());
            picture.setPicSize(size);
            picture.setPicWidth(width);
            picture.setPicHeight(height);
            picture.setPicScale(scale);
            picture.setPicFormat(type);
            picture.setUserId(id);

            transactionTemplate.execute(status -> {
                try {
                    pictureMapper.insertPicture(picture);
                    ImageIO.write(image,type,dest);
                }catch (Exception e){
                    log.error("图片上传失败，",e);
                    status.setRollbackOnly();
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                return null;
            });
        } catch (Exception e) {
            log.error("图片上传失败",e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    private String getImageType(InputStream inputStream){
        try(ImageInputStream imageInputStream=ImageIO.createImageInputStream(inputStream)){
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            if(!readers.hasNext()){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            ImageReader reader = readers.next();
            return reader.getFormatName().toLowerCase(Locale.ROOT);
        } catch (Exception e) {

            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

    }
}
