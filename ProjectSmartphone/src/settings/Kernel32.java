
package settings;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

/*https://stackoverflow.com/questions/3434719/how-to-get-the-remaining-battery-life-in-a-windows-system*/

public interface Kernel32 extends StdCallLibrary {

	public Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class);

	public class SYSTEM_POWER_STATUS extends Structure {
		public byte ACLineStatus;
		public byte BatteryFlag;
		public byte BatteryLifePercent;
		public byte Reserved1;
		public int BatteryLifeTime;
		public int BatteryFullLifeTime;

		@Override
		protected List<String> getFieldOrder() {
			ArrayList<String> fields = new ArrayList<String>();
			fields.add("ACLineStatus");
			fields.add("BatteryFlag");
			fields.add("BatteryLifePercent");
			fields.add("Reserved1");
			fields.add("BatteryLifeTime");
			fields.add("BatteryFullLifeTime");
			return fields;
		}

		public String getACLineStatusString() {
			switch (ACLineStatus) {
			case (0):
				return "Offline";
			case (1):
				return "Online";
			default:
				return "Unknown";
			}
		}

		public String getBatterystate() {
			if (BatteryLifePercent < ((byte) 25))
				return "images/icons/Battery-low.png";
			if (BatteryLifePercent < ((byte) 50))
				return "images/icons/Battery-half.png";
			if (BatteryLifePercent < ((byte) 75))
				return "images/icons/Battery-high.png";
			if (BatteryLifePercent <= ((byte) 100))
				return "images/icons/Battery-full.png";
			return null;

		}

		public String getBatterystate2() {

			for (int i = 0; i <= 100; i++) {

				if (BatteryLifePercent <= ((byte) i))
					return i + "%";
			}

			return null;

		}

		public String getBatteryLifePercent() {
			return (BatteryLifePercent == (byte) 255) ? "Unknown" : BatteryLifePercent + "%";
		}

		public String getBatteryLifeTime() {
			return (BatteryLifeTime == -1) ? "Unknown" : BatteryLifeTime + " seconds";
		}

		public String getBatteryFullLifeTime() {
			return (BatteryFullLifeTime == -1) ? "Unknown" : BatteryFullLifeTime + " seconds";
		}

	}

	public int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);
}