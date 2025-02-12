/**
 *  Idea Class for passing 5 components into the array
 */
public class Idea implements Comparable
{
    public String Title;
    public String Image;
    public String Link;
    public String Materials;
    public String Hints;

    /**
     *  A first constructor with no arguments
     */
    public Idea()
    {

    }

    /**
     *  A second constructor with 5 arguments
     * @param title  Idea title variable with String data type
     * @param image  Image file variable with String data type
     * @param link   Web link variable with String data type
     * @param materials Primary materials variable with String data type
     * @param hints Suggestion hints variable with String data type
     */
    public Idea(String title,String image,String link,String materials,String hints)
    {
        Title = title;
        Image = image;
        Link = link;
        Materials = materials;
        Hints = hints;
    }

    /**
     * Overwrite data converting to String and split them with ;
     * @return
     */
    @Override
    public  String toString()
    {
        return Title + ";" + Image + ";" + Link + ";" + Materials + ";" + Hints;
    }

    /**
     * Overwrite and compare idea title when sorting
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Object other)
    {
        if (other instanceof String)
        {
            String searchTerm = (String)other;
            return this.Title.toLowerCase().compareTo(searchTerm.toLowerCase());
        }
        Idea otherIdea = (Idea)other;
        return this.Title.toLowerCase().compareTo(otherIdea.Title.toLowerCase());
    }

}
