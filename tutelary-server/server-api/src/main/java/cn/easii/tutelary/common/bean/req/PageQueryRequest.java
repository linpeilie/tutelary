package cn.easii.tutelary.common.bean.req;

import lombok.Data;

@Data
public class PageQueryRequest extends AbstractRequest {

    private long pageIndex = 1;

    private long pageSize = 10;

}
