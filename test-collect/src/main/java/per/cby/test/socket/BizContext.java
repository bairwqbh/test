package per.cby.test.socket;

import java.util.Map;

import per.cby.frame.common.util.BaseUtil;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.experimental.UtilityClass;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月19日
 *
 */
@UtilityClass
public class BizContext {

    public final Map<String, Object> snMap = BaseUtil.newHashMap("abc123", "我在");

    public final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
