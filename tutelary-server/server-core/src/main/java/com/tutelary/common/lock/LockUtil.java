package com.tutelary.common.lock;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface LockUtil {

    boolean tryLock(String lockKey);

    boolean tryLock(long waitTime, String lockKey);

    boolean tryLock(long waitTime, long leaseTime, String lockKey);

    void unlock(String lockKey);

    <Param> void executeWithLock(String lockKey, Consumer<Param> consumer, Param param);

    <Param> void executeWithLock(long waitTime, String lockKey, Consumer<Param> consumer, Param param);

    <Param> void executeWithLock(long waitTime, long leaseTime, String lockKey, Consumer<Param> consumer, Param param);

    <Result> Result executeWithLock(String lockKey, Supplier<Result> supplier);

    <Result> Result executeWithLock(long waitTime, String lockKey, Supplier<Result> supplier);

    <Result> Result executeWithLock(long waitTime, long leaseTime, String lockKey, Supplier<Result> supplier);

    /**
     * 获取锁，执行方法后，自动释放锁
     *
     * @param lockKey  锁的key
     * @param function 执行函数
     * @param t        入参
     * @return function 函数执行的返回参数
     */
    <Param, Result> Result executeWithLock(String lockKey, Function<Param, Result> function, Param t);

    /**
     * 获取锁，执行方法后，自动释放锁
     *
     * @param waitTime 获取锁等待时间
     * @param lockKey  锁的key
     * @param function 执行函数
     * @param t        入参
     * @return function 函数执行的返回参数
     */
    <Param, Result> Result executeWithLock(long waitTime, String lockKey, Function<Param, Result> function, Param t);

    /**
     * 获取锁，执行方法后，自动释放锁
     *
     * @param waitTime  获取锁等待时间
     * @param leaseTime 获取到的锁，超时释放时间
     * @param lockKey   锁的key
     * @param function  执行函数
     * @param t         入参
     * @return function 函数执行的返回参数
     */
    <Param, Result> Result executeWithLock(long waitTime, long leaseTime, String lockKey, Function<Param, Result> function, Param t);

}