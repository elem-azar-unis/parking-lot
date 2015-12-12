package parkingLot;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class UI extends JFrame
{
	private static final long serialVersionUID = -8564100629069231827L;
	
	/**标签：总位置数目*/
	JLabel textTotalSpace=new JLabel("Total space: ");
	static JLabel jlbTotalSpace=null;
	/**标签：剩余数目*/
	JLabel textFreeSpace=new JLabel("Free space: ");
	static JLabel jlbFreeSpace=null;
	/**标签：被占据数目*/
	JLabel textOccupiedSpace=new JLabel("Occupied space: ");
	static JLabel jlbOccupiedSpace=null;
	/**标签：逻辑时钟周期数*/
	JLabel textLogicClock=new JLabel("Logic clock: ");
	static JLabel jlbLogicClock=null;
	/**标签：总连接数目*/
	JLabel textConnNum=new JLabel("Connection num: ");
	static JLabel jlbConnNum=null;
	
	JButton jbtEnter=new JButton("Enter");
	JButton jbtLeave=new JButton("Leave");
	
	Font textFont=new Font("Serif",Font.ITALIC,18);
	Font numberFont=new Font("Serif",Font.BOLD,18);
	
	public UI()
	{
		jlbTotalSpace=new JLabel(String.valueOf(FileInfo.total_space));
		jlbFreeSpace=new JLabel(String.valueOf(FileInfo.free_space));
		jlbOccupiedSpace=new JLabel(String.valueOf(FileInfo.total_space-FileInfo.free_space));
		jlbLogicClock=new JLabel(String.valueOf(0));
		jlbConnNum=new JLabel(String.valueOf(0));
		
		textConnNum.setFont(textFont);
		textFreeSpace.setFont(textFont);
		textLogicClock.setFont(textFont);
		textOccupiedSpace.setFont(textFont);
		textTotalSpace.setFont(textFont);
		
		jlbConnNum.setFont(numberFont);
		jlbFreeSpace.setFont(numberFont);
		jlbLogicClock.setFont(numberFont);
		jlbOccupiedSpace.setFont(numberFont);
		jlbTotalSpace.setFont(numberFont);
		
		jbtEnter.setFont(textFont);
		jbtEnter.setMnemonic('E');
		jbtEnter.setToolTipText("A car is entering.");
		jbtLeave.setFont(textFont);
		jbtLeave.setMnemonic('L');
		jbtLeave.setToolTipText("A car is leaving.");
		
		setLayout(new GridLayout(6,2));
		getContentPane().add(textConnNum);getContentPane().add(jlbConnNum);
		getContentPane().add(textLogicClock);getContentPane().add(jlbLogicClock);
		getContentPane().add(textTotalSpace);getContentPane().add(jlbTotalSpace);
		getContentPane().add(textOccupiedSpace);getContentPane().add(jlbOccupiedSpace);
		getContentPane().add(textFreeSpace);getContentPane().add(jlbFreeSpace);
		getContentPane().add(jbtEnter);getContentPane().add(jbtLeave);
		
		jbtEnter.addActionListener(e -> {
			jbtLeave.setEnabled(false);
			NodeTable.broadcast_request();
			BakeryQueue.wait_for_me();
			NodeTable.wait_all_reply();
			if(FileInfo.free_space>0)
			{
				int free=FileInfo.free_space-1;
				FileInfo.update_space(free);
				NodeTable.broadcast(new Message(Message.ALTER,free));
			}
			else 
			{
				JOptionPane.showMessageDialog(null,"Failed, there is no parking space left.");
			}
			NodeTable.broadcast_release();
			jbtLeave.setEnabled(true);
		});
		jbtLeave.addActionListener(e -> {
			jbtEnter.setEnabled(false);
			NodeTable.broadcast_request();
			BakeryQueue.wait_for_me();
			NodeTable.wait_all_reply();
			if(FileInfo.free_space<FileInfo.total_space)
			{
				int free=FileInfo.free_space+1;
				FileInfo.update_space(free);
				NodeTable.broadcast(new Message(Message.ALTER,free));
			}
			else 
			{
				JOptionPane.showMessageDialog(null,"Failed, there is no car parking here.");
			}
			NodeTable.broadcast_release();
			jbtEnter.setEnabled(true);
		});
	}
	public static void main(String[] args)
	{
		//请将nodes.txt文件放在工程的根目录下。
		FileInfo.init("nodes.txt");
		UI ui=new UI();
		ui.setTitle("Parking Lot No."+FileInfo.self);
		ui.setSize(310,300);
		ui.setLocationRelativeTo(null);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setVisible(true);
		Connector.connect();
	}

}
