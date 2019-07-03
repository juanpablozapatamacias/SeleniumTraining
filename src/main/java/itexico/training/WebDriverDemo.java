package itexico.training;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverDemo {
	
	static WebDriver driver;
	static int attempts;
	
	public static void main(String[] arg) throws MalformedURLException {
		
		//System.setProperty("webdriver.gecko.driver","/Users/juanpablozapatamacias/automation/drivers/firefox/geckodriver");	
		//WebDriver driver = new FirefoxDriver();
		//System.setProperty("webdriver.chrome.driver", "/Users/juanpablozapatamacias/automation/drivers/chrome/chromedriver-75");
		//driver = new ChromeDriver();
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		
		driver = new RemoteWebDriver(new URL("http://10.194.1.176:4444/wd/hub"),capabilities);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.google.com");
		
		WebElement input = getElementPresenceOfElementLocated(By.name("q"),4);
		
		input.sendKeys("pluralsight");
		input.submit();
		
		List<WebElement> imagesLinks = getListElements(By.xpath("//div//a[contains(text(),'genes')]"),10);
		imagesLinks.get(0).click();
		
		//List<WebElement> imageElements = getListElements(By.cssSelector("a[class = rg_l]"),10);
		WebElement imageElements = getListElements(By.cssSelector("a[class=rg_l]"),180).get(0);
		WebElement imageLink = imageElements.findElements(By.tagName("img")).get(0);
		imageLink.click();
		
		driver.quit();
	}
	
	public static WebDriverWait wait(int secs) {
		return new WebDriverWait(driver,secs);
	}
	
	public static WebElement getElement(By by, int secs) {
		attempts=0;
		while(attempts < 2) {
			try {
				WebElement ele = wait(secs)
						.ignoring(TimeoutException.class, NoSuchElementException.class)
						.until(ExpectedConditions.visibilityOfElementLocated(by));
				
				return ele;
			}
			catch(StaleElementReferenceException se) {}
			attempts++;
		}
		return null;
	}
	
	public static WebElement getElementPresenceOfElementLocated(By by, int secs) {
		attempts=0;
		while(attempts < 2) {
			try {
				WebElement ele =  wait(secs)
						.ignoring(TimeoutException.class, NoSuchElementException.class)
						.until(ExpectedConditions.presenceOfElementLocated(by));
				return ele;
				
			}
			catch(StaleElementReferenceException se) {}
			attempts++;
		}
		return null;
	}
	
	public static List<WebElement> getListElements(By by, int secs){
		attempts=0;
		while(attempts < 2) {
			try {
				List<WebElement> list = wait(secs)
						.ignoring(TimeoutException.class, NoSuchElementException.class)
						.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
				return list;
			}
			catch(StaleElementReferenceException se) {}
			attempts++;
		}
		return null;
	}

}
