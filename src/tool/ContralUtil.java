package tool;

import java.util.ArrayList;

import contral.MainContral;
import model.ImageBag;

public class ContralUtil {

	private static MainContral mainContral;
	
	public static void setMainContral(MainContral _mainContral) {
		mainContral = _mainContral;
	}
	
	public static ArrayList<ImageBag> getImageFilesData(String path) {
		return mainContral.getImageFilesData(path);
	}
	
	public static void stopImageHandle() {
		mainContral.stopImageHandle();
	}
	
	public static void startImageHandle() {
		mainContral.startImageHandle();
	}
	
	public static void saveConfig() {
		mainContral.saveConfig();
	}
	
	public static void setConfigDialog() {
		mainContral.setConfigDialog();
	}

	public static void decrypImageFile(String imagePath) {
		mainContral.decrypImageFile(imagePath);
	}
	
	public static void endProgram() {
		mainContral.endProgram();
	}

	public static void showNewFileTip() {
		mainContral.showNewFileTip();
	}
	
}
