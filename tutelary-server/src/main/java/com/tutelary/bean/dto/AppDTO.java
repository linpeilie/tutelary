package com.tutelary.bean.dto;

import com.tutelary.common.bean.dto.BaseDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppDTO extends BaseDto {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
