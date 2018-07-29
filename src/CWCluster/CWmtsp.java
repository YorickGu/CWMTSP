package CWCluster;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CWmtsp {
	public static int[] count = new int[init.N];
	public static int[] arr = new int[init.N];
	public static double[][] zx = new double[init.N][2];
	public static double[][] ozx = new double[init.N][2];
	public static double[][] paixu = new double[init.N][2];
	public static double[][] locate1 = new double[init.N][2];
//	public static double[][] Parameter = new double[init.N][2];
	// public static double[][] sum =new double[init.N][2];
	public static int Number_types;
	public static boolean r = true;
	private static int Z = 0;
	public static ArrayList<double[][]> list;

	public static void main(String[] args) {
		list = new ArrayList<double[][]>();
		Classification();
		paixu();
		// 查看排序以及分许的情况
		System.out.println();
		System.out.println();
		for (int i = 0; i < init.N; i++) {
			System.out.print(locate1[i][0] + "\t" + locate1[i][1] + "\t" + arr[i]);
			System.out.println();
		}
		/***************************************************************************************/
		// 接下来是将生成的有序的数组传入CW算法中，将每一个聚类单独作为一条数据链进行分析
		for (int i = 1; i <= Number_types; i++) {
			int j = 1;
			double[][] Parameter = new double[20][2];
			for (int m = 1; m < init.N; m++) {
				if (arr[m] == i) {
					Parameter[j][0] = locate1[m][0];
					Parameter[j][1] = locate1[m][1];
					j++;
				}
			}
			list.add(Parameter);
		}
		
		for(int i=0;i<list.size();i++) {
			for(int j=1;j<20;j++) {
				System.out.print(list.get(i)[j][0]+" "+list.get(i)[j][1]+" ");
			}
			System.out.println();
		}
		
		//CWTSP tCwtsp = new CWTSP(list);

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
		for (int i = 0; i < init.N; i++) {
			mindis[i][0] = MAX_VALUE;
		}
		double[][] distance = new double[init.N][Number_types + 1];
		for (int i = 1; i < init.N; i++) {
			for (int j = 1; j <= Number_types; j++) {
				distance[i][j] = sqrt(pow((init.locate[i][0] - zx[j][0]), 2) + pow((init.locate[i][1] - zx[j][1]), 2));
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
			if (abs(dis) < 0.1) {
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
}
