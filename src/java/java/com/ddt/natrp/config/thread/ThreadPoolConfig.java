package com.ddt.natrp.config.thread;

import com.example.demo.utils.Threads;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 线程池配置
 */
@Configuration
public class ThreadPoolConfig {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    /** 核心线程池大小 */
    private final int corePoolSize = 8;

    /** 最大可创建的线程数 */
    private final int maxPoolSize = 16;

    /** 队列最大长度 */
    private final int queueCapacity = 200;

    /** 线程池维护线程所允许的空闲时间 */
    private final int keepAliveSeconds = 120;

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor()
    {
        // 创建线程池
        logger.info("taskExecutor pool start");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService()
    {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build())
        {
            @Override
            protected void afterExecute(Runnable r, Throwable t)
            {
                Threads.printException(r, t);
            }
        };
    }
}
