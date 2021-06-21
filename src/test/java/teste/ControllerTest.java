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
	public void test() {
		assertTrue(true);
	}

	@Test
	public void testNavigate() {
		ModelAndView modelview = hangmanControler.hangmanbase();
		assertTrue(modelview != null);
		assertTrue(modelview.getViewName() != null);
		assertTrue(modelview.getViewName().equals("redirect:/hangman.html"));
	}

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


}
