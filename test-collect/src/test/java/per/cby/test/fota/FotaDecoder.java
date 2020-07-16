package per.cby.test.fota;

import java.time.LocalDateTime;
import java.util.List;

import per.cby.frame.common.exception.BusinessAssert;
import per.cby.frame.common.util.CodeUtil;
import per.cby.terminal.constant.FotaConstant;
import per.cby.terminal.constant.FotaIssuedOrder;
import per.cby.terminal.model.FotaIssued;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月23日
 *
 */
public class FotaDecoder extends ByteToMessageDecoder implements FotaConstant {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 校验报文头尾标识
        int head = in.readUnsignedShort();
        ByteBuf buf = in.readBytes(in.readableBytes() - FOTA_FLAG_LENGTH);
        int tail = in.readUnsignedShort();
        BusinessAssert.isTrue(head == FOTA_HEAD && tail == FOTA_TAIL, "报文头尾标识错误！");

        // 验证数据校验和
        byte[] bytes = new byte[buf.readableBytes() - CHECKSUM_SIZE];
        buf.readBytes(bytes);
        byte[] checksumByte = new byte[CHECKSUM_SIZE];
        buf.readBytes(checksumByte);
        int checksum = CodeUtil.bytesToInt(checksumByte);
        BusinessAssert.isTrue(CodeUtil.verifyChecksum(checksum, bytes), "数据校验有误！");
        buf = Unpooled.copiedBuffer(bytes);

        // 解析协议版本
        int version = buf.readUnsignedByte();

        // 解析指令
        FotaIssuedOrder order = FotaIssuedOrder.value(buf.readUnsignedByte());

        // 封装下发信息
        FotaIssued fotaIssued = new FotaIssued();
        fotaIssued.setVersion(version);
        fotaIssued.setTimestamp(LocalDateTime.now());
        fotaIssued.setOrder(order);

        // 解析载荷
        int length = buf.readUnsignedShort();
        if (length > 0) {
            BusinessAssert.isTrue(buf.readableBytes() >= length, "载荷数据长度有误！");
            buf = ByteBufUtil.readBytes(ByteBufAllocator.DEFAULT, buf, length);
            if (FotaIssuedOrder.ISSUED_MATE.equals(order)) {
                BusinessAssert.isTrue(length >= 10, "下发元信息报文低于最小限制！");
                int versionSerial = buf.readUnsignedShort();
                int dataLength = (int) buf.readUnsignedInt();
                int shardSize = buf.readUnsignedShort();
                int shardNum = buf.readUnsignedShort();
                fotaIssued.setVersionSerial(versionSerial);
                fotaIssued.setDataLength(dataLength);
                fotaIssued.setShardSize(shardSize);
                fotaIssued.setShardNum(shardNum);
            } else if (FotaIssuedOrder.ISSUED_SHARD.equals(order)) {
                BusinessAssert.isTrue(length > 6, "下发分片数据报文低于最小限制！");
                int versionSerial = buf.readUnsignedShort();
                int shardSerial = buf.readUnsignedShort();
                int shardSize = buf.readUnsignedShort();
                byte[] payload = ByteBufUtil.getBytes(buf, buf.readerIndex(), shardSize, false);
                fotaIssued.setVersionSerial(versionSerial);
                fotaIssued.setShardSerial(shardSerial);
                fotaIssued.setShardSize(shardSize);
                fotaIssued.setPayload(payload);
            }
        }
        out.add(fotaIssued);
    }

}
