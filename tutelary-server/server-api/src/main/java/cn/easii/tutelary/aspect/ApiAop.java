package cn.easii.tutelary.aspect;

import cn.dev33.satoken.exception.NotLoginException;
import cn.easii.tutelary.common.SessionContext;
import cn.easii.tutelary.common.bean.req.AbstractRequest;
import cn.easii.tutelary.common.exception.ExceptionConverter;
import cn.easii.tutelary.common.bean.R;
import cn.easii.tutelary.common.constants.CommonResponseCode;
import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.common.exception.SystemException;
import cn.easii.tutelary.utils.AuthHelper;
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

    @Pointcut("execution(* cn.easii.tutelary.api..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Date startTime = new Date();
        try {
            SessionContext.setUserId(AuthHelper.getUserId());

            // 校验入参
            Object[] args = joinPoint.getArgs();

            checkParam(args);

            Object result = joinPoint.proceed();

            return result;
        } catch (Throwable throwable) {
            if (throwable instanceof NotLoginException) {
                return R.failure(CommonResponseCode.NO_LOGIN.getCode(), CommonResponseCode.NO_LOGIN.getMessage());
            }
            if (!(throwable instanceof BaseException)) {
                log.error("系统异常", throwable);
                throw new SystemException();
            }
            BaseException exception = ExceptionConverter.transException(throwable);
            R<Void> response = R.failure(exception.getErrorCode(), exception.getErrorMessage());
            return response;
        } finally {
            SessionContext.clear();
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
