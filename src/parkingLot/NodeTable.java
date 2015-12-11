package parkingLot;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态类。所有节点的连接表，能够互斥的添加某个新连接的节点，广播某一个消息。
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
		broadcast(m);
	}
	/**向所有节点广播某一个消息m*/
	public static void broadcast(Message m)
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
	/**
	 * 等待自己之前发送的请求已经收到了所有人的reply。
	 */
	public static void wait_all_reply()
	{
		synchronized (nodes)
		{
			try
			{
				while(nodes.stream().anyMatch(n -> !n.replyed))
				{				
					nodes.wait();
				}				
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * 将节点n记录为已经回复。此方法是同步的。
	 * @param n 回复了的节点的{@link Node}数据结构的引用
	 */
	public static void set_replyed(Node n)
	{
		synchronized (nodes)
		{
			n.replyed=true;
			nodes.notify();
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