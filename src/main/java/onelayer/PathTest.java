package onelayer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {
	public static void main(String[] args) {
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK/英语语法大全.pdf");
		System.out.println("uri:"+p.toUri());
		System.out.println("filename:"+p.getFileName());
		System.out.println("parent:"+p.getParent());
		System.out.println("root:"+p.getRoot());
		System.out.println("filesystem:"+p.getFileSystem());
		File file = p.toFile();
		System.out.println("file:"+p.toFile());
	}
}
