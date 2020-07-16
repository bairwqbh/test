package per.cby.test.fota;

import per.cby.terminal.constant.FotaConstant;
import per.cby.terminal.constant.FotaReportOrder;
import per.cby.terminal.model.FotaReport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月25日
 *
 */
public class FotaEncoder extends MessageToByteEncoder<FotaReport> implements FotaConstant {


    @Override
    protected void encode(ChannelHandlerContext ctx, FotaReport msg, ByteBuf out) throws Exception {
        byte[] data = FotaMain.FOTA_PROTOCOL_SERVICE.protocolWrap(buf -> {
            FotaReportOrder order = msg.getOrder();
            buf.writeByte(order.getData());
            int length = 0;
            ByteBuf payload = null;
            if (FotaReportOrder.GET_SHARD.equals(order)) {
                length = GET_SHARD_LIMIT;
                payload = Unpooled.buffer();
                payload.writeShort(msg.getVersionSerial().shortValue());
                payload.writeShort(msg.getShardSerial().shortValue());
            }
            buf.writeShort(length);
            if (payload != null) {
                buf.writeBytes(payload);
            }
        });
        out.writeBytes(data);
    }

}
