package tools;

public class Menu
{
    private String title;
    private String[] options;
    private Input input = Input.getInstance();

    public Menu(String title, String[] options)
    {
        this.title = title;
        this.options = options;
    }

    private void printTitle()
    {
        System.out.println(Input.PURPLE + "~ " + title + " ~" + Input.RESET);
    }

    private void printOptions()
    {
        int i = 1;
        for (String option : options)
        {
            System.out.println(" " + i + ") " + option);
            i++;
        }
    }

    public String printMenu()
    {
        printTitle();
        printOptions();
        return input.getStr(Input.PURPLE + ">> Option: " + Input.RESET);
    }

}
