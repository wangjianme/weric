package poi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
  
public class testPOIExcelStyle {  
      
    public static void main(String[] args) throws IOException{  
    	BufferedReader br = new BufferedReader(new FileReader("C:\\zhilian\\black\\black_����_hadoop.txt"));
    	String line = br.readLine();
    	//����������  
        HSSFWorkbook workBook = new HSSFWorkbook();  
        //����������  ����������ֽ�helloWorld  
        HSSFSheet sheet = workBook.createSheet("hadoop");  
        int i = 0;
    	while(line != null){
    		String[] values = line.split(" ");
    		//������,��3��  
            HSSFRow row = sheet.createRow(i); 
            
            HSSFCellStyle linkStyle = workBook.createCellStyle();
            HSSFFont cellFont= workBook.createFont();
            cellFont.setUnderline((byte) 1);
            cellFont.setColor(HSSFColor.BLUE.index);
            linkStyle.setFont(cellFont);
            
            //������Ԫ�񣬲��������е�����  
            HSSFCell cell = row.createCell(0);  
            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            cell.setCellFormula("HYPERLINK(\"" + values[1]+ "\",\"" + values[0]+ "\")");
            cell.setCellStyle(linkStyle);
            
            HSSFCell cell1 = row.createCell(1);  
            cell1.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            cell1.setCellFormula("HYPERLINK(\"https://www.baidu.com/s?wd="+values[0]+"&rsv_spt=1&rsv_iqid=0xc51aeba700023fa6&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=23&rsv_sug1=18&rsv_sug7=100\",\"�ٶ�һ��\")");
            cell1.setCellStyle(linkStyle);
            line = br.readLine();
            i++;
    	}
        workBook.write(new File("d:\\poi\\hadoop.xls"));  
        workBook.close();//���ǵùرչ�����  
    }  
}