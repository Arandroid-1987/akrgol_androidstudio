package com.arandroid.risultatilive.core;

import android.app.Application;

import com.arandroid.risultatilive.net.ClassificaReader;
import com.arandroid.risultatilive.net.RisultatiReader;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class GlobalState extends Application {
    private boolean risultatiaggiornati;
    private boolean classificaaggiornata;

    private boolean adsBannerShown = false;
    private List<Squadra> list = new LinkedList<>();
    private Risultati ris = new Risultati();
    private String ora = "";
    private String oraClass = "";

    public static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-2997805148414323/5594824894";
    public static final String AD_UNIT_ID_BANNER = "ca-app-pub-2997805148414323/7071558099";

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
        list = new LinkedList<>();
        classificaaggiornata = false;
        risultatiaggiornati = false;
    }

    public List<Squadra> getClassifica(String url) {
        if (list == null || list.isEmpty() || list.size() == 1 || risultatiaggiornati || classificaaggiornata) {
            list = new ClassificaReader().read(url);
            risultatiaggiornati = false;
            oraClass = dammiOra();
        }
        if (list == null) list = new LinkedList<>();
        return list;
    }

    public Risultati getRisultati(String url) {
        if (ris == null || ris.getList() == null || classificaaggiornata || risultatiaggiornati) {
            ris = new RisultatiReader().read(url);
            classificaaggiornata = false;
            ora = dammiOra();
        }
        if (ris == null) {
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
