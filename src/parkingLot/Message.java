package parkingLot;

import java.io.Serializable;

/**
 * Lamport的Bakery算法，算法有3种消息类型，request，reply，release，request消息会带有时间戳。
 * 另外有在临界区使用的广播的消息消息类型为alter，广播自己更改之后的空闲停车位数目。
 * */
public class Message implements Serializable
{
	private static final long serialVersionUID = 1553985160840388148L;
	public static final short REQUEST=0; 
	public static final short REPLY=1; 
	public static final short RELEASE=2; 
	public static final short ALTER=3; 
	/** 消息类型*/
	public int type=-1;
	/** 消息携带的数值信息：时间信息、空闲停车位数目。*/
	public int value=-1;
	public Message(){}
	public Message(int type,int value)
	{
		this.type=type;
		this.value=value;
	}
}
