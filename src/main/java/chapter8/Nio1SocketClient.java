package chapter8;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
/**
 * author：lzq
 * */
public class Nio1SocketClient {

	public static final String IP = "127.0.0.1";

	public static final int POST = 5555;

	public static void main(String[] args) {
		ByteBuffer bytes = ByteBuffer.allocateDirect(4 * 1024);
		ByteBuffer hellobyte = ByteBuffer.wrap("hello".getBytes());
		Charset charset = Charset.defaultCharset();
		CharsetDecoder decode = charset.newDecoder();
		CharBuffer charbuff = CharBuffer.allocate(4 * 1024);
		try {
			SocketChannel channel = SocketChannel.open();
			channel.configureBlocking(true);
			channel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
			channel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);
			channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
			channel.setOption(StandardSocketOptions.SO_LINGER, 1);
			channel.connect(new InetSocketAddress(IP, POST));

			if (channel.isConnected()) {
				System.out.println("已连接");
				channel.write(hellobyte);

				if (channel.read(bytes) != -1) {
					bytes.flip();  //开启读模式，如果不掉用这个方法就读不出数据
					charbuff = decode.decode(bytes);
					
					System.out.println("client+=================="+charbuff);					
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
