package per.cby.test.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月19日
 *
 */
public class BizHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        String ack = "success";
        byte[] bytes = ack.getBytes();
        ByteBuf buf = Unpooled.copiedBuffer(bytes);
        ctx.channel().writeAndFlush(buf);
    }

}
