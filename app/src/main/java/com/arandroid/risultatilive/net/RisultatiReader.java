package com.arandroid.risultatilive.net;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arandroid.risultatilive.core.Risultati;
import com.arandroid.risultatilive.core.Risultato;

public class RisultatiReader {

	public RisultatiReader() {
	}

	public Risultati read(String feed) {
		Risultati risultati = new Risultati();
		List<Risultato> ris = new LinkedList<Risultato>();
		risultati.setList(ris);
		Document doc;
		try {
			InputStream input = new URL(feed).openStream();
			doc = Jsoup.parse(input, "UTF-8", feed);
			Elements partite = doc.select("table.matchtable td.match");
			Elements risultatiElem = doc.select("table.matchtable td.score");

			for (int i = 0; i < partite.size(); i++) {
				Element table = partite.get(i);
				String data = table.text();
				Element linkElem = table.select("a").first();
				String partita = linkElem.text().trim();
                data = data.replace(partita, "");
				Element table2 = risultatiElem.get(i);
				String risult = table2.text().trim();
				Risultato r = new Risultato();
				r.setMatch(partita);
				r.setRisultato(risult);
				r.setDate(data);
				ris.add(r);
			}
			Element giornataElem = doc.select("form select option[selected]").first();
			String giornata = giornataElem.text();
			risultati.setGiornata(giornata);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return risultati;
	}
}
