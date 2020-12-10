package tools;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Import {

    public Import() {
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

            for(int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {
                    for(int c = 0; c < cols; c++) {
                        cell = row.getCell((short)c);
                        if(cell != null) {
                            System.out.println(cell);
                        }
                    }
                }
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
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
