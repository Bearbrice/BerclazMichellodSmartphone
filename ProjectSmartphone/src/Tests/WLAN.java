/*
 * WLAN
 * Author: Brice Berclaz
 * Date creation: 13.05.2019
 * Date last modification: 13.05.2019
 */

package Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WLAN {
	private ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "netsh wlan show interfaces");
	
    private String wlanName;
    private String signalPercentage;
    
	public WLAN() {
		refreshWlanInfo();
	}
	
	public void refreshWlanInfo() {
		builder.redirectErrorStream(true);
	    Process p = null;
		try {
			p = builder.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String line = null;
	    boolean SSID=false;
	    try {
			while (r.read()!=-1) {
			    try {
					line = r.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    if(SSID==false) {
				    if(line.contains("SSID")) {
				    	line=line.substring(28);
				    	setWlanName(line);
				    	SSID = true;
				    }
			    }
			    if(line.contains("Signal")) {
			    	line=line.substring(28);
			    	setSignalPercentage(line);
			    	SSID = true;
			    }
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getWlanName() {
		return wlanName;
	}

	public void setWlanName(String wlanName) {
		this.wlanName = wlanName;
	}

	public String getSignalPercentage() {
		return signalPercentage;
	}

	public void setSignalPercentage(String signalPercentage) {
		this.signalPercentage = signalPercentage;
	}
}
