package onelayer;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Set;

import org.junit.Test;

public class FsTest {
	public static void main(String[] args) {
		FileSystem fs = FileSystems.getDefault();
		Set<String> views = fs.supportedFileAttributeViews();
		for (String view : views) {
			System.out.println(view);
		}
	}

	@Test
	public void testFileStore() {
		FileSystem fs = FileSystems.getDefault();
		for (FileStore store : fs.getFileStores()) {
			store.supportsFileAttributeView(BasicFileAttributeView.class);
			System.out.println(store.name() + ":");
		}
	}

	@Test
	public void pathFileStore() {
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		try {
			FileStore store = Files.getFileStore(p);
			boolean a = store.supportsFileAttributeView("basic");
			System.out.println(store.name() + ":" + a);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得文件参数
	 */
	@Test
	public void FileAttr() {
		BasicFileAttributes attr = null;
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		try {
			attr = Files.readAttributes(p, BasicFileAttributes.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("file size:"+attr.size());   //获得文件大小
		System.out.println("create time:"+attr.creationTime());   //获得文件创建时间
		System.out.println("last file access time:"+attr.lastAccessTime());   //获得文件最后访问时间
		System.out.println("last file modify time:"+attr.lastModifiedTime());   //获得文件最后修改时间
		
		System.out.println("is directory?:"+attr.isDirectory());      //是否文件夹
		System.out.println("is regular file?:"+attr.isRegularFile()); //是否有规则文件
		System.out.println("is symbolic link?:"+attr.isSymbolicLink());   //是否是链接
		System.out.println("is other?:"+attr.isOther());              //是否其它
	}
	
	/**
	 * get获得文件参数
	 * */
	@Test
	public void getFileAttr() {
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		try {
			long size = (Long)Files.getAttribute(p, "basic:size",LinkOption.NOFOLLOW_LINKS);
			System.out.println("file size:"+size);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 更新文件基础参数
	 * */
	@Test
	public void updateFileAttr() {
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		long millis = System.currentTimeMillis();
		FileTime filetime = FileTime.fromMillis(millis);
		try {
			/**
			 * setTimes() params:1.最后修改时间，2.最后访问时间,3.最后创建时间
			 * */
			Files.getFileAttributeView(p, BasicFileAttributeView.class).setTimes(filetime, filetime, filetime);;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 更新文件基础参数2
	 * */
	@Test
	public void updateFileAttr2() {
		Path p = Paths.get("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		long millis = System.currentTimeMillis();
		FileTime filetime = FileTime.fromMillis(millis);
		try {
			Files.setLastModifiedTime(p, filetime);
			Files.setAttribute(p, "basic:creationTime", filetime, LinkOption.NOFOLLOW_LINKS);
			FileTime createTime = (FileTime)Files.getAttribute(p, "basic:creationTime", LinkOption.NOFOLLOW_LINKS);
			System.out.println(createTime);
			Files.setAttribute(p, "basic:lastAccessTime", filetime, LinkOption.NOFOLLOW_LINKS);
			FileTime lastTime = (FileTime)Files.getAttribute(p, "basic:lastAccessTime", LinkOption.NOFOLLOW_LINKS);
			System.out.println(lastTime);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
