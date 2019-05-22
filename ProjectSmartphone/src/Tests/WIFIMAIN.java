package Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WIFIMAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String>ssids=new ArrayList<String>();
	    ArrayList<String>signals=new ArrayList<String>();
	    ProcessBuilder builder = new ProcessBuilder(
	            "cmd.exe", "/c", "netsh wlan show all");
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
	    try {
			while (r.read()!=-1) {
			    try {
					line = r.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    if (line.contains("SSID")||line.contains("Signal")){
			        if(!line.contains("BSSID"))
			            if(line.contains("SSID")&&!line.contains("name")&&!line.contains("SSIDs"))
			            {
			                line=line.substring(8);
			                ssids.add(line);

			            }
			            if(line.contains("Signal"))
			            {
			                line=line.substring(30);
			                signals.add(line);

			            }

			            if(signals.size()==7)
			            {
			                break;
			            }

			    }

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    for (int i=0;i<ssids.size();i++)
	    {
	        System.out.println("SSID name == "+ssids.get(i)+"   and its signal == "  );
	    }
	}

}
