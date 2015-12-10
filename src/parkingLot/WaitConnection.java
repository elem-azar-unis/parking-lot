package parkingLot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 等待所有比自己大的节点连接的进程。
 * */
class WaitConnection implements Runnable
{
	int selfport;
	NodeTable table;
	public WaitConnection(int port,NodeTable table)
	{
		selfport=port;
		this.table=table;
	}
	@Override
	public void run()
	{
		try
		{
			@SuppressWarnings("resource")
			ServerSocket server=new ServerSocket(selfport);
			while(true)
			{
				Socket recv=server.accept();
				ObjectInputStream in=new ObjectInputStream(recv.getInputStream());
				Message message=(Message)in.readObject();
				assert message.type==Message.INIT;
				new Thread(new Communicator(recv, message.value,table)).start();
			}
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}