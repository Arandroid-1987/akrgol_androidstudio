package com.arandroid.risultatilive.net;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

import org.ccil.cowan.tagsoup.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.arandroid.risultatilive.core.Squadra;

public class ClassificaReader {

	public ClassificaReader() {
	}

	public List<Squadra> read(String feed) {
		List<Squadra> ris = new LinkedList<Squadra>();
		try {
			URL url = new URL(feed);
			XMLReader reader = new Parser();
			reader.setFeature(Parser.namespacesFeature, false);
			reader.setFeature(Parser.namespacePrefixesFeature, false);

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();

			DOMResult result = new DOMResult();
			transformer.transform(
					new SAXSource(reader, new InputSource(url.openStream())),
					result);
			Element document = ((Document) result.getNode())
					.getDocumentElement(); // radice documento

			NodeList allDivs = document.getElementsByTagName("table");
			Element table = null;
			for (int i = 0; i < allDivs.getLength(); i++) {
				Element node = (Element) allDivs.item(i);
				String value = node.getAttribute("title");
				if (value.equals("Classifiche")) {
					table = node;
					break;
				}
			}

			NodeList alltd = table.getElementsByTagName("td");
			for (int i = 2; i < alltd.getLength(); i += 11) {
				Element node = (Element) alltd.item(i);
				Element linkimg = (Element) node.getElementsByTagName("img")
						.item(0);
				String img = linkimg.getAttribute("src");
				String img2 = img.replaceAll(" ", "%20");
				node = (Element) alltd.item(i + 1);
				Element linkElem = (Element) node.getElementsByTagName("a")
						.item(0);
				String link = linkElem.getAttribute("href");
				String squadra;
				squadra = linkElem.getFirstChild().getNodeValue();
				if (squadra == null){
					squadra = linkElem.getFirstChild().getFirstChild().getNodeValue();
				}
				node = (Element) alltd.item(i + 8);
				String punteggio = node.getFirstChild().getNodeValue();
				InputStream is = new URL(img2).openStream();
				Bitmap b = BitmapFactory.decodeStream(is);
				Squadra s = new Squadra();
				s.setNome(squadra);
				s.setScore(punteggio);
				s.setLogo(b);
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
