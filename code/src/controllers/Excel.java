package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import models.Address;
import models.Bill;
import models.BillDescription;
import models.Colony;
import models.Item;
import models.Statement;
import models.User;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
        static Workbook workbook = new XSSFWorkbook();
        //static CreationHelper createHelper = workbook.getCreationHelper();
        static{
            File file = new File("export");
            file.mkdir();
        }
        
        public static  void createUserSheet(ArrayList<User> data) throws FileNotFoundException, IOException{
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Customers");
            
            Row headerRow = sheet.createRow(0);
            System.out.println("usersheet");
        // Create cells
                headerRow.createCell(0).setCellValue("Sno.");
                headerRow.createCell(1).setCellValue("User Id");
                headerRow.createCell(2).setCellValue("User Name");
                headerRow.createCell(3).setCellValue("WhatsApp Number");
                headerRow.createCell(4).setCellValue("Mobile Number");
                headerRow.createCell(5).setCellValue("Address Id");
                headerRow.createCell(6).setCellValue("Serial Number");
            
            int rowNum = 1;
            
        for(User user: data) {
            System.out.println("usersheet loop");
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum);
            row.createCell(1).setCellValue(user.getUserId());
            row.createCell(2).setCellValue(user.getName());
            row.createCell(3).setCellValue(user.getWhatsApp());
            row.createCell(4).setCellValue(user.getMobile());
            row.createCell(5).setCellValue(user.getAddress().getAddressId());
            row.createCell(6).setCellValue(user.getSerialNumber());
        }
        CellStyle style = workbook.createCellStyle();
        for(int i = 0; i < 6; i++){
             //Create new style
            style.setWrapText(true); //Set wordwrap
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut = new FileOutputStream("export/Customers.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
        public static void createColonySheet(ArrayList<Colony> data) throws FileNotFoundException, IOException{
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Colony");
            
            Row headerRow = sheet.createRow(0);
            System.out.println("Colony Sheet");
        // Create cells
                headerRow.createCell(0).setCellValue("Sno.");
                headerRow.createCell(1).setCellValue("ColonyId");
                headerRow.createCell(2).setCellValue("ColonyName");
                headerRow.createCell(3).setCellValue("ColonyCode");
            
            int rowNum = 1;
            
        for(Colony c: data) {
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(rowNum++);
            row.createCell(1).setCellValue(c.getId());
            row.createCell(2).setCellValue(c.getColonyName());
            row.createCell(3).setCellValue(c.getColonyCode());
        }
        CellStyle style = workbook.createCellStyle();
        for(int i = 0; i < 4; i++){
             //Create new style
            //style.setWrapText(true); //Set wordwrap
            //sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut = new FileOutputStream("export/Colony.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
    
    public static void createItemSheet(ArrayList<Item> data) throws FileNotFoundException, IOException{
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Item");
            
            Row headerRow = sheet.createRow(0);
            System.out.println("Item Sheet");
            // Create cells
            headerRow.createCell(0).setCellValue("Sno.");
            headerRow.createCell(1).setCellValue("Item Id");
            headerRow.createCell(2).setCellValue("Item Name");
            headerRow.createCell(3).setCellValue("Item Code");

            int rowNum = 1;
            
            for(Item c: data) {
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(rowNum++);
                row.createCell(1).setCellValue(c.getId());
                row.createCell(2).setCellValue(c.getItemName());
                row.createCell(3).setCellValue(c.getItemCode());
            }
            CellStyle style = workbook.createCellStyle();
            for(int i = 0; i < 4; i++){
                style.setWrapText(true); //Set wordwrap
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream("export/Item.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
    }

    public static void createAddressSheet(ArrayList<Address> data) throws FileNotFoundException, IOException{
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Address");
            
            Row headerRow = sheet.createRow(0);
            System.out.println("Address Sheet");
            // Create cells
            headerRow.createCell(0).setCellValue("Sno.");
            headerRow.createCell(1).setCellValue("Address Id");
            headerRow.createCell(2).setCellValue("Area");
            headerRow.createCell(3).setCellValue("Phase/block/gali");
            headerRow.createCell(4).setCellValue("Colony id");
            headerRow.createCell(5).setCellValue("Floor");
            headerRow.createCell(6).setCellValue("House Number");

            int rowNum = 1;
            
            for(Address c: data){
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(rowNum++);
                row.createCell(1).setCellValue(c.getAddressId());
                row.createCell(2).setCellValue(c.getArea());
                row.createCell(3).setCellValue(c.getPhaseBlockGali());
                row.createCell(4).setCellValue(c.getColony().getId());
                row.createCell(5).setCellValue(c.getFloor());
                row.createCell(6).setCellValue(c.getHouseNumber());
            }
            CellStyle style = workbook.createCellStyle();
            for(int i = 0; i < 6; i++){
                style.setWrapText(true); //Set wordwrap
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream("export/Address.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
    }
    
    public static void createBillSheet(ArrayList<Bill> data) throws FileNotFoundException, IOException{
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Bill");
            
            Row headerRow = sheet.createRow(0);
            System.out.println("Bill Sheet");
            // Create cells
            headerRow.createCell(0).setCellValue("Sno.");
            headerRow.createCell(1).setCellValue("Bill Number");
            headerRow.createCell(2).setCellValue("User Id");
            headerRow.createCell(3).setCellValue("Bill Amount");
            headerRow.createCell(4).setCellValue("Billing Date");
            headerRow.createCell(5).setCellValue("Billing Time");
            headerRow.createCell(6).setCellValue("Order Status ");

            int rowNum = 1;
            
            for(Bill c: data){
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(rowNum++);
                row.createCell(1).setCellValue(c.getBillNo());
                row.createCell(2).setCellValue(c.getUser().getUserId());
                row.createCell(3).setCellValue(c.getBillAmount());
                row.createCell(4).setCellValue(c.getBillingDate());
                row.createCell(5).setCellValue(c.getBillingTime());
                row.createCell(6).setCellValue(c.getOrderStatus().toString());
            }
            CellStyle style = workbook.createCellStyle();
            for(int i = 0; i < 6; i++){
                style.setWrapText(true); //Set wordwrap
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream("export/Bills.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
    }
    
    public static void createBillDescriptionSheet(ArrayList<BillDescription> data) throws FileNotFoundException, IOException{
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("BillDescription");
            
            Row headerRow = sheet.createRow(0);
            // Create cells
            headerRow.createCell(0).setCellValue("Sno.");
            headerRow.createCell(1).setCellValue("Bill Number");
            headerRow.createCell(2).setCellValue("Item Id");
            headerRow.createCell(3).setCellValue("Item Name");
            headerRow.createCell(4).setCellValue("Quantity");
            headerRow.createCell(5).setCellValue("Unit Prize");
            headerRow.createCell(6).setCellValue("Order Status");

            int rowNum = 1;
            
            for(BillDescription c: data){
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(rowNum++);
                if(c.getBill()!=null){
                    row.createCell(1).setCellValue(c.getBill().getBillNo());
                    row.createCell(2).setCellValue(c.getItem().getId());
                }
                row.createCell(3).setCellValue(c.getItem().getItemName());
                row.createCell(4).setCellValue(c.getQuantity());
                row.createCell(5).setCellValue(c.getUnitPrize());
            }
            CellStyle style = workbook.createCellStyle();
            for(int i = 0; i < 5; i++){
                style.setWrapText(true); //Set wordwrap
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream("export/BillDescriptions.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
    }
    

}
