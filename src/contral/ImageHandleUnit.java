package contral;

import java.io.File;
import java.util.ArrayList;

import model.ConfigBag;
import model.ImageBag;

public class ImageHandleUnit {
	private String[] targetPath;
	private String savePath;
	private ImageHandleThread[] threads;
	private ArrayList<ImageBag> savedFileList;
	private ArrayList<String> savedFileNameList;
	public final long minHoldTime = (long) (2.5 * 60 * 1000); // 最短保留两分半钟
	public final long maxHoldTime = minHoldTime + 10000; // 最长保留两分四十秒
	
	public ImageHandleUnit(String[] targetPath) {
		this.targetPath = targetPath;
		savePath = ConfigBag.getSavePath();
		savedFileList = new ArrayList<ImageBag>();
		savedFileNameList = new ArrayList<String>();
	}
	
	public void loadSavedImageFile() {
		File file = new File(savePath);
		File[] fs = file.listFiles();
		for(File f : fs) {
			if(f.isFile()) {
				ImageBag imageBag = new ImageBag(f, "全选");
				imageBag.setWithdrawal(true);
				savedFileList.add(imageBag);
				savedFileNameList.add(f.getName());
			}
		}
		System.out.println("** list len:"+savedFileList.size());
		System.out.println("&& list len:"+savedFileNameList.size());
	}

	public void startAll() {
		threads = new ImageHandleThread[targetPath.length];
		for(int i=0; i<targetPath.length; i++) {
			threads[i] = new ImageHandleThread(this, targetPath[i]);
			threads[i].start();
		}
	}
	
	public void endAll() {
		if(threads==null) 
			return;
		
		for(int i=0; i<threads.length; i++) {
			if(threads[i]==null)
				continue;
			threads[i].end();
			threads[i] = null;
		}
		System.gc();
	}
	
	public void addImageFileToList(File file, String fromPath) {
		savedFileList.add(new ImageBag(file, fromPath));
		savedFileNameList.add(file.getName());
	}
	
	public void removeImageFileFromList(ImageBag imageBag) {
		System.out.println("^^^savedFileList len:"+savedFileList.size()
		+" savedFileNameList len:"+savedFileNameList.size());
		savedFileList.remove(imageBag);
		savedFileNameList.remove(imageBag.getImageName());
		System.out.println("***savedFileList len:"+savedFileList.size()
		+" savedFileNameList len:"+savedFileNameList.size());
	}
	
	public String getImageFileNameOfList(int index) {
		return savedFileList.get(index).getImageName();
	}
	
	public boolean isImageFileInList(String fileName) {
		return savedFileNameList.contains(fileName);
	}
	
	public ArrayList<ImageBag> getAllImageFileList() {
		return savedFileList;
	}
	
	public void deleteUnqualifiedFile(Long millis) {
		for(int i=0, len=savedFileList.size(); i<len; i++) {
			ImageBag imageBag = savedFileList.get(i);
			System.out.println("HandleUnit::i="+i+";len="+len);
			if( (millis - imageBag.getMillis()) > minHoldTime && !imageBag.isWithdrawal()) {
				File file = imageBag.getFile();
				System.out.println("i="+i+";len="+len+";file="+file);
				file.delete();
				savedFileList.remove(i);
				savedFileNameList.remove(imageBag.getImageName());
				i--;
				len--;
			}
		}
	}
	
	public ArrayList<ImageBag> getPartImageList(String path) {
		ArrayList<ImageBag> list = new ArrayList<ImageBag>();
		for(ImageBag imageBag : savedFileList) {
//			System.out.println(path+":"+imageBag.isAppointedPath(path));
			if(imageBag.isAppointedPath(path)) {
				list.add(imageBag);
			}
		}
		return list;
	}
}
