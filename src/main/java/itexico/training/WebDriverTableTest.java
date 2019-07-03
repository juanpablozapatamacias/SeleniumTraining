package itexico.training;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverTableTest {

	static WebDriver driver;
	static int attempts;

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chrome/chromedriver-75");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("file://"+ System.getProperty("user.dir") +"/src/main/webapp/TableTest.html");
		/*/html/body/table/tbody/tr/td[2]/table/tbody/tr[1]/td*/
		
		/*WebElement outerTable = getElement(By.tagName("table"),10);
		WebElement innerTable = outerTable.findElement(By.tagName("table"));
		WebElement row = innerTable.findElements(By.tagName("td")).get(1);
		System.out.println(row.getText());
*/
		
		WebElement row = getElementPresenceOfElementLocated(By.xpath("/html/body/table/tbody/tr/td[2]/table/tbody/tr[1]/td"),10);
		System.out.println(row.getText());
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
	
	public static Select getSelectElement(By by, int secs) {
		return new Select(getElement(by,secs));
	}

}
