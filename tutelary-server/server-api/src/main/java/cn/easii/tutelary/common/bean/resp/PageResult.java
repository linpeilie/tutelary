package cn.easii.tutelary.common.bean.resp;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResult<T> extends AbstractResponse {

    private long total = 0L;

    private List<T> records = Collections.emptyList();

    public static <T> PageResult<T> of(long total, List<T> records) {
        return new PageResult<>(total, records);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<T>(0, Collections.emptyList());
    }

}
