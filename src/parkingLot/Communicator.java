package parkingLot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 与某一个节点持续交流的线程，此处是被动监听对方的消息，有时候会发送回应。
 * */
public class Communicator implements Runnable
{
	Socket socket;
	int peer_id;
	ObjectInputStream in;
	ObjectOutputStream out;
	/**
	 * 对面节点的{@link Node}数据结构的引用，此方法只能用{@link NodeTable#set_replyed(Node)}来操作。
	 */
	Node node;
	public Communicator(Socket s,int peer_id)
	{
		socket=s;
		this.peer_id=peer_id;
		try
		{
			in=new ObjectInputStream(s.getInputStream());
			out=new ObjectOutputStream(s.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}		
		node=new Node(peer_id,out);
		NodeTable.add(node);
	}
	@Override
	public void run()
	{
		// TODO 自动生成的方法存根
		
	}
}
