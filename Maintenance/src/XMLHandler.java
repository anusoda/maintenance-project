import java.io.*;
import java.security.CodeSource;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
public class XMLHandler {
	File codeLoc=null;
	CodeSource codeSource = XMLHandler.class.getProtectionDomain().getCodeSource();
	XMLHandler(){
		try {
			codeLoc = new File("");

			if(!(new File(codeLoc.getPath()+"settings.xml").exists())){
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder;
				docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();

				Element info=doc.createElement("settings");
				Element rootPercent = doc.createElement("playpercentage");
				rootPercent.appendChild(doc.createTextNode(("50")));	
				info.appendChild(rootPercent);
				doc.appendChild(info);
				doc.normalize();

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(codeLoc.getPath()+"settings.xml");
				transformer.transform(source, result);
			}
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
			clearXML();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			clearXML();
		} catch (TransformerException e) {
			e.printStackTrace();
			clearXML();
		} catch (Exception e)
		{
			clearXML();
		}
	}
	public void clearXML(){
		try {
			if((new File(codeLoc.getPath()+"settings.xml").exists())){
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder;
				docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();

				Element info=doc.createElement("settings");
				Element rootPercent = doc.createElement("playpercentage");
				rootPercent.appendChild(doc.createTextNode(("50")));	
				info.appendChild(rootPercent);
				doc.appendChild(info);
				doc.normalize();
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(codeLoc.getPath()+"settings.xml");
				transformer.transform(source, result);
			}
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} 
	}
	public void SaveSettings(Settings settings){
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {

			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element info=doc.createElement("settings");

			Element rootPercent = doc.createElement("playpercentage");
			rootPercent.appendChild(doc.createTextNode((""+settings.getPlayPercentage())));
			info.appendChild(rootPercent);

			Element rootQueue = doc.createElement("queue");
			for(int x=0;x<settings.getIndexes().size();x++){
				Element index = doc.createElement("index");
				index.appendChild(doc.createTextNode((settings.getIndexes().get(x)).toString()));
				rootQueue.appendChild(index);
			}
			info.appendChild(rootQueue);

			Element rootLibrary = doc.createElement("library");
			for(int x=0;x<settings.getLibrary().size();x++){
				Element song = doc.createElement("name");
				if(!(settings.getLibrary().get(x).getName().length()>0))
					song.appendChild(doc.createTextNode(" "));
				else
					song.appendChild(doc.createTextNode(settings.getLibrary().get(x).getName()));
				rootLibrary.appendChild(song);
				
				Element url = doc.createElement("url");
				url.appendChild(doc.createTextNode(settings.getLibrary().get(x).getURL()));
				rootLibrary.appendChild(url);
				
				Element lastPlayed = doc.createElement("time");
				lastPlayed.appendChild(doc.createTextNode(Long.toString(settings.getLibrary().get(x).getLastPlayed())));
				rootLibrary.appendChild(lastPlayed);
				
				Element freq = doc.createElement("freq");
				freq.appendChild(doc.createTextNode(Integer.toString(settings.getLibrary().get(x).getFrequency())));
				rootLibrary.appendChild(freq);
				
				Element dynamic = doc.createElement("dynamic");
				dynamic.appendChild(doc.createTextNode(new Boolean(settings.getLibrary().get(x).getDynamic()).toString()));
				rootLibrary.appendChild(dynamic);
				
				Element artist = doc.createElement("artist");
				artist.appendChild(doc.createTextNode(settings.getLibrary().get(x).getArtist()));
				rootLibrary.appendChild(artist);
			}
			info.appendChild(rootLibrary);
			doc.appendChild(info);

			doc.normalize();

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(codeLoc.getPath()+"settings.xml");
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			clearXML();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			clearXML();
		} catch (TransformerException e) {
			e.printStackTrace();
			clearXML();
		} catch (Exception e){
			e.printStackTrace();
			clearXML();
		}
	}
	public Settings LoadSettings(){
		double playPercentage=0;
		ArrayList<Song> queue =new ArrayList<Song>();
		ArrayList<Song> library =new ArrayList<Song>();
		DocumentBuilder docFactory = null;
		try {
			docFactory = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			Document document = docFactory.parse(new FileInputStream(codeLoc.getPath()+"settings.xml"));

			Element settings = (Element) document.getElementsByTagName("settings").item(0);
			NodeList percentList = settings.getElementsByTagName("playpercentage").item(0).getChildNodes();
			playPercentage= Double.parseDouble(percentList.item(0).getNodeValue());
			if(settings.getElementsByTagName("library").getLength()>0){
				Element songlibrary = (Element) settings.getElementsByTagName("library").item(0);
				for(int x=0;x<songlibrary.getElementsByTagName("name").getLength();x++){
					if(songlibrary.getElementsByTagName("freq").item(x)==null)
						library.add(new Song(
								songlibrary.getElementsByTagName("name").item(x).getChildNodes().item(0).getNodeValue(),
								songlibrary.getElementsByTagName("url").item(x).getChildNodes().item(0).getNodeValue(),
								Long.parseLong(songlibrary.getElementsByTagName("time").item(x).getChildNodes().item(0).getNodeValue()),
								1,
								Boolean.parseBoolean(songlibrary.getElementsByTagName("dynamic").item(x).getChildNodes().item(0).getNodeValue())
								));
					else
						library.add(new Song(
								songlibrary.getElementsByTagName("name").item(x).getChildNodes().item(0).getNodeValue(),
								songlibrary.getElementsByTagName("url").item(x).getChildNodes().item(0).getNodeValue(),
								Long.parseLong(songlibrary.getElementsByTagName("time").item(x).getChildNodes().item(0).getNodeValue()),
								Integer.parseInt(songlibrary.getElementsByTagName("freq").item(x).getChildNodes().item(0).getNodeValue()),
								Boolean.parseBoolean(songlibrary.getElementsByTagName("dynamic").item(x).getChildNodes().item(0).getNodeValue())
						));
					if(Boolean.parseBoolean(songlibrary.getElementsByTagName("dynamic").item(x).getChildNodes().item(0).getNodeValue()))
					{
						library.get(library.size()-1).setArtist(songlibrary.getElementsByTagName("artist").item(x).getChildNodes().item(0).getNodeValue());
					}
				}
			}
			if(settings.getElementsByTagName("queue").getLength()>0){
				Element songqueue = (Element) settings.getElementsByTagName("queue").item(0);
				for(int x=0;x<songqueue.getElementsByTagName("index").getLength();x++){
					queue.add(library.get(Integer.parseInt(songqueue.getElementsByTagName("index").item(x).getChildNodes().item(0).getNodeValue())));
				}
			}
			return new Settings(null, library, queue, playPercentage);
		} catch (SAXException e) {
			e.printStackTrace();
			clearXML();
		} catch (IOException e) {
			e.printStackTrace();
			clearXML();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
			clearXML();
		} catch(Exception e){
			e.printStackTrace();
			clearXML();
		}
		return null;
	}
}
