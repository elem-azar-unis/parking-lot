package parkingLot;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有节点的连接表，能够互斥的添加某个新连接的节点，广播某一个消息。
 * 兼具维护请求列表，即需要所有人都reply。
 * */
public class NodeTable
{
	private static List<Node> nodes=new ArrayList<Node>();
	private NodeTable(){}
	/**添加一个已经连接上的节点*/
	public static void add(Node n)
	{
		synchronized (nodes)
		{
			nodes.add(n);
		}
	}
	/**向所有节点发送request，此时记录所有节点都没有返回reply*/
	public static void broadcast_request()
	{
		int t=GClock.get_round();
		Message m=new Message(Message.REQUEST,t);
		synchronized (nodes)
		{
			try
			{
				for(Node n:nodes)
				{
					n.replyed=false;
				}
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
	/**向所有节点发送release*/
	public static void broadcast_release()
	{
		Message m=new Message(Message.RELEASE,0);
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
	/**是否所有的节点都已经发送过reply*/
	public static boolean all_replyed()
	{
		synchronized (nodes)
		{
			for (Node node : nodes)
			{
				if(!node.replyed)return false;
			}
			return true;
		}
	}
	public static int size()
	{
		return nodes.size();
	}
}

/**
 * 一个节点，包括他的编号，与他相连的ObjectOutputStream，
 * 他是否reply了，初始为true，在发送request的时候设为false，收到reply设为true。
 * */
class Node
{
	int id;
	ObjectOutputStream to;
	boolean replyed=true;
	public Node(int id,ObjectOutputStream to)
	{
		this.id=id;
		this.to=to;
	}
}