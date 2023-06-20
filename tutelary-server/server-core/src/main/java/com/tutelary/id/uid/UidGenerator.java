package com.tutelary.id.uid;

import com.tutelary.id.IdGenerator;
import com.tutelary.id.worker.WorkerIdAssigner;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UidGenerator implements IdGenerator, InitializingBean {

    /**
     * Bits allocate
     */
    private final int timeBits = 28;
    private final int workerBits = 22;
    private final int seqBits = 13;

    private final WorkerIdAssigner workerIdAssigner;

    private long workerId;

    private BitsAllocator bitsAllocator;

    @Override
    public void afterPropertiesSet() throws Exception {
        // initialize bits allocator
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);

        // initialize worker id
        workerId = workerIdAssigner.assignWorkerId();
        if (workerId > bitsAllocator.getMaxWorkerId()) {
            log.warn("workerId {} exceeds max workerId {}", workerId, bitsAllocator.getMaxWorkerId());
            workerId = workerId % bitsAllocator.getMaxSequence();
        }

        log.info("Initialized bits (1, {}, {}, {}) for workerId : {}", timeBits, workerBits, seqBits, workerId);
    }

    @Override
    public long getId() {
        return 0;
    }

}
