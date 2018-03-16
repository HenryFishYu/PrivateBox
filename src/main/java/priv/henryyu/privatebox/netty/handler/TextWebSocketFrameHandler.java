package priv.henryyu.privatebox.netty.handler;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import priv.henryyu.privatebox.entity.Chat;
import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.jms.Producer;
import priv.henryyu.privatebox.siglton.Siglton;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年3月14日下午2:10:02
 * @version 1.0.0
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private Producer producer;
	private User user;
	private ObjectMapper mapper = new ObjectMapper();
	private Destination destination = new ActiveMQQueue("groupChat.queue");
	
	
	public TextWebSocketFrameHandler(Producer producer) {
		super();
		this.producer = producer;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception { // (1)
		Channel incoming = ctx.channel();
		if (msg.text().startsWith("UUID:")) {
			String UUID = msg.text().substring(5, msg.text().length());
			user = Siglton.INSTANCE.getUserUUIDMapMap().get(UUID);
			if (user == null) {
				Chat chat = new Chat(incoming.remoteAddress().toString(), msg.text(), null, null, "Illegal UserUUID");
				incoming.writeAndFlush("Server Error");
				ctx.close();
				producer.sendMessage(destination, chat);
				return;
			}
			Chat chat = new Chat(incoming.remoteAddress().toString(), msg.text(), user.getName(), null,
					"Start GroupChat");
			channels.writeAndFlush(new TextWebSocketFrame("[" + user.getName() + "]" + "加入聊天"));
			producer.sendMessage(destination, chat);
			return;
		}
		Chat chat = new Chat(incoming.remoteAddress().toString(), msg.text(), user.getName(), null, "GroupChat");
		channels.writeAndFlush(new TextWebSocketFrame("[" + user.getName() + "]" + msg.text()));
		producer.sendMessage(destination, chat);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		Channel incoming = ctx.channel();
		System.out.println(ctx.name());
		// Broadcast a message to multiple Channels
		channels.add(incoming);
		//channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
		Chat chat = new Chat(incoming.remoteAddress().toString(), null, null, null, "GroupChat handlerAdded");
		producer.sendMessage(destination, chat);
		System.out.println("Client:" + incoming.remoteAddress() + "加入");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel incoming = ctx.channel();
		// Broadcast a message to multiple Channels
		//channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress().toString() + " 离开"));
		Chat chat = new Chat(incoming.remoteAddress().toString(), null, null, null, "GroupChat handlerRemoved");
		producer.sendMessage(destination, chat);
		System.out.println("Client:" + incoming.remoteAddress().toString() + "离开");
		
		// A closed Channel is automatically removed from ChannelGroup,
		// so there is no need to do "channels.remove(ctx.channel());"
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		//channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + "在线"));
		Chat chat = new Chat(incoming.remoteAddress().toString(), null, incoming.remoteAddress().toString(), null, "GroupChat channelActive");
		producer.sendMessage(destination, chat);
		System.out.println("Client:" + incoming.remoteAddress() + "在线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + user.getName() + "离线"));
		Chat chat = new Chat(incoming.remoteAddress().toString(), null, user.getName(), null, "GroupChat channelInactive");
		producer.sendMessage(destination, chat);
		System.out.println("Client:" + user.getName() + "离线");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + user.getName() + "异常");
		// 当出现异常就关闭连接
		Chat chat = new Chat(incoming.remoteAddress().toString(), cause.getMessage(), user.getName(), null, "GroupChat exceptionCaught");
		producer.sendMessage(destination, chat);
		cause.printStackTrace();
		ctx.close();
	}

}
