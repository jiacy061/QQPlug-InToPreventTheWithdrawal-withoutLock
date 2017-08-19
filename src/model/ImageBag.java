package model;

import java.awt.Image;
import java.io.File;
import java.util.Date;

public class ImageBag {
	private Date date;
	private boolean isWithdrawal;
	private File file;
	private String fromPath;
	
	public ImageBag(File file, String fromPath) {
		super();
		this.file = file;
		this.fromPath = fromPath;
		date = new Date(file.lastModified());
		isWithdrawal = false;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Long getMillis() {
		return file.lastModified();
	}
	
	public boolean isWithdrawal() {
		return isWithdrawal;
	}
	
	public void setWithdrawal(boolean isWithdrawal) {
		this.isWithdrawal = isWithdrawal;
	}
	
	public Image getImage() {
		return null;
	}
	
	public String getImageName() {
		return file.getName();
	}
	
	public String getImagePath() {
		return file.getAbsolutePath();
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean isAppointedPath(String path) {
		if(path.contains(fromPath))
			return true;
		else if(fromPath.contains(path))
			return true;
		else
			return false;
	}

}
