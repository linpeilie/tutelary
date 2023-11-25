package cn.easii.tutelary.utils;

import cn.easii.tutelary.common.id.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdGeneratorHelper {

    private static IdGenerator idGenerator;

    public static String getId() {
        return String.valueOf(idGenerator.getId());
    }

    @Autowired
    public void setIdGenerator(IdGenerator idGenerator) {
        IdGeneratorHelper.idGenerator = idGenerator;
    }

}
