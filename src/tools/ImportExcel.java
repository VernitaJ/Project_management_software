package tools;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;


public class ImportExcel {
                /*
                - ROW
                | COL
                 */
    
    /*
        1. Set numbers for all the headers.
        2. Match numbers with keywords from the software.
        3. Loop over the rest of the data.
        4. Add the cell data into the appropriate software data slot.
     */
    ArrayList<String> headerList;

    public ImportExcel() {
        try {

            //POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(System.getProperty("user.home") + "/ProjectData.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(System.getProperty("user.home") + "/ProjectData.xlsx");
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            assignHeaders(sheet, cols);

            for(int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {
                    confirmObjectType(row, row.getCell(0));
                    for(int c = 0; c < cols; c++) {
                        cell = row.getCell(c);
                        if(cell != null) {
                           // System.out.println(cell);
                        }
                    }
                }
            }
            System.out.println(this.headerList);
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
    }

    /*
                1. Set numbers for all the headers.
                2. Match numbers with keywords from the software.
                3. Loop over the rest of the data.
                4. Add the cell data into the appropriate software data slot.
             */
    
   private void confirmObjectType(XSSFRow row, XSSFCell cell) {
       if(cell.toString().equalsIgnoreCase("Project")) {
           // Run Project Import
           importProject(row);
           System.out.println("Project");
       } else if(cell.toString().equalsIgnoreCase("User")) {
           // User Import
           System.out.println("User");
       } else if(cell.toString().equalsIgnoreCase("Task")) {
           // Task Import
           System.out.println("Task");
       } else if(cell.toString().equalsIgnoreCase("Worklog")) {
           // Run Workload Import
           System.out.println("Worklog");
       }
   }
   
   private void importProject(XSSFRow row) {
      // Index/Column 1-6
   }
   
    private void assignHeaders(XSSFSheet sheet,  int cols) {
        this.headerList = new ArrayList<>();
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell;
        if (row != null) {
            for (int c = 0; c < cols; c++) {
                cell = row.getCell(c);
                if (cell != null) {
                    headerList.add(cell.toString());
                }
            }
        }
    }
    
    // IMPORT
        // Read data from excel
        // Excel location in user home folder
        // Read all data
        // Generate JSON output file
        
        // JSON File Structure
            // User
            // Project
            // Task
            // Worklog
    
        // Read JSON output file into system
            // Check encrypted string for match with encrypted username of user importing.
            // If no string exists for the owner, boolean ownerExists is set to false.
    
    // EXPORT
        // If ownerExists is false, create a new row in JSON file with the username string encrypted.
        // Check user access against owner of JSON file.
        // Generate JSON output file
        
    
    // projekt:A, desc:rädda världen, start:2020-12-09 end:2020-12-12
}
