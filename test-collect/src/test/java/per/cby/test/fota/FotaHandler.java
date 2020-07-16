package per.cby.test.fota;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.util.JudgeUtil;
import per.cby.terminal.constant.FotaConstant;
import per.cby.terminal.constant.FotaIssuedOrder;
import per.cby.terminal.constant.FotaReportOrder;
import per.cby.terminal.model.FotaIssued;
import per.cby.terminal.model.FotaReport;

import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.slf4j.Slf4j;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月19日
 *
 */
@Slf4j
@Sharable
public class FotaHandler extends SimpleChannelInboundHandler<FotaIssued> implements FotaConstant {

    private int shardNum = 0;

    private static File file = null;
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FotaIssued msg) throws Exception {
        FotaIssuedOrder order = msg.getOrder();
        BusinessAssert.isTrue(JudgeUtil.isOneEqual(order, FotaIssuedOrder.ISSUED_MATE, FotaIssuedOrder.ISSUED_SHARD),
                order.getDesc());
        if (FotaIssuedOrder.ISSUED_MATE.equals(order)) {
            log.debug("收到 - {} 升级包序号：{}，升级包大小：{}，分片长度：{}，分片数量：{} ", order.getDesc(), msg.getVersionSerial(),
                    msg.getDataLength(), msg.getShardSize(), msg.getShardNum());
            shardNum = msg.getShardNum();
            BusinessAssert.isTrue(shardNum > 0, "分片数量为0！");
            file = new File(FotaMain.FILE_PATH);
            getShard(ctx, msg.getVersionSerial(), 1);
        } else if (FotaIssuedOrder.ISSUED_SHARD.equals(order)) {
            log.debug("收到 - {} 升级包序号：{}，分片序号：{}，分片长度：{}", order.getDesc(), msg.getVersionSerial(), msg.getShardSerial(),
                    msg.getShardSize());
            byte[] data = msg.getPayload();
            if (ArrayUtils.isNotEmpty(data) && file != null) {
                writeFile(data);
            }
            int shardSerial = msg.getShardSerial();
            if (shardSerial < shardNum) {
                getShard(ctx, msg.getVersionSerial(), shardSerial + 1);
            } else {
                over(ctx);
            }
        }
    }

    private void getShard(ChannelHandlerContext ctx, int versionSerial, int shardSerial) {
        FotaReport report = new FotaReport();
        report.setOrder(FotaReportOrder.GET_SHARD);
        report.setVersionSerial(versionSerial);
        report.setShardSerial(shardSerial);
        ctx.channel().writeAndFlush(report);
        log.debug("发送 - {} 升级包序号：{}，分片序号：{}", FotaReportOrder.GET_SHARD.getDesc(), versionSerial, shardSerial);
    }

    private void over(ChannelHandlerContext ctx) {
        FotaReport report = new FotaReport();
        report.setOrder(FotaReportOrder.OVER);
        ctx.channel().writeAndFlush(report);
        log.debug("发送 - {}", FotaReportOrder.OVER.getDesc());
    }

    private synchronized void writeFile(byte[] data) throws IOException {
        FileUtils.writeByteArrayToFile(file, data, true);
    }

}
