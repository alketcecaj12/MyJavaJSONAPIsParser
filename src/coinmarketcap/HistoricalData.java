package coinmarketcap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class HistoricalData {

	static String [] coins = {"BTC", "ETH", "XRP", "LTC", "ETC", "DASH", "XEM", "XMR", "ZEC", "WAVES", "BCC", "STRAT", "GNO", "IOS", "MIOTA"};
	static String url = "https://min-api.cryptocompare.com/data/pricehistorical?fsym=DASH&tsyms=BTC,USD,EUR&ts=1452680400&extraParams=your_app_name";
	//static long ts_genes= 1373446257L;  // 2013
	//static long ts_genes = 1436518257L;   // 2015
	static long ts_genes = 1491814257L; // 2017
	public static void main (String [] args) throws Exception {
		List<String>li = new ArrayList<String>();
		List<Long>ts_list = new ArrayList<Long>();
		ts_list.add(ts_genes);
		long step = 86400L;
		System.out.println(ts_list.get(0));
		
		for(int i = 1; i < 1460; i++){
			//Long ts_i = ts_list.get(i)+step;
			ts_list.add(ts_list.get(i-1)+step);
		}
		for(int k = 0; k < coins.length; k++){
			for(int i = 0; i < ts_list.size(); i++){

				//System.out.println(ts_list);
				int ri = (int)(Math.random()*3);
				System.out.println(ri + " random generated of "+i);
				Thread.sleep(1000*ri);
				String jsonstring = getJSON("https://min-api.cryptocompare.com/data/pricehistorical?fsym="+coins[k]+"&tsyms=BTC,USD,EUR&ts="+ts_list.get(i).toString()+"&extraParams=your_app_name");
				li.add(jsonstring);
				if(i % 100 == 0)System.out.println("got string = "+jsonstring);
			}
			// stampa list 
			print("HistData4_"+coins[k]+"_Coin.csv", li);
			li = new ArrayList<String>();
		}
		
		
	}
	public static void print(String file,  List<String>m) throws Exception{  
		
		PrintWriter out = new PrintWriter(new FileWriter(new File(file)));  
		

			for(int i = 0; i < m.size(); i++){
				out.println(m.get(i));
			}
		    out.close(); 
	}
	
	private static String getJSON(String urlString) throws IOException{ 
		URL url = new URL(urlString); 
		URLConnection conn = url.openConnection();  
		
		InputStream is = conn.getInputStream();   
		String json_string = IOUtils.toString(is, "UTF-8");  
		is.close();    
		return json_string; 
	} 
}
