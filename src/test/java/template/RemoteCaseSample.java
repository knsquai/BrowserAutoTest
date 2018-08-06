package template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import common.RemoteBase;
import common.csv.CsvReader;
import common.csv.bean.DataBean;

public class RemoteCaseSample extends RemoteBase {

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

}
