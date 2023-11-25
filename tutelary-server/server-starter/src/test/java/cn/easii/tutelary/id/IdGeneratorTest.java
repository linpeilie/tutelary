package cn.easii.tutelary.id;

import cn.easii.tutelary.common.id.IdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdGeneratorTest {

    @Autowired
    private IdGenerator idGenerator;

    @Test
    void getId() {
        final long id = idGenerator.getId();
        System.out.println(id);
    }
}