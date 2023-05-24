package com.ead.course.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModuleInsertDTO implements Serializable {

    private static final long serialVersionUID = -6066680044408666489L;

    private String title;
    private String description;

}
