package parkingLot;

/**全局时间，可以获得时间、更新时间、自增时间。静态类*/
public class GClock
{
	private static Integer round=0;
	private GClock(){}
	/**获取逻辑时钟的同时，逻辑时钟自增。因为只有在发送request之前会获取逻辑时钟*/
	public static int get_round()
	{
		synchronized (round)
		{
			int temp=round;
			round=round+1;
			return temp;
		}		
	}
	/**传入收到的时间t，更新自己的时间为R=1+max(R,t)*/
	public static void update_round(int t)
	{
		synchronized (round)
		{
			if(round<t)round=t;
			round=round+1;
		}
	}
}
