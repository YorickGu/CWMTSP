/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ACO;
public class ACOmain {


	public static void main(String[] args) {

		ACO aco = new ACO();
		double locate[][] = {  {37,52},{49,49},{52,64},{20,26},{40,30},{21,47},{17,63},{31,62},
				{52,33},{51,21},{42,41},{31,32},{5,25},{12,42},{36,16},{52,41},
				{27,23},{17,33},{13,13},{57,58},{62,42},{42,57},{16,57},{8,52},
				{7,38},{27,68},{30,48},{43,67},{58,48},{58,27},{37,69},{38,46},
				{46,10},{61,33},{62,63},{63,69},{32,22},{45,35},{59,15},{5,6},
				{10,17},{21,10},{5,64},{30,15},{39,10},{32,39},{25,32},{25,55},
				{48,28},{56,37},{30,40} 
				  };
		
		
		int M=30;
		int N=locate.length;
		int L=2000;
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
			System.out.println("最优的路径长度为: "+aco.globalBestLength);
			
/*			此处可以查看每次循环得到的最优解的情况
 * 			System.out.println("第"+i+"次循环过程中最优的路径长度为: "+ant[i].totalLength);
			System.out.println("第"+i+"最优路径为：");
			for(int j=0;j<locate.length;j++)
			{
			System.out.print("→"+aco.globalBestTour[j][0]);
			}
			System.out.println();*/
		}
		System.out.println();
		System.out.println("最优的路径长度为: "+aco.globalBestLength);
		System.out.println("最优路径为： ");
		for(int i=0;i<aco.globalBestTour.length;i++)
		{
		System.out.print("→"+aco.globalBestTour[i][0]);
		}
	}

}
