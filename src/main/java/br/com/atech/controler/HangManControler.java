package br.com.atech.controler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.hangman.teste.ReadXML;

@RestController
public class HangManControler {

	@RequestMapping(value = "/hangmanserver")
	public HangMan hangmanplay(@RequestBody HangMan jogada, ModelMap model, HttpSession session) {
		Logger.getLogger("hangan").info("received " + jogada.getLetter());

		HangMan result = new HangMan();
		result.setStatus("Rec");

		if (jogada.getLetter().equals("new")) {
			Logger.getLogger("hangan").info("new game " + jogada.getLetter());

			List<String> readXMLWords;
			try {
				readXMLWords = new ReadXML().readXMLWords();

				String draft = getDraft(readXMLWords);
				System.out.println("sorteado " + draft);

				// HangMan hangman = new HangMan();
				result.setDraft(draft);
				int maxChances = draft.length();
				result.setMaxChances(maxChances);
				result.setFound(0);
				result.setChancesUsed(0);
				result.setStatus("playing");
				result.setDraftBlocked(getBlockedDraft(draft));
				result.setStatus("playing");
				model.addAttribute("hangman", result);
				model.addAttribute("status", "playing");
				model.addAttribute("found", "0");
				session.setAttribute("hangman", result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Logger.getLogger("hangan").info("jogada " + jogada.getLetter());

			HangMan hangmanSession = (HangMan) session.getAttribute("hangman");

			if (hangmanSession != null) {
				Logger.getLogger("hangan").info("session has values ");
				String draft = hangmanSession.getDraft();

				// int maxChances = draft.length();

				String letter = jogada.getLetter();
				System.out.println("digitou " + letter);
				hangmanSession.setTyped(hangmanSession.getTyped() + letter);
				result.setTyped(hangmanSession.getTyped());
				result.setMaxChances(hangmanSession.getMaxChances());
				result.setChancesUsed(hangmanSession.getChancesUsed());
				result.setFound(hangmanSession.getFound());
				int countMatches = StringUtils.countMatches(draft, letter);
				System.out.println("count " + countMatches);
				if (countMatches > 0) {
					System.out.println("achou " + letter);
					hangmanSession.setFound(hangmanSession.getFound() + countMatches);
					result.setFound(hangmanSession.getFound());
				} else {
					hangmanSession.setChancesUsed(hangmanSession.getChancesUsed() + 1);
					result.setChancesUsed(hangmanSession.getChancesUsed());
					hangmanSession.setMaxChances(hangmanSession.getMaxChances() - 1);
					result.setMaxChances(hangmanSession.getMaxChances());
				}
				result.setDraftBlocked(getBlocked(draft, hangmanSession.getTyped()));

				System.out.println("sorteado " + result.getDraft() + " maxChances: " + result.getMaxChances()
						+ " found " + result.getFound() + " chancesUsed " + result.getChancesUsed());

				if (hangmanSession.getFound() == draft.length()) {
					System.out.println("won");
					result.setStatus("won");
				} else if (hangmanSession.getChancesUsed() == draft.length()) {
					System.out.println("lost");
					result.setStatus("lost");
				} else {
					result.setStatus("playing");
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
					// break;
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

	@RequestMapping(value = "/hangmanbase")
	public String hangmanbase(HttpResponse response) {
		return "index.html";
	}

	@RequestMapping(value = "/hangmannew")
	public ModelAndView hangmannew(HttpSession session, ModelMap model, HttpServletRequest request) {
		Logger.getLogger("hangan").info("new  game ");

		List<String> readXMLWords;
		try {
			readXMLWords = new ReadXML().readXMLWords();

			String draft = getDraft(readXMLWords);
			System.out.println("sorteado " + draft);

			HangMan hangman = new HangMan();
			hangman.setDraft(draft);
			int maxChances = draft.length();
			hangman.setMaxChances(maxChances);
			hangman.setFound(0);
			hangman.setChancesUsed(0);
			hangman.setStatus("playing");
			model.addAttribute("hangman", hangman);
			model.addAttribute("status", "playing");
			model.addAttribute("found", "0");
			request.setAttribute("param1", "one");
			session.setAttribute("hangman", hangman);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return new ModelAndView("forward:/hangman.html");
		return new ModelAndView("redirect:/hangman.html", model);

	}

	/*
	 * @RequestMapping(value = "/hangmannew") public HangMan hangmannew(HttpSession
	 * session, ModelMap model, HttpServletRequest request) {
	 * Logger.getLogger("hangan").info("new  game "); HangMan hangman = new
	 * HangMan();
	 *
	 * List<String> readXMLWords; try { readXMLWords = new ReadXML().readXMLWords();
	 *
	 * String draft = getDraft(readXMLWords); System.out.println("sorteado " +
	 * draft);
	 *
	 * hangman.setDraft(draft); int maxChances = draft.length();
	 * hangman.setMaxChances(maxChances); hangman.setFound(0);
	 * hangman.setChancesUsed(0); hangman.setStatus("playing");
	 * model.addAttribute("hangman", hangman); model.addAttribute("status",
	 * "playing"); model.addAttribute("found", "0"); request.setAttribute("param1",
	 * "one"); session.setAttribute("hangman", hangman); } catch (Exception e) {
	 * e.printStackTrace(); } //return new ModelAndView("forward:/hangman.html");
	 * //return new ModelAndView("redirect:/hangman.html", model); return hangman; }
	 */

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

	private String getDraft(List<String> readXMLWords) {
		int nextInt = RandomUtils.nextInt(1, readXMLWords.size());
		return readXMLWords.get(nextInt);
	}

}
