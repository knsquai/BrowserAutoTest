package common.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import common.csv.bean.DataBean;

/**
 * CsvReader
 */
public class CsvReader {

    private static final String[] HEADER = new String[] { "val01","val02","val03","val04","val05","val06","val07",
    	"val08","val09","val10","val11","val12","val13","val14","val15","val16","val17","val18","val19","val20",
    	"val21","val22","val23","val24","val25","val26","val27","val28","val29","val30" };

    /**
     * csvファイルからString配列のリストを返却
     * @param file
     * @return String配列のリスト
     */
    public List<String[]> opencsvToStringArray(File file) {
    	CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "SJIS"));
        	return reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return null;
    }

    /**
     * csvファイルからbeanリストを返却
     * @param file
     * @return beanリスト
     */
    public List<DataBean> opencsvToBean(File file) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "SJIS"), ',', '"', 1);
            ColumnPositionMappingStrategy<DataBean> strat = new ColumnPositionMappingStrategy<DataBean>();
            strat.setType(DataBean.class);
            strat.setColumnMapping(HEADER);
            CsvToBean<DataBean> csv = new CsvToBean<DataBean>();
            return csv.parse(strat, reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
