// 代码生成时间: 2025-09-23 15:38:53
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * 内存使用情况分析服务
 * 该服务提供了内存使用情况的分析，用于健康检查。
 */
@Readiness
@ApplicationScoped
public class MemoryUsageAnalysis implements HealthCheck {

    @Inject
    MemoryMXBean memoryMXBean;

    /**
     * 检查内存使用情况
     *
     * @return 健康检查响应，指示内存使用是否正常
     */
    @Override
    public HealthCheckResponse call() {
        try {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            long maxMemory = heapMemoryUsage.getMax();
            long usedMemory = heapMemoryUsage.getUsed();
            long threshold = calculateThreshold(maxMemory);

            if (usedMemory > threshold) {
                return HealthCheckResponse.named("MemoryCheck").
                        withData("usedMemory", usedMemory).
                        withData("maxMemory", maxMemory).
                        withData("threshold", threshold).
                        down();
            }
        } catch (Exception e) {
            // 日志记录异常，这里简单输出到控制台
            System.err.println("Failed to check memory usage: " + e.getMessage());
       }
        return HealthCheckResponse.up("MemoryCheck");
   }

    /**
     * 计算内存使用阈值
     * 这里简单地将阈值设置为最大内存的80%
     *
     * @param maxMemory 最大内存
     * @return 计算得到的阈值
     */
    private long calculateThreshold(long maxMemory) {
        return (long) (maxMemory * 0.8);
    }
}
