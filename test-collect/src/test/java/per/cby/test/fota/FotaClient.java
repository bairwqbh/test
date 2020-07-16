package per.cby.test.fota;

import per.cby.collect.common.socket.AbstractSocketClient;
import per.cby.collect.common.socket.CommonHandler;

import io.netty.channel.ChannelPipeline;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月24日
 *
 */
public class FotaClient extends AbstractSocketClient {

    public FotaClient() {
        super(FotaMain.HOST, FotaMain.PORT);
    }

    public FotaClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void pipeline(ChannelPipeline pipeline) {
        pipeline.addLast(new FotaDecoder());
        pipeline.addLast(new FotaHandler());
        pipeline.addLast(new FotaEncoder());
        pipeline.addLast(new CommonHandler());
    }

}
