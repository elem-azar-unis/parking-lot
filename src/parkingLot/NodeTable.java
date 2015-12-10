package parkingLot;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有节点的连接表，能够互斥的添加某个新连接的节点，广播某一个消息。
 * */
public class NodeTable
{
	List<Node> nodes=new ArrayList<Node>();
	public void add(Node n)
	{
		synchronized (nodes)
		{
			nodes.add(n);
		}
	}
	public void broadcast(Message m)
	{
		synchronized (nodes)
		{
			try
			{
				for(Node n:nodes)
				{
					n.to.writeObject(m);				
				}
			}
			 catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

/**
 * 一个节点，包括他的编号，与他相连的ObjectOutputStream
 * */
class Node
{
	int id;
	ObjectOutputStream to;
	public Node(int id,ObjectOutputStream to)
	{
		this.id=id;
		this.to=to;
	}
}