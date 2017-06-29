package coinmarketcap;

public class Coin {

public static void main (String [] args) throws Exception{}

//"id": "bitcoin", 
//"name": "Bitcoin", 
//"symbol": "BTC", 
//"rank": "1", 
//"price_usd": "2555.09", 
//"price_btc": "1.0", 
//"24h_volume_usd": "1089640000.0", 
//"market_cap_usd": "41947134823.0", 
//"available_supply": "16417087.0", 
//"total_supply": "16417087.0", 
//"percent_change_1h": "-1.08", 
//"percent_change_24h": "0.97", 
//"percent_change_7d": "-4.93", 
//"last_updated": "1498731850", 
//"price_eur": "2240.93657432", 
//"24h_volume_eur": "955666582.72", 
//"market_cap_eur": "36789650702.0"


public String sym;
public int rank;
public double price_usd; 
public double price_btc;
public double h24_volume_usd;
public double market_cap_usd;
public double available_sup;
public double tot_sup;
public double per_change1h;
public double per_change24h;
public double per_change7d;
public long last_updated;
public double price_eur;
public double vol_eur_24h;
public double market_cap;



public Coin(String s, int r, double p,double p_btc, double h24,double mcap, double avai_sup, 
		    double tot_sup,double pch1h, double pch24h,double pch7d, long l, double pe, 
		    double ve24, double mc){
	this.sym = s;
	this.rank = r; 
	this.price_usd = p; 
	this.price_btc = p_btc;
	this.h24_volume_usd = h24;
	this.market_cap_usd = mcap;
	this.available_sup = avai_sup;
	this.tot_sup = tot_sup; 
	this.per_change1h = pch1h;
	this.per_change24h = pch24h;
	this.per_change7d = pch7d;
	this.last_updated = l;
	this.price_eur = pe;
	this.vol_eur_24h = ve24;
	this.market_cap = mc;
}

public double tot_market_cap_usd; 
public double total_24h_volume_usd; 
public double bitcoin_percentage_of_market_cap; 
public double active_currencies;
public double active_assets; 
public double active_markets;

public Coin(long tmc, long tv24, long bpmc, long ac,long aa, long am ){
	this.tot_market_cap_usd = tmc;
	this.total_24h_volume_usd = tv24;
	this.bitcoin_percentage_of_market_cap = bpmc; 
	this.active_currencies = ac; 
	this.active_assets = aa;
	this.active_markets = am;
}

public String toString(){
	return sym+","+rank+","+price_usd+","+price_btc+","+h24_volume_usd+","+market_cap_usd+","+available_sup+","
		+ ""+tot_sup+","+per_change1h+","+per_change24h+","+per_change7d+","+last_updated+","+price_eur+","
					+ ""+vol_eur_24h+","+market_cap;
}
}
