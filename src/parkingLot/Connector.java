package parkingLot;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 用于与所有其他的节点建立连接。具体的行为为：主动连接所有编号小于自己的节点，同时发送给他自己的编号，开新线程等待所有编号比自己大的节点。
 * */
public class Connector
{
	public Connector()
	{
		NodeList.init();
		new Thread(new WaitConnection()).start();
		try
		{
			for(NodeAddr addr:NodeList.lst)
			{
				Socket socket=new Socket(addr.ip,addr.port);
				ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(new Message(Message.INIT,NodeList.self));
				new Thread(new Communicator(socket, addr.id)).start();
			} 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}