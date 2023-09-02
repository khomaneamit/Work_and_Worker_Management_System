import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;

public class Project extends JFrame implements ActionListener
{
    JMenuBar mbr;
	JMenu m1,m2,m3;
	JMenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8;
	JLabel l1,l2,l3;
	
	Connection cn;
	Statement stm;
	ResultSet rst,rst1,rst2;
	JTable table,table1,table2;
	String data[][],data1[][],data2[][];
	int r_cnt,r_cnt1,r_cnt2,i,j,k;
	
	Project()
	{
		setTitle("Contractor and worker Management");
		setSize(500,500);
		setLocation(500,150);
		setVisible(true);
		setLayout(null);
		setFont(new Font("Arial",Font.BOLD,14));
		
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
		l1=new JLabel("New Projects");
		l2=new JLabel("Projects in Progress");
		l3=new JLabel("Completed Projects");
		
		m1.add(mi1);	m1.add(mi2);	m1.add(mi3);
		m2.add(mi5);	m2.add(mi6);	m2.add(mi7);
		m3.add(mi4);	m3.add(mi8);
		mbr.add(m1);	mbr.add(m2);	mbr.add(m3);
		add(mbr);
		mbr.setBounds(0,0,500,20);
		
		add(l1);
			l1.setBounds(200,30,100,20);
		add(l2);
			l2.setBounds(180,170,140,20);
		add(l3);
			l3.setBounds(180,310,140,20);
		
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi4.addActionListener(this);
		mi5.addActionListener(this);
		mi6.addActionListener(this);
		mi7.addActionListener(this);
		mi8.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
			stm=cn.createStatement();
			
			//first table
			rst=stm.executeQuery("select count(*) from project where status='not completed'");
			rst.next();
			r_cnt=rst.getInt(1);
			data=new String[r_cnt][6];
			String colhead[]={"Id","P_Type","W_Type","Location","Start Date","Amount"};
			rst=stm.executeQuery("select * from project where status='not completed'");
			i=0;
			while(rst.next())
			{
				data[i][0]=rst.getString(13);
				data[i][1]=rst.getString(1);
				data[i][2]=rst.getString(2);
				data[i][3]=rst.getString(3);
				data[i][4]=rst.getString(4);
				data[i][5]=rst.getString(6);
				i++;
			}
			table=new JTable(data,colhead);
			table.setEnabled(false);
			int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
			int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
			JScrollPane jsp=new JScrollPane(table,v,h);
			add(jsp);
			jsp.setBounds(20,60,440,100);
			
			//second table
			rst1=stm.executeQuery("select count(*) from project where status='in progress'");
			rst1.next();
			r_cnt1=rst1.getInt(1);
			data1=new String[r_cnt1][7];
			String colhead1[]={"Id","P_Type","W_Type","Location","Start Date","Amount","Workers"};
			rst1=stm.executeQuery("select * from project where status='in progress'");
			j=0;
			while(rst1.next())
			{
				data1[j][0]=rst1.getString(13);
				data1[j][1]=rst1.getString(1);
				data1[j][2]=rst1.getString(2);
				data1[j][3]=rst1.getString(3);
				data1[j][4]=rst1.getString(4);
				data1[j][5]=rst1.getString(6);
				data1[j][6]=rst1.getString(10);
				j++;
			}
			table1=new JTable(data1,colhead1);
			table1.setEnabled(false);
			JScrollPane jsp1=new JScrollPane(table1,v,h);
			add(jsp1);
			jsp1.setBounds(20,200,440,100);
			
			//third table
			rst2=stm.executeQuery("select count(*) from project where status='completed'");
			rst2.next();
			r_cnt2=rst2.getInt(1);
			data2=new String[r_cnt2][7];
			String colhead2[]={"Id","P_Type","W_Type","Location","Start Date","Completed Date","Amount"};
			rst2=stm.executeQuery("select * from project where status='completed'");
			k=0;
			while(rst2.next())
			{
				data2[k][0]=rst2.getString(13);
				data2[k][1]=rst2.getString(1);
				data2[k][2]=rst2.getString(2);
				data2[k][3]=rst2.getString(3);
				data2[k][4]=rst2.getString(4);
				data2[k][5]=rst2.getString(5);
				data2[k][6]=rst2.getString(6);
				k++;
			}
			table2=new JTable(data2,colhead2);
			table2.setEnabled(false);
			JScrollPane jsp2=new JScrollPane(table2,v,h);
			add(jsp2);
			jsp2.setBounds(20,340,440,100);
		}
		catch(Exception e){}
	}
	
	public void actionPerformed(ActionEvent e)
	{
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
	}
	
	public static void main(String args[])
	{
		new Project();
	}
}

