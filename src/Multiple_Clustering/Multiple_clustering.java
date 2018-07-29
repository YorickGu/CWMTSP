package Multiple_Clustering;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Multiple_clustering extends JFrame{

	public static int Total_cycle = 200;
	 static ArrayList<double[][]> list = new ArrayList<double[][]>();
     static ArrayList<int[]> result = new ArrayList<int[]>();
	
	
	public void init(){
		Pattern_Display pd = new Pattern_Display();
		/**************************************
		 * 绘图设置
		 ********************************************************/
		this.add(pd);
		this.setSize(1366, 730);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); // 最大化
		this.setResizable(false); // 不能改变大小
		// this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		/**************************************
		 * 绘图设置
		 ********************************************************/
		
		int Cycle = 0;
		double error;
		double error_old=Integer.MAX_VALUE;;
		while(Cycle<Total_cycle) {
			Cycle++;
			System.out.println();
			System.out.println(Cycle);
			System.out.println();
			
			CWMTSP cwmtsp_1= new CWMTSP();
			pd.repaint();
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//秒
			
			//这边是在计算误差平方和
			error=cwmtsp_1.wuchapingfanghe();
			System.out.println(error);
			System.out.println(error_old);
			if(error<error_old) {
				error_old = error;
				Multiple_clustering.list.clear();
				Multiple_clustering.result.clear();
				Multiple_clustering.list.addAll(CWMTSP.list);
				Multiple_clustering.result.addAll(CWMTSP.result);	
//				Multiple_clustering.list = (ArrayList<double[][]>) CWMTSP.list.clone();
//				Multiple_clustering.result = (ArrayList<int[]>) CWMTSP.result.clone();
			}		
			 
		}
		System.out.println(Cycle);
		CWMTSP.list.clear();
		CWMTSP.result.clear();
		CWMTSP.list.addAll(Multiple_clustering.list);
		CWMTSP.result.addAll(Multiple_clustering.result);
		
		System.out.println();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < result.get(i).length; j++) {
				System.out.print(result.get(i)[j] + "→");
			}
			System.out.println();
		}
		System.out.println();
		
		pd.repaint();


	}
	
	public static void main(String[] args) {
		new Multiple_clustering().init();

	}
	
	public static void Test(CWMTSP cwmtsp) {
		
	}
	

}
