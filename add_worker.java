import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;
import java.util.*;

public class add_worker extends JFrame implements ActionListener
{
    JMenuBar mbr;
	JMenu m1,m2,m3;
	JMenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8;
	
	JLabel label,name,email,address,skill,bdate,contact;
	JTextField name1,email1,address1,skill1,bdate1,contact1;
	JButton add,clear;
	Font f;
	
	Connection cn;
	PreparedStatement prstm;
	Statement stm;
	ResultSet rst;
	String name2,email2,address2,skill2,bdate2,contact2,wid;
	
	
	add_worker()
	{
		setTitle("New Worker Registration");
		setSize(500,400);
		setLocation(500,150);
		setVisible(true);
		setLayout(null);
		f=new Font("Arial",Font.BOLD,20);
		setFont(new Font("Arial",Font.BOLD,20));
		
		//Menubar element declaration
		mbr= new JMenuBar();
		m1=new JMenu("Project");
		m2=new JMenu("Worker");
		m3=new JMenu("Report");
		mi1=new JMenuItem("All Projects");
		mi2=new JMenuItem("New Project");
		mi3=new JMenuItem("Update Project");
		mi5=new JMenuItem("All Workers");
		mi6=new JMenuItem("New Worker");
		mi7=new JMenuItem("Allocate Worker");
		mi8=new JMenuItem("Project Report");
		mi4=new JMenuItem("Worker Report");
		
		//form elements declaration
		label=new JLabel("Add New Worker");
		name=new JLabel("Name :");
		email=new JLabel("Email :");
		address=new JLabel("Address :");
		skill=new JLabel("Skill :");
		bdate=new JLabel("Birth Date :");
		contact=new JLabel("Contact :");
		
		name1=new JTextField();
		email1=new JTextField();
		address1=new JTextField();
		skill1=new JTextField();
		bdate1=new JTextField();
		contact1=new JTextField();
		
		add=new JButton("Add");
		clear=new JButton("Clear");
		
		//add form element on frame
		add(label);
			label.setBounds(180,50,160,30);
		add(name);
			name.setBounds(40,100,85,30);
		add(name1);
			name1.setBounds(145,100,85,30);
		add(email);
			email.setBounds(270,100,85,30);
		add(email1);
			email1.setBounds(375,100,85,30);
		add(address);
			address.setBounds(40,150,85,30);
		add(address1);
			address1.setBounds(145,150,85,30);
		add(skill);
			skill.setBounds(270,150,85,30);
		add(skill1);
			skill1.setBounds(375,150,85,30);
		add(bdate);
			bdate.setBounds(40,200,85,30);
		add(bdate1);
			bdate1.setBounds(145,200,85,30);
		add(contact);
			contact.setBounds(270,200,85,30);
		add(contact1);
			contact1.setBounds(375,200,85,30);
		add(add);
			add.setBounds(100,250,100,30);
		add(clear);
			clear.setBounds(300,250,100,30);
		label.setFont(f);
		
		
		m1.add(mi1);	m1.add(mi2);	m1.add(mi3);
		m2.add(mi5);	m2.add(mi6);	m2.add(mi7);
		m3.add(mi3);	m3.add(mi8);
		mbr.add(m1);	mbr.add(m2);	mbr.add(m3);
		add(mbr);
		mbr.setBounds(0,0,500,30);
		
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi8.addActionListener(this);
		mi5.addActionListener(this);
		mi6.addActionListener(this);
		mi7.addActionListener(this);
		mi4.addActionListener(this);
		add.addActionListener(this);
		clear.addActionListener(this);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				new Project();
			}
		});
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==mi1)
		{
			new Project();
			this.setVisible(false);
		}
		if(e.getSource()==mi2)
		{
			new upload_project();
			this.setVisible(false);
		}
		if(e.getSource()==mi3)
		{
			new update_project();
			this.setVisible(false);
		}
		if(e.getSource()==mi4)
		{
			new worker_report();
			this.setVisible(false);
		}
		if(e.getSource()==mi5)
		{
			new all_workers();
			this.setVisible(false);
		}
		if(e.getSource()==mi7)
		{
			new allocate_worker();
			this.setVisible(false);
		}
		if(e.getSource()==mi8)
		{
			new project_report();
			this.setVisible(false);
		}
		
		if(e.getSource()==add)
		{
			name2=name1.getText();
			email2=email1.getText();
			address2=address1.getText();
			skill2=skill1.getText();
			bdate2=bdate1.getText();
			contact2=contact1.getText();
			
			try
			{
				cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
				stm=cn.createStatement();
				
				prstm=cn.prepareStatement("insert into worker(name,email,address,skill,bdate,contact,status) values('"+name2+"','"+email2+"','"+address2+"','"+skill2+"','"+bdate2+"','"+contact2+"','available')");
				prstm.execute();
				JOptionPane.showMessageDialog(null,"New Worker Added Successfully");
				name1.setText("");
				email1.setText("");
				address1.setText("");
				skill1.setText("");
				bdate1.setText("");
				contact1.setText("");
			}
			catch(Exception e1){
				System.out.println(e1);
			}
		}
		
		if(e.getSource()==clear)
		{
			name1.setText("");
			email1.setText("");
			address1.setText("");
			skill1.setText("");
			bdate1.setText("");
			contact1.setText("");
		}
	}
	
	public static void main(String args[])
	{
		new add_worker();
	}
}

