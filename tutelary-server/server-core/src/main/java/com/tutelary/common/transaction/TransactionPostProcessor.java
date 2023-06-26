package com.tutelary.common.transaction;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class TransactionPostProcessor implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @FunctionalInterface
    public interface InnerFunction {

        void exec();

    }

    public void afterCommit(InnerFunction innerFunction) {
        publisher.publishEvent((TransactionListener.CommitFunction) innerFunction::exec);
    }

    public void afterRollBack(InnerFunction innerFunction) {
        publisher.publishEvent((TransactionListener.RollBackFunction) innerFunction::exec);
    }

    public void afterComplete(InnerFunction innerFunction) {
        publisher.publishEvent((TransactionListener.CompleteFunction) innerFunction::exec);
    }

}
