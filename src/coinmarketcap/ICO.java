package coinmarketcap;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ICO {
		
	public static String baseURL ="https://api.coinmarketcap.com/v1/ticker?limit=650";
	public static String globalURL = "https://api.coinmarketcap.com/v1/global/";
	public static void main (String [] args)throws Exception{
		
		String CM_response = getJSON(baseURL);
		String CM_global_response = getJSON(globalURL);
		
		List<Coin>coinlist = getDailyValues(CM_response);
		
		List<Coin>global = getGlobal(CM_global_response);
		long ts = coinlist.get(0).last_updated;
		
		for(int i = 0; i < coinlist.size(); i++){
			System.out.println(coinlist.get(i).toString());
		}
		
		print("coindata/Data_"+ts+".csv", coinlist);
	    printGlobal("global/GlobalData"+ts+".csv",global);
	}
	
	public static List<Coin>getDailyValues(String resp) throws Exception{
		
		 List<Coin> coins = new ArrayList<Coin>();
		 String sym ="";
		 String name = "";
		 int rank = 0;
		 double price = 0;
		 long last_up = 0L;
		 double price_btc;
		 double per_ch1h = 0;
		 double per_ch1d = 0;
		 double volume;
		 
		 try { 

				JSONArray jarr = new JSONArray(resp);  
				
				for(int i = 0; i < jarr.length(); i++){ 
					
					Object jo = jarr.get(i); 
				
					JSONObject jso = (JSONObject)jo;    
                    sym = jso.getString("symbol");
	                name = jso.getString("id");
	                rank = jso.getInt("rank");
	                price = jso.getDouble("price_usd");
	                last_up = jso.getLong("last_updated");
	                volume = jso.getDouble("24h_volume_usd");
	                price_btc = jso.getDouble("price_btc");
	                per_ch1h = jso.getDouble("percent_change_1h");
	                per_ch1d = jso.getDouble("percent_change_24h");
	                coins.add(new Coin(sym, name, rank, price,price_btc,per_ch1h, per_ch1d, volume, last_up));
	                
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 
		 return coins;
	}
	
	public static List<Coin>getGlobal(String resp) throws Exception {
		
		List<Coin> global = new ArrayList<Coin>();	
		JSONObject jso = new JSONObject(resp); 
		long total_market_cap_usd = jso.getLong("total_market_cap_usd"); 
		System.out.println(total_market_cap_usd );
		long total_24h_volume_usd = jso.getLong("total_24h_volume_usd"); 
		long bitcoin_percentage_of_market_cap = jso.getLong("bitcoin_percentage_of_market_cap"); 
		long active_currencies = jso.getLong("active_currencies");
		long active_assets = jso.getLong("active_assets");
		long active_markets = jso.getLong("active_markets");
		
		global.add(new Coin(total_market_cap_usd, total_24h_volume_usd,bitcoin_percentage_of_market_cap
				,active_currencies,active_assets,active_markets));
		return global;
	} 
	private static String getJSON(String urlString) throws IOException{ 
		URL url = new URL(urlString); 
		URLConnection conn = url.openConnection();  
		
		InputStream is = conn.getInputStream();   
		String json_string = IOUtils.toString(is, "UTF-8");  
		is.close();    
		return json_string; 
	}  

	public static void print(String file,  List<Coin>m) throws Exception{  
		
		PrintWriter out = new PrintWriter(new FileWriter(new File(file)));  
		out.println("sym,name,rank,price_usd,price_btc,percent_change1h,percent_change1d,vol,last_up");

			for(int i = 0; i < m.size(); i++){
				out.println(m.get(i));
			}
		    out.close(); 
	}

    public static void printGlobal(String file,  List<Coin>m) throws Exception{  
		
		PrintWriter out = new PrintWriter(new FileWriter(new File(file), true));  
//		out.println("total_market_cap_usd,total_24h_volume_usd,bitcoin_percentage_of_market_cap,"
//				+ "active_currencies,active_assets,active_markets");
		
			for(int i = 0; i < m.size(); i++){
				out.print(m.get(i).tot_market_cap_usd+","+m.get(i).total_24h_volume_usd+","
						+ ""+m.get(i).bitcoin_percentage_of_market_cap+","+m.get(i).active_currencies+","
								+ ""+m.get(i).active_assets+","+m.get(i).active_markets);
			}
			
		    out.close(); 
	}
	public static List<Train> getTrains( String resp){
		
		List<Train>trains = new ArrayList<Train>();
		
		String stazione = ""; 
		
		Long orario = null; 
		Long orarioEff = null; 
		 
		Train t = null;
		try { 

			JSONArray jarr = new JSONArray(resp);  
			
			for(int i = 0; i < jarr.length(); i++){ 
				
				Object jo = jarr.get(i); 
				System.out.println("jarr_i = "+jarr.get(i).toString());
				JSONObject jso = (JSONObject)jo;    
				JSONObject fermata = (JSONObject)jso.get("fermata");
              
                stazione = fermata.getString("stazione");
                orario = fermata.getLong("programmata");
                orarioEff = fermata.getLong("effettiva");
                double delay_in_min = (double)(orarioEff-orario)/1000/60;
                trains.add(new Train(stazione, orario, orarioEff, delay_in_min));
			} 
			
		}catch (JSONException e) {   
			System.err.println("Can't parse JSON string");   
			e.printStackTrace(); 
		}
	return trains;
	}
	
    public static String convertTsp2Date(long timestamp){
		
		Date data ;
		
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(timestamp);
		data = c.getTime();
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy HH.mm.ss");
		String datainstringa = new String(dateformat.format(data));
		return datainstringa;
		
	}
}
