package tools;


import access_roles.RoleFactory;
import entities.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
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
    ProjectLibrary projectLibrary;
    UserLibrary userLibrary;
    User currentUser;
    RoleFactory roleFactory = new RoleFactory();

    public ImportExcel(UserLibrary userLibrary, ProjectLibrary projectLibrary, User currentUser) {
        this.projectLibrary = projectLibrary;
        this.userLibrary = userLibrary;
        this.currentUser = currentUser;
        loopExcel();
        //userLibrary.printAllUsers();
        //projectLibrary.printAllProjects();

    }

    private void loopExcel() {
        try {

            //POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(System.getProperty("user.home") + "/ProjectData.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(System.getProperty("user.home") + "/ProjectData.xlsx");
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            int rows;
            rows = sheet.getPhysicalNumberOfRows();
            int cols = 0;
            int tmp = 0;

            for (int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if (tmp > cols) cols = tmp;
                }
            }

            assignHeaders(sheet, cols);

            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    confirmObjectType(row, row.getCell(0));
                    for (int c = 0; c < cols; c++) {
                        cell = row.getCell(c);
                        if (cell != null) {
                        }
                    }
                }
            }
        } catch (Exception ioe) {
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
        if (cell.toString().equalsIgnoreCase("Project")) {
            importProject(row);
        } else if (cell.toString().equalsIgnoreCase("User")) {
            importUser(row);
        } else if (cell.toString().equalsIgnoreCase("Task")) {
            importTask(row);
        } else if (cell.toString().equalsIgnoreCase("Worklog")) {
            importWorkLog(row);
        }
    }

    private void importProject(XSSFRow row) {
        this.projectLibrary.addProjectToList(
                new Project(row.getCell(1).toString(),
                        this.currentUser,
                        row.getCell(2).toString(),
                        LocalDate.parse(row.getCell(3).toString()),
                        LocalDate.parse(row.getCell(4).toString())));
        this.projectLibrary.printAllProjects();
    }

    private void importUser(XSSFRow row) {
        if (row.getCell(30).toString().equalsIgnoreCase(this.currentUser.getUserName())) {
            return;
        }
        this.userLibrary.addUserToList(
                new User(row.getCell(30).toString(),
                        row.getCell(31).toString(),
                        row.getCell(32).toString(),
                        row.getCell(33).toString(),
                        row.getCell(34).toString(),
                        100.0f,
                        parseFloat(row.getCell(35).toString())));
    }

    private void importTask(XSSFRow row) {

        String projectName = row.getCell(1).toString();
        Project currentProject = this.projectLibrary.projectNameExists(projectName);
        if (currentProject == null) {
            return;
        }
        currentProject.getTaskList().addTaskToList(currentProject,
                this.currentUser,
                row.getCell(7).toString(),
                "",
                LocalDate.parse(row.getCell(3).toString()),
                LocalDate.parse(row.getCell(4).toString()));

    }

    private void importWorkLog(XSSFRow row) {
        String projectName = row.getCell(1).toString();
        Project currentProject = this.projectLibrary.projectNameExists(projectName);
        TaskLibrary taskLibrary = currentProject.getTaskList();
        String taskName = row.getCell(7).toString();
        Task currentTask = currentProject.getTaskList().taskNameExists(taskLibrary, taskName);
        User user = (User) this.userLibrary.findUserInList(row.getCell(18).toString().toLowerCase());
        if (currentProject == null) {
            return;
        }
        if (currentTask == null || taskName.equals("")) {
            return;
        }
        if (user == null) {
            return;
        }
        currentTask.addWorkedHours(
                new WorkedHours(
                        (User) userLibrary.findUserInList(row.getCell(18).toString().toLowerCase()),
                        parseDouble(row.getCell(13).toString())));

        addUserToProjectTeam(currentProject, user);
        assignUserToTask(currentTask, user);
    }

    private void addUserToProjectTeam(Project currentProject, User user) {
        if (!currentProject.getTeam().isMember(user)) {
            currentProject.getTeam().getMemberList().put(
                    user.getUserName(),
                    new TeamMember(
                            user,
                            roleFactory.createMaintainer()));
        }
    }

    private void assignUserToTask(Task currentTask, User user) {
        if (!currentTask.getAssignees().toString().contains(user.getUserName())) {
            currentTask.getAssignees().add(user);

        }
    }

    private void assignHeaders(XSSFSheet sheet, int cols) {
        this.headerList = new ArrayList<>();
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell;
        if (row != null) {
            for (int c = 0; c < cols; c++) {
                cell = row.getCell(c);
                if (cell != null) {
                    this.headerList.add(cell.toString());
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
}
