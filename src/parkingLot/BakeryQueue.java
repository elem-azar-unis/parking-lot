package parkingLot;

import java.util.PriorityQueue;


/**
 * 静态类，Lamport算法维护的临界区访问队列。最小的时间戳、或者时间戳相等，则最小的ID优先。
 */
public class BakeryQueue
{
	public static PriorityQueue<Request> queue=new PriorityQueue<Request>();
	private BakeryQueue(){}
	/**
	 * 同步的向队列添加一个元素
	 * @param r 要添加的元素
	 */
	public static void add(Request r)
	{
		synchronized (queue)
		{
			queue.add(r);
		}
	}
	/**
	 * 将ID是传入的ID的元素删除，若有删除则唤醒可能在等待的线程。
	 * @param id 要删除的申请者ID
	 */
	public static void remove(int id)
	{
		synchronized (queue)
		{
			if(queue.removeIf(r -> r.id==id))
			{			
				queue.notify();
			}
		}
	}
	/**
	 * 阻塞的等待在队列上，直到自己的申请到了优先级队列的队首。
	 */
	public static void wait_for_me()
	{
		synchronized (queue)
		{
			try
			{
				while (queue.peek().id!=NodeList.self)
				{				
					queue.wait();
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

/**
 * 记录某一个请求的发起：发起人和时间。
 */
class Request implements Comparable<Request>
{
	/**申请时间*/
	int time;
	/**申请者ID*/
	int id;
	/**
	 * @param ID 申请者ID
	 * @param time 申请时间
	 */
	public Request(int ID,int time)
	{
		this.id=ID;
		this.time=time;
	}
	@Override
	public int compareTo(Request o)
	{
		return (time!=o.time)? time-o.time : id-o.id;
	}
}