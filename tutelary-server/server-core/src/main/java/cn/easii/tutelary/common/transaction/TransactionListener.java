package cn.easii.tutelary.common.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class TransactionListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void afterCommit(CommitFunction function) {
        function.afterCommit();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK, fallbackExecution = true)
    public void afterRollBack(RollBackFunction function) {
        function.afterRollBack();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION, fallbackExecution = true)
    public void afterComplete(CompleteFunction function) {
        function.afterComplete();
    }

    @FunctionalInterface
    public interface CommitFunction {
        void afterCommit();
    }

    @FunctionalInterface
    public interface CompleteFunction {
        void afterComplete();
    }

    @FunctionalInterface
    public interface RollBackFunction {
        void afterRollBack();
    }

}
