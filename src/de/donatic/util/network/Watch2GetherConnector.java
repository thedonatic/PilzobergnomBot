package de.donatic.util.network;

import java.io.Console;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Watch2GetherConnector {

	private FirefoxDriver foxDriver = null;
	private JavascriptExecutor js = null;
	private boolean init = false;
	
	 public Watch2GetherConnector() {
		System.setProperty("webdriver.gecko.driver", "G:/Programme/Geckodriver/geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
//		options.setHeadless(true);
		foxDriver = new FirefoxDriver(options);
		js = (JavascriptExecutor) foxDriver;
		foxDriver.get("https://watch2gether.com/");
	 }
	 
	 public boolean init(String text) {
		 foxDriver.navigate().refresh();
		 createPrivateRoom();
		 setLink(text);
		 init = true;
		 return true;
	 }
	 
	 public void setLink(String text) {
//		 js.executeScript("document.getElementById('search-bar-input').value = \""+text+"\"");
		 foxDriver.findElementById("search-bar-input").sendKeys(text);
		 WebDriverWait wait = new WebDriverWait(foxDriver, 20);
		 System.out.println(wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".w2g-item-image"))).getAttribute("title")); 
//		 foxDriver.findElement(By.cssSelector(".w2g-item-image")).click();
	 }
	 
	 private void createPrivateRoom() {
		 foxDriver.findElementById("create_room_button").click();
		 js.executeScript("window.__cmpui(\"setAndSaveAllConsent\",!0)");
		 foxDriver.findElementByClassName("content").findElement(By.cssSelector("*")).click();
		 System.out.println("Search Completed!");
	 }
	 
	 public String getInviteLink() {
		 foxDriver.findElementByClassName("invite-cta w2g-search-hide").click();
		 WebElement e = foxDriver.findElementById("main-invite-url");
		 String s =  e.getAttribute("value");
		 return s;
	 }
	 
	 public void close() {
		 if(foxDriver != null)
			 foxDriver.close();
		 foxDriver = null;
	 }
	 
	
}
