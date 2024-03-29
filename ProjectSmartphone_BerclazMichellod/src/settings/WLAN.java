/*
 * WLAN
 * Author: B. Berclaz
 * Date creation: 13.05.2019
 * Date last modification: 13.05.2019
 */

package settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The WLAN class displays gets information about the wlan interfaces the user
 * is connected to
 * 
 * @author Brice Berclaz
 * @see java.io.InputStreamReader;
 */
public class WLAN {
	private ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "netsh wlan show interfaces");

	private String wlanName = "Disconnected";
	private String signalPercentage = "0% ";

	public String pathForce = "images/icons/Wi-fi-off-48.png";

	public String getPathForce() {
		return pathForce;
	}

	/** Constructor of the WLAN class */
	public WLAN() {
		refreshWlanInfo();
	}

	/**
	 * Refresh the information of the WLAN the user is connected to
	 */
	public void refreshWlanInfo() {
		builder.redirectErrorStream(true);
		Process p = null;
		try {
			p = builder.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		boolean SSID = false;
		try {
			while (r.read() != -1) {
				try {
					line = r.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (SSID == false) {
					if (line.contains("SSID")) {
						line = line.substring(28);
						setWLANName(line);
						SSID = true;
					}
				}
				if (line.contains("Signal")) {
					line = line.substring(28);
					setSignalPercentage(line);
				}

				if (line.contains("Statut")) {
					setWLANName("Disconnected");
					setSignalPercentage("0% ");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		SSID = false;
		refreshForcePath();
	}

	/**
	 * Refresh the path to the icon for the strength of the WLAN
	 */
	private void refreshForcePath() {
		int length = signalPercentage.length();

		String forceS = signalPercentage.substring(0, length - 2);
		int force = Integer.parseInt(forceS);

		if (force == 0) {
			pathForce = "images/icons/Wi-fi-off-48.png";
			return;
		}
		if (force < 30) {
			pathForce = "images/icons/Wi-fi-LOW-48.png";
			return;
		}
		if (force < 60) {
			pathForce = "images/icons/Wi-fi-MEDIUM-48.png";
			return;
		} else {
			pathForce = "images/icons/Wi-fi-FULL-48.png";
			return;
		}

	}

	public String getWLANName() {
		return wlanName;
	}

	public void setWLANName(String wlanName) {
		this.wlanName = wlanName;
	}

	public String getSignalPercentage() {
		return signalPercentage;
	}

	public void setSignalPercentage(String signalPercentage) {
		this.signalPercentage = signalPercentage;
	}
}
