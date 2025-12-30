
package integration.auctionsniper.xmpp;

import auctionsniper.xmpp.XMPPAuction;
import auctionsniper.xmpp.XMPPFailureReporter;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class XMPPAuctionTest {
    private final XMPPConnection connection = mock(XMPPConnection.class);
    private final Chat chat = mock(Chat.class);
    private final ChatManager chatManager = mock(ChatManager.class);
    private final XMPPFailureReporter failureReporter = mock(XMPPFailureReporter.class);
    private XMPPAuction auction;

    @Before
    public void setup() {
        // 模拟 Smack 的嵌套调用结构
        when(connection.getChatManager()).thenReturn(chatManager);
        when(chatManager.createChat(anyString(), any())).thenReturn(chat);

        auction = new XMPPAuction(connection, "auction-item-id", failureReporter);
    }

    @Test
    public void sendsJoinMessageToChat() throws Exception {
        auction.join();
        // 验证是否发送了正确的 JOIN 协议字符串
        verify(chat).sendMessage("SOLVersion: 1.1; Command: JOIN;");
    }

    @Test
    public void sendsBidMessageToChat() throws Exception {
        auction.bid(123);
        // 验证是否发送了带金额的 BID 协议字符串
        verify(chat).sendMessage("SOLVersion: 1.1; Command: BID; Price: 123;");
    }
}
