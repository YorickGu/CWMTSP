package Clustering;

import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;

import javax.swing.JFrame;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
 
public class CWMTSP extends JFrame {
	public static int[] count = new int[init.N];
	public static int[] arr = new int[init.N];
	public static double[][] zx = new double[init.N][2];
	public static double[][] ozx = new double[init.N][2];
	public static double[][] paixu = new double[init.N][2];
	public static double[][] locate1 = new double[init.N][2];
	public static int Number_types;
	public static boolean r = true;
	private static int Z = 0;
	public static ArrayList<double[][]> list;
	public static ArrayList<double[][]> arry;
	public static ArrayList<int[]> result;
	Pattern_Display pd = null;
	private static CWMTSP cwtsp;

	public static void main(String[] args) {
		Find_center();
		list = new ArrayList<double[][]>();
		arry = new ArrayList<double[][]>();
		result = new ArrayList<int[]>();
		Classification();
		paixu();
		// 查看排序以及分组的情况
		System.out.println();
		System.out.println();
		for (int i = 0; i < init.N; i++) {
			System.out.print(locate1[i][0] + "\t" + locate1[i][1] + "\t" + arr[i]);
			System.out.println();
		}
		/***************************************************************************************/
		// 接下来是将生成的有序的数组传入CW算法中，将每一个聚类单独作为一条数据链进行分析
		for (int i = 1; i <= Number_types; i++) {
			// int j = 1;
			double[][] Parameter;
			double[][] arrayst;
			for (int m = 1; m < init.N; m++) {
				if (arr[m] == i) {
					Parameter = new double[1][2];
					Parameter[0][0] = locate1[m][0];
					Parameter[0][1] = locate1[m][1];
					// j++;
					arry.add(Parameter);
				}
			}
			int size = arry.size();
			arrayst = new double[size+1][2];
			arrayst[0][0] = init.locate[0][0];
			arrayst[0][1] = init.locate[0][1];
			for(int q=0;q<size;q++) {
				arrayst[q+1][0] = arry.get(q)[0][0];
				arrayst[q+1][1] = arry.get(q)[0][1];
			}

			list.add(arrayst);
			arry.clear();
		}

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j <list.get(i).length; j++) {
				System.out.print(list.get(i)[j][0] + " " + list.get(i)[j][1] + " ");
			}
			System.out.println();
		}

		int size = list.size();
		for (int i = 0; i < size; i++) {
			CWTSP tCwtsp = new CWTSP(list.get(i));
			result.add(tCwtsp.getans());
		}
		//int size = result.size();
		for(int i=0;i<size;i++) {
			for(int j=0;j<result.get(i).length;j++) {
				System.out.print(result.get(i)[j]+"→");
			}
			System.out.println();
		}
		
		setCwtsp(new CWMTSP());

	
	}
	
	public CWMTSP() {
		/**************************************绘图设置********************************************************/
//		for(int i=0;i<list.size();i++) {
//			pd = new Pattern_Display(list.get(i), result.get(i));
//			this.add(pd);
//		}
		 pd = new Pattern_Display(list,result,zx);
		 this.add(pd);
		 this.setSize(1366, 730);
		 this.setLocationRelativeTo(null);
		 this.setExtendedState(JFrame.MAXIMIZED_BOTH); // 最大化
		 this.setResizable(false); // 不能改变大小
		 // this.setUndecorated(true);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
/**************************************绘图设置********************************************************/
	}

	public static void Classification() {
		double sum = 0;
		for (int i = 0; i < init.Demand.length; i++) {
			sum = sum + init.Demand[i];
		}
		Number_types = (int) (sum / 5.0) + 1;
		Random R = new Random();
		for (int i = 1; i < init.N; i++) { // 随机划分组
			int j = Math.abs(R.nextInt()) % Number_types;
			arr[i] = j + 1;
		}

		// 查看分组情况
		for (int i = 0; i < init.N; i++) {
			System.out.print(arr[i] + "  ");
		}
		System.out.println();

		// 计算质心
		ClassiFicateZhixin();
		while (r) {
			Z++;
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("Z   " + Z);
			distance();
			ClassiFicateZhixin();
			if (Calculating_Centroid_Distance()) {
				r = false;
			}

			if (Z > 1000) {
				break;
			}

		}

		// 接下来开始将所有已经归类好的数组进行分组排列
		// for(int i= 1;i<=Number_types;i++){
		// for(int j=1;j<init.N;j++){
		// if(arr[j]==i){
		//
		// }
		// }
		// }

	}

	public static void ClassiFicateZhixin() {
		double[][] sum = new double[init.N][2];
		for (int i = 0; i < init.N; i++)
			count[i] = 0;
		for (int i = 1; i < init.N; i++) {
			ozx[i][0] = zx[i][0];
			ozx[i][1] = zx[i][1];
		}
		for (int i = 1; i < init.N; i++) { // 为每一类的聚类点计算总的横纵坐标的总和
			sum[arr[i]][0] = sum[arr[i]][0] + init.locate[i][0];
			sum[arr[i]][1] = sum[arr[i]][1] + init.locate[i][1];
			count[arr[i]]++;
		}

		for (int i = 1; i <= Number_types; i++) {
			zx[i][0] = sum[i][0] / count[i];
			zx[i][1] = sum[i][1] / count[i];
		}

		// 查看质心的分布情况
		for (int i = 1; i < 9; i++) {
			System.out.println(zx[i][0] + "  " + zx[i][1]);
		}

	}

	public static void distance() { // 计算质心与各个点之间的距离
		double[][] mindis = new double[init.N][2];
		for (int i = 1; i < init.N; i++) {
			mindis[i][0] = MAX_VALUE;
		}
		double[][] distance = new double[init.N][Number_types + 1];
		for (int i = 1; i < init.N; i++) {
			for (int j = 1; j <= Number_types; j++) {
				distance[i][j] = sqrt(pow((init.locate[i][0] - zx[j][0]), 2) 
						            + pow((init.locate[i][1] - zx[j][1]), 2));
				if (distance[i][j] < mindis[i][0]) {
					mindis[i][0] = distance[i][j];
					mindis[i][1] = j;
				}
			}
		}
		for (int i = 1; i < init.N; i++) {
			arr[i] = (int) mindis[i][1];
		}

		for (int i = 0; i < init.N; i++) {
			System.out.print(arr[i] + "  ");
		}
		System.out.println();
	}

	// 计算新旧的质心之间的偏差距离，如果偏差在一定的范围内，那么则可以退出聚类算法的过程
	public static boolean Calculating_Centroid_Distance() {
		double dis;
		for (int i = 1; i <= Number_types; i++) {
			dis = sqrt(pow((zx[i][0] - ozx[i][0]), 2) + pow((zx[i][1] - ozx[i][1]), 2));
			if (abs(dis) < 0.01) {
				continue;
			} else
				return false;
		}
		return true;
	}

	public static void paixu() {
		boolean flag = true;
		for (int i = 0; i < init.N; i++) {
			locate1[i][0] = init.locate[i][0];
			locate1[i][1] = init.locate[i][1];
		}
		while (flag) {
			flag = false;
			int temp;
			double temp1;
			for (int i = 1; i < arr.length; i++) {
				for (int j = 1; j < arr.length - i; j++) {
					if (arr[j + 1] < arr[j]) {
						temp = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = temp;

						temp1 = locate1[j + 1][0];
						locate1[j + 1][0] = locate1[j][0];
						locate1[j][0] = temp1;

						temp1 = locate1[j + 1][1];
						locate1[j + 1][1] = locate1[j][1];
						locate1[j][1] = temp1;
						flag = true;

					}
				}
				if (!flag)
					break;
			}
		}
	}
	
	 public static void Find_center() {
		 double avex=0;
		 double avey=0;
		 
		for(int i =1;i<init.N;i++) {
            avex = avex + init.locate[i][0];
            avey = avey + init.locate[i][0];
		}
		//这边是计算所有城市的横坐标的平均值，作为所有城市到这个点的依据。
        init.locate[0][0] = avex/init.N;      
        init.locate[0][1] = avey/init.N;
	}

	public static CWMTSP getCwtsp() {
		return cwtsp;
	}

	public static void setCwtsp(CWMTSP cwtsp) {
		CWMTSP.cwtsp = cwtsp;
	}
}
