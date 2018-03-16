package priv.henryyu.privatebox.netty.initializer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import priv.henryyu.privatebox.jms.Producer;
import priv.henryyu.privatebox.netty.handler.HttpRequestHandler;
import priv.henryyu.privatebox.netty.handler.TextWebSocketFrameHandler;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年3月14日下午2:11:39
 * @version 1.0.0
 */
@Component
public class WebsocketChatServerInitializer extends ChannelInitializer<SocketChannel> { // 1
	@Autowired
	private Producer producer;
	@Override
	public void initChannel(SocketChannel ch) throws Exception {// 2
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		pipeline.addLast(new ChunkedWriteHandler());
		//pipeline.addLast(new HttpRequestHandler("/groupChat"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/groupChat"));
		pipeline.addLast(new TextWebSocketFrameHandler(producer));

	}
}
 

