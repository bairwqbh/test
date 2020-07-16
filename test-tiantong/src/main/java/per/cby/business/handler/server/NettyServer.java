package per.cby.business.handler.server;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.cby.business.handler.handler.ServerHandler;
import per.cby.frame.common.config.properties.SysProperties;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty服务器
 * 
 * @author chenboyang
 *
 */
@Slf4j
@Component
public class NettyServer {

	/** 连接线程组 */
	private EventLoopGroup bossGroup;

	/** 业务处理线程组 */
	private EventLoopGroup workerGroup;

	/** 业务处理器 */
	@Autowired
	private ServerHandler serverHandler;

	/** 系统属性 */
	@Autowired
	private SysProperties sysProperties;

    /**
     * 启动服务器
     * 
     * @throws InterruptedException 异常
     */
	@PostConstruct
	public void start() throws InterruptedException {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			public void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS));
                pipeline.addLast(new ByteArrayDecoder());
                pipeline.addLast(serverHandler);
			}
		});
		bootstrap.bind(sysProperties.getPort()).sync();
		log.info("服务器已启动！");
	}

    /**
     * 服务器停止
     */
	@PreDestroy
	public void stop() {
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		log.info("服务器已停止！");
	}

}
