package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseDomain;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class App extends BaseDomain {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
