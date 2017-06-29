package coinmarketcap;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.Timestamp;
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
	public static String euro = "https://api.coinmarketcap.com/v1/ticker/?convert=EUR&limit=790";
	public static void main (String [] args)throws Exception{
		for(int i = 0; i < 1000; i++){
		 run();
		 Thread.sleep(1000*60*60);
		}
	
	}
		public static void run() throws Exception{
		String CM_response = getJSON(euro);
		System.out.println(CM_response);
		//String CM_global_response = getJSON(globalURL);
		
		List<Coin>coinlist = getDailyValues(CM_response);
		
		//List<Coin>global = getGlobal(CM_global_response);
		long ts = System.currentTimeMillis();
		System.out.println("timestamp = "+ts);
		//Timestamp timestamp = new Timestamp(ts);
       // System.out.println(timestamp);
		
		for(int i = 0; i < coinlist.size(); i++){
			System.out.println(coinlist.get(i).toString());
		}
	
		print("coindata/Data_"+ts+".csv", coinlist);
	  //  printGlobal("global/GlobalData"+ts+".csv",global);
	}
	
	public static List<Coin>getDailyValues(String resp) throws Exception{
		
		 List<Coin> coins = new ArrayList<Coin>();
		 List<String> coins2 = new ArrayList<String>();

//	        "symbol": "BTC", 
//	        "rank": "1", 
//	        "price_usd": "2574.48", 
//	        "price_btc": "1.0", 
//	        "24h_volume_usd": "1075410000.0", 
//	        "market_cap_usd": "42265366884.0", 
//	        "available_supply": "16417050.0", 
//	        "total_supply": "16417050.0", 
//	        "percent_change_1h": "-0.1", 
//	        "percent_change_24h": "2.02", 
//	        "percent_change_7d": "-4.13", 
//	        "last_updated": "1498729448", 
//	        "price_eur": "2257.94253504", 
//	        "24h_volume_eur": "943186189.68", 
//	        "market_cap_eur": "37068755495.0"
		 
		 
		 
		 String sym ="";
		 int rank = 0;
		 Double priceusd = null;
		 Double pricebtc = null;
		 Double h24_volusd = null;
		 Double mcapusd = null;
		 Double aval_sup = null;;
		 Double tot_sup = null;
		 Double per_ch1h = null;
		 Double per_ch24h = null;
		 Double per_ch7d = null;
		 long last_up = 0L;
		 Double price_euro;
		 Double volume_24h_euro = null;
		 Double mc_euro = null;
		 try { 

				JSONArray jarr = new JSONArray(resp);  
				
				for(int i = 0; i < jarr.length(); i++){ 
					
					Object jo = jarr.get(i); 
				
					JSONObject jso = (JSONObject)jo; 
					String s = jso.toString();
					String sfinal = s.replace("{", "");
					System.out.println(sfinal);
					String sfinalfinal = sfinal.replace("\"", "");
					sfinalfinal = sfinalfinal.replace(":", ",");
					System.out.println("--> --> --> final = "+sfinalfinal);
					sfinalfinal = sfinalfinal.replace("null", "0");
					String [] r = sfinalfinal.substring(0, sfinalfinal.length()-1).split(",");
					
					 sym =r[3];
					 System.out.println(sym);
					
					 rank = Integer.parseInt(r[27]);
					  priceusd = Double.parseDouble(r[1]);
					  pricebtc =  Double.parseDouble(r[15]);
					  h24_volusd = Double.parseDouble(r[11]);
					 mcapusd =  Double.parseDouble(r[19]);
					 aval_sup = Double.parseDouble(r[17]);
					 tot_sup =  Double.parseDouble(r[9]);
					  per_ch1h = Double.parseDouble(r[21]);
					  System.out.println("wtf is "+r[23]);
					  if(r[23] != null) per_ch24h = Double.parseDouble(r[23]);
					  else  per_ch24h = 0.0;
					  per_ch7d = Double.parseDouble(r[31]);
					 last_up = Long.parseLong(r[5]);
					 price_euro = Double.parseDouble(r[33]);
					 volume_24h_euro = Double.parseDouble(r[7]);
					 mc_euro = Double.parseDouble(r[13]);
					
	               
	                coins.add(new Coin (sym,rank,priceusd,pricebtc,h24_volusd,mcapusd,aval_sup,tot_sup,per_ch1h,
	                		per_ch24h,per_ch7d,last_up, price_euro, volume_24h_euro, mc_euro));
				}
		 }catch(Exception e){
			 e.printStackTrace();
			 System.out.println("The field is null");
			 
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
		out.println("sym,rank,price_usd,price_btc,h24_volume_usd,market_cap_usd,avail_sup,"
				+ "tot_sup,per_change_1h,per_change_24h,per_change_7d,last_updated,price_euro,vol_euro_24h,"
				+ "market_cap_euro");
		
			for(int i = 0; i < m.size(); i++){
				
				out.print(m.get(i).sym+","+m.get(i).rank+","+m.get(i).price_usd+","+m.get(i).price_btc+","
						+ ""+m.get(i).h24_volume_usd+","+m.get(i).market_cap_usd+","
								+ ""+m.get(i).available_sup+","
						+ ""+m.get(i).tot_sup+","+m.get(i).per_change1h+","+m.get(i).per_change24h+","
								+ ""+m.get(i).per_change7d+","+m.get(i).last_updated+","+m.get(i).price_eur+","
									+ ""+m.get(i).vol_eur_24h+","+m.get(i).market_cap);
				
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
