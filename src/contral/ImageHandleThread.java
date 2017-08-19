package contral;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.ConfigBag;
import model.ImageBag;
import tool.ContralUtil;
import tool.FileUtil;

public class ImageHandleThread extends Thread {
	
	private ImageHandleUnit imageHandleUnit;
	private String fromPath;
	private File file;
	private File[] imageFiles;
	private long lastModifyTime;
	private boolean runFlag;
	private final int flushInterval = 100; // À¢–¬º‰∏Ù1000∫¡√Î
	
	public ImageHandleThread(ImageHandleUnit imageHandleUnit, String path) {
		this.imageHandleUnit = imageHandleUnit;
		fromPath = path.substring(path.lastIndexOf("//"));
		file = new File(path);
		lastModifyTime = 0;
		runFlag = true;
	}

	@Override
	public void run() {
		System.out.println("Thread-" + this.getId() + " start!");
		while(runFlag) {
			try {
			Long timeMillis = System.currentTimeMillis();
//			System.out.println("Thread-" + this.getId() + " running!");
			if( file.lastModified()!=lastModifyTime ) {
				System.out.println("**file.lastModified()!=lastModifyTime**");
				lastModifyTime = file.lastModified();
				imageFiles = file.listFiles();
				FileUtil.sortByDate(imageFiles);
				saveSatisfactoryFile(timeMillis);
//				System.out.println("*****current time:" + timeMillis);
				checkAndDeleteUnqualifiedFile(timeMillis);
				checkAndChangeWithdrawnFile(timeMillis);
			} else if( (timeMillis-lastModifyTime)>imageHandleUnit.minHoldTime && 
					(timeMillis-lastModifyTime)<(imageHandleUnit.maxHoldTime) ) {
//				System.out.println("**(timeMillis-lastModifyTime)>imageHandleUnit.maxHoldTime**"
//						+ "\nlastModifyTime="+lastModifyTime+";timeMillis="+timeMillis
//						+"≤Ó÷µ£∫"+(timeMillis-lastModifyTime));
				checkAndDeleteUnqualifiedFile(timeMillis);
			}
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
				if( timeDiff<imageHandleUnit.minHoldTime && file.getName().equals(fileName)) {
//					System.out.println("’“µΩŒ¥≥∑ªÿµƒÕº∆¨£°");
					findFlag = true;
					break;
				} else if( timeDiff>imageHandleUnit.minHoldTime ) {
					findFlag = true;
					break;
				}
			}
			if(!findFlag) {
				imageBag.setWithdrawal(true);
//				imageHandleUnit.removeImageFileFromList(imageBag);
				System.out.println("**Õº∆¨≥∑ªÿÃ·–—£°");
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
			if( (timeMillis-file.lastModified()) < imageHandleUnit.minHoldTime && 
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
