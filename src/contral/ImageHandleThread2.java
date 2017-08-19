package contral;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.ConfigBag;
import model.ImageBag;
import tool.ContralUtil;
import tool.FileUtil;

public class ImageHandleThread2 extends Thread {
	
	private ImageHandleUnit imageHandleUnit;
	private String fromPath;
	private File file;
	private File[] imageFiles;
	private boolean runFlag;
	private final int flushInterval = 1000; // 刷新间隔1000毫秒
	private final int maxHoldTime = (int) (2.5 * 60 * 1000); // 最长保留两分半钟
	
	public ImageHandleThread2(ImageHandleUnit imageHandleUnit, String path) {
		this.imageHandleUnit = imageHandleUnit;
		fromPath = path.substring(path.lastIndexOf("//"));
		file = new File(path);
		runFlag = true;
	}

	@Override
	public void run() {
		System.out.println("Thread-" + this.getId() + " start!");
		while(runFlag) {
			try {
			Long timeMillis = System.currentTimeMillis();
//			System.out.println("Thread-" + this.getId() + " running!");
			imageFiles = file.listFiles();
			FileUtil.sortByDate(imageFiles);
			saveSatisfactoryFile(timeMillis);
//			System.out.println("*****current time:" + timeMillis);
			checkAndDeleteUnqualifiedFile(timeMillis);
			checkAndChangeWithdrawnFile(timeMillis);
			Thread.sleep(flushInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkAndChangeWithdrawnFile(Long timeMillis) {
		ArrayList<ImageBag> list = imageHandleUnit.getPartImageList(fromPath);
//		System.out.println("checkAndChange list size:"+list.size());
		for(ImageBag imageBag : list) {
			if(imageBag.isWithdrawal())
				continue;
			
			boolean findFlag = false;
			Long timeDiff = timeMillis-file.lastModified();
//			System.out.println(this.getId()+"***list len:" + list.size()+"***imageFiles len:"+imageFiles.length);
			String fileName = imageBag.getImageName();
//			System.out.println("f1 name:"+fileName);
			for(File file : imageFiles) {
//				System.out.println("f2 name:"+file.getName());
				if( timeDiff<maxHoldTime && file.getName().equals(fileName)) {
//					System.out.println("找到未撤回的图片！");
					findFlag = true;
					break;
				} else if( timeDiff>maxHoldTime ) {
					findFlag = true;
					break;
				}
			}
			if(!findFlag) {
				imageBag.setWithdrawal(true);
//				imageHandleUnit.removeImageFileFromList(imageBag);
				System.out.println("**图片撤回提醒！");
			}
		}
	}

	private void checkAndDeleteUnqualifiedFile(Long timeMillis) {
		imageHandleUnit.deleteUnqualifiedFile(timeMillis);
	}

	private void saveSatisfactoryFile(Long timeMillis) throws IOException {
		for(File file : imageFiles) {
//			System.out.println("file time:" + file.lastModified());
//			System.out.println(ConfigBag.getSavePath()+file.getName());
			if( (timeMillis-file.lastModified()) < 150000 && 
					!imageHandleUnit.isImageFileInList(file.getName())) {
				System.out.println(file.getName() + " is new one!");
				String newFilePath = ConfigBag.getSavePath()+"//"+file.getName();
				FileUtil.copyFile(file.getAbsolutePath(), newFilePath);
				imageHandleUnit.addImageFileToList(new File(newFilePath), fromPath);
				ContralUtil.showNewFileTip();
			} else {
//				System.out.println("file is created before 2 minutes ago!");
				break;
			}
		}
	}

	public void end() {
		runFlag = false;
	}
	
}
