package com.example.picares.model.dto.user;

import com.example.picares.model.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends PageDTO {
    private String userAccount;
    private String userName;
    private String userProfile;
    private String userRole;
}
