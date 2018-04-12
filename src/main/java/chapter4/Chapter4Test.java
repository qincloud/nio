package chapter4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.MessageDigest;
import java.util.Set;

import org.junit.Test;

public class Chapter4Test {
	/**
	 * 文件存在
	 */
	@Test
	public void existsTest() {
		Path path = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		boolean path_exists = Files.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		System.out.println(path_exists);
	}

	/**
	 * 文件不存在
	 */
	@Test
	public void notExistsTest() {
		Path path = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		boolean path_exists = Files.notExists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		System.out.println(path_exists);
	}

	/**
	 * 文件是否可以访问
	 */
	@Test
	public void accessFile() {
		Path path = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		boolean is_readable = Files.isReadable(path);
		boolean is_writable = Files.isReadable(path);
		boolean is_executable = Files.isReadable(path);
		boolean is_regular = Files.isReadable(path);
		if ((is_readable) && (is_writable) && (is_executable) && (is_regular)) {
			System.out.println("可以访问");
		} else {
			System.out.println("不可以访问");
		}
	}

	/**
	 * 文件路径是否相同比较
	 */
	@Test
	public void sameFile() {
		Path path_1 = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		Path path_2 = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "英语语法大全1.pdf");
		Path path_3 = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "primeton20171209.sql");
		try {
			boolean is_same_file12 = Files.isSameFile(path_1, path_2); // 比较path1和path2
			boolean is_same_file13 = Files.isSameFile(path_1, path_3); // 比较path1和path3
			boolean is_same_file23 = Files.isSameFile(path_2, path_3); // 比较path2和path3
			System.out.println(is_same_file12);
			System.out.println(is_same_file13);
			System.out.println(is_same_file23);
		} catch (Exception e) {

		}
	}

	/**
	 * 比较两个文件是否是一样的
	 */
	@Test
	public void comparaFile() {
		Path path_1 = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "英语语法大全.pdf");
		Path path_2 = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK", "英语语法大全1.pdf");
		File file1 = path_1.toFile();
		File file2 = path_2.toFile();
		String MD5one = getFileMD5(file1);
		String MD5two = getFileMD5(file2);
		System.out.println(MD5one);
		System.out.println(MD5two);
		if (MD5one.equals(MD5two)) { // 根据md5码判断是不是同一个文件
			System.out.println("是同一个文件");
		} else {
			System.out.println("不是同一个文件");
		}
	}

	/**
	 * @dscription:获取文件的md5码
	 * @author:lzq996298643
	 * @param:file 文件
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[8192];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获得文件根目录
	 * 
	 * @author:liaozq
	 */
	@Test
	public void RootDectionaryTest() {
		Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();
		for (Path p : paths) {
			System.out.println(p);
		}
	}

	/**
	 * 创建一个文件夹
	 */
	@Test
	public void createDictionary() {
		Path path = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK2");
		boolean path_exists = Files.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		if (!path_exists) {
			Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
			FileAttribute<Set<PosixFilePermission>> posixpermissions = PosixFilePermissions
					.asFileAttribute(permissions);
			try {
				Files.createDirectory(path, posixpermissions);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(path_exists);
	}

	/**
	 * 列出一个文件夹指定的所有文件
	 */
	@Test
	public void dictionaryFile() {
		Path path = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK");
		DirectoryStream.Filter<Path> ds_filter = new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path path) throws IOException {
				String filename = path.toFile().getName();
				if (filename.endsWith(".pdf") || Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
					return true;
				}
				return false;
			}
		};
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path,ds_filter)) {
			for (Path p : ds) {
				System.out.println(p.getFileName());
			}
		} catch (Exception e) {

		}
	}

}
