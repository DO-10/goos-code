package integration.auctionsniper;
import auctionsniper.SniperState;
import auctionsniper.util.Defect;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SniperStateTest {

    @Test
    public void isWonWhenAuctionClosesWhileWinning() {
        // 当处于 WINNING 状态时收到关闭事件，状态应转为 WON
        assertEquals(SniperState.WON, SniperState.WINNING.whenAuctionClosed());
    }

    @Test
    public void isLostWhenAuctionClosesWhileJoining() {
        // 当处于 JOINING 状态时收到关闭事件，状态应转为 LOST
        assertEquals(SniperState.LOST, SniperState.JOINING.whenAuctionClosed());
    }

    @Test
    public void isLostWhenAuctionClosesWhileBidding() {
        // 当处于 BIDDING 状态时收到关闭事件，状态应转为 LOST
        assertEquals(SniperState.LOST, SniperState.BIDDING.whenAuctionClosed());
    }

    @Test
    public void isLostWhenAuctionClosesWhileLosing() {
        // 当处于 LOSING 状态时收到关闭事件，状态应转为 LOST
        assertEquals(SniperState.LOST, SniperState.LOSING.whenAuctionClosed());
    }

    @Test(expected = Defect.class)
    public void throwsDefectIfAuctionClosesWhenAlreadyWon() {
        // 已经在 WON 状态，再收到关闭事件应抛出异常
        SniperState.WON.whenAuctionClosed();
    }

    @Test(expected = Defect.class)
    public void throwsDefectIfAuctionClosesWhenAlreadyLost() {
        // 已经在 LOST 状态，再收到关闭事件应抛出异常
        SniperState.LOST.whenAuctionClosed();
    }
}
