package teste;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.atech.controler.HangManControler;

@WebMvcTest(HangManControler.class)
public class NavTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	HangManControler hangManControler;

	@Test
	public void test() {
		assertTrue(true);
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(hangManControler).build();
	}

	@Test
	public void testNavigate() {
		try {
			mockMvc.perform(get("/")).andExpect(status().is3xxRedirection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
