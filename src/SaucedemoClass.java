import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SaucedemoClass {

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
	@Test(priority = 1, enabled = false)
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
	@Test(priority = 2, enabled = false)
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

//4.--------------To add Items as The Number of Times  -----------------
	@Test(priority = 3, enabled = false)
	public void add_number_of_times_to_shopping_cart() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(3000);
		List<WebElement> btnAddToCartList = driver.findElements(By.className("btn"));
		int originalListSize = btnAddToCartList.size();
//		System.out.println("The Original Size is: " + originalListSize);

		int numOfTimes = 2;

		for (int i = 0; i < numOfTimes; i++) {
			btnAddToCartList.get(i).click();
			Thread.sleep(3000);
		}

		String shoppCartCount = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]")).getText();
//		System.out.println("The Shopping Cart Count String is: " + shoppCartCount);

		int shoppCartCountInt = Integer.parseInt(shoppCartCount);
//		System.out.println("The Shopping Cart Count Integer is: " + shoppCartCountInt);

		Assert.assertEquals(numOfTimes, shoppCartCountInt);
//		Assert.assertEquals(numOfTimes, shoppCartCountInt-1);
	}

//	5.--------------To Add All Items   ----------------------------------
	@Test(priority = 4, enabled = true)
	public void add_all_items_to_shopping_cart() throws InterruptedException {

		driver.navigate().refresh();
		Thread.sleep(3000);
		List<WebElement> btnAddAllToCartList = driver.findElements(By.className("btn"));
		int originalListSizeAll = btnAddAllToCartList.size();
//		System.out.println("The Original Size is: " + originalListSizeAll);
		for (int i = 0; i < originalListSizeAll; i++) {
//	System.out.println("The Item No. " + (i+1) + ": "+ btnAddAllToCartList.get(i).getText());
			btnAddAllToCartList.get(i).click();
			Thread.sleep(2000);
		}

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-450)");
		
		String allShopCartCount = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).getText();
//		System.out.println("The All Shopping Cart Count String is: " + allShopCartCount);
		int allshoppCartCountInt = Integer.parseInt(allShopCartCount);
//		System.out.println("The All Shopping Cart Count Integer is: " + allshoppCartCountInt);
	
		Assert.assertEquals(originalListSizeAll, allshoppCartCountInt);
//		Assert.assertEquals(originalListSizeAll, allshoppCartCountInt-1);
	}

//6.--------------To Print The Method Names -----------------
	@AfterMethod(enabled = false)
	public void getTestName(Method method) {
		System.out.println("Test name: " + method.getName() + "--------Done");
	}

}