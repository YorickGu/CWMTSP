package Multiple_Clustering;

import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;

import javax.swing.JFrame;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@SuppressWarnings({ "serial", "unused" })
public class CWMTSP {
	public static int[] count = new int[init.N];
	public static int[] arr = new int[init.N];
	public static double[][] zx = new double[init.N][2];
	public static double[][] ozx = new double[init.N][2];
	public static double[][] paixu = new double[init.N][2];
	public static double[][] locate1 = new double[init.N][2];
	public static int Number_types;
	public boolean r = true;
	private int Z = 0;
	public static ArrayList<double[][]> list;
	public ArrayList<double[][]> arry;
	public static ArrayList<int[]> result;

	public CWMTSP() {
		Find_center();
		list = new ArrayList<double[][]>(); // 定义了三个ArrayList集合
		arry = new ArrayList<double[][]>();
		result = new ArrayList<int[]>();
		Classification(); // 这边是分类，是将所有的数据点进行分成几个类
		paixu();
		/***************************************************************************************/
		// 接下来是将生成的有序的数组传入CW算法中，将每一个聚类单独作为一条数据链进行分析
		
		for (int i = 1; i <= Number_types; i++) {
			double[][] Parameter;
			double[][] arrayst;
			for (int m = 1; m < init.N; m++) {
				if (arr[m] == i) {
					Parameter = new double[1][2];
					Parameter[0][0] = locate1[m][0];
					Parameter[0][1] = locate1[m][1];
					arry.add(Parameter);
				}
			}
			int size = arry.size();
			arrayst = new double[size + 1][2];
			arrayst[0][0] = init.locate[0][0];
			arrayst[0][1] = init.locate[0][1];
			for (int q = 0; q < size; q++) {
				arrayst[q + 1][0] = arry.get(q)[0][0];
				arrayst[q + 1][1] = arry.get(q)[0][1];
			}

			//list这个集合就是将每一个二维数组构成的类添加到list中，使之将每一个聚类都可以单独运算
			list.add(arrayst); 
			arry.clear();
		}

		//wuchapingfanghe();
		//这里是将list中的每一个集合体中的元素全部拎出来显示，其中每一个聚类构成一行显示
//		for (int i = 0; i < list.size(); i++) {
//			for (int j = 0; j < list.get(i).length; j++) {
//				System.out.print(list.get(i)[j][0] + " " + list.get(i)[j][1] + " ");
//			}
//			System.out.println();
//		}

		
		int size = list.size();
		for (int i = 0; i < size; i++) {
			//将每一个list中的元素传入CWTSP中去进行计算，其中每一个元素都是一个数组
			CWTSP tCwtsp = new CWTSP(list.get(i));
			//result中添加的每一个元素也都是一个数组，
			result.add(tCwtsp.getans());
		}
		//这里是将已经进行CW节约算法过的路径进行显示
//		System.out.println();
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < result.get(i).length; j++) {
//				System.out.print(result.get(i)[j] + "→");
//			}
//			System.out.println();
//		}
//		System.out.println();

	}

	public void Classification() {
		double sum = 0;
		// 这是在将各个城市的需求进行求总和
		for (int i = 0; i < init.Demand.length; i++) {
			sum = sum + init.Demand[i];
		}
		Number_types = (int) (sum / 7.0) + 1; // 求出真正需要分成的类数

		/*
		 * 进行随机划分组， 这边其实是需要改动的，因为随机划分组是真正影响K均值算法的一个很重要的关键点，
		 * 因为正是由于随机产生的初分类，使得K均值的分类情况产生很大的随机性，而并不能保证其会是在最优解上。
		 */
		Random R = new Random();
		for (int i = 1; i < init.N; i++) {
			int j = Math.abs(R.nextInt()) % Number_types;
			arr[i] = j + 1;
		}
		
		System.out.println("miaomiaomiao");

		// 计算质心
		ClassiFicateZhixin();
		while (r) {
			Z++;
//			System.out.println("Z    " + Z);
			distance();
			ClassiFicateZhixin();
			if (Calculating_Centroid_Distance()) {
				Z = 0;
				r = false;
			}

			if (Z > 1000) {
				Z = 0;
				break;
			}

		}

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
		// for (int i = 1; i < 9; i++) {
		// System.out.println(zx[i][0] + " " + zx[i][1]);
		// }
		// System.out.println();

	}

	// 计算质心与各个点之间的距离，同时得出每个城市点距离哪个质心的距离最近，从而将城市归入哪个类
	public static void distance() {
		double[][] mindis = new double[init.N][2];
		for (int i = 1; i < init.N; i++) {
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
			// 这边是对所有的城市点的一个分类汇总，arr[i]即每个城市属于哪个类
			arr[i] = (int) mindis[i][1];
		}

		// 查看分组情况
		// for (int i = 0; i < init.N; i++) {
		// System.out.print(arr[i] + " ");
		// }
		// System.out.println();
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

	/*
	 * paixu 函数主要是将所有的已经分类好的城市点按照从1到n的分类数目依次排列好， 
	 * 简而言之，就是将类别为1的城市点放在最前面，接下来是分类点为2的城市点，依次往下
	 */
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
		double avex = 0;
		double avey = 0;

		for (int i = 1; i < init.N; i++) {
			avex = avex + init.locate[i][0];
			avey = avey + init.locate[i][0];
		}
		// 这边是计算所有城市的横坐标的平均值，作为所有城市到这个点的依据。
		init.locate[0][0] = avex / init.N;
		init.locate[0][1] = avey / init.N;
	}
	
	public double wuchapingfanghe() {
		double Error = 0;
		for(int i = 0;i<list.size();i++) {
			for(int j=1;j<list.get(i).length;j++) {
				Error = Error + sqrt(pow((list.get(i)[0][0] - list.get(i)[j][0]), 2) 
						+ pow((list.get(i)[0][1] - list.get(i)[j][1]), 2));
			}
		}
		return Error;
		
	}

	// public static CWMTSP getCwtsp() {
	// return cwtsp;
	// }
	//
	// public static void setCwtsp(CWMTSP cwtsp) {
	// CWMTSP.cwtsp = cwtsp;
	// }
}
