package com.arandroid.risultatilive.net;

import java.io.InputStream;
import java.net.URL;


import com.arandroid.risultatilive.core.Squadra;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InfoReader {
	private Squadra s;
	private String [] str = {"Posizione", "Incontri", "Won", "Tied", "Lost"};

	public InfoReader(Squadra s) {
		this.s = s;
	}

	public Squadra read() {
		Document doc;
		try {
			InputStream input = new URL(s.getLink()).openStream();
			doc = Jsoup.parse(input, "UTF-8", s.getLink());

			Element table = doc.select("dl.team").first();
			Elements alldd = table.select("dd");
			for (int i = 0; i < alldd.size(); i++) {
				str[i]= alldd.get(i).text();
			}
			Element prevMatch = doc.select("div.prev_match").first();
            String prevMatchDesc = prevMatch.select(".match").first().text();
            String score = prevMatch.select(".score").first().text();
			String match = prevMatchDesc+" "+score;
			s.setPosizione(str[0]);
			s.setIncontri(str[1]);
			s.setWon(str[2]);
			s.setTied(str[3]);
			s.setLost(str[4]);
			s.setProssimoMatch(match);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return s;
	}
}