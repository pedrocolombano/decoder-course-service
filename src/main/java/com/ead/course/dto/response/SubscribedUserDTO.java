package com.ead.course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribedUserDTO implements Serializable {

    private static final long serialVersionUID = -8824484602897321904L;

    private UUID courseId;
    private UUID userId;

}
