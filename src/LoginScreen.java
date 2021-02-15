import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.sql.Connection;

public class LoginScreen extends UIScreen {


  //  Connection conn= null;

    LoginScreen(){
        super();

     //   conn= DB_Connection.connect(); // Creating DB connection by calling connect method inside DB_Connection class.

        f=new JFrame();
        pcs = new PropertyChangeSupport(this);

        final JLabel jl =new JLabel("BlackJack Game");
        jl.setBounds(70,50,150,20);

        final JLabel jl2 =new JLabel("Username:");
        jl2.setBounds(70,70, 150,20);

        final JTextField tf1=new JTextField();
        tf1.setBounds(70,90, 150,20);

        final JLabel jl3 =new JLabel("Password:");
        jl3.setBounds(70,120, 150,20);

        final JPasswordField tf2=new JPasswordField();
        tf2.setBounds(70,140, 150,20);
        JButton b=new JButton("Log In");
        b.setBounds(90,180,100, 40);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                System.out.println("Log in");
//                try{
//
//                    //Querying the table to verify the entered username and password
//                    String query="Select * from Player where username= ? and password= ? ";
//                    PreparedStatement st= conn.prepareStatement(query);
//                    String us= tf1.getText();
//                    String pa= tf2.getText();
//                    st.setString(1,tf1.getText());
//                    st.setString(2,tf2.getText());
//                    ResultSet re= st.executeQuery();
//                    int count=0;
//                    while(re.next()){
//                        count+=1;
//                        // Showing the alert messages.
//                        if(count==1){
//                            JOptionPane.showMessageDialog(null,"Correct credentials");
//                        }
//                        else{
//                            JOptionPane.showMessageDialog(null,"Incorrect credentials");
//                        }
//                    }
//                    st.close();
//                    re.close();
//
//                }catch (Exception l) {
//                    JOptionPane.showMessageDialog(null,l);
//                }
                pcs.firePropertyChange("switch", "Login", "PrePlay");
            }
        });

        f.add(jl);
        f.add(jl2);
        f.add(tf1);
        f.add(jl3);
        f.add(tf2);
        f.add(b);

        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);


    }

}