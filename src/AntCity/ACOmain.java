package AntCity;

import javax.swing.JFrame;



public class ACOmain extends JFrame {
	
	Pattern pa =null;
	static int[][] result_route;
	static double globalBestLength;
	
	public void main_init(){
		ACO aco = new ACO();
		double locate[][] = new double[init.N - 1][2];
		for (int i = 0; i < init.N - 1; i++) {
			locate[i] = init.locate[i + 1].clone();
		}

		for (int i = 0; i < locate.length; i++) {
			System.out.println(locate[i][0] + "  " + locate[i][1]);
		}

		int N = locate.length; // 城市数目
		// int M=locate.length;
		int M = (int)(N*0.666667);  //城市数目
		int L=2500;
		double value;                        //信息素的初值设定
		double beta=5;
		double alpha=2;
		double localRate=0.1;
		double globalRate=0.1;
		
		aco.InitData(locate, N,M,L,beta,alpha,localRate,globalRate);
		aco.Length();
		value=1/(aco.Length*N);
		aco.InitInfo(value);
		
		Ant []ant=new Ant[L]; //记录每次循环过程中的最优蚂蚁
		
		aco.globalBestLength=aco.MAX_LENGTH;
		aco.globalBestTour=new int[N][2];
	
		for(int i=0;i<L;i++){
			
			ant[i]=aco.OneIterator();
			//System.out.println(i);
		//	此处可以查看每次循环得到的最优解的情况
//  		System.out.println(ant[i].totalLength);
  		System.out.println(aco.globalBestLength);
//			System.out.println("第"+i+"最优路径为：");
//			for(int j=0;j<locate.length;j++)
//			{
//			System.out.print("→"+aco.globalBestTour[j][0]);
//			}
//			System.out.println();
			
		}
		
		System.out.println();
		System.out.println("最优的路径长度为: "+aco.globalBestLength);
		System.out.println("最优路径为： ");
		for(int i=0;i<aco.globalBestTour.length;i++)
		{
		System.out.print("→"+aco.globalBestTour[i][0]);
		}
		result_route = aco.globalBestTour;
		globalBestLength = aco.globalBestLength;
	}
	
	
	private void acopaint(int i) {
		pa = new Pattern(result_route,globalBestLength);
		this.add(pa);
		this.setSize(1366, 730);
		this.setTitle(i+"");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		for(int i=0;i<20;i++) {
			long startTime=System.currentTimeMillis();
			new ACOmain().main_init();
			long endTime=System.currentTimeMillis();
			System.out.println();
			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
			new ACOmain().acopaint(i);
		}
		

	}

}
