package com.sundeinfo.file.fulltext.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.*;

public class ExcelReader extends DefaultReader {

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    public ExcelReader(File file) {
        super(file);
    }

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public String read() {
        if (file == null){
            return "";
        }
        List<String[]> context = new ArrayList<String[]>();
        StringBuilder builder = new StringBuilder();
        try {
            //检查文件
            checkFile(file);
            //获得Workbook工作薄对象
            Workbook workbook = getWorkBook(file);
            //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回

            if(workbook != null){
                for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                    //获得当前sheet工作表
                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if(sheet == null){
                        continue;
                    }
                    //获得当前sheet的开始行
                    int firstRowNum  = sheet.getFirstRowNum();
                    //获得当前sheet的结束行
                    int lastRowNum = sheet.getLastRowNum();
                    //循环除了第一行的所有行
                    for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                        //获得当前行
                        Row row = sheet.getRow(rowNum);
                        if(row == null){
                            continue;
                        }
                        //获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        //获得当前行的列数
                        int lastCellNum = row.getPhysicalNumberOfCells();
                        String[] cells = new String[row.getPhysicalNumberOfCells()];
                        //循环当前行
                        for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                        }
                        context.add(cells);
                    }
                }
                workbook.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (String[] row:context) {
                builder.append(StringUtils.join(row,"\n"));
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\Jinni\\Desktop\\深信服服务续费合同(2019).xls";
        File file = new File(path);
        PdfReader reader = new PdfReader(file);
        String value = reader.read();
    }

    private void checkFile(File file) throws IOException {
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getName();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }

    private Workbook getWorkBook(File file) {
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            FileInputStream fileInputStream = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(fileInputStream);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(fileInputStream);
            }
        } catch (IOException e) {

        }
        return workbook;
    }

    private String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == NUMERIC){
            cell.setCellType(STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA://公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK://空值
                cellValue = "";
                break;
            case ERROR://故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

}
