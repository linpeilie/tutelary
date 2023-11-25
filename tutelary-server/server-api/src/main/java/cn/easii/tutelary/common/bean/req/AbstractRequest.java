package cn.easii.tutelary.common.bean.req;

import java.io.Serializable;
import lombok.Data;

@Data
public class AbstractRequest implements Serializable {

    public void checkInput() {
    }

}
