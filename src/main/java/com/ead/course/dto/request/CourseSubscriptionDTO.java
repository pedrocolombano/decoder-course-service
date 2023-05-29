package com.ead.course.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseSubscriptionDTO implements Serializable {

    private static final long serialVersionUID = 8805251563735068486L;

    @NotNull(message = "User id must not be null.")
    private UUID userId;

}
