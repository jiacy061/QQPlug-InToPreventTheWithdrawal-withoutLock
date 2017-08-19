package model;

public class ConfigBag {
	private static String qqNumber;
	private static String savePath;
	private static boolean isRememberNum;
	private static boolean isSelectAll;
	private static String wlanAddress;
	private static String computerName;
	
	public static String getWlanAddress() {
		return wlanAddress;
	}
	public static void setWlanAddress(String _wlanAddress) {
		wlanAddress = _wlanAddress;
	}
	public static String getComputerName() {
		return computerName;
	}
	public static void setComputerName(String _computerName) {
		computerName = _computerName;
	}
	public static String getQQNumber() {
		return qqNumber;
	}
	public static void setQQNumber(String _qqNumber) {
		qqNumber = _qqNumber;
	}
	public static String getSavePath() {
		return savePath;
	}
	public static void setSavePath(String _savePath) {
		savePath = _savePath;
	}
	public static boolean isRememberNum() {
		return isRememberNum;
	}
	public static void setRememberNum(boolean _isRememberNum) {
		isRememberNum = _isRememberNum;
	}
	public static boolean isSelectAll() {
		return isSelectAll;
	}
	public static void setSelectAll(boolean _isSelectAll) {
		isSelectAll = _isSelectAll;
	}
	
}
