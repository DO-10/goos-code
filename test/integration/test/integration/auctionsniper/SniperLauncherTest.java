package integration.auctionsniper;
import auctionsniper.*;
import auctionsniper.UserRequestListener.Item;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

/**
 * 测试计划：竞拍者加入一次拍卖
 * 核心对象：SniperLauncher
 * 目的：验证 Launcher 接收到 UI 请求后，是否正确执行了“创建狙击手”和“加入拍卖”的一系列动作。
 */
public class SniperLauncherTest {
    // 1. 准备阶段 (Arrange): 创建模拟对象（Mocks）
    // 模拟拍卖行，用于获取拍卖连接
    private final AuctionHouse auctionHouse = mock(AuctionHouse.class);
    // 模拟狙击手收集器，用于存放新创建的狙击手
    private final SniperCollector collector = mock(SniperCollector.class);
    // 模拟拍卖连接，用于验证是否发出了 join 指令
    private final Auction auction = mock(Auction.class);

    // 被测对象
    private final SniperLauncher launcher = new SniperLauncher(auctionHouse, collector);

    @Test
    public void testJoinsAuctionAndAddsSniperToCollector() {
        // 准备测试数据：一个包含 ID 和最高价的项目
        final String itemId = "item-id-123";
        final Item item = new Item(itemId, 1000);

        // 设置模拟行为：当询问该项目的拍卖时，返回我们准备好的 mock 拍卖对象
        when(auctionHouse.auctionFor(item)).thenReturn(auction);

        // 2. 执行阶段 (Act): 模拟 UI 触发了加入请求
        // 这行代码会触发 SniperLauncher 内部的一系列逻辑：
        // 1. 获取 Auction -> 2. 创建 AuctionSniper -> 3. 注册监听 -> 4. 加入 Collector -> 5. 发出 Join
        launcher.joinAuction(item);

        // 3. 验证阶段 (Assert): 确保业务逻辑按预期顺序执行

        // 验证步骤 A: 是否向服务器发送了正式的加入请求
        verify(auction).join();

        // 验证步骤 B: 是否将新创建的狙击手添加到了收集器（Portfolio）中
        // 这里使用 any() 因为 AuctionSniper 是在 joinAuction 方法内部动态创建的
        verify(collector).addSniper(any(AuctionSniper.class));

        // 验证步骤 C: 验证狙击手是否被注册为拍卖的监听器
        // 这一步确保了拍卖的价格变动能传达到狙击手手中
        verify(auction).addAuctionEventListener(any(AuctionEventListener.class));
    }
}
