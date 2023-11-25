package cn.easii.tutelary.client.thread;

import cn.easii.tutelary.client.loader.ClassLoaderWrapper;
import cn.easii.tutelary.common.thread.NamedThreadFactory;

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
