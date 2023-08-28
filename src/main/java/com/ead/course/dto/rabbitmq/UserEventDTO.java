package com.ead.course.dto.rabbitmq;

import com.ead.commonlib.enumerated.ActionType;
import com.ead.commonlib.enumerated.UserStatus;
import com.ead.commonlib.enumerated.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEventDTO implements Serializable {

    private static final long serialVersionUID = 5975517879228987765L;

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String document;
    private String imageUrl;
    private ActionType actionType;

}
