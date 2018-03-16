package priv.henryyu.privatebox.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import priv.henryyu.privatebox.netty.initializer.WebsocketChatServerInitializer;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年3月14日下午2:12:35
 * @version 1.0.0
 */
@Component
public class WebsocketChatServer {
	@Autowired
	private WebsocketChatServerInitializer websocketChatServerInitializer;

    public void run() throws Exception {
        
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(websocketChatServerInitializer)  //(4)
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            
    		System.out.println("WebsocketChatServer 启动了");
    		
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(9080).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            
    		System.out.println("WebsocketChatServer 关闭了");
        }
    }

}
 

