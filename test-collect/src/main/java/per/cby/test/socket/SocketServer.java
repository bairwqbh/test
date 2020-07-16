package per.cby.test.socket;

import java.util.concurrent.TimeUnit;

import per.cby.collect.common.socket.AbstractSocketServer;
import per.cby.collect.common.socket.CommonHandler;
import per.cby.test.socket.handler.AuthenHandler;
import per.cby.test.socket.handler.BizDecode;
import per.cby.test.socket.handler.BizHandler;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
 * 
 * 
 * @author chenboyang
 * @since 2020年3月19日
 *
 */
//@Component
public class SocketServer extends AbstractSocketServer {

    public SocketServer() {
        super(20000);
    }

    @Override
    protected void pipeline(ChannelPipeline pipeline) {
        pipeline.addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS));
        pipeline.addLast(new AuthenHandler());
        pipeline.addLast(new BizDecode());
        pipeline.addLast(new BizHandler());
        pipeline.addLast(new CommonHandler());
    }

}
