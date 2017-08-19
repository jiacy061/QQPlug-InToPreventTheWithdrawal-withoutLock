package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SysInfoManager {
	private String wlanAddress;
	private String computerName;
	
	public SysInfoManager() {
		// 获得系统名称
		String os = System.getProperty("os.name");
		if (os != null && os.startsWith("Windows")) {
			try {
				// 定义dos命令
				String command = "cmd.exe /c ipconfig /all";
				// 执行dos命令
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				boolean wlanFlag = false;
				while ((line = br.readLine()) != null) {
//					System.out.println(line);
					if(line.indexOf("主机名") > 0) {
						setComputerName(line);
						continue;
					}
					if(line.indexOf("WLAN") > 0) {
						wlanFlag = true;
						continue;
					}
					if( (line.indexOf("物理地址") > 0 || line.indexOf("Physical Address") > 0)
							&& wlanFlag) {
						setWlanAddress(line);
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setWlanAddress(String line) {
		int index = line.indexOf(":");
		index += 2;
		wlanAddress = line.substring(index);
	}

	private void setComputerName(String line) {
		int index = line.indexOf(":");
		index += 2;
		computerName = line.substring(index);
	}

	public String getWlanAddress() {
		System.out.println("mac address:" + wlanAddress);
		return wlanAddress;
	}
	
	public String getComputerName() {
		System.out.println("computer name:" + computerName);
		return computerName;
	}
}
