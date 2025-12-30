package integration.auctionsniper;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperPortfolio;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class SniperPortfolioTest {
    // 1. 创建 Mock 监听者
    private final SniperPortfolio.PortfolioListener listener = mock(SniperPortfolio.PortfolioListener.class);
    // 2. 创建被测对象
    private final SniperPortfolio portfolio = new SniperPortfolio();

    @Test
    public void notifiesListenersOfNewSniper() {
        // 创建一个 Mock 的 Sniper 对象作为参数
        AuctionSniper sniper = mock(AuctionSniper.class);

        // 注册监听者
        portfolio.addPortfolioListener(listener);

        // 3. 执行动作：添加 Sniper
        portfolio.addSniper(sniper);

        // 4. 验证行为：
        // 验证 Announcer 是否正确调用了 listener 的 sniperAdded 方法
        verify(listener).sniperAdded(sniper);
    }
}