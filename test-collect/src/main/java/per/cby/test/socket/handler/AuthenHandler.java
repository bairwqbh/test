package per.cby.test.socket.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import per.cby.frame.common.exception.BusinessAssert;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月19日
 *
 */
public class AuthenHandler extends ChannelInboundHandlerAdapter {

    public static final Map<String, Boolean> SN_MAP = new ConcurrentHashMap<String, Boolean>() {

        private static final long serialVersionUID = 1L;

        {
            put("abc123", true);
        }
    };

    public static final Map<String, String> CHANNEL_MAP = new ConcurrentHashMap<String, String>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        if (CHANNEL_MAP.containsKey(channelId)) {
            super.channelRead(ctx, msg);
        } else {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String sn = new String(bytes);
            BusinessAssert.isTrue(SN_MAP.containsKey(sn), "错误的认证信息:" + sn);
            CHANNEL_MAP.put(channelId, sn);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        CHANNEL_MAP.remove(channelId);
    }

}
