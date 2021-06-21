package br.hangman.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXML {

	public List<String> readXMLWords() throws ParserConfigurationException, SAXException, IOException {
		List<String> words = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse(new File("src/main/resources/words.xml"));

		document.getDocumentElement().normalize();

		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());

		NodeList nList = document.getElementsByTagName("word_list");
		System.out.println("============================");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				NodeList elementsByTagName = eElement.getElementsByTagName("word");
				for (int i = 0; i < elementsByTagName.getLength(); i++) {
					System.out.println(elementsByTagName.item(i).getTextContent());
					words.add(elementsByTagName.item(i).getTextContent());
				}
			}
		}
		return words;
	}

	public static void main(String[] args) {
		try {
			List<String> readXMLWords = new ReadXML().readXMLWords();
			String draft = getDraft(readXMLWords);
			System.out.println("sorteado " + draft);

			int maxChances = draft.length();
			int chancesUsed = 0;
			int found = 0;
			Scanner scan = new Scanner(System.in);
			while (chancesUsed < maxChances && found < maxChances) {
				String letter = scan.next();
				System.out.println("digitou " + letter);
				int countMatches = StringUtils.countMatches(draft, letter);
				System.out.println("count " + countMatches);
				if (countMatches > 0) {
					System.out.println("achou " + letter);
					found += countMatches;
				} else {
					chancesUsed++;
				}
				System.out.println("tamanho: " + maxChances + " found " + found + " chancesUsed " + chancesUsed);
			}
			if (found == maxChances) {
				System.out.println("win");
			} else if (chancesUsed == maxChances) {
				System.out.println("lost");
			}
			scan.close();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	private static String getDraft(List<String> readXMLWords) {
		int nextInt = RandomUtils.nextInt(1, readXMLWords.size());
		return readXMLWords.get(nextInt);
	}

}
