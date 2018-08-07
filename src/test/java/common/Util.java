package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

public class Util {

	public static void main(String[] args) {

		System.out.println("ProductKey:" + getPKey());

	}

	private static String getPKey() {

        Runtime runtime = Runtime.getRuntime();
        String[] Command = new String[]{ "wmic.exe", "path", "SoftwareLicensingService", "get", "OA3xOriginalProductKey" };

        try {
            Process p = null;
            p = runtime.exec(Command);
            p.waitFor(); // プロセスの正常終了まで待機

            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break; // 全ての行を読み切ったら抜ける
                } else {
                	if (line.matches(".*[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}.*")) {
                        return StringUtils.trim(line);
                	}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
	}
}
