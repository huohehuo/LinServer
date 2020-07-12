package App4GoodsNotes;

import ServerVueWeb.Bean.BuyAtBean;
import Utils.Lg;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/*https://blog.csdn.net/ethan_10/article/details/80335350*/
public class ExcelExportUtil {


   // 第一步，创建一个webbook，对应一个Excel文件
   public HSSFWorkbook generateExcel() {
      return new HSSFWorkbook();
   }

   //处理公司信息表对应的xls位置数据
   public HSSFWorkbook generateExcelSheet(HSSFWorkbook wb, String sheetName, String[] fields, List<BuyAtBean> list) {

      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
      HSSFSheet sheet = wb.createSheet(sheetName);

//      sheet.setDefaultRowHeightInPoints(30);//设置缺省列宽
//      sheet.setDefaultColumnWidth(50);//设置缺省列高

      HSSFCellStyle style = wb.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
      style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//水平居中
      style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

      // 设置普通单元格字体样式
      HSSFFont font = wb.createFont();
      font.setFontName("宋体");
      font.setFontHeightInPoints((short) 12);//设置字体大小
      style.setFont(font);
      style.setBorderLeft(BorderStyle.THIN);
      style.setBorderBottom(BorderStyle.THIN);
      style.setBorderRight(BorderStyle.THIN);
      style.setBorderTop(BorderStyle.THIN);
      style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
      style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
      style.setTopBorderColor(IndexedColors.BLACK.getIndex());
      style.setRightBorderColor(IndexedColors.BLACK.getIndex());



      // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
      HSSFRow rowH = sheet.createRow(0);
      rowH.setHeight((short) 1000);
      sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));

      HSSFCell cellHead;
      cellHead = rowH.createCell(0);
      cellHead.setCellValue(list.get(0).FBuyName);
      cellHead.setCellStyle(style);
      // 第四步，创建单元格，并设置值表头 设置表头居中
      //设置表头字段名
      HSSFCell cell;
      int m=0;
      HSSFRow row = sheet.createRow(1);
      row.setHeight((short) 500);
      for (int i = 0; i < fields.length; i++) {
         cell = row.createCell(m);
         cell.setCellValue(fields[i]);
         cell.setCellStyle(style);
         if (i==0){//设置指定列的宽度
            sheet.setColumnWidth(cell.getColumnIndex(), 4000);
         }else if (i==1){
            sheet.setColumnWidth(cell.getColumnIndex(), 5000);
         }else if (i==2){
            sheet.setColumnWidth(cell.getColumnIndex(), 4000);
         }else{
            sheet.setColumnWidth(cell.getColumnIndex(), 3000);
         }
         m++;
      }
      for(String fieldName:fields){//设置首行的说明行

      }
      Lg.e("得到list",list);
      HSSFCell hssfCell;
//      font.setFontHeightInPoints((short) 12);
//      style.setFont(font);
      for (int i = 0; i < list.size(); i++)
      {
         row = sheet.createRow(i + 2);
         row.setHeight((short) 388);
         BuyAtBean data = list.get(i);
         // 第五步，创建单元格，并设置值
         int pos =0;
         hssfCell =row.createCell(pos);hssfCell.setCellValue("");hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue("");hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue(data.FModelName);hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue(data.FStuffName);hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue(data.FColorName);hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue(data.FNum);hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue(data.FUnitName);hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue(data.FPrice);hssfCell.setCellStyle(style);pos++;
         hssfCell =row.createCell(pos);hssfCell.setCellValue(data.FSum);hssfCell.setCellStyle(style);pos++;
      }

      return wb;
   }

   public boolean writeExcelToDisk(HSSFWorkbook wb, String fileName){
      try {
//         String fileAddress = "C:/LinsServer/AppExcel4GoodsNotes/";
         String fileAddress = BaseUtil.baseFileUrl;
         File f = new File(fileAddress);
         if (!f.exists()) {
            f.mkdirs();
         }
         File file = new File(fileAddress + fileName);
         FileOutputStream fops = new FileOutputStream(file);

         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         wb.write(baos);

         byte[] xlsBytes = baos .toByteArray();
         fops.write(xlsBytes);
         fops.flush();
         fops.close();
         System.out.println("数据已写入");
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   public void export(HSSFWorkbook wb,String fileName, HttpServletResponse response){
      // 第六步，实现文件下载保存
      try
      {

         response.setHeader("content-disposition", "attachment;filename="
                 + URLEncoder.encode(fileName, "utf-8") + ".xls");

         OutputStream out = response.getOutputStream();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         wb.write( baos);
         byte[] xlsBytes = baos .toByteArray();
         out.write( xlsBytes);
         out.close();

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
