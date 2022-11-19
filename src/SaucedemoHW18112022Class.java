import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SaucedemoHW18112022Class {

	public WebDriver driver;
	public String myUrl = "https://www.saucedemo.com/";
	public String userName = "standard_user";
	public String password = "secret_sauce";

//1.--------------To Login to The WebSite-----------------
	@BeforeTest
	public void login() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(myUrl);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys(userName);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
		Thread.sleep(3000);
	}

//2.--------------To Sort Items form Low to High -----------------
@Test(priority = 1)
	public void sort_items_low_to_high() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div[2]/span/select")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div[2]/span/select/option[3]")).click();
		Thread.sleep(3000);
		List<WebElement> myLowPriceList = driver.findElements(By.className("inventory_item_price"));
		Thread.sleep(3000);

		List<Double> lowValDouble = new ArrayList<>();

		for (int i = 0; i < myLowPriceList.size(); i++) {
			String priceLowStr = myLowPriceList.get(i).getText();
//	System.out.println(priceLowStr);	
			priceLowStr.trim();
			String priceLowStrNew = priceLowStr.replace("$", "");
//	System.out.println(priceLowStrNew);	

			double priceLowVal = Double.parseDouble(priceLowStrNew);
//			System.out.println(priceLowVal);

			lowValDouble.add(priceLowVal);
		}
//		System.out.println("The Size of ArrayList is: " + lowValDouble.size());
//		System.out.println(lowValDouble);
		int lowLastIndex = lowValDouble.size() - 1;
//		System.out.println(lowLastIndex);
		boolean actualCheckLowPrice = lowValDouble.get(0) < lowValDouble.get(lowLastIndex);
//		System.out.println(actualCheckLowPrice);
		Assert.assertEquals(actualCheckLowPrice, true);
//		Assert.assertEquals(actualCheckLowPrice, false);
	}

	
//3.--------------To Sort Items form High to Low   -----------------
@Test(priority = 2)
	public void sort_items_high_to_low() throws InterruptedException {
		driver.navigate().refresh();
		driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div[2]/span/select")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div[2]/span/select/option[4]")).click();
		Thread.sleep(3000);

		List<WebElement> myHighPriceList = driver.findElements(By.className("inventory_item_price"));
		List<Double> highValDouble = new ArrayList<>();
		for (int i = 0; i < myHighPriceList.size(); i++) {
			String priceHighStr = myHighPriceList.get(i).getText();
//			System.out.println(priceHighStr);
			priceHighStr.trim();

			String priceHighStrNew = priceHighStr.replace("$", "");
//			System.out.println(priceHighStrNew);

			double priceHighVal = Double.parseDouble(priceHighStrNew);
//			System.out.println(priceHighVal);
			highValDouble.add(priceHighVal);
		}

//		System.out.println("The Size of ArrayList is: " + highValDouble.size());
//		System.out.println(highValDouble);
		int highLastIndex = highValDouble.size() - 1;
//		System.out.println(highLastIndex);
		boolean actualCheckHighPrice = highValDouble.get(0) > highValDouble.get(highLastIndex);
//		System.out.println(actualCheckHighPrice);
		Assert.assertEquals(actualCheckHighPrice, true);
//		Assert.assertEquals(actualCheckHighPrice, false);
		
	}

}