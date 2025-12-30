package integration.auctionsniper.xmpp;


import auctionsniper.xmpp.LoggingXMPPFailureReporter;
import org.junit.Test;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;

public class LoggingXMPPFailureReporterTest {
    // 1. 创建一个 Mock 的 Logger
    private final Logger logger = mock(Logger.class);
    // 2. 将 Mock Logger 注入到被测类中
    private final LoggingXMPPFailureReporter reporter = new LoggingXMPPFailureReporter(logger);

    @Test
    public void writesMessageToLogWhenFailureOccurs() {
        //  When: 触发失败报告动作
        String auctionId = "auction-id-54321";
        String failedMessage = "badly formatted message";
        Exception exception = new Exception("something went wrong");

        // 3. 执行动作
        reporter.cannotTranslateMessage(auctionId, failedMessage, exception);

        // 4. 验证行为：
        // 验证 logger.severe() 被调用，且传入的参数符合 MESSAGE_FORMAT 的预期结果
        verify(logger).severe("<auction-id-54321> Could not translate message \"badly formatted message\" because \"java.lang.Exception: something went wrong\"");
    }
}