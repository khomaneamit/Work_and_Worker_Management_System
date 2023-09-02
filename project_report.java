import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;
import java.util.*;
import java.text.*;

public class project_report extends JFrame implements ActionListener
{
    JMenuBar mbr;
	JMenu m1,m2,m3;
	JMenuItem mi1,mi2,mi3,mi4,mi5,mi6,mi7,mi8;
	
	Font f;
	JLabel pid,id,ptype,wtype,loc,start_date,amt,total,status;
	JTextField pid1;
	JButton pid2,print;
	
	Connection cn;
	PreparedStatement prstm;
	Statement stm;
	ResultSet rst,rst1;
	String p_id,total2,skill2,w_id;
	
	JScrollPane jsp;
	JTable table,table1;
	String data[][],data1[][];
	String allocid,str;
	int r_cnt,i;
	
	project_report()
	{
		setTitle("Project Report");
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
		print=new JButton("Print");
		add(print);
			print.setBounds(200,330,100,25);
			print.setVisible(false);
		
		id=new JLabel();
		ptype=new JLabel();
		wtype=new JLabel();
		loc=new JLabel();
		start_date=new JLabel();
		amt=new JLabel();
		total=new JLabel();
		status=new JLabel();
		
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
		add(status);
			status.setBounds(275,180,175,25);
		
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
		print.addActionListener(this);
		
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
		if(e.getSource()==mi7)
		{
			new allocate_worker();
			this.setVisible(false);
		}
		
		if(e.getSource()==pid2)
		{
			try
			{
				p_id=pid1.getText();
				cn=DriverManager.getConnection("jdbc:mysql://localhost:3307/project","root","Amit@7282");
				stm=cn.createStatement();
				
				rst=stm.executeQuery("select count(*) from project where project_id='"+p_id+"'");
				rst.next();
				if(rst.getString(1).equals("0"))
				{
					JOptionPane.showMessageDialog(null,"Project not found","Error",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					rst=stm.executeQuery("select * from project where project_id='"+p_id+"'");
					rst.next();
					if(rst.getString(12).equals("not completed"))
					{
						JOptionPane.showMessageDialog(null,"Allocate Worker first!","Error",JOptionPane.ERROR_MESSAGE);
						id.setText("");
						ptype.setText("");
						wtype.setText("");
						loc.setText("");
						start_date.setText("");
						amt.setText("");
						total.setText("");
						status.setText("");
						jsp.setVisible(false);
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
						status.setText("Status = "+rst.getString(12));
						/*data1=new String[1][5];
						String colhead1[]={"Id","ptype","wtype","location","total"};
						data1[0][0]=rst.getString(1);
						data1[0][1]=rst.getString(2);
						data1[0][2]=rst.getString(3);
						data1[0][3]=rst.getString(4);
						data1[0][4]=rst.getString(11);
						table1=new JTable(data1,colhead1);*/
						
						
						str="Project Report	                Project id="+rst.getString(13);
						
						rst=stm.executeQuery("select count(*) from worker,project_worker where project_worker.project_id='"+p_id+"' and worker.w_id=project_worker.w_id");
						rst.next();
						if(rst.getString(1).equals("0"))
						{
							JOptionPane.showMessageDialog(null,"Worker not found","Error",JOptionPane.ERROR_MESSAGE);
							jsp.setVisible(false);
						}
						else
						{
							r_cnt=rst.getInt(1);
							data=new String[r_cnt][5];
							String colhead[]={"Id","Name","Address","Skill","Contact"};
							rst=stm.executeQuery("select worker.* from worker,project_worker where project_worker.project_id='"+p_id+"' and worker.w_id=project_worker.w_id");
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
								jsp.setBounds(20,215,440,100);
							print.setVisible(true);
						}
					}
				}
			}
			catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		if(e.getSource()==print)
		{
			MessageFormat header=new MessageFormat(str);
			MessageFormat footer=new MessageFormat("");
			try{
				table.print(JTable.PrintMode.FIT_WIDTH,header,footer);
			}
			catch(Exception ex){}
		}
	}
	
	public static void main(String args[])
	{
		new project_report();
	}
}

