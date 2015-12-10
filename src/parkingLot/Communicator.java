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
	int node;
	ObjectInputStream in;
	ObjectOutputStream out;
	Node peer;
	public Communicator(Socket s,int node) throws IOException
	{
		socket=s;
		this.node=node;
		in=new ObjectInputStream(s.getInputStream());
		out=new ObjectOutputStream(s.getOutputStream());
		peer=new Node(node,out);
		NodeTable.add(peer);
	}
	@Override
	public void run()
	{
		// TODO 自动生成的方法存根
		
	}

}
