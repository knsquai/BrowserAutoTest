package common;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;



/**
 * ブラウザ自動テストツールの基底クラス
 */
public class Base {

	/**
	 * スクリーンショットの拡張子
	 */
	public static final String EXTENSION = ".jpg";

	/**
	 * スクリーンショット保存先フォルダ指定
	 */
	public static final String SCREENSHOT_LOCATION_TEST = "C:/autotest_capture/test/";

	// TODO: テストしたいdriverの選択
	public WebDriver driver = new InternetExplorerDriver();
	//public WebDriver driver = new FirefoxDriver();
	//public WebDriver driver = new ChromeDriver();
	//public WebDriver driver = new EdgeDriver();

	/**
	 * テストクラスの開始時に１度だけ実行される
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.ie.driver", "./driver/IEDriverServer.exe");
	}

	/**
	 * テストメソッドの開始時に１度だけ実行される
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// 暗黙待機30s
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * テストメソッドの修了時に１度だけ実行される
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		// 閉じる
		driver.quit();
	}

	/**
	 * テストクラスの終了時に１度だけ実行される
	 * @throws Exception
	 */
	@AfterClass
	public void tearDownAfterClass() throws Exception {
	}

	/**
	 * スクリーンショット
	 *
	 * @param filePath
	 *
	 */
	public void takeScreenshot(String filePath) {
		driver.manage().window().maximize();
    	File tmpFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    	try {
    	    FileUtils.copyFile(tmpFile, new File(filePath));
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
	}

	/**
	 * 分割スクリーンショット
	 *
	 * @param filePath
	 *
	 */
	public void takeSplitScreenshot(String filePath) {

		String[] filePathArray = filePath.split("\\.",0);

		JavascriptExecutor js = (JavascriptExecutor)driver;
		int scrollHeight = Integer.parseInt(((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());
		js.executeScript("window.scrollTo(0," + scrollHeight +")", "");
		int scrollLength = Integer.parseInt(((JavascriptExecutor) driver).executeScript("return document.body.scrollTop").toString());
		js.executeScript("window.scrollTo(0,0)", "");

		int ssLength = Integer.parseInt(((JavascriptExecutor) driver).executeScript("return screen.availHeight").toString());;
		int sheets = (int)Math.ceil((double)scrollLength / ssLength);

		if ( sheets > 1 ) {

			for( int i = 0; i < sheets; i++ ) {

				js.executeScript("window.scrollTo(0," + ssLength * i +")", "");
				takeScreenshot(filePathArray[0] + "_" + String.format("%02d", i+1) + "." + filePathArray[1]);

			}

		} else {

			js.executeScript("window.scrollTo(0," + scrollLength +")", "");
			takeScreenshot(filePath);
		}

	}

	/**
	 * テキストフィールド（textbox, textarea）の入力処理
	 *
	 * @param driver
	 * @param xpath
	 * @param text
	 */
	public void textInput(WebDriver driver, String xpath, String text) {
		WebElement element = driver.findElement(By.xpath(xpath));
		element.sendKeys(text);
	}

	/**
	 * プルダウンの選択処理
	 *
	 * @param driver
	 * @param xpath
	 * @param label
	 */
	public void select(WebDriver driver, String xpath, String label) {
		Select element = new Select(driver.findElement(By.xpath(xpath)));
		element.selectByVisibleText(label);
	}

	/**
	 * クリック処理
	 *
	 * @param driver
	 * @param xpath
	 */
	public void click(WebDriver driver, String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	/**
	 * クリア処理
	 *
	 * @param driver
	 * @param xpath
	 */
	public void clear(WebDriver driver, String xpath) {
		driver.findElement(By.xpath(xpath)).clear();
	}

	/**
	 * スイッチウインドウ処理
	 *
	 * @param driver
	 */
	public void switchWindow(WebDriver driver) {
		String currentWindowHandle = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = driver.getWindowHandles();
		windowHandles.remove(currentWindowHandle);
		driver.switchTo().window(windowHandles.iterator().next());
	}

	/**
	 * 待機処理
	 *
	 * @param milliSecond
	 */
	public void pause(long milliSecond){
		try {
			Thread.sleep(milliSecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
