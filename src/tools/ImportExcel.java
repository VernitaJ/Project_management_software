package tools;


import entities.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;


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

    public ImportExcel(UserLibrary userLibrary, TeamLibrary teamLibrary, ProjectLibrary projectLibrary,User currentUser) {
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
                    confirmObjectType(row, row.getCell(0), currentUser);
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
        
        System.out.println("Test");
        userLibrary.printAllUsers();
        projectLibrary.printAllProjects();
    }

    /*
                1. Set numbers for all the headers.
                2. Match numbers with keywords from the software.
                3. Loop over the rest of the data.
                4. Add the cell data into the appropriate software data slot.
             */
    
    private void confirmObjectType(XSSFRow row, XSSFCell cell, User currentUser) {
        if(cell.toString().equalsIgnoreCase("Project")) {
            // Run Project Import
            importProject(row, currentUser);
        } else if(cell.toString().equalsIgnoreCase("User")) {
            // User Import
            importUser(row, currentUser);
        } else if(cell.toString().equalsIgnoreCase("Task")) {
            // Task Import
            importTask(row, currentUser);
        } else if(cell.toString().equalsIgnoreCase("Worklog")) {
            // Run Workload Import
        }
    }
    
    private void importProject(XSSFRow row, User currentUser) {
        // Index/Column 1-6
        ProjectLibrary.getInstance().addProjectToList(
                new Project(row.getCell(1).toString(),
                        currentUser,
                        row.getCell(2).toString(),
                        LocalDate.parse(row.getCell(3).toString()),
                        LocalDate.parse(row.getCell(4).toString())));
       /*
       1 = project id
       2 = project desc.
       3 = start date
       4 = end date
       5 = budget hour
       6 = budget money
        */
        ProjectLibrary.getInstance().printAllProjects();
        System.out.println("Project added successfully.");
       
    }
    
    private void importUser(XSSFRow row, User currentUser) {
        if(row.getCell(30).toString().equalsIgnoreCase(currentUser.getUserName())){
            return;
        }
        UserLibrary.getInstance().addUserToList(
                new User(row.getCell(30).toString(),
                        row.getCell(31).toString(),
                        row.getCell(32).toString(),
                        row.getCell(33).toString(),
                        row.getCell(34).toString(),
                        100.0f,
                        parseFloat(row.getCell(35).toString())));
        System.out.println("User added successfully.");
       /*
       30-35
        */
    }
    
    private void importTask(XSSFRow row, User currentUser) {
        String tasksProject = row.getCell(1).toString();
        ProjectLibrary projectLibrary = ProjectLibrary.getInstance();
        if(projectLibrary.projectNameExists(tasksProject) != null) {
            Project currentProject = projectLibrary.projectNameExists(tasksProject);
            currentProject.getTaskList().addTaskToList(currentProject,
                    currentUser,
                    row.getCell(6).toString(),
                    "",
                    LocalDate.parse(row.getCell(3).toString()),
                            LocalDate.parse(row.getCell(4).toString()));
            System.out.println("Task added successfully.");
        } else {
            return;
        }
        /*
        1 = Project
        3 = Start Date
        4 = End Date
        6 = Desc
        8 = Est. Hours
        */
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
