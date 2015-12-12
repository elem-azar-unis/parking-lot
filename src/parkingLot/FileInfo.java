package parkingLot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态类。
 * 读取节点列表文件，获得自己的编号，总空位数目，剩余空位数目，所有的比自己小的节点的编号、IP地址、端口号。
 * 文件的结构为：第一行：自己的编号（空格）总空位数目。其他行：编号（空格）IP（空格）端口号
 * 文件中要有所有（已知）节点（包括自己）的编号、IP、端口号信息，起码要有所有ID小于等于自己的ID的信息。
 * */
public class FileInfo
{
	/**所有比自己小的节点列表：编号、IP地址、端口号*/
	public static List<NodeAddr> lst;
	/**自己的编号*/
	public static int self;
	/**自己节点的监听端口号*/
	public static int port;
	/**总共空位数目*/
	public static int total_space;
	/**剩余空位数目*/
	public static int free_space;
	private FileInfo(){}
	/**
	 * 初始化读文件。仅使用一次即可。
	 */
	public static void init(String file)
	{
		try
		{
			lst=new ArrayList<NodeAddr>();
			BufferedReader fin=new BufferedReader(new FileReader(file));
			String temp=fin.readLine();
			String[] s=temp.split(" ");
			assert s.length==2;
			self=Integer.parseInt(s[0]);
			total_space=Integer.parseInt(s[1]);
			free_space=total_space;
			while((temp=fin.readLine())!=null)
			{
				s=temp.split(" ");
				assert s.length==3;
				int nodenum=Integer.parseInt(s[0]);
				if(nodenum==self)
					port=Integer.parseInt(s[2]);
				else if(nodenum<self)
					lst.add(new NodeAddr(nodenum,s[1],Integer.parseInt(s[2])));
			}
			fin.close();
		} catch (IOException e)
		{
			System.out.println("读文件错误");
			e.printStackTrace();
		}
	}
	/**
	 * 更新剩余位置数目，并在界面上打印。
	 * @param n 新的剩余位置数目。
	 */
	public static void update_space(int n)
	{
		free_space=n;
		//在界面上打印剩余位置数目
		UI.jlbFreeSpace.setText(String.valueOf(free_space));
		UI.jlbOccupiedSpace.setText(String.valueOf(total_space-free_space));
		UI.jlbFreeSpace.repaint();
		UI.jlbOccupiedSpace.repaint();
	}
}
class NodeAddr
{
	int id;
	String ip;
	int port;
	public NodeAddr(int id,String ip,int port)
	{
		this.id=id;
		this.ip=ip;
		this.port=port;
	}
}