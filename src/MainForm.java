import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;


/**
 *  MainForm Class for containing variables and different data types
 *  Add extension of JFrame and implementing ActionListener
 */
public class MainForm extends JFrame implements ActionListener
{
    SpringLayout layout = new SpringLayout();
    FileManager fileManager = new FileManager();

    // Create 6 JLabel
    JLabel lblLabel, lblTitle, lblImage, lblLink, lblMaterials, lblHints;
    // Create 3 text field
    JTextField txtTitle, txtImage, txtLink;
    // Create 2 text area
    JTextArea txtMaterials, txtHints;
    // Create Find
    JLabel lblFind;
    JTextField txtFind;

    JButton btnNew, btnSave, btnDelete, btnFind, btnExit;

    // Create 4 navigation buttons
    JButton btnFirst, btnPrevious, btnNext,btnLast;
    JTextArea txtOutput;
    JButton btnSort, btnBinary, btnFilter;
    JLabel lblList;
    JTextField txtFilter;
    Idea[] ideasArray = new Idea[100];

    int numberOfEntries = 0;
    int currentEntry = 0;
    boolean isNewEntry = false;


    /**
     * Create a constructor to set up the window, layout, all the components
     * Add reading to the file and closing function
     * @throws HeadlessException
     */
    public MainForm() throws HeadlessException {
        setSize(650,600);
        setLocation(100,150);
        setTitle("Re-purposing Suggestions");
        setLayout(layout);

        // when window closes, stop running
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        // Create an object to change JLabel color
        Color color = Color.decode("#2471A3");
        // Create 5 Entry Fields
        BuildFormEntryField(color);
        // Create Find(Search) function
        BuildFindFuctionalityComponets(color);
        // Create new/save/delete buttons
        BuildFunctionalityComponents();
        // Create navigation buttons
        BuildNavigationButtons();
        // Create Exit button
        BuildExitButton();
        // Create sort/binary/filter button
        BuildSortFilterButtons();
        // Create output field
        BuildOutputArea(color);

        // Reading the file
        FileData data = fileManager.ReadFromFile();
        // If the data is null, display the warning message
        if (data.ideasData == null)
        {
            JOptionPane.showMessageDialog(this,"Error Loading File. Please shutdown and try again");
        }
        // Read the data file from the array
        else
        {
            ideasArray = data.ideasData;
            numberOfEntries = data.count;
        }
        DisplayCurrentEntry();
        setVisible(true);
    }

    private void BuildOutputArea(Color color) {
        lblList = UIBuilderLibrary.BuildJLabelInlineBelow(" Re-Purposing Suggestion List: ",10,layout,btnSort);
        lblList.setPreferredSize(new Dimension(600, 25));
        lblList.setBackground(color);
        lblList.setForeground(Color.white);
        lblList.setOpaque(true);
        add(lblList);

        txtOutput = new JTextArea();
        txtOutput.setLineWrap(true);
        txtOutput.setWrapStyleWord(true);

        JScrollPane outputPane = new JScrollPane(txtOutput);
        outputPane.setPreferredSize(new Dimension(600,150));
        layout.putConstraint(SpringLayout.NORTH,outputPane,10,SpringLayout.SOUTH, lblList);
        layout.putConstraint(SpringLayout.WEST,outputPane,20,SpringLayout.WEST, this);
        add(outputPane);
    }

    private void BuildSortFilterButtons() {
        btnSort= UIBuilderLibrary.BuildJButtonInlineBelow(135,25,"Sort by Idea Title:", 50, this, layout, lblHints);
        add(btnSort);
        btnBinary = UIBuilderLibrary.BuildJButtonInlineToRight(200,25,"Binary Search by Idea Title:",5,this,layout,btnSort);
        add(btnBinary);
        btnFilter = UIBuilderLibrary.BuildJButtonInlineToRight(120,25,"Filter by:",5,this,layout,btnBinary);
        add(btnFilter);
        txtFilter = UIBuilderLibrary.BuildJTextFieldInlineToRight(12,5,layout,btnFilter);
        add(txtFilter);
    }

    private void BuildExitButton() {
        btnExit = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Exit", 20, this, layout, btnFirst);
        add(btnExit);
    }

    private void BuildNavigationButtons() {
        btnFirst = UIBuilderLibrary.BuildJButtonInlineBelow(30,25,"|<",5,this,layout,btnDelete);
        btnFirst.setMargin(new Insets(0,0,0,0));
        add(btnFirst);
        btnPrevious = UIBuilderLibrary.BuildJButtonInlineToRight(30,25,"<<",0,this,layout,btnFirst);
        btnPrevious.setMargin(new Insets(0,0,0,0));
        add(btnPrevious);
        btnNext = UIBuilderLibrary.BuildJButtonInlineToRight(30,25,">>",0,this,layout,btnPrevious);
        btnNext.setMargin(new Insets(0,0,0,0));
        add(btnNext);
        btnLast = UIBuilderLibrary.BuildJButtonInlineToRight(30,25,">|",0,this,layout,btnNext);
        btnLast.setMargin(new Insets(0,0,0,0));
        add(btnLast);
    }

