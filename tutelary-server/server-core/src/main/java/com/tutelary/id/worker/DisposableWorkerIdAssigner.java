package com.tutelary.id.worker;

import com.tutelary.common.cache.CacheKeyTemplateEnum;
import com.tutelary.common.cache.CacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisposableWorkerIdAssigner implements WorkerIdAssigner{

    private final CacheManager cacheManager;

    @Value("${id.worker.app-name:tutelary-server}")
    private String workerAppName;

    @Override
    public long assignWorkerId() {
        return cacheManager.incrementAndGet(CacheKeyTemplateEnum.WORKER.toCacheKey(workerAppName));
    }
}
