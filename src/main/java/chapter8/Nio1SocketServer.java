package chapter8;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
/**
 * author:lzq
 * */
public class Nio1SocketServer {

	public static final String IP = "127.0.0.1";

	public static final int POST = 5555;

	public static void main(String[] args) {
		ByteBuffer bytes = ByteBuffer.allocateDirect(1024);
		Charset charset = Charset.defaultCharset();
		CharsetDecoder decode = charset.newDecoder();
		CharBuffer charbuff = CharBuffer.allocate(4 * 1024);
		try {
			ServerSocketChannel serverChannel = ServerSocketChannel.open();

			if (serverChannel.isOpen()) {
				serverChannel.configureBlocking(true);
				serverChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
				serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
				serverChannel.bind(new InetSocketAddress(IP, POST));

				System.out.println("等待连接中...");
				while (true) {
					SocketChannel channel = serverChannel.accept();
					System.out.println("连接服务器地址:" + channel.getRemoteAddress());
					if (channel.read(bytes) != -1) {
						System.out.println("接收到了数据");
						bytes.flip();    //开启读模式，如果不掉用这个方法就读不出数据
						charbuff = decode.decode(bytes);
						System.out.println(charbuff.toString());

						if (bytes.hasRemaining()) {
							bytes.compact();
						} else {
							bytes.clear();
						}
						
						channel.write(bytes);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
