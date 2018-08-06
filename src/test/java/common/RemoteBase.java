package common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;



/**
 * ブラウザ自動テストツールの基底クラス
 * (RemoteWebDriver版)
 */
public class RemoteBase {

	/**
	 * スクリーンショットの拡張子
	 */
	public static final String EXTENSION = ".jpg";

	/**
	 * スクリーンショット保存先フォルダ指定
	 */
	public static final String SCREENSHOT_LOCATION_TEST = "C:/autotest_capture/test/";

	public static RemoteWebDriver driver = null;

	public static String envVariable = "";

	/**
	 * テストクラスの開始時に１度だけ実行される
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(Platform.WINDOWS);

		// ブラウザの選択（テスト起動時の環境変数にて設定）
		String browserType = System.getenv("BROWSER_TYPE");
		if ("CHROME".equals(browserType)) {
			capabilities.setBrowserName("chrome");

		} else if ("IE".equals(browserType)) {
			capabilities.setBrowserName("internet explorer");

		} else if ("FF".equals(browserType)) {
			capabilities.setBrowserName("firefox");

		} else if ("EDGE".equals(browserType)) {
			capabilities.setBrowserName("edge");

		} else {
			throw new Exception();
		}
		driver = new RemoteWebDriver(new URL("http://10.128.117.251:4444/wd/hub"), capabilities);

		// 環境依存情報の設定（テスト起動時の環境変数にて設定）
		String envType = System.getenv("ENV_TYPE");
		if ("PRODUCT".equals(envType)) {
			envVariable = "0";

		} else if ("DEVELOP".equals(envType)) {
			envVariable = "1";

		} else if ("TRAINING".equals(envType)) {
			envVariable = "2";

		} else {
			throw new Exception();
		}

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
		//driver.quit();
	}

	/**
	 * テストクラスの終了時に１度だけ実行される
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// 閉じる
		//driver.quit();
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
