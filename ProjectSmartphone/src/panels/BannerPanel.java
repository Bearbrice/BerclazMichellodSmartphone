/*
 * Banner panel
 * Author: B. Berclaz
 * Date creation: 20.04.2019
 * Date last modification: 01.06.2019
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

/**
 * The BannerPanel class displays a panel with different information to show to
 * the user
 * 
 * @author Brice Berclaz
 * @see settings.Kernel32
 * @see settings.WLAN
 */
public class BannerPanel extends JPanel {

	private WLAN wlan = new WLAN();
	private Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();

	private String wlanName = wlan.getWLANName();
	private String percentage = wlan.getSignalPercentage();

	private JPanel pan1 = new JPanel();
	private JPanel pan1Icon = new JPanel();
	private JPanel pan2 = new JPanel();
	private JPanel pan3Icon = new JPanel();
	private JPanel pan3 = new JPanel();

	private JPanel right = new JPanel();
	private JPanel left = new JPanel();
	private JPanel center = new JPanel();
	private JPanel north = new JPanel();

	private JPanel panL = new JPanel();
	private JPanel panR = new JPanel();

	private DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
	private Date d = new Date();
	private String strDate = df.format(d);

	private JLabel operateur = new JLabel(wlanName);
	private JLabel perc = new JLabel(percentage);
	private JLabel date = new JLabel(strDate);
	private JLabel batteryPercentage = new JLabel("");

	private Icon iconMusicRun = new Icon("images/icons/MusicRun-48.png", 18, 18);
	private Icon iconWlanForce = new Icon(wlan.getPathForce(), 18, 18);
	private Icon battery = new Icon("", 20, 20);

	/** Constructor of the BannerPanel class */
	public BannerPanel() {
		Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
		String batteryIcon = batteryStatus.getBatterystate();
		battery.setLocation(batteryIcon);
		batteryPercentage.setText(batteryStatus.getBatterystate2());

		bannerSettings();

		pan1.setOpaque(false);

		pan1.add(operateur, BorderLayout.WEST);

		pan1Icon.setOpaque(false);
		pan1Icon.add(iconWlanForce, BorderLayout.EAST);
		iconWlanForce.setVisible(true);

		pan1Icon.add(perc);

		pan2.setOpaque(false);

		pan2.add(date, BorderLayout.CENTER);

		pan3Icon.setOpaque(false);
		pan3Icon.add(iconMusicRun, BorderLayout.WEST);
		iconMusicRun.setVisible(false);

		pan3.setOpaque(false);
		pan3.add(batteryPercentage, BorderLayout.WEST);
		pan3.add(battery, BorderLayout.EAST);

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

		north.setPreferredSize(new Dimension(480, 68));
		north.setOpaque(false);
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
	}

	/** Modifies the parameters of the main panel. */
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

	/** Refresh WLAN information and the icon wlan in the panel */
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
