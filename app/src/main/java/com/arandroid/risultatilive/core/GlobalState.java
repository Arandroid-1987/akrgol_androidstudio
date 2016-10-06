package com.arandroid.risultatilive.core;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.arandroid.risultatilive.net.ClassificaReader;
import com.arandroid.risultatilive.net.RisultatiReader;

import android.app.Application;

public class GlobalState extends Application {
	private boolean risultatiaggiornati;
	private boolean classificaaggiornata;;
	private boolean adsBannerShown=false;
	private List<Squadra> list = new LinkedList<Squadra>();
//	private List<Risultato> ris = new LinkedList<Risultato>();
	private Risultati ris = new Risultati();
	private String ora = "";
	private String oraClass = "";
	
	public static final String AD_UNIT_ID_INTERSTITIAL= "ca-app-pub-8851138749802557/8326498829";
	public static final String AD_UNIT_ID_BANNER = "ca-app-pub-8851138749802557/2304828029";
	
	public String getOraClass() {
		return oraClass;
	}

	public String getOra() {
		return ora;
	}

	public boolean isClassificaaggiornata() {
		return classificaaggiornata;
	}

	public void setClassificaaggiornata(boolean classificaaggiornata) {
		this.classificaaggiornata = classificaaggiornata;
	}

	public boolean isRisultatiaggiornati() {
		return risultatiaggiornati;
	}

	public void setRisultatiaggiornati(boolean risultatiaggiornati) {
		this.risultatiaggiornati = risultatiaggiornati;
	}


	public void reset() {
		ris = new Risultati();
//		ris.setList(new LinkedList<Risultato>());
		list = new LinkedList<Squadra>();
		classificaaggiornata = false;
		risultatiaggiornati = false;
	}

	public List<Squadra> getClassifica(String url) {
		if (list == null || list.isEmpty() || list.size() == 1
				|| risultatiaggiornati || classificaaggiornata) {
			list = new ClassificaReader().read(url);
			risultatiaggiornati = false;
			oraClass = dammiOra();
		}
		if(list==null) list = new LinkedList<Squadra>();
		return list;
	}

	public Risultati getRisultati(String url) {

		if (ris == null || ris.getList()==null
				|| classificaaggiornata || risultatiaggiornati) {
			ris = new RisultatiReader().read(url);
			classificaaggiornata = false;
			ora = dammiOra();
		}
		if(ris==null){
			ris = new Risultati();
			ris.setList(new LinkedList<Risultato>());
		}
		return ris;
	}

	public String dammiOra() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);
		return "Ultimo aggiornamento: " + hour + ":" + minutes;
	}
	
	public boolean isAdsBannerShown() {
		return adsBannerShown;
	}

	public void setAdsBannerShown(boolean adsBannerShown) {
		this.adsBannerShown = adsBannerShown;
	}

}
