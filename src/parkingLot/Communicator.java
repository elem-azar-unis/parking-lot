package parkingLot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 与某一个节点持续交流的线程
 * */
public class Communicator implements Runnable
{
	Socket socket;
	int node;
	ObjectInputStream in;
	ObjectOutputStream out;
	public Communicator(Socket s,int node,NodeTable table) throws IOException
	{
		socket=s;
		this.node=node;
		in=new ObjectInputStream(s.getInputStream());
		out=new ObjectOutputStream(s.getOutputStream());
		table.add(new Node(node,out));
	}
	@Override
	public void run()
	{
		// TODO 自动生成的方法存根
		
	}

}
