import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;

public class allocate_worker extends JFrame implements ActionListener
{
    JMenuBar mbr;
	JMenu m1,m2,m3;
	JMenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8;
	
	Font f;
	JLabel pid,id,ptype,wtype,loc,start_date,amt,total,skill,wid;
	JTextField pid1,total1,wid1;
	JComboBox skill1;
	JButton pid2,submit,wid2;
	
	Connection cn;
	PreparedStatement prstm;
	Statement stm;
	ResultSet rst,rst1;
	String p_id,total2,skill2,w_id;
	
	JScrollPane jsp,jsp1;
	JTable table;
	String data[][];
	String allocid;
	Date date;
	int r_cnt,i;
	
	allocate_worker()
	{
		setTitle("Allocation of worker");
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
		
		pid=new JLabel("Enter Project id :");
		pid1=new JTextField();
		pid2=new JButton("Search");
		
		id=new JLabel();
		ptype=new JLabel();
		wtype=new JLabel();
		loc=new JLabel();
		start_date=new JLabel();
		amt=new JLabel();
		
		total=new JLabel("Total Workers :");
		skill=new JLabel("Skill :");
		total1=new JTextField();
		skill1=new JComboBox();
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
			stm=cn.createStatement();
			rst=stm.executeQuery("select distinct skill from worker");
			while(rst.next())
			{
				skill1.addItem(rst.getString(1));
			}	
		}
		catch(Exception ex){}
		
		submit=new JButton("Submit");
		
		wid=new JLabel("Enter Worker Id :");
		wid1=new JTextField();
		wid2=new JButton("Allocate");
		
		add(pid);
			pid.setBounds(50,40,125,25);
		add(pid1);
			pid1.setBounds(175,40,125,25);
		add(pid2);
			pid2.setBounds(350,40,100,25);
		add(submit);
		
		m1.add(mi1);	m1.add(mi2);	m1.add(mi3);
		m2.add(mi5);	m2.add(mi6);	m2.add(mi7);
		m3.add(mi4);	m3.add(mi8);
		mbr.add(m1);	mbr.add(m2);	mbr.add(m3);
		add(mbr);
		mbr.setBounds(0,0,500,30);
		
