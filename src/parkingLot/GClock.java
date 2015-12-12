package parkingLot;

/**静态类。全局时间，可以获得时间、更新时间、自增时间。*/
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
			//在界面上打印时间。
			UI.jlbLogicClock.setText(String.valueOf(round));
			UI.jlbLogicClock.repaint();
			return temp;
		}		
	}
	/**传入收到的时间t，更新自己的时间为R=1+max(R,t)*/
	public static void update_round(int t)
	{
		synchronized (round)
		{
			round=1+Math.max(round,t);
			//在界面上打印时间。
			UI.jlbLogicClock.setText(String.valueOf(round));
			UI.jlbLogicClock.repaint();
		}
	}
}