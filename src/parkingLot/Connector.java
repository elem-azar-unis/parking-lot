package parkingLot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 静态类。
 * 用于与所有其他的节点建立连接。具体的行为为：主动连接所有编号小于自己的节点，同时发送给他自己的编号，开新线程等待所有编号比自己大的节点。
 * */
public class Connector
{
	public static void connect()
	{
		new Thread(new WaitConnection()).start();
		try
		{
			for(NodeAddr addr:FileInfo.lst)
			{
				@SuppressWarnings("resource")
				Socket socket=new Socket(addr.ip,addr.port);
				ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(new Message(Message.INIT,FileInfo.self));
				new Thread(new Communicator(addr.id, new ObjectInputStream(socket.getInputStream()), out)).start();
			} 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}