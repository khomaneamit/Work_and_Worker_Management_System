import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class login extends JFrame implements ActionListener
{
	JLabel user,pass;
	JTextField user1;
	JButton submit;
	JPasswordField pass1;
	String user2,pass2;
	
	login()
	{
		setTitle("Login Form");
		setSize(300,200);
		setLocation(500,150);
		setVisible(true);
		setLayout(null);
		setFont(new Font("Arial",Font.BOLD,14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		user=new JLabel("Username :");
		pass=new JLabel("Password :");
		user1=new JTextField();
		pass1=new JPasswordField();
		submit=new JButton("Submit");
		
		add(user);
			user.setBounds(50,20,80,25);
		add(user1);
			user1.setBounds(140,20,105,25);
		add(pass);
			pass.setBounds(50,65,105,25);
		add(pass1);
			pass1.setBounds(140,65,105,25);
		add(submit);
			submit.setBounds(100,110,100,25);
			
		submit.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		user2=user1.getText();
		pass2=pass1.getText();
		if(user2.equals("Admin"))
		{
			if(pass2.equals("admin"))
			{
				JOptionPane.showMessageDialog(null,"Login Successfull");
				new Project();
				this.setVisible(false);
			}
			else
				JOptionPane.showMessageDialog(null,"Invalid Password","Error",JOptionPane.ERROR_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null,"Invalid Username","Error",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String args[])
	{
		new login();
	}
}

