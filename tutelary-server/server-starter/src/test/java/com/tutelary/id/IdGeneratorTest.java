package com.tutelary.id;

import com.tutelary.common.id.IdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IdGeneratorTest {

    @Autowired
    private IdGenerator idGenerator;

    @Test
    void getId() {
        final long id = idGenerator.getId();
        System.out.println(id);
    }
}