//https://www.javatpoint.com/login-form-java
package Connect; 
import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  
import java.lang.Exception;
import java.sql.*;

//create CreateLoginForm class to create login form  
//class extends JFrame to create a window where our component add  
//class implements ActionListener to perform an action on button click  
class CreateLoginForm extends JFrame implements ActionListener  
{  
    //initialize button, panel, label, and text field  
    JButton b1; 
    JButton b2; 
    JPanel newPanel;  
    JLabel userLabel, passLabel;  
    final JTextField  textField1, textField2;  
    // create a connection to UserProfile.db
    //call Connect.java to connect to database
      
    //calling constructor  
    CreateLoginForm()  
    {     
          
        //create label for username   
        userLabel = new JLabel();  
        userLabel.setText("Username");      //set label value for textField1  
          
        //create text field to get username from the user  
        textField1 = new JTextField(15);    //set length of the text  
  
        //create label for password  
        passLabel = new JLabel();  
        passLabel.setText("Password");      //set label value for textField2  
          
        //create text field to get password from the user  
        textField2 = new JPasswordField(15);    //set length for the password  
          
        //create submit button  
        b1 = new JButton("LOGIN"); //set label to button  
        b2 = new JButton("REGISTER"); //set label to button  
          
        //create panel to put form elements  
        newPanel = new JPanel(new GridLayout(3, 1));  
        newPanel.add(userLabel);    //set username label to panel  
        newPanel.add(textField1);   //set text field to panel  
        newPanel.add(passLabel);    //set password label to panel  
        newPanel.add(textField2);   //set text field to panel  
        newPanel.add(b1);           //set button to panel  
        newPanel.add(b2);           //set button to panel
          
        //set border to panel   
        add(newPanel, BorderLayout.CENTER);  
          
        //perform action on button click   
        b1.addActionListener(this);     //add action listener to button  
        b2.addActionListener(this);     //add action listener to button
        setTitle("LOGIN FORM");         //set title to the login form  
    }  
      
    //define abstract method actionPerformed() which will be called on button click   
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter  
    {  
        String userValue = textField1.getText();        //get user entered username from the textField1  
        String passValue = textField2.getText();        //get user entered pasword from the textField2  
        String path = "db/UserProfile.db";
        try (Connection conn = Connect.connect(path)) {
            try (conn) {
                //call connect and insert methods from Connect.java to connect to database
                String sql = "SELECT * FROM users WHERE username = ? AND user_password = ?";
                if (ae.getSource() == b1) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, userValue);
                        pstmt.setString(2, passValue);
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) {
                            // username and password match, do something
                            JOptionPane.showMessageDialog(this, "Username and password match!");
                            //System.out.println("Username and password match!");
                        } else {
                            // username and password don't match, create new user
                            JOptionPane.showMessageDialog(this, "Username and password do not match.");
                            System.out.println("Username and password do not match.");
                        }
                    }
                } else if (ae.getSource() == b2) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, userValue);
                        pstmt.setString(2, passValue);
                        //ResultSet rs = pstmt.executeQuery();
                        String ret = Connect.insert(userValue, passValue);
                        if (ret == "true") {
                            // new user
                            JOptionPane.showMessageDialog(this, "New user created!");
                        } else if (ret == "false"){
                            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.");
                        } else {
                            // new user not created
                            JOptionPane.showMessageDialog(this, "New user not created. " + ret);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }  
}  
//create the main class  
class LoginFormDemo  
{  
    //main() method start  
    public static void main(String arg[]) throws ClassNotFoundException  
    {  
        try  
        {  
            //create instance of the CreateLoginForm  
            CreateLoginForm form = new CreateLoginForm();  
            form.setSize(300,100);  //set size of the frame  
            form.setVisible(true);  //make form visible to the user  
        }  
        catch(Exception e)  
        {     
            //handle exception   
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }  
    }  
}  
