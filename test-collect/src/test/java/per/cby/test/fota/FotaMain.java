package per.cby.test.fota;

import java.nio.charset.StandardCharsets;

import per.cby.terminal.constant.FotaReportOrder;
import per.cby.terminal.model.FotaReport;
import per.cby.terminal.service.FotaProtocolService;
import per.cby.terminal.service.impl.SimpleFotaProtocolService;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月24日
 *
 */
@Slf4j
public class FotaMain {

    static final String HOST = "iot.ecsat.cn";

    static final int PORT = 20011;

    static final String TERMINAL_ID = "0101132001090003";

    static final FotaProtocolService FOTA_PROTOCOL_SERVICE = new SimpleFotaProtocolService();

    static final String FILE_PATH = "C:/Users/Dell/Desktop/fota.bin";

    public static void main(String[] args) throws InterruptedException {
        FotaClient fotaClient = new FotaClient(HOST, PORT);
        fotaClient.start();
        ChannelFuture future = fotaClient.connect();
        future.channel().writeAndFlush(Unpooled.copiedBuffer(TERMINAL_ID.getBytes(StandardCharsets.ISO_8859_1)))
                .addListener((ChannelFuture f) -> {
                    if (f.isSuccess()) {
                        getMate(future);
                    }
                });
        future.channel().closeFuture().sync();
    }

    private static void getMate(ChannelFuture future) {
        FotaReportOrder order = FotaReportOrder.GET_MATE;
        FotaReport report = new FotaReport();
        report.setOrder(order);
        future.channel().writeAndFlush(report);
        log.debug("发送 - {} ", order.getDesc());
    }

}
