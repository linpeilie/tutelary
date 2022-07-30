package com.tutelary.bean.domain;

import com.tutelary.common.bean.domain.BaseDomain;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class App extends BaseDomain {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
