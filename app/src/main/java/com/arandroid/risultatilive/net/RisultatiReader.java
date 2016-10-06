package com.arandroid.risultatilive.net;

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

import com.arandroid.risultatilive.core.Risultati;
import com.arandroid.risultatilive.core.Risultato;

public class RisultatiReader {

	public RisultatiReader() {
	}

	public Risultati read(String feed) {
		Risultati risultato = new Risultati();
		List<Risultato> ris = new LinkedList<Risultato>();
		risultato.setList(ris);
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
			NodeList allDivs = document.getElementsByTagName("td");
			List<Element> partite = new LinkedList<Element>();
			List<Element> risultati = new LinkedList<Element>();

			for (int i = 0; i < allDivs.getLength(); i++) {
				Element node = (Element) allDivs.item(i);
				String value = node.getAttribute("class");
				if (value.equals("match"))
					partite.add(node);
				else if (value.equals("score"))
					risultati.add(node);

			}
			for (int i = 0; i < partite.size(); i++) {
				Element table = partite.get(i);
				String Date = table.getFirstChild().getNodeValue();
				Element linkElem = (Element) table.getElementsByTagName("a")
						.item(0);
				String a = linkElem.getFirstChild().getNodeValue();
				if (a == null) {
					Element linkElem2 = (Element) table.getElementsByTagName(
							"strong").item(0);
					a = linkElem2.getFirstChild().getNodeValue();
				}
				Element table2 = risultati.get(i);
				String b = table2.getFirstChild().getNodeValue();
				Risultato r = new Risultato();
				r.setMatch(a.trim());
				r.setRisultato(b.trim());
				r.setDate(Date);
				ris.add(r);
			}
			String giornata = "";
			NodeList allOptions = document.getElementsByTagName("option");
			for (int i = 0; i < allOptions.getLength(); i++) {
				Element optionElem = (Element) allOptions.item(i);
				if(optionElem.hasAttribute("selected")){
					giornata = optionElem.getAttribute("value");
					break;
				}
			}
			risultato.setGiornata(giornata);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return risultato;
	}
}
