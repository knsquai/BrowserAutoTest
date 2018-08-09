package common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;



/**
 * ブラウザ自動テストツールの基底クラス
 */
public class Base {

	public static WebDriver driver = null;

	/**
	 * テスト対象環境のパラメータ(以下は設定例)
	 * 　・本番　　　　：PRODUCT
	 * 　・トレーニング：TRAINING
	 * 　・開発　　　　：DEVELOP
	 * ※任意設定項目、各プロジェクトごとに柔軟に設定可
	 */
	public static String envType = "";

	/**
	 * テストクラスの開始時に１度だけ実行される
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		/*
		 * ★テスト起動時の環境変数の変数格納
		 */
		// 実行用端末情報（"STAND_ALONE" or "REMOTE"）
		String replayEnv = System.getenv("REPLAY_ENV");
		// 実行ブラウザ情報("CHROME" or "IE" or "FF32" or "FF64" or "EDGE")
		String browserType = System.getenv("BROWSER_TYPE");
		// テスト対象環境（任意設定項目、子クラスにて参照可）
		envType = System.getenv("ENV_TYPE");

		/*
		 * ★WebDriverのインスタンス化
		 */
		if ("STAND_ALONE".equals(replayEnv)) {
			// 自端末での実行
			if ("CHROME".equals(browserType)) {
				System.setProperty("webdriver.chrome.driver", "./driver/chrome/chromedriver.exe");
				driver = new ChromeDriver();

			} else if ("IE".equals(browserType)) {
				System.setProperty("webdriver.ie.driver", "./driver/ie/IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			} else if ("FF32".equals(browserType)) {
				System.setProperty("webdriver.gecko.driver", "./driver/ff32/geckodriver.exe");
				driver = new FirefoxDriver();

			} else if ("FF64".equals(browserType)) {
				System.setProperty("webdriver.gecko.driver", "./driver/ff64/geckodriver.exe");
				FirefoxOptions firefoxOptions = new FirefoxOptions();
			    firefoxOptions.setCapability("marionette", true);
			    driver = new FirefoxDriver(firefoxOptions);

			} else if ("EDGE".equals(browserType)) {
				System.setProperty("webdriver.edge.driver", "./driver/edge/MicrosoftWebDriver.exe");
				driver = new FirefoxDriver();

			} else {
				throw new Exception();
			}
		} else if ("REMOTE".equals(replayEnv)) {
			// テスト用リモート端末での実行
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setPlatform(Platform.WINDOWS);

			if ("CHROME".equals(browserType)) {
				capabilities.setBrowserName("chrome");

			} else if ("IE".equals(browserType)) {
				capabilities.setBrowserName("internet explorer");

			} else if ("FF32".equals(browserType)) {
				capabilities.setBrowserName("firefox");

			} else if ("FF64".equals(browserType)) {
				capabilities.setBrowserName("firefox");

			} else if ("EDGE".equals(browserType)) {
				capabilities.setBrowserName("edge");

			} else {
				throw new Exception();
			}
			driver = new RemoteWebDriver(new URL("http://10.128.117.251:4444/wd/hub"), capabilities);

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
