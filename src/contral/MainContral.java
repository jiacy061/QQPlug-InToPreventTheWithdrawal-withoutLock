package contral;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.ConfigBag;
import model.ImageBag;
import tool.ContralUtil;
import tool.FileUtil;
import tool.PicUtil;
import view.ConfigDialog;
import view.MainView;

public class MainContral {
	private boolean executableFlag;
	private MainView mainView;
	private ConfigDialog configDialog;
	private String[] targetPath;
	private ImageHandleUnit imageHandleUnit;
	private PicUtil picUtil;
	
	private final String configFileName = "config.dat";
	private final int maxWidth = 320;
	private final int maxHeight = 200;
	
	public MainContral() {
		ContralUtil.setMainContral(this);
		mainView = new MainView();
		start();
	}

	private void start() {
		getConfig();
		if(executableFlag)
			loadSavedImageFile();
	}
	
	private void loadSavedImageFile() {
		imageHandleUnit = new ImageHandleUnit(targetPath);
		imageHandleUnit.loadSavedImageFile();
		mainView.refreshTableData();
	}
	
	public void stopImageHandle() {
		if(imageHandleUnit!=null)
			imageHandleUnit.endAll();
	}

	public void startImageHandle() {
		if(executableFlag) {
			System.out.println("start threads!");
			if(imageHandleUnit==null)
				imageHandleUnit = new ImageHandleUnit(targetPath);
			imageHandleUnit.startAll();
		} else {
			
		}
	}

	private void getConfig() {
		StringBuffer projectPath = new StringBuffer(30);
		projectPath.append(System.getProperty("user.dir"));
		projectPath.append("//");
		projectPath.append(configFileName);
//		System.out.println(projectPath);
		File configFile = new File(projectPath.toString());
		if( configFile.exists() ) {
			System.out.println("configFile exists!");
			setConfig(configFile);
			setTargetPath();
			executableFlag = true;
		} else {
			System.out.println("configFile doesn't exist!");
			executableFlag = false;
		}
	}

	private void setTargetPath() {
		StringBuffer homePath = new StringBuffer(30);
		homePath.append(System.getProperty("user.home"));
		homePath.append("//Documents//Tencent Files");
		String tencentFilesPath = FileUtil.formatPath(homePath.toString());
//		System.out.println(homePath);
//		System.out.println("tencentFilesPath:" + tencentFilesPath);
		if(ConfigBag.isSelectAll()) {
			File file = new File(tencentFilesPath);
			File[] fs = file.listFiles();
			targetPath = new String[2*(fs.length-1)];
			for(int i=0; i<fs.length; i++) {
				if(!fs[i].getName().equals("All Users")) {
					StringBuffer sb = new StringBuffer(30);
					sb.append(tencentFilesPath);
					sb.append("//");
					sb.append(fs[i].getName());
					sb.append("//Image//C2C");
					targetPath[2*i] = sb.toString();
					sb.delete(sb.lastIndexOf("//")+2, sb.length());
					sb.append("Group");
					targetPath[2*i+1] = sb.toString();
				}
			}
		} else {
			targetPath = new String[2];
			StringBuffer sb = new StringBuffer(30);
			sb.append(tencentFilesPath);
			sb.append("//");
			sb.append(ConfigBag.getQQNumber());
			sb.append("//Image//C2C");
			targetPath[0] = sb.toString();
			sb.delete(sb.lastIndexOf("//")+2, sb.length());
			sb.append("Group");
			targetPath[1] = sb.toString();
		}
		
		for(String s : targetPath)
			System.out.println(s);
	}

	public void saveConfig() {
		StringBuffer sb = new StringBuffer();
		sb.append("<QQNumber>");
		sb.append(configDialog.getQQNumber());
		sb.append("</QQNumber>\r\n");
		sb.append("<isRememberNum>");
		sb.append(configDialog.isRememberNum()?"true":"false");
		sb.append("</isRememberNum>\r\n");
		sb.append("<isSelectAll>");
		sb.append(configDialog.isSelectAll()?"true":"false");
		sb.append("</isSelectAll>\r\n");
		sb.append("<savePath>");
		sb.append(FileUtil.formatPath(configDialog.getSavePath()));
		sb.append("</savePath>\r\n");
		System.out.println(sb.toString());
		
		StringBuffer projectPath = new StringBuffer(30);
		projectPath.append(System.getProperty("user.dir"));
		projectPath.append("//");
		projectPath.append(configFileName);
//		System.out.println(projectPath);
		File configFile = new File(projectPath.toString());
		if(configFile.exists()) {
			configFile.delete();
		}
		FileWriter fw;
		try {
			fw = new FileWriter(configFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.print(sb.toString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setConfig(configFile);
		setTargetPath();
		executableFlag = true;
	}

	private void setConfig(File configFile) {
		String s;
		try {
			FileReader fr = new FileReader(configFile);
			BufferedReader br = new BufferedReader(fr);
			while( (s = br.readLine()) != null ) {
				if(s.indexOf("<QQNumber>")!=-1) {
					s = s.substring(s.indexOf(">")+1, s.indexOf("</"));
					ConfigBag.setQQNumber(s);
				} else if(s.indexOf("<isRememberNum>")!=-1) {
					boolean isRememberNum;
					s = s.substring(s.indexOf(">")+1, s.indexOf("</"));
					if(s.equals("true")) {
						isRememberNum = true;
					} else {
						isRememberNum = false;
						ConfigBag.setQQNumber("");
					}
					ConfigBag.setRememberNum(isRememberNum);
				} else if(s.indexOf("<isSelectAll>")!=-1) {
					boolean isSelectAll;
					s = s.substring(s.indexOf(">")+1, s.indexOf("</"));
					if(s.equals("true")) {
						isSelectAll = true;
					} else {
						isSelectAll = false;
					}
					ConfigBag.setSelectAll(isSelectAll);
				} else if(s.indexOf("<savePath>")!=-1) {
					s = s.substring(s.indexOf(">")+1, s.indexOf("</"));
					s = FileUtil.formatPath(s);
					ConfigBag.setSavePath(s);
				}
				System.out.println(s);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ImageBag> getImageFilesData(String path) {
		if(imageHandleUnit==null)
			return null;
		
		if(path.equals("ȫѡ")) {
			return imageHandleUnit.getAllImageFileList();
		} else {
			return imageHandleUnit.getPartImageList(path);
		}
	}

	public void setConfigDialog() {
		configDialog = mainView.getConfigDialog();
	}

	public void decrypImageFile(String imagePath) {
		StringBuffer newPath = new StringBuffer(30);
		newPath.append(ConfigBag.getSavePath());
		newPath.append("//showPic.jpg");
		try {
			if(picUtil==null)
				picUtil = new PicUtil(maxWidth, maxHeight);
			picUtil.compressPhoto(imagePath, newPath.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void endProgram() {
		if(imageHandleUnit==null)
			return;
		
		ArrayList<ImageBag> list = imageHandleUnit.getAllImageFileList();
		for(ImageBag imageBag : list) {
			if(imageBag.isWithdrawal()) {
				continue;
			} else {
				imageBag.getFile().delete();
			}
		}
		
		StringBuffer path = new StringBuffer(30);
		path.append(ConfigBag.getSavePath());
		path.append("//showPic.jpg");
		File showPic = new File(path.toString());
		showPic.delete();
	}
	
	public void showNewFileTip() {
		mainView.showNewFileTip();
	}
}
