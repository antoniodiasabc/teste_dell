package teste;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TesteSelenium {

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void openFirefox() {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://localhost:8080/hangman.html");
		driver.findElement(By.id("newgame")).click();

		WebElement busca = driver.findElement(By.id("letter"));
		busca.sendKeys("O");
		busca.sendKeys(Keys.RETURN);
		driver.findElement(By.id("btn_generate_graph")).click();

		WebElement remains = driver.findElement(By.id("chances"));
		WebElement status = driver.findElement(By.id("status"));
		WebElement letterEntered = driver.findElement(By.id("letter"));

		String val = "";
		String statusGame = "";
		do {
			char key = getKey();
			String letter = Character.toString(key).toUpperCase();
			busca.sendKeys(letter);
			busca.sendKeys(Keys.RETURN);
			driver.findElement(By.id("btn_generate_graph")).click();

			val = remains.getAttribute("value");
			statusGame = status.getAttribute("value");
			String letDis = letterEntered.getAttribute("disabled");
			System.out.println("typed " + letter +" letter status " + letDis + " chances " + val);
		} while (!val.equals("0") && !statusGame.equals("won"));
	}

	private char getKey() {
		Random random = new Random();
		char randomizedCharacter = (char) (random.nextInt(26) + 'a');
		System.out.println("Generated Random Character: " + randomizedCharacter);
		return randomizedCharacter;
	}

}
