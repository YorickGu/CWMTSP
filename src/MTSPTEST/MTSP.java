package MTSPTEST;

/*
 *CW节约值算法 
 * */
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class MTSP {
	
	public static double[][] locate = {{0,0},{37,52},{49,49},{52,64},{20,26},{40,30},{21,47},{17,63},{31,62},
			{52,33},{51,21},{42,41},{31,32},{5,25},{12,42},{36,16},{52,41},
			{27,23},{17,33},{13,13},{57,58},{62,42},{42,57},{16,57},{8,52},
			{7,38},{27,68},{30,48},{43,67},{58,48},{58,27},{37,69},{38,46},
			{46,10},{61,33},{62,63},{63,69},{32,22},{45,35},{59,15},{5,6},
			{10,17},{21,10},{5,64},{30,15},{39,10},{32,39},{25,32},{25,55},
			{48,28},{56,37},{30,40}};
	
	public final static int N=locate.length;
	static double[] disself=new double[N];
	static double[][] dis = new double[N][N];  //距离矩阵
	static double[][] S = new double[N][N];	//节省值矩阵
	static double[] e = new double[N*N];    //节省值降序排列
	static int[] route =new int [N*2+1];  
	static int[] x =new int[N*N];
	static int[] y =new int[N*N];
	static int n = 0;
	static int flag =0;
	static int q= 3;
	static int r=0;
	static int[] s = new int[2*N+1];

	public static void main(String[] args) {
		
		for(int i=1;i<N;i++) {
			disself[i] = sqrt(pow((locate[i][0]-35),2)+pow((locate[i][1]-39),2)); //每个点到原点的距离
		}
		
		//每个点和每个点之间的距离，同时计算节约值
		for(int i=1;i<N;i++) {
			for(int j=i+1;j<N;j++) {
				n++;
				dis[i][j] = sqrt(pow((locate[i][0]-locate[j][0]),2)+pow((locate[i][1]-locate[j][1]),2));
				S[i][j] = disself[i]+disself[j]-dis[i][j];
				x[n] = i;
				y[n] = j;
				e[n] = S[i][j];
			}
		}
		
		//对节省值进行排序，按照从小到大的顺序依次进行查看
		for(int i =1;i<n;i++) {
			flag = 0;
			for(int j = 1;j<n+1-i;j++) {
				if(e[j]>e[j+1]) {
					double k=e[j];
					e[j] =e[j+1];
					e[j+1] = k;
					int a = x[j];
					x[j] = x[j+1];
					x[j+1] = a;
					int b = y[j];
					y[j] = y[j+1];
					y[j+1] = b;
					flag = 1;
				}
			}
			if(flag==0) {
				break;
			}
		}
		
		//控制台输出节省值排序的结果
		System.out.println("节约值排序");
		for(int i =n;i>0;i--) {
			if(e[i]!=0) {
				System.out.println(e[i]+"...("+locate[x[i]][0]+","+locate[x[i]][1]+")--("+locate[y[i]][0]+","+locate[y[i]][1]+")");
			}
		}
		
		route[1] = x[n];
		route[2] = y[n];
		
		System.out.println("x[n]= "+x[n]);
		System.out.println("y[n]= "+y[n]);
		
		for(int m = 1;m<N;m++) {
			for(int j = n-1;j>0;j--) {
				int p=0;
				for(int i=1;i<(2*N+1);i++) {
					if(x[j]==route[i])
						p = p+1;
					if(y[j]==route[i])
						p = p+1;
				}
				if(p==1) {
					route[q] = x[j];
					route[q+1] = y[j];
				}
				if(p==1)
					break;
			}
			q=q+2;
		}
		
		System.out.println("选出连接点");
		for(int i=1;i<2*N+1;i=i+2) {
			if(route[i]!=0) {
				System.out.println("("+locate[route[i]][0]+","+locate[route[i]][1]+")---("+locate[route[i+1]][0]+","+locate[route[i+1]][1]+")");
				r=r+2;
			}
		}

		s[N] = route[1];
		s[N+1] = route[2];
		for(int i =3;i<r;i=i+2) {
			for(int j =1;j<2*N+1;j++) {
				if(route[i]==s[j]) {
					if(s[j-1]==0)
						s[j-1] = route[i+1];
					else 
						s[j+1] = route[i+1];
					break;
				}
				if(route[i+1]==s[j]) {
					if(s[j-1]==0)
						s[j-1] =route[i];
					else
						s[j-1] = route[i];
					break;
				}
			}
		}
		
		//显示旅行商路线
		System.out.println("旅行商路线");
		for(int i=0;i<2*N+1;i++) {
			if((s[i]!=0)&&(s[i+1]!=0)) {
				System.out.print(s[i]+"→");
			}
		}
		
		//显示坐标点的移动
		System.out.println();
		for(int i =0;i<2*N+1;i++) {
			if((s[i]!=0)&&(s[i+1]!=0)) {
				System.out.print("("+locate[s[i]][0]+","+locate[s[i]][1]+")→");
			}
		}

	}

}
