package parkingLot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 读取节点列表文件，获得自己的编号，所有的比自己小的节点的编号、IP地址、端口号。
 * 文件的结构为：第一行：自己的编号（空格）端口号。其他行：编号（空格）IP（空格）端口号
 * */
public class NodeList
{
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
	public List<NodeAddr> lst=new LinkedList<NodeList.NodeAddr>();
	public int self;
	public int port;
	private static final String file=new String("nodes.txt");
	public NodeList()
	{
		try
		{
			BufferedReader fin=new BufferedReader(new FileReader(file));
			String temp=fin.readLine();
			String[] s=temp.split(" ");
			assert s.length==2;
			self=Integer.parseInt(s[0]);
			port=Integer.parseInt(s[1]);
			while((temp=fin.readLine())!=null)
			{
				s=temp.split(" ");
				assert s.length==3;
				int nodenum=Integer.parseInt(s[0]);
				if(nodenum>=self)continue;
				lst.add(new NodeAddr(nodenum,s[1],Integer.parseInt(s[2])));
			}
			fin.close();
		} catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			System.out.println("读文件错误");
			e.printStackTrace();
		}
	}
}