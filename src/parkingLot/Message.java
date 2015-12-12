package parkingLot;

import java.io.Serializable;

/**
 * Lamport的Bakery算法，算法有3种消息类型，request，reply，release，request消息会带有时间戳。
 * 另外有在临界区使用的广播的消息消息类型为alter，广播自己更改之后的空闲停车位数目。
 * 还有一种在连接时候使用的INIT，传送自己的编号。
 * */
public class Message implements Serializable
{
	private static final long serialVersionUID = 1553985160840388148L;
	/**请求*/
	public static final short REQUEST=0; 
	/**回复*/
	public static final short REPLY=1; 
	/**释放*/
	public static final short RELEASE=2; 
	/**更改空位数目*/
	public static final short ALTER=3; 
	/**初始时候发送自己的ID*/
	public static final short INIT=4; 
	/** 消息类型*/
	public int type=-1;
	/** 消息携带的数值信息：时间信息、空闲停车位数目。*/
	public int value=-1;
	/**
	 * 创建一个新的消息。
	 * @param type 消息的类型。从5种静态数字中选择。
	 * @param value 消息携带的信息。有的消息不需要。
	 */
	public Message(int type,int value)
	{
		this.type=type;
		this.value=value;
	}
}
