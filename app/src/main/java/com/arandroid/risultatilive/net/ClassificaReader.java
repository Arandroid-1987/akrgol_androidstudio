package com.arandroid.risultatilive.net;

import com.arandroid.risultatilive.core.Squadra;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ClassificaReader {

    public ClassificaReader() {
    }

    public List<Squadra> read(String feed) {
        List<Squadra> ris = new LinkedList<Squadra>();
        Document doc;
        try {
            InputStream input = new URL(feed).openStream();
            doc = Jsoup.parse(input, "UTF-8", feed);

            Element table = doc.select("table.standingstable").first();

            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Element linkElem = row.select("a").first();
                String link = linkElem.attr("href");
                String squadra = linkElem.text();
                Elements tds = row.select("td");
                String punteggio = tds.get(tds.size() - 2).text();
                Squadra s = new Squadra();
                s.setNome(squadra);
                s.setScore(punteggio);
                s.setLink(link);
                ris.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ris;
    }
}