    private void BuildFunctionalityComponents() {
        btnNew = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"New", 15, this, layout, btnFind);
        add(btnNew);
        btnSave = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Save", 5, this, layout, btnNew);
        add(btnSave);
        btnDelete = UIBuilderLibrary.BuildJButtonInlineBelow(120,25,"Delete", 5, this, layout, btnSave);
        add(btnDelete);
    }

    private void BuildFindFuctionalityComponets(Color color) {
        // Create find label, text field and button
        lblFind = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor(" Find:   ", 500, 10, layout, this);
        lblFind.setBackground(color);
        lblFind.setForeground(Color.white);
        lblFind.setOpaque(true);
        add(lblFind);
        txtFind = UIBuilderLibrary.BuildJTextFieldInlineToRight(7,5,layout,lblFind);
        add(txtFind);
        btnFind = UIBuilderLibrary.BuildJButtonInlineBelow(120, 25,"Find", 10, this, layout,
                lblFind);
        add(btnFind);
    }

    private void BuildFormEntryField(Color color) {
        // Create 5 Labels
        lblLabel = UIBuilderLibrary.BuildJLabelWithNorthWestAnchor("Re-Purposing Suggestions",20,10,layout,this);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 20));
        lblLabel.setForeground(color);
        add(lblLabel);
        lblTitle = UIBuilderLibrary.BuildJLabelInlineBelow(" Idea Title: ",20,layout,lblLabel);
        lblTitle.setPreferredSize(new Dimension(120, 20));
        lblTitle.setBackground(color);
        lblTitle.setForeground(Color.white);
        lblTitle.setOpaque(true);
        add(lblTitle);
        lblImage = UIBuilderLibrary.BuildJLabelInlineBelow(" Image File: ",10,layout,lblTitle);
        lblImage.setPreferredSize(new Dimension(120, 20));
        lblImage.setBackground(color);
        lblImage.setForeground(Color.white);
        lblImage.setOpaque(true);
        add(lblImage);
        lblLink = UIBuilderLibrary.BuildJLabelInlineBelow(" Web Link: ",10,layout,lblImage);
        lblLink.setPreferredSize(new Dimension(120, 20));
        lblLink.setBackground(color);
        lblLink.setForeground(Color.white);
        lblLink.setOpaque(true);
        add(lblLink);
        lblMaterials = UIBuilderLibrary.BuildJLabelInlineBelow(" Primary Materials: ",10,layout,lblLink);
        lblMaterials.setPreferredSize(new Dimension(120, 20));
        lblMaterials.setBackground(color);
        lblMaterials.setForeground(Color.white);
        lblMaterials.setOpaque(true);
        add(lblMaterials);
        lblHints = UIBuilderLibrary.BuildJLabelInlineBelow(" Construction Hints: ",40,layout,lblMaterials);
        lblHints.setPreferredSize(new Dimension(120, 20));
        lblHints.setBackground(color);
        lblHints.setForeground(Color.white);
        lblHints.setOpaque(true);
        add(lblHints);


        // Create 3 text fields
        txtTitle = UIBuilderLibrary.BuildJTextFieldInlineToRight(29, 10,layout,lblTitle);
        add(txtTitle);
        txtImage = UIBuilderLibrary.BuildJTextFieldInlineToRight(29, 10,layout,lblImage);
        add(txtImage);
        txtLink = UIBuilderLibrary.BuildJTextFieldInlineToRight(29, 10,layout,lblLink);
        add(txtLink);

        // Create Text Area inside the scroll pane
        txtMaterials = new JTextArea();
        txtMaterials.setLineWrap(true);
        txtMaterials.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtMaterials);
        scroll.setPreferredSize(new Dimension(325,50));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        layout.putConstraint(SpringLayout.WEST,scroll,0,SpringLayout.WEST,txtLink);
        layout.putConstraint(SpringLayout.NORTH,scroll,10,SpringLayout.SOUTH,txtLink);
        add(scroll);

        txtHints = new JTextArea();
        txtHints.setLineWrap(true);
        txtHints.setWrapStyleWord(true);
        JScrollPane scrollHints = new JScrollPane(txtHints);
        scrollHints.setPreferredSize(new Dimension(325,50));
        scrollHints.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        layout.putConstraint(SpringLayout.WEST, scrollHints,10,SpringLayout.EAST,lblHints);
        layout.putConstraint(SpringLayout.NORTH, scrollHints,0,SpringLayout.NORTH,lblHints);
        add(scrollHints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // New button and save button
        if (e.getSource() != btnNew && e.getSource() != btnSave)
        {
            isNewEntry = false;
        }
        // New button when it's clicked
        if (e.getSource() == btnNew)
        {
            ClearFormForEntry();
        }
        // Save button
        if (e.getSource() == btnSave)
        {
            // When there is no existing entry, change to isNewEntry = true to save a new entry
            if (numberOfEntries == 0)
            {
                isNewEntry = true;
            }
            // Save new entry, create a new object and get the text from the text field
            if (isNewEntry == true)
            {
                // Display a message if the text field is null or blank
                if (isNullOrWhitespace(txtTitle.getText()))
                {
                    JOptionPane.showMessageDialog(this,"Please enter idea title");
                    return;
                }
                if (isNullOrWhitespace(txtImage.getText()))
                {
                    JOptionPane.showMessageDialog(this,"Please enter Image File");
                    return;
                }
                if (isNullOrWhitespace(txtLink.getText()))
                {
                    JOptionPane.showMessageDialog(this,"Please enter Web Link");
                    return;
                }
                if (isNullOrWhitespace(txtMaterials.getText()))
                {
                    JOptionPane.showMessageDialog(this,"Please enter Primary Materials");
                    return;
                }
                if (isNullOrWhitespace(txtHints.getText()))
                {
                    JOptionPane.showMessageDialog(this,"Please enter Suggestion Hints");
                    return;
                }

                else
                {
                    Idea newIdea = new Idea();
                    newIdea.Title = txtTitle.getText();
                    newIdea.Image = txtImage.getText();
                    newIdea.Link = txtLink.getText();
                    newIdea.Materials = txtMaterials.getText();
                    newIdea.Hints = txtHints.getText();

                    // Add the data into array
                    ideasArray[numberOfEntries] = newIdea;
                    numberOfEntries++;
                    currentEntry = numberOfEntries -1;
                    JOptionPane.showMessageDialog(this,"New entry added");
                }
            }
            else
            {
                // Save the existing record (Edit: overwrite the existing one)
                ideasArray[currentEntry].Title = txtTitle.getText();
                ideasArray[currentEntry].Image = txtImage.getText();
                ideasArray[currentEntry].Link = txtLink.getText();
                ideasArray[currentEntry].Materials = txtMaterials.getText();
                ideasArray[currentEntry].Hints = txtHints.getText();

                JOptionPane.showMessageDialog(this,"Entry details updated");
            }
            isNewEntry = false;
            // Write the data to the file
            fileManager.WriteToFile(ideasArray);
        }
        // Delete button when clicked
        if (e.getSource() == btnDelete)
        {
            // If there is no existing record, display a message
            if (numberOfEntries == 0)
            {
                JOptionPane.showMessageDialog(this,"No existing record to delete");
                return;
            }
            // If the idea title text field is null or blank, display a warning message
            if (isNullOrWhitespace(txtTitle.getText()))
            {
                JOptionPane.showMessageDialog(this,"Please select a record to delete");
                return;
            }
            // Warning message display when clicked the delete button
            int result = JOptionPane.showConfirmDialog(this, "Delete this entry?",
                    "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            // When the user select No, back to the window
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
            // When the user select Yes, Loop the array and shuffle the value, duplicate the value
            for (int i = currentEntry; i < numberOfEntries; i++)
            {
                // when the value is null, do not display and show the next one
                // if it is the last entry, the last entry is null and break the loop
                if (i == numberOfEntries - 1)
                {
                    ideasArray[i] = null;
                    break;
                }
                // Copy the value from the next array
                ideasArray[i] = ideasArray[i + 1];
            }
            // Decreasing the number of the entries when it is not the last entry
            numberOfEntries--;
            // If the last entry is deleted in the array, reduce the last array from the number of the entries
            if (currentEntry == numberOfEntries)
            {
                currentEntry = numberOfEntries - 1;
            }
            // Display a message when the selected entry is deleted
            JOptionPane.showMessageDialog(this,"The selected record deleted");
            // Display the current entry
            DisplayCurrentEntry();
            // Write to the file
            fileManager.WriteToFile(ideasArray);
        }
        // Navigation button
        if(e.getSource() == btnFirst)
        {
            currentEntry = 0;
            DisplayCurrentEntry();
        }

        if(e.getSource() == btnPrevious)
        {
            if(currentEntry !=0)
            {
                currentEntry--;
                DisplayCurrentEntry();
            }
        }

        if(e.getSource() == btnNext)
        {
            if(currentEntry != numberOfEntries -1)
            {
                currentEntry++;
                DisplayCurrentEntry();
            }
        }

        if (e.getSource() == btnLast)
        {
            currentEntry = numberOfEntries -1;
            DisplayCurrentEntry();
        }
        // Sorting and searching buttons
        if (e.getSource() == btnFind)
        {
            FindByImageTitle();
        }

        // Exit button
        if (e.getSource() == btnExit)
        {
            int result = JOptionPane.showConfirmDialog(this, "Close the window?",
                    "Window Closing", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            // When the user select No, back to the window
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
            System.exit(0);
        }

        // Filter by Primary Materials
        if (e.getSource() == btnFilter)
        {
            String filter = txtFilter.getText().toLowerCase();
            // If the text filed is null or black, display a warning message
            if (isNullOrWhitespace(filter))
            {
                JOptionPane.showMessageDialog(this,"Please enter any keyword of primary materials");

            }
            // Display a set title in the output area
            txtOutput.setText("Records matching filter:\n\n");
            boolean notFound = true;
            for (int i = 0; i < numberOfEntries; i++)
            {
                // If the keyword matches with the record of primary materials, display a result
                if (ideasArray[i].Materials.toLowerCase().contains(filter) == true)
                {
                    txtOutput.append(ideasArray[i].toString() + "\n");
                    notFound = false;
                }
            }
            // If it doesn't match, display the message
            if (notFound)
            {
                txtOutput.append("No matches found");
            }
        }

        // Sort button or binary button
        if (e.getSource() == btnSort || e.getSource() == btnBinary) {
            txtOutput.setText("Re-purposing Suggestions Sorted By Idea Title:\n\n");
            // Display a message when no record existed
            if (numberOfEntries == 0)
            {
                JOptionPane.showMessageDialog(this,"No existing record");
                return;
            }
            // Create a new array and copy the elements with data from our main array into it
            Idea[] sortedArray = new Idea[numberOfEntries];
            System.arraycopy(ideasArray, 0, sortedArray, 0, numberOfEntries);
            // Sort the array in ascending order
            Arrays.sort(sortedArray);

            for (int i = 0; i < numberOfEntries; i++) {
                txtOutput.append(sortedArray[i].toString() + "\n");
            }

            // Binary search
            if (e.getSource() == btnBinary)
            {
                // Check the keyword to match the record of idea title
                String searchTitle = txtFilter.getText().toUpperCase();
                int index = Arrays.binarySearch(sortedArray, searchTitle);
                if (index >= 0)
                {
                    txtOutput.append("\nSearch name: " + searchTitle + " was found at index: " + index + ".");
                }
                else
                {
                    txtOutput.append("\nSearch name: " + searchTitle + " was not found.");
                }
            }
        }
    }


    private boolean ClearFormForEntry() {
        // When the data array is full, show the message
        if (numberOfEntries >= ideasArray.length)
        {
            JOptionPane.showMessageDialog(this,"The file is full. Delete old entries before adding new ones.");
            return true;
        }
        // Clear the text field
        txtTitle.setText("");
        txtImage.setText("");
        txtLink.setText("");
        txtMaterials.setText("");
        txtHints.setText("");

        isNewEntry = true;
        return false;
    }

    private void DisplayCurrentEntry()
    {
        if(numberOfEntries == 0)
        {
            ClearFormForEntry();
            return;
        }
        txtTitle.setText(ideasArray[currentEntry].Title);
        txtImage.setText(ideasArray[currentEntry].Image);
        txtLink.setText(ideasArray[currentEntry].Link);
        txtMaterials.setText(ideasArray[currentEntry].Materials);
        txtHints.setText(ideasArray[currentEntry].Hints);
    }

    private void FindByImageTitle() {
        String keyword = txtFind.getText().toLowerCase();
        // If the find text field is null or blank, display a message
        if (isNullOrWhitespace(keyword) == true)
        {
            JOptionPane.showMessageDialog(this,"Please enter a keyword of idea title");
        }
        // Display a message when no record exists
        else if (numberOfEntries == 0)
        {
            JOptionPane.showMessageDialog(this,"No existing record to search");
        }
        // Check the keyword will match the record of idea title
        else
        {
            boolean notFound = true;
            for (int i = 0; i < numberOfEntries; i++) {
                if (ideasArray[i].Title.toLowerCase().contains(keyword)) {
                    currentEntry = i;
                    DisplayCurrentEntry();
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                JOptionPane.showMessageDialog(this, "No matches found!");
            }
        }
    }

    /**
     * Boolean method to check the value is null or black in the text field
     * @param s the string variable to check true or false
     * @return
     */
    public static boolean isNullOrWhitespace(String s)
    {
        return s == null || s.isBlank();
    }
}