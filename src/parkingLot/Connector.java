package parkingLot;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import parkingLot.NodeList.NodeAddr;

/**
 * 用于与所有其他的节点建立连接。具体的行为为：主动连接所有编号小于自己的节点，同时发送给他自己的编号，开新线程等待所有编号比自己大的节点。
 * */
public class Connector
{
	NodeList nodeList;
	public Connector(NodeTable nodeTable)
	{
		nodeList=new NodeList();
		new Thread(new WaitConnection(nodeList.port, nodeTable)).start();
		try
		{
			for(NodeAddr addr:nodeList.lst)
			{
				Socket socket=new Socket(addr.ip,addr.port);
				ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(new Message(Message.INIT,nodeList.self));
				new Thread(new Communicator(socket, addr.id,nodeTable)).start();
			} 
		}
		catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}