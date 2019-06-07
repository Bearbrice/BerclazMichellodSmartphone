/*
 * Banner panel - contains informations & notifications
 * Author: Brice Berclaz
 * Date creation: 
 * Date last modification: 
 */

package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import images.Icon;
import settings.Kernel32;
import settings.WLAN;

public class BannerPanel extends JPanel {

	private WLAN wlan = new WLAN();
	private Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();

	private String wlanName = wlan.getWLANName();
	private String percentage = wlan.getSignalPercentage();

	JPanel pan1 = new JPanel();
	JPanel pan1Icon = new JPanel();
	JPanel pan2 = new JPanel();
	JPanel pan3Icon = new JPanel();
	JPanel pan3 = new JPanel();

	JPanel right = new JPanel();
	JPanel left = new JPanel();
	JPanel center = new JPanel();
	JPanel north = new JPanel();

	JPanel panL = new JPanel();
	JPanel panR = new JPanel();

	DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
	Date d = new Date();
	String strDate = df.format(d);

	JLabel operateur = new JLabel(wlanName);
	JLabel perc = new JLabel(percentage);
	JLabel date = new JLabel(strDate);
	JLabel batteryPercentage = new JLabel("");

	Icon iconMusicRun = new Icon("images/icons/MusicRun-48.png", 18, 18);
	Icon iconWlanForce = new Icon(wlan.getPathForce(), 18, 18);
	Icon battery = new Icon("", 20, 20);

	// CONSTRUCTEUR
	public BannerPanel() {
		Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
		String batteryIcon = batteryStatus.getBatterystate();
		battery.setLocation(batteryIcon);
		batteryPercentage.setText(batteryStatus.getBatterystate2());

		bannerSettings();

		// left.setLayout(new FlowLayout(0, 0, 60));

		// PAN1 - WEST
		// pan1.setLayout(new FlowLayout(15, 30, 70));
		pan1.setOpaque(false);

		pan1.add(operateur, BorderLayout.WEST);

		pan1Icon.setOpaque(false);
		pan1Icon.add(iconWlanForce, BorderLayout.EAST);
		iconWlanForce.setVisible(true);

		pan1Icon.add(perc);

		// pan2.setLayout(new FlowLayout(FlowLayout.LEFT));
		// pan1.setPreferredSize(new Dimension(150,20));

		// PAN2 - CENTER
		// pan2.setLayout(new FlowLayout(15, 30, 70));
		pan2.setOpaque(false);

		pan2.add(date, BorderLayout.CENTER);

		// pan2.setPreferredSize(new Dimension(80,20));

		// PAN3 - EAST
		// pan3.setLayout(new FlowLayout(15, 30, 70));
		pan3Icon.setOpaque(false);
		pan3Icon.add(iconMusicRun, BorderLayout.WEST);
		iconMusicRun.setVisible(false);

		pan3.setOpaque(false);
		pan3.add(batteryPercentage, BorderLayout.WEST);
		pan3.add(battery, BorderLayout.EAST);

		// pan3.setPreferredSize(new Dimension(100,20));

		// Ajout des panels
		center.setLayout(new GridLayout(1, 4));
		center.add(pan1, BorderLayout.WEST);
		center.add(pan1Icon, BorderLayout.WEST);
		center.add(pan2, BorderLayout.CENTER);
		center.add(pan3Icon, BorderLayout.EAST);
		center.add(pan3, BorderLayout.EAST);

		left.setOpaque(false);
		right.setOpaque(false);

		panL.setLayout(new FlowLayout(0, 6, 0));
		left.add(panL);
		panL.setOpaque(false);

		panR.setLayout(new FlowLayout(0, 6, 0));
		right.add(panR);
		panR.setOpaque(false);

		// this.add(left, BorderLayout.WEST);
		// this.add(center, BorderLayout.CENTER);
		// this.add(right, BorderLayout.EAST);
		north.setPreferredSize(new Dimension(480, 68));
		north.setOpaque(false);
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
	}

	// Propriétés du panel banner
	public void bannerSettings() {
		this.setLayout(new BorderLayout(3, 1));
		this.setPreferredSize(new Dimension(480, 92));
		setOpaque(false);
	}

	public void setVisibleIconMusic(boolean x) {
		if (x == true) {
			iconMusicRun.setVisible(true);
		}
		if (x == false) {
			iconMusicRun.setVisible(false);
		}
	}

	public void refreshNetwork() {
		wlan.refreshWlanInfo();
		wlanName = wlan.getWLANName();
		percentage = wlan.getSignalPercentage();
		operateur.setText(wlanName);
		perc.setText(percentage);
		iconWlanForce.setLocation(wlan.getPathForce());
		iconWlanForce.refresh();
	}

}
