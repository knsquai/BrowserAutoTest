package template;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import common.Base;

public class SampleTestCase extends Base {

	/**
	 * BaseクラスにsetUpBeforeClass()、setUp()を定義しているため、
	 * extendsする場合は子クラスに定義しないこと。
	 */

	@Test
	public void test001() {

		driver.get("https://auctions.yahoo.co.jp/sell/f/imgup/");

		// ID
		driver.findElement(By.xpath("")).sendKeys("");

		// PW
		driver.findElement(By.xpath("")).sendKeys("");

		// ボタン押下
		driver.findElement(By.xpath("")).click();


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
		pause(2000);

		// 単一行フィールドへの文字入力
		driver.findElement(By.xpath("/html/body/form[1]/input")).sendKeys("単一行");
		pause(2000);

		// 複数行フィールドへの文字入力
		driver.findElement(By.xpath("/html/body/form[2]/textarea")).sendKeys("複\n数\n行");
		pause(2000);

		// ラジオボタン(男)
		driver.findElement(By.xpath("/html/body/form[3]/input[1]")).click();
		pause(2000);
		// ラジオボタン(女)
		driver.findElement(By.xpath("/html/body/form[3]/input[2]")).click();
		pause(2000);

		// チェックボックス(アメリカ)
		driver.findElement(By.xpath("/html/body/form[4]/input[1]")).click();
		pause(1000);
		// チェックボックス(イタリア)
		driver.findElement(By.xpath("/html/body/form[4]/input[2]")).click();
		pause(2000);

		// リストボックス（肉じゃが・インデックス指定）
		Select dropdown = new Select(driver.findElement(By.name("ryouri")));
		dropdown.selectByIndex(1);
		pause(1000);
		// リストボックス（すき焼き・value指定）
		dropdown.selectByValue("sukiyaki");
		pause(1000);
		// リストボックス（カレー・項目名指定）
		dropdown.selectByVisibleText("カレーライス");
		pause(2000);

		// ボタン押下
		driver.findElement(By.xpath("/html/body/form[6]/input")).click();

	}

}
