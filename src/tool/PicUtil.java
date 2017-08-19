package tool;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

public class PicUtil {
	private int newWidth;
	private int maxWidth;
	private int maxHeight;
	private final int defaultWidth = 200;
	/**
	 * 压缩照片
	 */
	public PicUtil() {
		newWidth = defaultWidth;
		maxWidth = 0;
		maxHeight = 0;
	}
	
	public PicUtil(int maxWidth, int maxHeight) {
		this.newWidth = defaultWidth;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getNewWidth() {
		return newWidth;
	}
	
	public void setNewWidth(int new_Width) {
		newWidth = new_Width;
	}
	
	public void compressPhoto(String newFullPath) throws IOException {
		// 压缩处理
		File oldFile = new File(newFullPath);
		BufferedImage oldImage = ImageIO.read(oldFile);
		
		int srcWidth = oldImage.getWidth(null); // 得到文件原始宽度
		int srcHeight = oldImage.getHeight(null); // 得到文件原始高度
		double scaling; // 缩放比例
		int newHeight; // 按比例缩放高度
		
		if(maxHeight>0 && maxWidth>0) {
			if(srcHeight/srcWidth > maxHeight/maxWidth) {
				newHeight = maxHeight;
				scaling = (double) newHeight / srcHeight;
				newWidth = (int) (scaling * srcWidth);
			} else {
				newWidth = maxWidth;
				scaling = (double) newWidth / srcWidth;
				newHeight = (int) (scaling * srcHeight);
			}
		} else {
			scaling = (double) newWidth / srcWidth; // 缩放比例
			newHeight = (int) (srcHeight * scaling); // 按比例缩放高度
		}

		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		newImage.getGraphics().drawImage(oldImage.getScaledInstance(newWidth, newHeight, 
				Image.SCALE_SMOOTH), 0, 0, null);

		ImageIO.write(newImage, "jpg", new File(newFullPath));
	}
	
	public void compressPhoto(String oldFullPath, String newFullPath) throws IOException {
		// 压缩处理
		File oldFile = new File(oldFullPath);
		BufferedImage oldImage = ImageIO.read(oldFile);
		
		int srcWidth = oldImage.getWidth(null); // 得到文件原始宽度
		int srcHeight = oldImage.getHeight(null); // 得到文件原始高度
		double scaling; // 缩放比例
		int newHeight; // 按比例缩放高度
		
		if(maxHeight>0 && maxWidth>0) {
			if(srcHeight/srcWidth > maxHeight/maxWidth) {
				newHeight = maxHeight;
				scaling = (double) newHeight / srcHeight;
				newWidth = (int) (scaling * srcWidth);
			} else {
				newWidth = maxWidth;
				scaling = (double) newWidth / srcWidth;
				newHeight = (int) (scaling * srcHeight);
			}
		} else {
			scaling = (double) newWidth / srcWidth; // 缩放比例
			newHeight = (int) (srcHeight * scaling); // 按比例缩放高度
		}

		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		newImage.getGraphics().drawImage(oldImage.getScaledInstance(newWidth, newHeight, 
				Image.SCALE_SMOOTH), 0, 0, null);

		ImageIO.write(newImage, "jpg", new File(newFullPath));
	}
	
	public void copyPhoto(String oldFullPath, String newFullPath) throws IOException {
//		long time = new Date().getTime();
		// 拷贝图片
		File oldFile = new File(oldFullPath);
		BufferedImage oldImage = ImageIO.read(oldFile);

		int width = oldImage.getWidth(null); // 得到文件原始宽度
		int height = oldImage.getHeight(null); // 得到文件原始高度

		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		newImage.getGraphics().drawImage(
				oldImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,null);

		ImageIO.write(newImage, "jpg", new File(newFullPath));
//		System.out.println(new Date().getTime() - time);
	}
	
//	public static void main(String agrs[]) {
//		try {
//			new PicUtil(300,300)
//			.compressPhoto("D://Program Files//Java//Java_Learning//QQPlug-InToPreventTheWithdrawal//src//image//119852-106.jpg");
//			System.out.println("压缩完成");
////			PicUtil picUtil = new PicUtil();
////			picUtil.copyPhoto("C://Users//Jiacy-PC//Pictures//lovewallpaper//112.png", 
////					"C://Users//Jiacy-PC//Pictures//copy1.jpg");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
