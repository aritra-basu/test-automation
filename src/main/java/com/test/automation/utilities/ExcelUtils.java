package com.test.automation.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ExcelUtils {
    public static HSSFSheet ExcelWSheet;
    public static HSSFWorkbook ExcelWBook;
    public static HSSFRow Row;
    public static HSSFCell Cell;
    public static HSSFCellStyle Style;

    public static void setExcelFile(String Path,int sheetIndex) throws Exception {
        FileInputStream ExcelFile = new FileInputStream(Path);
        ExcelWBook = new HSSFWorkbook(ExcelFile);
        ExcelWSheet = ExcelWBook.getSheetAt(sheetIndex);     
    }
    
    
    public static String getCellData(int RowNum, int ColNum) throws Exception{
	  	Row = ExcelWSheet.getRow(RowNum);
		Cell = Row.getCell(ColNum);
		String CellData = Cell.getStringCellValue();
		return CellData;
	}
    
    
    public static void loadExcelSheetIntoMap(Map<Integer, Object[]> map)throws Exception{
		int key = 0;
		for(int i = 0; i <= ExcelWSheet.getLastRowNum(); i++){
			int rowNum = ExcelWSheet.getRow(i).getRowNum();
			key = rowNum++ ;
			Object[] mapObject = new Object[100];
			for (int j = 0; j <= ExcelWSheet.getRow(i).getPhysicalNumberOfCells(); j++){	
				if(!(ExcelWSheet.getRow(i).getCell(j) == null)){
					Cell = ExcelWSheet.getRow(i).getCell(j);
					mapObject[j] = Cell;
				}
						
			}
			map.put(key, mapObject);
			
		}
	
	}


	public static void writeToExcelColumn(Map<Integer, Object[]> map, String columnToWrite, String excelFile) throws Exception{
		int columnToWriteIndex = MapUtils.getMapObjectIndexByHeader(map, columnToWrite);
		for(Entry<Integer, Object[]> entry: map.entrySet()){
			Object[] obj = entry.getValue();
			ExcelWSheet.getRow(entry.getKey()).getCell(columnToWriteIndex).setCellValue(obj[columnToWriteIndex].toString());
		}
			
			FileOutputStream outFile = new FileOutputStream(excelFile);
			outFile.getChannel();
			ExcelWBook.write(outFile);
	        outFile.close();
	}



	public static void createExcelFromMap(Map<String, Object[]> map, String excelFile, String excelSheet) throws Exception {
		
		ExcelWBook = new HSSFWorkbook();
		ExcelWSheet = ExcelWBook.createSheet(excelSheet);
		int rowNum = 0;
		for(Entry<String, Object[]> entry: map.entrySet()){
			Row = ExcelWSheet.createRow(rowNum++);
			int cellNum = 0;
			Object[] obj = entry.getValue();
			for(Object ob : obj){	
				if(!(ob==null)){
					ExcelWSheet.autoSizeColumn(cellNum);
					Cell = Row.createCell(cellNum++);
					Cell.setCellValue(ob.toString());
					}
				}
			}
		
		
		FileOutputStream outFile = new FileOutputStream(new File(excelFile));
		
	    ExcelWBook.write(outFile);
	    outFile.close();
	
	}
	
public static void createExcelSheetFromMap(Map<String, Object[]> map, String excelFile, String excelSheet) throws Exception {

		ExcelWSheet = ExcelWBook.createSheet(excelSheet);
		int rowNum = 0;
		for(Entry<String, Object[]> entry: map.entrySet()){
			Row = ExcelWSheet.createRow(rowNum++);
			int cellNum = 0;
			Object[] obj = entry.getValue();
			for(Object ob : obj){	
				if(!(ob==null)){
					ExcelWSheet.autoSizeColumn(cellNum);
					Cell = Row.createCell(cellNum++);
					Cell.setCellValue(ob.toString());
					
					
					
					}
				}
			}
		
		
		FileOutputStream outFile = new FileOutputStream(new File(excelFile));
		
	    ExcelWBook.write(outFile);
	    outFile.close();
	
	}
	
	public static void createExcelSheetFromMapWithStringValue(Map<Integer, String> map, String excelFile, String excelSheet) throws Exception {
		
		ExcelWSheet = ExcelWBook.createSheet(excelSheet);
		int rowNum = 0;
		for(Entry<Integer, String> entry: map.entrySet()){
			Row = ExcelWSheet.createRow(rowNum++);
			int cellNum = 0;			
			String obj = entry.getValue();			
				if(!(obj==null)){
					ExcelWSheet.autoSizeColumn(cellNum);
					Cell = Row.createCell(cellNum);
					Cell.setCellValue(obj);
					
					
					
					
				}
			}
		
		
		FileOutputStream outFile = new FileOutputStream(excelFile);
		
	    ExcelWBook.write(outFile);
	    outFile.close();
	
	}
	

	
	public static void createExcelHeader(String[] header) throws Exception {
		Row = ExcelWSheet.createRow(0);
		for(int i = 0; i<header.length; i++){
			Cell = Row.createCell(i);
			Cell.setCellValue(header[i]);
			ExcelWSheet.autoSizeColumn(i);
		}
	}
	
	public static void createChart(String excelFile) throws Exception {
			DefaultPieDataset dataSet = new DefaultPieDataset();
			String chartLabel = null;
			Number chartData = 0;
			Row = ExcelWSheet.getRow(ExcelWSheet.getLastRowNum());
			for(int i = 2; i < Row.getPhysicalNumberOfCells(); i++){
				Cell = Row.getCell(i);
				chartData = Integer.parseInt(Cell.getStringCellValue());
				chartLabel = ExcelWSheet.getRow(0).getCell(i).toString();
				dataSet.setValue(chartLabel, chartData);
			}
			
			int width=640; /* Width of the chart */
            int height=480; /* Height of the chart */
            float quality=1;
			
			JFreeChart chart = ChartFactory.createPieChart("Test Report", dataSet);
			ByteArrayOutputStream chart_out = new ByteArrayOutputStream();          
            ChartUtilities.writeChartAsJPEG(chart_out,quality,chart,width,height);
			
            
            InputStream feed_chart_to_excel=new ByteArrayInputStream(chart_out.toByteArray());
            byte[] bytes = IOUtils.toByteArray(feed_chart_to_excel);
            
            int my_picture_id = ExcelWBook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            /* We can close Piped Input Stream. We don't need this */
            feed_chart_to_excel.close();
            /* Close PipedOutputStream also */
            chart_out.close();
            
            HSSFPatriarch drawing = ExcelWSheet.createDrawingPatriarch();
            /* Create an anchor point */
            ClientAnchor my_anchor = new HSSFClientAnchor();
            /* Define top left corner, and we can resize picture suitable from there */
            my_anchor.setCol1(1);
            my_anchor.setRow1(ExcelWSheet.getLastRowNum()+2);
            /* Invoke createPicture and pass the anchor point and ID */
            HSSFPicture  my_picture = drawing.createPicture(my_anchor, my_picture_id);
            /* Call resize method, which resizes the image */
            my_picture.resize();             
            /* Write changes to the workbook */
            FileOutputStream out = new FileOutputStream(excelFile);
            ExcelWBook.write(out);
            out.close();  
	}
	
	
	
	
	
}
