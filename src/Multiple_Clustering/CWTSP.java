package Multiple_Clustering;



import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CWTSP {
	static int length;
	static int[] ans = null;

	public CWTSP(double[][] arr) {
		CWTSP.length = arr.length;
		double[] disself = new double[length];
		double[][] dis = new double[length][length];
		double[][] S = new double[length][length];
		double[] e = new double[length * length];
		int[][] route = new int[length][length + 2];
		int[] x = new int[length * length];
		int[] y = new int[length * length];
		ans = new int[length + 2];
		int n = init(arr, disself, dis, S, e, x, y, route);
		function fun = new function(length);
		calculation(fun, route, x, y, ans,n);
	}

	public static int init(double[][] arr, double[] disself,
			double[][] dis, double[][] S, double[] e, 
			int[] x, int[] y,int[][] route) {
		for (int i = 1; i < length; i++) {
			disself[i] = sqrt(pow((arr[i][0]) - (arr[0][0]), 2) + pow((arr[i][1]) - (arr[0][1]), 2)); 
			// 每个点到原点的距离
		}

		int n = 0;

		for (int i = 1; i < length; i++) {
			for (int j = i + 1; j < length; j++) {
				n++;
				dis[i][j] = sqrt(pow((arr[i][0] - arr[j][0]), 2) // 点i和点j之间的距离
						+ pow((arr[i][1] - arr[j][1]), 2));
				dis[j][i] = dis[i][j];
				S[i][j] = disself[i] + disself[j] - dis[i][j]; // 计算节省值
				x[n] = i; // 将节省值的对应的下标对应记录下来
				y[n] = j;
				e[n] = S[i][j]; // 将节省值二维数组变成一维数组，以方便后面进行排序运算
			}
		}

		int flag = 0;
		for (int i = 1; i <= n; i++) {
			flag = 0;
			for (int j = 1; j < n + 1 - i; j++) { // 将各个点的顺序按照节省值的大小进行排列，用的方法是冒泡排序。
				if (e[j] < e[j + 1]) {
					double k = e[j];
					e[j] = e[j + 1];
					e[j + 1] = k;
					int a = x[j];
					x[j] = x[j + 1];
					x[j + 1] = a;
					int b = y[j];
					y[j] = y[j + 1];
					y[j + 1] = b;
					flag = 1;
				}
			}
			if (flag == 0) {
				break;
			}
		}

		for (int i = 0; i < length - 1; i++) {
			route[i][1] = i + 1;
		}

		return n;

	}

	// 这里是将已经排序好了的节省值对照路径，进行插入，将两头进行连接
	public static void calculation(function fun, int[][] route, int[] x, int[] y, int[] ans,int n) {
		for (int i = 1; i <= n; i++) {
			int[] a = fun.findindex(x[i], route);
			int[] b = fun.findindex(y[i], route);
			if (a[0] != b[0]) {
				int k = fun.Type(a, b, route);
				switch (k) {
				case 1:
					fun.inthequeue(a, b, route);
					break;
				case 2:
					fun.inthequeue2(a, b, route);
					break;
				case 3:
					fun.inthequeue3(a, b, route);
					break;
				case 4:
					fun.inthequeue4(a, b, route);
					break;
				default:
					break;
				}

			}

		}

		int cont = 0;
		for (int i = 0; i < length; i++) {
			if ((route[i][0] == 0) && (route[i][1] == 0)) {
				continue;
			}
			for (int j = 0; j < length + 2; j++) {
				ans[cont] = route[i][j];
				cont++;
				if ((route[i][j] == 0) && (route[i][j + 1] == 0)) {
					break;
				}

			}
		}
		
	}

	public static void print(double[][] dis, int length,int[] ans) {
		for (int i = 0; i < length + 2; i++) {
			System.out.print(ans[i] + "→");
		}
		System.out.println();

		double distance = 0;
		for (int i = 1; i < length - 1; i++) {
			distance = distance + dis[ans[i]][ans[i + 1]];
		}
		distance = distance + dis[length - 1][1];
		System.out.println(distance);
	}


	public int[] getans() {
		return ans;
	}
}