		add(wid);
			wid.setBounds(50,370,125,25);
		add(wid1);
			wid1.setBounds(175,370,125,25);
		add(wid2);
			wid2.setBounds(350,370,100,25);
		wid.setVisible(false);
		wid1.setVisible(false);
		wid2.setVisible(false);
		
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
		wid2.addActionListener(this);
		
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
		if(e.getSource()==mi6)
		{
			new add_worker();
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
				System.out.println(p_id);
				cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
				stm=cn.createStatement();
				
				rst=stm.executeQuery("select count(*) from project where project_id="+p_id+" and status='not completed'");
				rst.next();
				if(rst.getString(1).equals("0"))
				{
					JOptionPane.showMessageDialog(null,"Project not found","Error",JOptionPane.ERROR_MESSAGE);
					id.setText("");
					ptype.setText("");
					wtype.setText("");
					loc.setText("");
					start_date.setText("");
					amt.setText("");
				}
				else
				{
					rst=stm.executeQuery("select * from project where project_id="+p_id+" and status='not completed'");
					rst.next();
					id.setText("Project id = "+rst.getString(13));
					ptype.setText("Project type = "+rst.getString(1));
					wtype.setText("Work type = "+rst.getString(2));
					loc.setText("Location = "+rst.getString(3));
					start_date.setText("Start Date = "+rst.getString(4));
					amt.setText("Amount = "+rst.getString(6));
					add(total);
						total.setBounds(50,185,100,25);
					add(total1);
						total1.setBounds(50,215,100,25);
					add(skill);
						skill.setBounds(200,185,100,25);
					add(skill1);
						skill1.setBounds(200,215,100,25);
						submit.setBounds(350,215,100,25);
				}
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
			}
			catch(Exception e1){}
		}
		
		if(e.getSource()==submit)
		{
			try
			{
				p_id=pid1.getText();
				total2=total1.getText();
				skill2=skill1.getSelectedItem().toString();
				System.out.println(skill2);
				cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
				prstm=cn.prepareStatement("update project set required_workers="+total2+" where project_id="+p_id);
				prstm.execute();
				stm=cn.createStatement();
				rst=stm.executeQuery("select count(*) from worker where status='available' and skill='"+skill2+"'");
				rst.next();
				if(rst.getString(1).equals("0"))
				{
					JOptionPane.showMessageDialog(null,"Worker not found","Error",JOptionPane.ERROR_MESSAGE);
					jsp.setVisible(false);
					wid.setVisible(false);
					wid1.setVisible(false);
					wid2.setVisible(false);
				}
				else
				{
					r_cnt=rst.getInt(1);
					data=new String[r_cnt][5];
					String colhead[]={"Id","Name","Address","Skill","Contact"};
					rst=stm.executeQuery("select * from worker where status='available' and skill='"+skill2+"'");
					i=0;
					while(rst.next())
					{
						data[i][0]=rst.getString(8);
						data[i][1]=rst.getString(1);
						data[i][2]=rst.getString(3);
						data[i][3]=rst.getString(4);
						data[i][4]=rst.getString(6);
						i++;
					}
					table=new JTable(data,colhead);
					table.setEnabled(false);
					int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
					int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
					jsp=new JScrollPane(table,v,h);
					add(jsp);
						jsp.setBounds(20,250,440,100);
					wid.setVisible(true);
					wid1.setVisible(true);
					wid2.setVisible(true);
				}
			}
			catch(Exception e2){
				System.out.println(e2);
			}
		}
		
		if(e.getSource()==wid2)
		{
			try
			{
				jsp.setVisible(false);
				p_id=pid1.getText();
				w_id=wid1.getText();
				cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
				stm=cn.createStatement();
				rst1=stm.executeQuery("select count(*) from worker where w_id="+w_id);
				rst1.next();
				if(rst1.getString(1).equals("1"))
				{
					rst1=stm.executeQuery("select * from worker where w_id="+w_id);
					rst1.next();
					if(rst1.getString(7).equals("available"))
					{
						rst=stm.executeQuery("select * from project where project_id="+p_id);
						rst.next();
						int a=rst.getInt(10);
						int b=rst.getInt(11);
						String c=rst.getString(10);
						if(rst.getInt(10)<rst.getInt(11) || rst.getString(10).equals("null"))
						{
							prstm=cn.prepareStatement("update worker set status='not available' where w_id="+w_id);
							prstm.execute();
							SimpleDateFormat d1=new SimpleDateFormat("yyyy-MM-dd");
							date=new Date();
							String str=d1.format(date);
							prstm=cn.prepareStatement("insert into project_worker(project_id,w_id,date) values("+p_id+","+w_id+",'"+str+"')");
							prstm.execute();
							int cnt;
							if(c==null)
								cnt=1;
							else
								cnt=a+1;
							System.out.println(a+" "+b+" "+c+" "+cnt);
							prstm=cn.prepareStatement("update project set total_workers="+cnt+", status='in progress', start_date='"+str+"' where project_id="+p_id);
							prstm.execute();
							JOptionPane.showMessageDialog(null,"Worker allocated successfully...\n"+cnt+" out of "+b+" are Allocated");
							
							rst=stm.executeQuery("select count(*) from worker where status='available' and skill='"+skill2+"'");
							rst.next();
							if(rst.getString(1).equals("0"))
							{
								JOptionPane.showMessageDialog(null,"Worker not found","Error",JOptionPane.ERROR_MESSAGE);
								jsp.setVisible(false);
								wid.setVisible(false);
								wid1.setVisible(false);
								wid2.setVisible(false);
							}
							else
							{
								r_cnt=rst.getInt(1);
								data=new String[r_cnt][5];
								String colhead[]={"Id","Name","Address","Skill","Contact"};
								rst=stm.executeQuery("select * from worker where status='available' and skill='"+skill2+"'");
								i=0;
								while(rst.next())
								{
									data[i][0]=rst.getString(8);
									data[i][1]=rst.getString(1);
									data[i][2]=rst.getString(3);
									data[i][3]=rst.getString(4);
									data[i][4]=rst.getString(6);
									i++;
								}
								table=new JTable(data,colhead);
								table.setEnabled(false);
								int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
								int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
								jsp=new JScrollPane(table,v,h);
								add(jsp);
									jsp.setBounds(20,250,440,100);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Already Required Workers are Allocated","Error",JOptionPane.ERROR_MESSAGE);
							jsp.setVisible(false);
							wid.setVisible(false);
							wid1.setVisible(false);
							wid2.setVisible(false);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Already Worker are Allocated","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Invalid Worker Id","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception e3){
				e3.printStackTrace();
			}
		}
	}
	
	public static void main(String args[])
	{
		new allocate_worker();
	}
}

