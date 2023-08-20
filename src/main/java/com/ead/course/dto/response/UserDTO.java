package com.ead.course.dto.response;

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
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 3208118908630259675L;

    private UUID userId;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String imageUrl;
}
