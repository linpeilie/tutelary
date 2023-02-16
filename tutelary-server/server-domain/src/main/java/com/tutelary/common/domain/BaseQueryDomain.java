package com.tutelary.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseQueryDomain extends BaseDomain {

    private String keyword;

    public String[] getKeywordFields() {
        return null;
    }

}
