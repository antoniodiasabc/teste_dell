package teste;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import br.com.atech.ApplicationHangMan;
import br.com.atech.controler.HangMan;
import br.com.atech.controler.HangManControler;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationHangMan.class)
public class ControllerTest {

	@Autowired
	HangManControler hangmanControler;

	@Test
	public void testNewGame() {
		HangMan hangMan = new HangMan();
		hangMan.setLetter("new");
		HangMan hangmanplay = hangmanControler.hangmanplay(hangMan, null);
		assertTrue(hangmanplay != null);
		assertTrue(hangmanplay.getDraft() != null);
		System.out.println(hangmanplay.getDraft());

	}

	@Test
	public void testNavigate() {
		ModelAndView modelview = hangmanControler.hangmanbase();
		assertTrue(modelview != null);
		assertTrue(modelview.getViewName() != null);
		assertTrue(modelview.getViewName().equals("redirect:/hangman.html"));
	}


	@Test
	public void testDrawn() {
		MockHttpSession session = new MockHttpSession();

		HangMan hangMan = new HangMan();

		hangMan.setLetter("A");
		session.setAttribute("hangman", hangMan);

		HangMan hangmanplay = hangmanControler.hangmanplay(hangMan, session);
		assertTrue(hangmanplay != null);
		assertTrue(hangmanplay.getDraft() != null);
		assertTrue(hangmanplay.getTyped().length() == 1);
	}

	@Test
	public void testDrawnRepeated() {
		MockHttpSession session = new MockHttpSession();

		HangMan hangMan = new HangMan();

		hangMan.setLetter("A");
		session.setAttribute("hangman", hangMan);

		HangMan hangmanplay = hangmanControler.hangmanplay(hangMan, session);
		assertTrue(hangmanplay != null);
		assertTrue(hangmanplay.getDraft() != null);
		assertTrue(hangmanplay.getTyped().length() == 1);

		hangMan.setLetter("A");
		session.setAttribute("hangman", hangMan);

		hangmanplay = hangmanControler.hangmanplay(hangMan, session);
		assertTrue(hangmanplay != null);
		assertTrue(hangmanplay.getDraft() != null);
		assertTrue(hangmanplay.getTyped().length() == 1);
	}

	@Test
	public void testDrawn2Letters() {
		MockHttpSession session = new MockHttpSession();

		HangMan hangMan = new HangMan();

		hangMan.setLetter("new");
		HangMan hangmanplay = hangmanControler.hangmanplay(hangMan, null);
		assertTrue(hangmanplay != null);
		assertTrue(hangmanplay.getDraft() != null);
		session.setAttribute("hangman", hangmanplay);

		hangMan.setLetter("A");

		HangMan h2 = hangmanControler.hangmanplay(hangMan, session);
		assertTrue(h2 != null);
		assertTrue(h2.getDraft() != null);
		assertTrue(h2.getTyped().length() == 1);
		session.setAttribute("hangman", h2);

		hangMan.setLetter("W");

		HangMan h3 = hangmanControler.hangmanplay(hangMan, session);
		assertTrue(h3 != null);
		assertTrue(h3.getDraft() != null);
		assertTrue(h3.getTyped().length() == 2);
	}

}
