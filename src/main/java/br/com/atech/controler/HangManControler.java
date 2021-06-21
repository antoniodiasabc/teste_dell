package br.com.atech.controler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.hangman.domain.ReadXML;

@RestController
public class HangManControler {

	protected final Logger Log = Logger.getLogger("HangManControler");

	@RequestMapping(value = "/hangmanserver")
	public HangMan hangmanplay(@RequestBody HangMan jogada, HttpSession session) {
		Log.info("received " + jogada.getLetter());

		HangMan result = new HangMan();
		result.setStatus("Rec");

		if (jogada.getLetter().equals("new")) {
			Log.info("new game " + jogada.getLetter());

			List<String> readXMLWords;
			try {
				readXMLWords = new ReadXML().readXMLWords();

				String draft = getDraft(readXMLWords);
				Log.info("drawn " + draft);

				result.setDraft(draft);
				int maxChances = draft.length();
				result.setMaxChances(maxChances);
				result.setFound(0);
				result.setChancesUsed(0);
				result.setStatus("playing");
				result.setDraftBlocked(getBlockedDraft(draft));
				result.setStatus("playing");
				session.setAttribute("hangman", result);
			} catch (Exception e) {
				Log.warning(e.getMessage());
			}
		} else {

			HangMan hangmanSession = (HangMan) session.getAttribute("hangman");

			if (hangmanSession != null) {
				Log.info("session has values ");
				String draft = hangmanSession.getDraft();

				if (hangmanSession.getTyped().contains(jogada.getLetter())) {
					Log.info("letter already typed " + jogada.getLetter());
					result.setMessage("already typed letter");
					result.setTyped(hangmanSession.getTyped());
					result.setMaxChances(hangmanSession.getMaxChances());
					result.setChancesUsed(hangmanSession.getChancesUsed());
					result.setFound(hangmanSession.getFound());
					result.setDraftBlocked(getBlocked(draft, hangmanSession.getTyped()));
					result.setStatus("playing");
				} else {

					String letter = jogada.getLetter();
					Log.info("typed " + letter);
					hangmanSession.setTyped(hangmanSession.getTyped() + letter);
					result.setTyped(hangmanSession.getTyped());
					result.setMaxChances(hangmanSession.getMaxChances());
					result.setChancesUsed(hangmanSession.getChancesUsed());
					result.setFound(hangmanSession.getFound());
					int countMatches = StringUtils.countMatches(draft, letter);
					Log.info("count " + countMatches);
					if (countMatches > 0) {
						Log.info("found " + letter);
						hangmanSession.setFound(hangmanSession.getFound() + countMatches);
						result.setFound(hangmanSession.getFound());
					} else {
						hangmanSession.setChancesUsed(hangmanSession.getChancesUsed() + 1);
						result.setChancesUsed(hangmanSession.getChancesUsed());
						hangmanSession.setMaxChances(hangmanSession.getMaxChances() - 1);
						result.setMaxChances(hangmanSession.getMaxChances());
					}
					result.setDraftBlocked(getBlocked(draft, hangmanSession.getTyped()));

					Log.info("drawn " + result.getDraft() + " maxChances: " + result.getMaxChances() + " found "
							+ result.getFound() + " chancesUsed " + result.getChancesUsed());

					if (hangmanSession.getFound() == draft.length()) {
						Log.info("won");
						result.setStatus("won");
					} else if (hangmanSession.getChancesUsed() == draft.length()) {
						Log.info("lost");
						result.setStatus("lost");
						result.setDraft(draft);
					} else {
						result.setStatus("playing");
					}
				}
			}
		}
		return result;
	}

	private String getBlocked(String draft, String letter) {
		StringBuffer result = new StringBuffer(getBlockedDraft(draft));
		for (int j = 0; j < letter.length(); j++) {
			for (int i = 0; i < draft.length(); i++) {
				char one = letter.charAt(j);
				char two = draft.charAt(i);
				if (one == two) {
					result.setCharAt(i, letter.charAt(j));
				}
			}
		}
		return result.toString();
	}

	private String getBlockedDraft(String draft) {
		String result = "";
		for (int i = 0; i < draft.length(); i++) {
			result += "-";
		}
		return result;
	}

	@RequestMapping(value = "/")
	public ModelAndView hangmanbase() {
		return new ModelAndView("redirect:/hangman.html");
	}

	public List<String> readXMLWords() throws ParserConfigurationException, SAXException, IOException {
		List<String> words = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse(new File("src/main/resources/words.xml"));

		document.getDocumentElement().normalize();

		Element root = document.getDocumentElement();
		Log.info(root.getNodeName());

		NodeList nList = document.getElementsByTagName("word_list");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				NodeList elementsByTagName = eElement.getElementsByTagName("word");
				for (int i = 0; i < elementsByTagName.getLength(); i++) {
					Log.info(elementsByTagName.item(i).getTextContent());
					words.add(elementsByTagName.item(i).getTextContent());
				}
			}
		}
		return words;
	}

	private String getDraft(List<String> readXMLWords) {
		int nextInt = RandomUtils.nextInt(1, readXMLWords.size());
		return readXMLWords.get(nextInt);
	}

}
