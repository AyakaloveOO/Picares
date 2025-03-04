package com.example.picares.model.entity.user;

import com.example.picares.model.entity.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends Page {
    private String userAccount;
    private String userName;
    private String userProfile;
    private String userRole;
}
