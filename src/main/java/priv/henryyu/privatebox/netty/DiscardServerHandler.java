package priv.henryyu.privatebox.netty; 
import java.nio.charset.Charset;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年3月14日下午3:18:18
 * @version 1.0.0
 */
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // 默默地丢弃收到的数据
    	ByteBuf in = (ByteBuf) msg;
    	System.out.println(in.toString(io.netty.util.CharsetUtil.UTF_8));

    	ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }*/
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(1); // (2)
        time.writeBytes("122232222".getBytes());

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(ChannelFutureListener.CLOSE); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
 

