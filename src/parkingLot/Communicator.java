package parkingLot;

import java.net.Socket;

/**
 * 与某一个节点持续交流的线程
 * */
public class Communicator implements Runnable
{
	Socket socket;
	int node;
	public Communicator(Socket s,int node)
	{
		socket=s;
		this.node=node;
	}
	@Override
	public void run()
	{
		// TODO 自动生成的方法存根
		
	}

}
