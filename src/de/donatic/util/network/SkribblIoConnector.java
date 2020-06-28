package de.donatic.util.network;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SkribblIoConnector {

	private FirefoxDriver foxDriver = null;
	private JavascriptExecutor js = null;
	private boolean init = false;
	
	 public SkribblIoConnector() {
		System.setProperty("webdriver.gecko.driver", "G:/Programme/Geckodriver/geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		foxDriver = new FirefoxDriver(options);
		js = (JavascriptExecutor) foxDriver;
		foxDriver.get("https://skribbl.io/");
	 }
	 
	 public boolean init(String text) {
		 //Local storage for name and language
		 LocalStorage local = foxDriver.getLocalStorage();
		 local.setItem("lang", "German");
		 local.setItem("name", "Obergnom");
		 local.setItem("avatar", "[6,5,9,-1]");
		 foxDriver.navigate().refresh();
		 createPrivateRoom();
		 setCustomWords(text);
		 init = true;
		 return true;
	 }
	 
	 public void setCustomWords(String text) {
		 js.executeScript("document.getElementById('lobbySetCustomWords').value = \""+text+"\"");
	 }
	 
	 public void setExclusiveWordsOnly() {
		 new WebDriverWait(foxDriver, 30).until((ExpectedCondition<Boolean>) wd -> !((JavascriptExecutor) wd).executeScript("return document.getElementById('invite').value").equals(""));
		 foxDriver.findElementById("lobbyCustomWordsExclusive").click();
	 }
	 
	 public void startGame() {
		 if(init)
			 foxDriver.findElementById("buttonLobbyPlay").click();
		 close();
	 }
	 
	 private void createPrivateRoom() {
		 foxDriver.findElementById("buttonLoginCreatePrivate").click();
	 }
	 
	 public String getInviteLink() {
		 new WebDriverWait(foxDriver, 30).until((ExpectedCondition<Boolean>) wd -> !((JavascriptExecutor) wd).executeScript("return document.getElementById('invite').value").equals(""));
		 WebElement e = foxDriver.findElementById("invite");
		 String s =  e.getAttribute("value");
		 return s;
	 }
	 
	 public void close() {
		 if(foxDriver != null)
			 foxDriver.close();
		 foxDriver = null;
	 }
	 
	
}
