import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;
import java.util.*;

public class upload_project extends JFrame implements ActionListener
{
    JMenuBar mbr;
	JMenu m1,m2,m3;
	JMenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8;
	
	JLabel label,project_type,work_type,sdate,edate,wlocation,amount,tasks;
	JComboBox pro_type,wo_type;
	JTextField wloc,sdate1,edate1,amt;
	TextArea task;
	JButton add,clear;
	Font f;
	
	Connection cn;
	PreparedStatement prstm;
	Statement stm;
	ResultSet rst;
	String ptype,wtype,loc,start,end,t,amount1,pid;
	
	
	upload_project()
	{
		setTitle("New Work Registration");
		setSize(500,500);
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
		label=new JLabel("Add New Project");
		project_type=new JLabel("Project Type :");
		work_type=new JLabel("Work Type :");
		wlocation=new JLabel("Location :");
		sdate=new JLabel("Start Date :");
		edate=new JLabel("End Date :");
		amount=new JLabel("Amount :");
		tasks=new JLabel("Description :");
		
		pro_type=new JComboBox();
		pro_type.addItem("Private");
		pro_type.addItem("Industrial");
		wo_type=new JComboBox();
		wo_type.addItem("Mechanical");
		wo_type.addItem("Electrical");
		wo_type.addItem("Civil");
		wo_type.addItem("Chemical");
		wo_type.addItem("Software");
		
		wloc=new JTextField();
		sdate1=new JTextField();
		edate1=new JTextField();
		amt=new JTextField();
		
		task=new TextArea();
		
		add=new JButton("Add");
		clear=new JButton("Clear");
		
		//add form element on frame
		add(label);
			label.setBounds(180,50,160,30);
		add(project_type);
			project_type.setBounds(40,100,85,30);
		add(pro_type);
			pro_type.setBounds(145,100,85,30);
		add(work_type);
			work_type.setBounds(270,100,85,30);
		add(wo_type);
			wo_type.setBounds(375,100,85,30);
		add(wlocation);
			wlocation.setBounds(40,150,85,30);
		add(wloc);
			wloc.setBounds(145,150,85,30);
		add(sdate);
			sdate.setBounds(270,150,85,30);
		add(sdate1);
			sdate1.setBounds(375,150,85,30);
		add(edate);
			edate.setBounds(40,200,85,30);
		add(edate1);
			edate1.setBounds(145,200,85,30);
		add(amount);
			amount.setBounds(270,200,85,30);
		add(amt);
			amt.setBounds(375,200,85,30);
		add(tasks);
			tasks.setBounds(40,250,420,30);
		add(task);
			task.setBounds(40,282,420,100);
		add(add);
			add.setBounds(100,400,100,30);
		add(clear);
			clear.setBounds(300,400,100,30);
		label.setFont(f);
		
		
		m1.add(mi1);	m1.add(mi2);	m1.add(mi3);
		m2.add(mi5);	m2.add(mi6);	m2.add(mi7);
		m3.add(mi4);	m3.add(mi8);	
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
		if(e.getSource()==mi6)
		{
			new add_worker();
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
			ptype=pro_type.getSelectedItem().toString();
			wtype=wo_type.getSelectedItem().toString();
			loc=wloc.getText();
			start=sdate1.getText();
			end=edate1.getText();
			amount1=amt.getText();
			t=task.getText();
			
			try
			{
				cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
				stm=cn.createStatement();
				
				prstm=cn.prepareStatement("insert into project(project_type,work_type,project_loc,start_date,completed_date,amount,status,tasks) values('"+ptype+"','"+wtype+"','"+loc+"','"+start+"','"+end+"','"+amount1+"','not completed','"+t+"')");
				prstm.execute();
				JOptionPane.showMessageDialog(null,"New Project Added Successfully");
				wloc.setText("");
				sdate1.setText("");
				edate1.setText("");
				amt.setText("");
				task.setText("");
			}
			catch(Exception e1){
				System.out.println(e1);
			}
		}
		if(e.getSource()==clear)
		{
			wloc.setText("");
			sdate1.setText("");
			edate1.setText("");
			amt.setText("");
			task.setText("");
		}
	}
	
	public static void main(String args[])
	{
		new upload_project();
	}
}

