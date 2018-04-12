package onelayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

import org.junit.Test;

public class FsAttrTest {
	public static void main(String[] args) {
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK","英语语法大全.pdf");
		PosixFileAttributes posix = null;
		try {
			posix = Files.readAttributes(p, PosixFileAttributes.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * dos attr test
	 * */
	@Test
	public void dosAttr() {
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK","英语语法大全.pdf");
		DosFileAttributes dos = null;
		try {
			dos = Files.readAttributes(p, DosFileAttributes.class);
			System.out.println(dos.isReadOnly());
			System.out.println(dos.isArchive());
			System.out.println(dos.isHidden());
			System.out.println(dos.isSystem());
			//效果同上
			System.out.println(Files.getAttribute(p, "dos:hidden", LinkOption.NOFOLLOW_LINKS));
			System.out.println(Files.getAttribute(p, "dos:archive", LinkOption.NOFOLLOW_LINKS));
			System.out.println(Files.getAttribute(p, "dos:hidden", LinkOption.NOFOLLOW_LINKS));
			System.out.println(Files.getAttribute(p, "dos:system", LinkOption.NOFOLLOW_LINKS));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
