package com.tutelary.client.thread;

import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.common.thread.NamedThreadFactory;

public class AgentNamedThreadFactory extends NamedThreadFactory {

    public AgentNamedThreadFactory(final String name) {
        super(name);
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Thread thread = super.newThread(r);
        thread.setContextClassLoader(ClassLoaderWrapper.getAgentClassLoader());
        return thread;
    }
}
