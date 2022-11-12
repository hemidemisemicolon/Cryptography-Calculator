/**
 * Represents a layout of a menu page, with a header, ordered list of options, and an input message.
 * 
 * @author sophia song
 * @version java 11.0.9, 6/6/22
 */
public class Menu
{
    private String header;
    private String[] options;

    /**
     * Constructs a menu layout based on the given header, options list, and user input message.
     */
    public Menu(String welcome, String[] optionsList)
    {
        header = welcome;
        options = optionsList;
    }

    /**
     * Prints this menu to the standard output.
     */
    public void printMenu()
    {
        System.out.println(header);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%-5s%s%n", (i + 1) + ".", options[i]);
        }
    }

    /**
     * Returns one of the options of the list of options given the option index.
     * 
     * @param optionIndex the index of the option
     * @return the option description associated with the index
     */
    public String getOption(int optionIndex)
    {
        String option = "";
        
        if (optionIndex - 1 >= 0)
        {
            option = options[optionIndex - 1];
        }
        
        return option;
    }
}