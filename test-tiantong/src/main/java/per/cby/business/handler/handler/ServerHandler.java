package per.cby.business.handler.handler;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务器处理程序
 * 
 * @author chenboyang
 *
 */
@Slf4j
@Sharable
@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServerHandler extends SimpleChannelInboundHandler<byte[]> {

    private AtomicLong counter = new AtomicLong();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        StringBuilder sb = new StringBuilder();
        int length = msg.length;
        sb.append("[");
        for (int i = 0; i < length; i++) {
            sb.append(msg[i] & 0xFF).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        log.info("receive-data-[{}][{}][{}] : {}", counter.incrementAndGet(), getIp(ctx), length, sb.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (IdleState.ALL_IDLE.equals(((IdleStateEvent) evt).state())) {
                ctx.close();
                log.warn("客户端[{}]连接空闲超时，连接已被回收！", getIp(ctx));
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 获取客户端IP
     * 
     * @param ctx 通道处理上下文
     * @return IP地址
     */
    private String getIp(ChannelHandlerContext ctx) {
        return ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
    }

}
