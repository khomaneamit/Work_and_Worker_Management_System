import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;

public class update_project extends JFrame implements ActionListener
{
    JMenuBar mbr;
	JMenu m1,m2,m3;
	JMenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8;
	
	Font f;
	JLabel pid,id,ptype,wtype,loc,start_date,amt,total;
	JTextField pid1;
	JButton pid2,submit;
	
	Connection cn;
	PreparedStatement prstm;
	Statement stm;
	ResultSet rst,rst1;
	String p_id,total2,skill2,w_id;
	Date date;
	
	update_project()
	{
		setTitle("Update Project");
		setSize(500,300);
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
		
		pid=new JLabel("Enter Project id :");
		pid1=new JTextField();
		pid2=new JButton("Search");
		
		id=new JLabel();
		ptype=new JLabel();
		wtype=new JLabel();
		loc=new JLabel();
		start_date=new JLabel();
		amt=new JLabel();
		total=new JLabel();
		submit=new JButton("Complete");
		
		add(id);
			id.setBounds(50,75,175,25);
		add(ptype);
			ptype.setBounds(275,75,175,25);
		add(wtype);
			wtype.setBounds(50,110,175,25);
		add(loc);
			loc.setBounds(275,110,175,25);
		add(start_date);
			start_date.setBounds(50,145,175,25);
		add(amt);
			amt.setBounds(275,145,175,25);
		add(total);
			total.setBounds(50,180,175,25);
		add(submit);
			submit.setBounds(200,215,100,25);
			submit.setVisible(false);
		
		add(pid);
			pid.setBounds(50,40,125,25);
		add(pid1);
			pid1.setBounds(175,40,125,25);
		add(pid2);
			pid2.setBounds(350,40,100,25);
		
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
		pid2.addActionListener(this);
		submit.addActionListener(this);
		
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
		
		if(e.getSource()==pid2)
		{
			try
			{
				p_id=pid1.getText();
				cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
				stm=cn.createStatement();
				
				rst=stm.executeQuery("select count(*) from project where project_id="+p_id);
				rst.next();
				if(rst.getString(1).equals("0"))
				{
					JOptionPane.showMessageDialog(null,"Project not found","Error",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					rst=stm.executeQuery("select * from project where project_id="+p_id);
					rst.next();
					if(rst.getString(13).equals("not completed"))
					{
						JOptionPane.showMessageDialog(null,"Allocate Worker first!","Error",JOptionPane.ERROR_MESSAGE);
						id.setText("");
						ptype.setText("");
						wtype.setText("");
						loc.setText("");
						start_date.setText("");
						amt.setText("");
						total.setText("");
						submit.setVisible(false);
					}
					else if(rst.getString(12).equals("completed"))
					{
						JOptionPane.showMessageDialog(null,"Project already completed!","Error",JOptionPane.ERROR_MESSAGE);
						id.setText("");
						ptype.setText("");
						wtype.setText("");
						loc.setText("");
						start_date.setText("");
						amt.setText("");
						total.setText("");
						submit.setVisible(false);
					}
					else
					{
						id.setText("Project id = "+rst.getString(13));
						ptype.setText("Project type = "+rst.getString(1));
						wtype.setText("Work type = "+rst.getString(2));
						loc.setText("Location = "+rst.getString(3));
						start_date.setText("Start Date = "+rst.getString(4));
						amt.setText("Amount = "+rst.getString(6));
						total.setText("Total Workers = "+rst.getString(10));
						submit.setVisible(true);
					}
				}
			}
			catch(Exception e1){}
		}
		
		if(e.getSource()==submit)
		{
			try
			{
				SimpleDateFormat d1=new SimpleDateFormat("yyyy-MM-dd");
				date=new Date();
				String str=d1.format(date);
				prstm=cn.prepareStatement("update project set status='completed', completed_date='"+str+"' where project_id='"+p_id+"'");
				prstm.execute();
				prstm=cn.prepareStatement("update worker, project_worker set worker.status='available' where project_worker.project_id='"+p_id+"' and worker.w_id=project_worker.w_id");
				prstm.execute();
				JOptionPane.showMessageDialog(null,"Project updated successfully..");
				id.setText("");
				ptype.setText("");
				wtype.setText("");
				loc.setText("");
				start_date.setText("");
				amt.setText("");
				total.setText("");
				submit.setVisible(false);
			}
			catch(Exception e1){}
		}
	}
	
	public static void main(String args[])
	{
		new update_project();
	}
}

