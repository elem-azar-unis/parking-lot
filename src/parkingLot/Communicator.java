package parkingLot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 与某一个节点持续交流的线程，此处是被动监听对方的消息，有时候会发送回应。
 * */
public class Communicator implements Runnable
{
	int peer_id;
	ObjectInputStream in;
	ObjectOutputStream out;
	/**
	 * 对面节点的{@link Node}数据结构的引用，此方法只能用{@link NodeTable#set_replyed(Node)}来操作。
	 */
	Node node;
	public Communicator(int peer_id,ObjectInputStream in,ObjectOutputStream out)
	{
		this.peer_id=peer_id;
		this.in=in;
		this.out=out;
		node=new Node(peer_id,out);
		NodeTable.add(node);
	}
	@Override
	public void run()
	{
		Message m;
		try
		{
			while(true)
			{
				m=(Message) in.readObject();
				switch (m.type)
				{
					case Message.ALTER :
					{
						FileInfo.update_space(m.value);
						break;
					}
					case Message.RELEASE :
					{
						BakeryQueue.remove(peer_id);
						break;
					}
					case Message.REPLY :
					{
						NodeTable.set_replyed(node);
						break;
					}
					case Message.REQUEST :
					{
						GClock.update_round(m.value);
						BakeryQueue.add(new Request(peer_id, m.value));
						out.writeObject(new Message(Message.REPLY,0));
						break;
					}
					default :
						break;
				}
			}		
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
