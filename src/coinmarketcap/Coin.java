package coinmarketcap;

public class Coin {

public static void main (String [] args) throws Exception{}

public String name; 
public String sym;
public int rank;
public double price_usd; 
public double price_btc;
public double per_change1h;
public double per_change1d;
public double volume;
public long last_updated;


public Coin(String s, String n, int r, double p,double p_btc, double pch1h, 
		double pch1d, double v, long l){
	this.sym = s;
	this.name = n; 
	this.rank = r; 
	this.price_usd = p; 
	this.price_btc = p_btc;
	this.per_change1h = pch1h;
	this.per_change1d = pch1d;
	this.volume = v;
	this.last_updated = l; 
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
	return sym+","+name+","+rank+","+price_usd+","+price_btc+","
			+ ""+per_change1h+","+per_change1d+","+volume+","+last_updated;
}
}
