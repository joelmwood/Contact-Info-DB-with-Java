/**@Author Joel M. Wood
 * This program uses the Derby Database to access and display a list/table
 * of contacts and the associated information. Information saved in the table
 * is Name, Age, Email, and phone number. The driver needed to run this 
 * application is made accessible through the Project Properties - Libraries
 * tab.
 * 
 */

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ContactInputForm extends JFrame{
    private JLabel addNameLabel;
    private JTextField addNameTextField;
    
    private JLabel addAgeLabel;
    private JTextField addAgeTextField;
    
    private JLabel addEmailLabel;
    private JTextField addEmailTextField;
    
    private JLabel addCellLabel;
    private JTextField addCellTextField;
    
    private JButton addInfoButton;
    
    private final int TXTFLDWDTH = 120;
    private final int TXTFLDHGHT = 21;
    
    //static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/contact";
    static final String USER = "nbuser";
    static final String PASS = "nbuser";
    
    public ContactInputForm(){
        
        super("Contact Info Database");
        
        try{
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
       }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
       }finally{            
            
       }
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
               
        String[] colNames = {"Name", "Age", "Email", "Cell#"};
        
        DefaultTableModel tableModel = new DefaultTableModel(colNames, 0);
        JTable table = new JTable(tableModel);
        
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "Select * FROM CONTACTS";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("Name");
                String age = rs.getString("Age");
                String email = rs.getString("Email");
                String cell = rs.getString("Cell"); 
                      
                String test[] ={name,age,email,cell};
                tableModel.addRow(test);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception se){
            //Handle errors for Class.forName
            se.printStackTrace();                 
        }
                
        JMenuItem clear = new JMenuItem("Clear Fields");
        fileMenu.add(clear);
        
        fileMenu.addSeparator();  //add separator line between menu's items
        
        JMenuItem exitFileMenu = new JMenuItem("Exit");
        fileMenu.add(exitFileMenu);
        
        addNameLabel = new JLabel("First Name");
        addNameLabel.setBounds(16, 16, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addNameLabel);
        addNameTextField = new JTextField(15);
        addNameTextField.setBounds(100, 16, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addNameTextField);
            
        addAgeLabel = new JLabel("Age");
        addAgeLabel.setBounds(16, 46, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addAgeLabel);
        addAgeTextField = new JTextField(15);
        addAgeTextField.setBounds(100, 46, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addAgeTextField);
              
        addEmailLabel = new JLabel("Email ");
        addEmailLabel.setBounds(16, 76, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addEmailLabel);
        addEmailTextField = new JTextField(10); 
        addEmailTextField.setBounds(100, 76, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addEmailTextField);
        
        addCellLabel = new JLabel("Cell# ");
        addCellLabel.setBounds(16, 106, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addCellLabel);
        addCellTextField = new JTextField(10);
        addCellTextField.setBounds(100, 106, TXTFLDWDTH, TXTFLDHGHT);
        contentPane.add(addCellTextField);
               
        addInfoButton = new JButton("Add to table");
        addInfoButton.setBounds(16, 136, TXTFLDWDTH, TXTFLDHGHT );
        contentPane.add(addInfoButton);
        
        JButton deleteRow = new JButton("Delete Selected Row");
        deleteRow.setBounds( 146, 136, 160, 21 );       
        contentPane.add(deleteRow);
        
        addInfoButton.addActionListener((ActionEvent e) -> {
            int age;
            try{
                age = Integer.parseInt(addAgeTextField.getText());
            }catch(NumberFormatException n){
                //n.printStackTrace();
                JOptionPane.showMessageDialog
                    (null,"Please make sure a number is in the age field.",
                    "Age Field Error", JOptionPane.WARNING_MESSAGE);
            }//end try-catch
            age = Integer.parseInt(addAgeTextField.getText());
            if((addNameTextField.getText().length()==0)||
                    (addAgeTextField.getText().length()==0)||
                    (addCellTextField.getText().length()==0)){
                JOptionPane.showMessageDialog
                    (null,"Please make sure information is in each entry field.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            //}else if(Integer.parseInt(addAgeTextField.getText())){
            }else if((age<0)||(age>120)){
                JOptionPane.showMessageDialog
                    (null,"Please enter an age between 0-120 ",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
                
            }else{
                String a = addNameTextField.getText();
                String b = addAgeTextField.getText();
                String c = addEmailTextField.getText();
                String d = addCellTextField.getText();
                String test[]={a,b,c,d};
                tableModel.addRow(test);
                
                try{
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement();
                    String sql = "INSERT INTO CONTACTS (Name, Age, Email, Cell)"
                            + "VALUES ('"+a+"', '"+b+"', '"+c+"', '"+d+"')";
                    stmt.executeUpdate(sql);
                    
                    stmt.close();
                    conn.close();
                }catch(SQLException se){
                    //Handle errors for JDBC
                    se.printStackTrace();
                }catch(Exception se){
                     //Handle errors for Class.forName
                     se.printStackTrace();                 
                }
                
                addNameTextField.setText(null);
                addAgeTextField.setText(null);
                addEmailTextField.setText(null);
                addCellTextField.setText(null);                
            }//end if/else
        });//end addInfoButton Action Listener
                      
        JScrollPane scrollPane = new JScrollPane(table); 
        scrollPane.setBounds(16, 226, 350, 101 );
        contentPane.add(scrollPane);
        
        deleteRow.addActionListener((ActionEvent e) -> {                        
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                String nameX = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
                String sql = "DELETE FROM CONTACTS WHERE Name='"+nameX+"'";
                //int deleteCount = 
                stmt.executeUpdate(sql);
                 
                stmt.close();
                conn.close();
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }catch(Exception se){
                //Handle errors for Class.forName
                se.printStackTrace();                 
            }
            
            tableModel.removeRow(table.getSelectedRow());
        });
        
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
		addNameTextField.setText(null);
                addAgeTextField.setText(null);
                addEmailTextField.setText(null);
                addCellTextField.setText(null);
            }
	});//end exit button action listener
    
        exitFileMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
				System.exit(0);
            }
	});//end exit button action listener
        
    }//end ContactInfoForm
}//end class
