package integration.auctionsniper;
import auctionsniper.Auction;
import auctionsniper.AuctionEventListener.PriceSource;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;
import auctionsniper.UserRequestListener.Item;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * 黑盒测试：出价策略测试
 * 验证：当收到新的报价时，狙击手是否能正确计算并发送新的出价
 */
public class AuctionSniperTest {
    // 模拟拍卖连接，用于接收 bid 指令
    private final Auction auction = mock(Auction.class);
    // 模拟监听器，用于观察状态改变
    private final SniperListener sniperListener = mock(SniperListener.class);

    // 准备测试数据
    private final Item item = new Item("item-543", 1000);
    private final AuctionSniper sniper = new AuctionSniper(item, auction);

    @Test
    public void testReportsLostIfAuctionClosesImmediately() {
        // 注册监听器
        sniper.addSniperListener(sniperListener);

        // 场景：收到来自其他人的价格变动
        int price = 123;
        int increment = 45;
        int expectedBid = price + increment; // 168

        // 执行动作：触发 currentPrice 事件
        sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);

        // 验证结果：狙击手是否向拍卖行发送了正确的加价金额
        verify(auction).bid(expectedBid);
    }
}