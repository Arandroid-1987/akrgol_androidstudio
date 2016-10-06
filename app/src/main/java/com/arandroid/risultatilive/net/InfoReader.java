package com.arandroid.risultatilive.net;

import java.net.URL;

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

import com.arandroid.risultatilive.core.Squadra;

public class InfoReader {
	private Squadra s;
	private String [] str = {"Posizione", "Incontri", "Won", "Tied", "Lost"};

	public InfoReader(Squadra s) {
		this.s = s;
	}

	public Squadra read() {
		try {
			URL url = new URL(s.getLink());
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

			NodeList allDivs = document.getElementsByTagName("div");
			Element table = null;
			for (int i = 0; i < allDivs.getLength(); i++) {
				Element node = (Element) allDivs.item(i);
				String value = node.getAttribute("class");
				if (value.equals("teampage")) {
					table = node;
					break;
				}
			}
			NodeList alldd = table.getElementsByTagName("dd");
			NodeList allp = table.getElementsByTagName("p");
			for (int i = 0; i < alldd.getLength()-2; i++) {
				Element node2= (Element) alldd.item(i);
				String val = node2.getFirstChild().getNodeValue();
				str[i]=val;
				
			}
			String match = "";
			for (int i = 4; i < allp.getLength(); i+=2) {
				Element node = (Element) allp.item(i);
				match += node.getFirstChild().getNodeValue()+" ";
			}
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