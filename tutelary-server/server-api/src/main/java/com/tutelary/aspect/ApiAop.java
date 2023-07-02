package com.tutelary.aspect;

import com.tutelary.common.bean.R;
import com.tutelary.common.bean.req.AbstractRequest;
import com.tutelary.common.exception.BaseException;
import com.tutelary.common.exception.ExceptionConverter;
import java.util.Collection;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@Order(1)
public class ApiAop {

    @Pointcut("execution(* com.tutelary.api..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Date startTime = new Date();
        try {
            // 校验入参
            Object[] args = joinPoint.getArgs();

            checkParam(args);

            Object result = joinPoint.proceed();

            return result;
        } catch (Throwable throwable) {
            if (!(throwable instanceof BaseException)) {
                log.error("系统异常", throwable);
            }
            BaseException exception = ExceptionConverter.transException(throwable);
            R<Void> response = R.failure(exception.getErrorMessage());
            return response;
        }
    }

    private void checkParam(Object[] args) {
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }
            if (arg instanceof AbstractRequest) {
                ((AbstractRequest) arg).checkInput();
            } else if (Collection.class.isAssignableFrom(arg.getClass())) {
                Collection collection = (Collection) arg;
                checkParam(collection.toArray());
            }
        }
    }

}
