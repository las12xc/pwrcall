package itsecnetback;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import org.msgpack.Unpacker;
import org.msgpack.MessagePackObject;

public class MessagePackStreamDecoder extends FrameDecoder {
	protected Unpacker pac = new Unpacker();

	public MessagePackStreamDecoder() {
		super();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer source) throws Exception {
		ByteBuffer buffer = source.toByteBuffer();
		if(!buffer.hasRemaining())
			return null;

		byte[] bytes = buffer.array();
		int offset = buffer.arrayOffset() + buffer.position();
		int length = buffer.arrayOffset() + buffer.limit();

		int noffset = pac.execute(bytes, offset, length);
		if(noffset > offset)
			source.skipBytes(noffset - offset);

		if(pac.isFinished()) {
			MessagePackObject msg = pac.getData();
			pac.reset();
			return msg;
		} else
			return null;
	}
}
