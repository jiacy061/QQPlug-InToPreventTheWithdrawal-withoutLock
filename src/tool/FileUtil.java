package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileUtil {
	
	public static String formatPath(String path) {
//		System.out.println("before formatting:" + path);
		return path.replaceAll("\\\\", "//");
//		System.out.println("after formatting:" + path);
	}
	
	// 拷贝文件
	 public static void copyFile(String oldFullPath, String newFullPath) throws IOException {
//			long time = new Date().getTime();
			File f1 = new File(oldFullPath);
			File f2 = new File(newFullPath);
			int length = 2097152;
			FileInputStream in = new FileInputStream(f1);
			FileOutputStream out = new FileOutputStream(f2);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			ByteBuffer b = null;
			
			while (true) {
				if (inC.position() == inC.size())	break;

				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				} else {
					length = 2097152;
				}
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
			
			inC.close();
			outC.close();
			in.close();
			out.close();
			System.out.println("copyFile is finished!");
//			System.out.println(new Date().getTime() - time);
		 }
	 
	public static void sortByLength(File[] fs) {
		List<File> files = Arrays.asList(fs);
		Collections.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.length() - f2.length();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;
			}

			public boolean equals(Object obj) {
				return true;
			}
		});
//		for (File f : files) {
//			if (f.isDirectory())
//				continue;
//			System.out.println(f.getName() + ":" + f.length());
//		}
	}

	// 按照文件名称排序
	public static void sortByName(File[] fs) {
		List<File> files = Arrays.asList(fs);
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});
//		for (File f : files) {
//			System.out.println(f.getName());
//		}
	}

	// 按日期排序（从新到旧）
	public static void sortByDate(File[] fs) {
		Arrays.sort(fs, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return -1;
				else if (diff == 0)
					return 0;
				else
					return 1;
			}

			public boolean equals(Object obj) {
				return true;
			}
		});
//		for (int i = 0; i < fs.length; i++) {
//			System.out.println(fs[i].getName());
//			System.out.println(new Date(fs[i].lastModified()));
//		}
	}

}
