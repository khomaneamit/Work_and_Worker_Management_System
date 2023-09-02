import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;
import java.util.*;

public class all_workers extends JFrame implements ActionListener
{
    JMenuBar mbr;
	JMenu m1,m2,m3;
	JMenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8;
	
	Font f;
	JLabel l1,l2,l3;
	JComboBox search;
	JButton search1;
	
	Connection cn;
	Statement stm;
	ResultSet rst;
	JTable table;
	String data[][];
	String sql;
	int r_cnt,i;
	
	all_workers()
	{
		setTitle("All Workers");
		setSize(500,500);
		setLocation(500,150);
		setVisible(true);
		setLayout(null);
		f=new Font("Arial",Font.BOLD,14);
		setFont(new Font("Arial",Font.BOLD,20));
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
			stm=cn.createStatement();
			//available workers
			rst=stm.executeQuery("select count(*) from worker where status='available'");
			rst.next();
			r_cnt=rst.getInt(1);
			l1=new JLabel("Available : "+r_cnt);
			//not available workers
			rst=stm.executeQuery("select count(*) from worker where status='not available'");
			rst.next();
			r_cnt=rst.getInt(1);
			l2=new JLabel("Not Available : "+r_cnt);
			//Total workers
			rst=stm.executeQuery("select count(*) from worker");
			rst.next();
			r_cnt=rst.getInt(1);
			l3=new JLabel("Total Workers : "+r_cnt);
		}
		catch(Exception e){}
		
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
		
		search=new JComboBox();
		search.addItem("Select Option");
		search.addItem("Available");
		search.addItem("Not Available");
		search1=new JButton("Search");
		
		m1.add(mi1);	m1.add(mi2);	m1.add(mi3);
		m2.add(mi5);	m2.add(mi6);	m2.add(mi7);
		m3.add(mi4);	m3.add(mi8);
		mbr.add(m1);	mbr.add(m2);	mbr.add(m3);
		add(mbr);
		mbr.setBounds(0,0,500,30);
		
		add(l1);
			l1.setBounds(30,40,110,30);
		add(l2);
			l2.setBounds(170,40,130,30);
		add(l3);
			l3.setBounds(330,40,140,30);
		add(search);
			search.setBounds(50,90,175,25);
		add(search1);
			search1.setBounds(275,90,175,25);
		l1.setFont(f);
		l2.setFont(f);
		l3.setFont(f);
		
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi8.addActionListener(this);
		mi5.addActionListener(this);
		mi6.addActionListener(this);
		mi7.addActionListener(this);
		mi4.addActionListener(this);
		search1.addActionListener(this);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				new Project();
			}
		});
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
			stm=cn.createStatement();
			
			rst=stm.executeQuery("select count(*) from worker");
			rst.next();
			r_cnt=rst.getInt(1);
			data=new String[r_cnt][6];
			String colhead[]={"Id","Name","Address","Skill","Contact","Status"};
			rst=stm.executeQuery("select * from worker order by skill,status");
			i=0;
			while(rst.next())
			{
				data[i][0]=rst.getString(8);
				data[i][1]=rst.getString(1);
				data[i][2]=rst.getString(3);
				data[i][3]=rst.getString(4);
				data[i][4]=rst.getString(6);
				data[i][5]=rst.getString(7);
				i++;
			}
			table=new JTable(data,colhead);
			table.setEnabled(false);
			int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
			int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
			JScrollPane jsp=new JScrollPane(table,v,h);
			add(jsp);
			jsp.setBounds(20,130,440,300);
		}
		catch(Exception e){}
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
		
		if(e.getSource()==search1)
		{
			String str=search.getSelectedItem().toString();
			if(str=="Available")
				sql="where status='available'";
			else if(str=="Not Available")
				sql="where status='not available'";
			else
				sql="";
			try
			{
				rst=stm.executeQuery("select count(*) from worker "+sql);
				rst.next();
				r_cnt=rst.getInt(1);
				data=new String[r_cnt][6];
				String colhead[]={"Id","Name","Address","Skill","Contact","Status"};
				rst=stm.executeQuery("select * from worker "+sql+" order by skill,status");
				i=0;
				while(rst.next())
				{
					data[i][0]=rst.getString(8);
					data[i][1]=rst.getString(1);
					data[i][2]=rst.getString(3);
					data[i][3]=rst.getString(4);
					data[i][4]=rst.getString(6);
					data[i][5]=rst.getString(7);
					i++;
				}
				table=new JTable(data,colhead);
				table.setEnabled(false);
				int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
				int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
				JScrollPane jsp=new JScrollPane(table,v,h);
				add(jsp);
				jsp.setBounds(20,130,440,300);
			}
			catch(Exception e1){}
		}
	}
	
	public static void main(String args[])
	{
		new all_workers();
	}
}

