package template;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import common.Base;
import common.csv.CsvReader;
import common.csv.bean.DataBean;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class CaseName extends Base {

	/**
	 * BaseクラスにsetUpBeforeClass()、setUp()を定義しているため、
	 * extendsする場合は子クラスに定義しないこと。
	 */

	/**
	 * 固定文字列はここに定義
	 */
	private static final String INPUT_FILE_PATH = "./param/data.csv";

	private List<DataBean> paramList = new ArrayList<DataBean>();

	public void beforeOperation() {
		// パラメータファイルの読み込み
		File paramFile = new File(INPUT_FILE_PATH);
		if (paramFile.exists()) {
			CsvReader csvReader = new CsvReader();
			paramList = csvReader.opencsvToBean(paramFile);
		}
	}

	/**
	 * サンプルページの制御
	 */
	@Test
	public void test999() {

		driver.get("https://www.flierinc.com/summary/1583");
		((JavascriptExecutor) driver).executeScript("document.getElementsByTagName('header')[0].remove();");
		((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('page-top')[0].remove();");
		driver.manage().window().maximize();
		pause(5000);
		for (int i = 0; i < 5; i++) {
		    ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(0,1000)");
		    pause(2000);
		}
		for (int i = 0; i < 5; i++) {
		    ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(0,-1000)");
		    pause(2000);
		}
	    Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
	    try {
			ImageIO.write(screenshot.getImage(), "JPG", new File("c:/tmp/hoge.jpg"));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	/**
	 * サンプルページの制御
	 */
	@Test
	public void test000() {
		driver.get("https://knsquai.github.io/docs");

		// 画面文字列の取得
		String letters = driver.findElement(By.xpath("/html/body/p[2]")).getText();
		System.out.println("画面文字列の取得：" + letters);
		pause(3000);

		// 単一行フィールドへの文字入力
		driver.findElement(By.xpath("/html/body/form[1]/input")).sendKeys("単一行");
		pause(3000);

		// 複数行フィールドへの文字入力
		driver.findElement(By.xpath("/html/body/form[2]/textarea")).sendKeys("複\n数\n行");
		pause(3000);

		// ラジオボタン(男)
		driver.findElement(By.xpath("/html/body/form[3]/input[1]")).click();
		pause(1000);
		// ラジオボタン(女)
		driver.findElement(By.xpath("/html/body/form[3]/input[2]")).click();
		pause(3000);

		// チェックボックス(アメリカ)
		driver.findElement(By.xpath("/html/body/form[4]/input[1]")).click();
		pause(1000);
		// チェックボックス(イタリア)
		driver.findElement(By.xpath("/html/body/form[4]/input[2]")).click();
		pause(3000);

		// リストボックス（肉じゃが・インデックス指定）
		Select dropdown = new Select(driver.findElement(By.name("ryouri")));
		dropdown.selectByIndex(1);
		pause(1000);
		// リストボックス（すき焼き・value指定）
		dropdown.selectByValue("sukiyaki");
		pause(1000);
		// リストボックス（カレー・項目名指定）
		dropdown.selectByVisibleText("カレーライス");
		pause(3000);

		// ボタン押下
		driver.findElement(By.xpath("/html/body/form[6]/input")).click();

	}


	/**
	 * サンプルケース１（画面キャプチャ取得）
	 */
	@Test
	public void test001() {

		beforeOperation();

		// 電話番号を追加
//		driver.findElement(By.xpath("/html/body/form/table[6]/tbody/tr[26]/td[2]/font/input")).sendKeys(paramList.get(0).getVal01());
		textInput(driver, "/html/body/form/table[6]/tbody/tr[26]/td[2]/font/input", paramList.get(0).getVal01());

		// FAX番号を追加
//		driver.findElement(By.xpath("/html/body/form/table[6]/tbody/tr[27]/td[2]/font/input")).sendKeys(paramList.get(0).getVal02());
		textInput(driver, "/html/body/form/table[6]/tbody/tr[27]/td[2]/font/input", paramList.get(0).getVal02());

		// スクリーンショット
		takeScreenshot("./result/test001_01" + EXTENSION);

		// 確認画面へ
//		driver.findElement(By.xpath("/html/body/form/table[8]/tbody/tr/td[2]/font/input")).click();
		click(driver, "/html/body/form/table[8]/tbody/tr/td[2]/font/input");

		// スクリーンショット
		takeSplitScreenshot("./result/test001_02" + EXTENSION);

	}

	/**
	 * サンプルケース２（エラーメッセージ確認）
	 */
	@Test
	public void test002() {

		beforeOperation();

		// 電話番号を追加
//		driver.findElement(By.xpath("/html/body/form/table[6]/tbody/tr[26]/td[2]/font/input")).sendKeys(paramList.get(1).getVal01());
		textInput(driver, "/html/body/form/table[6]/tbody/tr[26]/td[2]/font/input", paramList.get(1).getVal01());

		// FAX番号を追加
//		driver.findElement(By.xpath("/html/body/form/table[6]/tbody/tr[27]/td[2]/font/input")).sendKeys(paramList.get(1).getVal02());
		textInput(driver, "/html/body/form/table[6]/tbody/tr[27]/td[2]/font/input", paramList.get(1).getVal02());

		// スクリーンショット
		takeScreenshot("./result/test002_01" + EXTENSION);

		// 確認画面へ
//		driver.findElement(By.xpath("/html/body/form/table[8]/tbody/tr/td[2]/font/input")).click();
		click(driver, "/html/body/form/table[8]/tbody/tr/td[2]/font/input");

		// エラーメッセージ確認
		assertEquals("電話番号の書式が誤りです。(xxx-xxx-xxx)",
				driver.findElement(By.xpath("/html/body/form/table[6]/tbody/tr[27]/td[2]/font/font[2]")).getText());

		// スクリーンショット
		takeSplitScreenshot("./result/test002_02" + EXTENSION);

	}

}
