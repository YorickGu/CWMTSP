package Multiple_Clustering;

import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;

public class Newest {
	static double locate[][] = {{22,22},{36,26},{21,45},{45,35},{55,20},{33,34},{50,50},
			{55,45},{26,59},{40,66},{55,65},{35,51},{62,35},{62,57},{62,24},{21,36},{33,44},
			{9,56},{62,48},{66,14},{44,13},{26,13},{11,28},{7,43},{17,64},{41,46},{55,34},
			{35,16},{52,26},{43,26},{31,76},{22,53},{26,29},{50,40},{55,50},{54,10},{60,15},
			{47,66},{30,60},{30,50},{12,17},{15,14},{16,19},{21,48},{50,30},{51,42},{50,15},
			{48,21},{12,38},{15,56},{29,39},{54,38},{55,57},{67,41},{10,70},{6,25},{65,27},
			{40,60},{70,64},{64,4},{36,6},{30,20},{20,30},{15,5},{50,70},{57,72},{45,42},
			{38,33},{50,4},{66,8},{59,5},{35,60},{27,24},{40,20},{40,37},{40,40}};
	final static int N=locate.length;    //the number of cities
	final static int M=30;                //the number of the ants
	final static int iter_max = 1000;    
	static double[][] dis=new double[N][N];
	static double[][] eta=new double[N][N];   // 启发式矩阵
	static double[][] Tau=new double[N][N];    //信息素矩阵
	static int[][] Table=new int[M][N];       //路径矩阵
	static int[] tabu=new int[N];          
	static int[] citys_index=new int[N];
	static int[] yjd=new int[N];             //拥挤度的下标
	static double[] yjds=new double[N];      //拥挤度的数值倒数
	static int[][] Routebest=new int[iter_max][N];
	static double[] Lengthbest=new double[iter_max];
	static double length_best;
	static double[] length=new double[M];
	static double Delta_Tau;
	static double[] P=new double[N];
	static double[] P1=new double[N];  
	static int min_index;
	
	public static void main(String[] args) {
		long startTime=System.currentTimeMillis();
		int iter=0;
	    init_1 it = new init_1();
	   
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				Tau[i][j]=0.000009;
			}
		}
		
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				Table[i][j]=0;
			}
		}
		
		for(int num=0;num<N;num++) {
			yjd[num]=0;
			yjds[num]=0.0;
		}

		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(i != j) {
					dis[i][j]= sqrt(pow((locate[i][0]-locate[j][0]),2)+pow((locate[i][1]-locate[j][1]),2));
					eta[i][j]=500/dis[i][j];
				}
			}
		}
	   // start to search the route
	   while(iter<iter_max) {
	   for (int i=1;i<=M;i++) {
		   Table[i-1][0]=i%N;     //将M只蚂蚁均匀放在N个城市上	
   }
    
	   for(int i=0;i<M;i++) {
		   for(int j=1;j<N;j++) {
//*******************************search a route****************************************************			   
			   for(int x=0;x<N;x++) {
				   citys_index[x]=x+1;
			   }
//**********************************************************************************************
			   for(int x=0;x<j;x++) {
				   tabu[x]= Table[i][x];    //tabu have visited the city
				   citys_index[tabu[x]-1]=0;  //the citys_index has become the cities that wait to search
				   }
//**********************************************************************************************			   
			   //计算拥挤度
			   for(int x=0;x<i;x++) {
				   if(Table[x][j-1]==tabu[j-1]) {
					   for(int num=0;num<N;num++) {
						   if(Table[x][j]==citys_index[num]) {
							   yjd[num]=yjd[num]+1;
						   }
					   }
				   }
			   }
			   for(int num=0;num<N;num++) {
				   if(citys_index[num]!=0) {
					   yjds[num]=(N-yjd[num])/N;
				   }
			   }
			   
//*************************************************************************************************
			   //limit the size of the pheromone
			   if(iter>0) {
			   for(int x=0;x<N;x++) {
				   if(citys_index[x] !=0) {
					   if(Tau[tabu[j-1]-1][x]>100/(it.rho*Lengthbest[iter])) {
						   Tau[tabu[j-1]-1][x]=100/(it.rho*Lengthbest[iter]);
						   Tau[x][tabu[j-1]-1]=100/(it.rho*Lengthbest[iter]);
					   }
					   else{
						   if(Tau[tabu[j-1]-1][x]<1/(it.rho*Lengthbest[iter])) {
							   Tau[tabu[j-1]-1][x]=1/(it.rho*Lengthbest[iter]);
							   Tau[x][tabu[j-1]-1]=1/(it.rho*Lengthbest[iter]);
						   }
					   }
				   }
			   }
			   }
//*************************************************************************************************
			// calculate the transition probability
			double q0 = 0.4;
			double q1 = random();
			int index=0;
			if(q1<q0) {
				for(int x=0;x<N;x++) {
					if(citys_index[x] !=0) {
						P[x]= pow(Tau[tabu[j-1]-1][x],it.alpha)*pow(eta[tabu[j-1]-1][x],(yjds[x]*it.beta));
						//P[x]= pow(Tau[tabu[j-1]-1][x],it.alpha)*pow(eta[tabu[j-1]-1][x],5.0);
						//P1[x]=P[x];
					}
					else {
						P[x]=0;
						//P1[x]=0;
					}
				}
			double max_p=P[0];
			for(int x=0;x<N;x++) {
				if(max_p<P[x]) {
					max_p=P[x];
					index=x;
				}
			}
			
			}
			else {
			for(int x=0;x<N;x++) {
				if(citys_index[x] !=0) {
					P[x]= pow(Tau[tabu[j-1]-1][x],it.alpha)*pow(eta[tabu[j-1]-1][x],(yjds[x]*it.beta));
					//P[x]= pow(Tau[tabu[j-1]-1][x],it.alpha)*pow(eta[tabu[j-1]-1][x],5.0);
					P1[x]=P[x];
				}
				else {
					P[x]=0;
					P1[x]=0;
				}
			}
			double Pum=0;
			double Pum1=0;
			for(int x=0;x<N;x++) {
				Pum=Pum+P[x];
			}
			for(int x=0;x<N;x++) {
				if(P[x]!=0) {
					Pum1=Pum1+P[x];
					P1[x]=Pum1/Pum;
				}

			}
			double q = random();
			
			while(P1[index]<q) {
				index++;
			}
			}
			Table[i][j] = index+1;	
			
			for(int num=0;num<N;num++) {
				yjd[num]=0;
				yjds[num]=0.0;
			}
//************************************************************************************************* 
			Tau[Table[i][j-1]-1][index]= (1.0-it.kes)*Tau[Table[i][j-1]-1][index]+(it.kes)*(it.tao0);
			Tau[index][Table[i][j-1]-1]=Tau[Table[i][j-1]-1][index];
//*************************************************************************************************
			   }
		   
		   }
//  start to calculate the length of the route
	   for(int i=0;i<M;i++) {
		   length[i]=0;
	   }
	   for(int i=0;i<M;i++) {
		   for(int j=0;j<N-1;j++) {
		   length[i] = length[i]+dis[Table[i][j]-1][Table[i][j+1]-1];
		   }
		   length[i] = length[i] +dis[Table[i][N-1]-1][Table[i][0]-1];
	   }
	   for(int x=0;x<M;x++) {
		   length_best=length[0];
		   min_index = 0;
		   if(length_best>length[x]) {
			   length_best=length[x];
			   min_index = x;
		   }
	   }
	   if(iter==0) {
		   Lengthbest[iter]=length_best;
		   for(int x=0;x<N;x++) {
			   Routebest[iter][x]=Table[min_index][x];
		   }
	   }
	   else {
		   if(Lengthbest[iter-1]<length_best) {
			   Lengthbest[iter]=Lengthbest[iter-1];
			   for(int x=0;x<N;x++) {
				   Routebest[iter][x]=Routebest[iter-1][x];
			   }
		   }
		   else {
			   Lengthbest[iter]=length_best;
			   for(int x=0;x<N;x++) {
				   Routebest[iter][x]=Table[min_index][x];
			   }
		   }
	   }

//********************************************************************************************

	  // if(iter>100) {
		   double length_tem=Lengthbest[iter];
		   double length1=0;
		   int[] route_tem=new int[N];
		   for(int x=0;x<N;x++) {
			   route_tem[x]=Routebest[iter][x];
		   }
		   route_tem=opt(route_tem,length_tem);
		   for(int x=0;x<N;x++) {
			   Routebest[iter][x]=route_tem[x];
		   }
		   for(int x=0;x<N-1;x++) {
				length1=length1+dis[route_tem[x]-1][route_tem[x+1]-1];
			}
			length1=length1+dis[route_tem[N-1]-1][route_tem[0]-1];
			Lengthbest[iter]=length1;

	   //}
	   

//********************************************************************************************
	   // update the local pheromone
	   Delta_Tau = 1/Lengthbest[iter];
	   for(int x=0;x<N-1;x++) {
		   Tau[Routebest[iter][x]-1][Routebest[iter][x+1]-1]=(1.0-it.rho)*Tau[Routebest[iter][x]-1][Routebest[iter][x+1]-1]+Delta_Tau*it.rho;
		   Tau[Routebest[iter][x+1]-1][Routebest[iter][x]-1]=Tau[Routebest[iter][x]-1][Routebest[iter][x+1]-1];
	   }
	   Tau[Routebest[iter][N-1]-1][Routebest[iter][0]-1]=(1.0-it.rho)*Tau[Routebest[iter][N-1]-1][Routebest[iter][0]-1]+Delta_Tau*it.rho;
	   Tau[Routebest[iter][0]-1][Routebest[iter][N-1]-1]=Tau[Routebest[iter][N-1]-1][Routebest[iter][0]-1];
	   
	  // System.out.println(iter);
	   
	   iter++;
	  
	   for(int i=0;i<M;i++) {
		   for(int j=0;j<N;j++) {
			   Table[i][j]=0;
		   }
		   
	   }
	   
//**********************************************	   
	   }
// show the best route 
	   System.out.println();
	   System.out.println(Lengthbest[iter_max-1]);
	   System.out.print("the best route is ");
	   for(int i=0;i<N;i++) {
		   System.out.print("→"+Routebest[iter_max-1][i]);
	   }
//**********************
	   long endTime=System.currentTimeMillis();
		System.out.println();
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	}
   //the end of the search
	
	public static int[] opt(int[] route,double length) {
		System.out.println(length);
		int[] route1=new int[N];
 		int[] route2=new int[N];
 		for(int t=0;t<N;t++) {
			route2[t]=route[t];
		}
		int i,j,i1,j1;
		double length1=0;
		double length3=length;
		int length2=0;
		int num=route.length;
		//System.out.println("num  "+num);
		for(i=0;i<num-1;i++) {
			//System.out.println("hhh");
			for(j=i+1;j<num;j++) {
				i1=i;
				j1=j;
				for(int x=0;x<N;x++) {
					route1[x]=route[x];
				}
				while(i1<j1) {
					length2=route1[i1];
					route1[i1]=route1[j1];
					route1[j1]=length2;
					i1++;
					j1--;
				}
				for(int x=0;x<N-1;x++) {
					length1=length1+dis[route1[x]-1][route1[x+1]-1];
				}
				length1=length1+dis[route1[N-1]-1][route1[0]-1];
				//System.out.println("length1  "+length1);
				if(length1<length3) {
				//	System.out.println("biale");
					length3=0;
					for(int x=0;x<N;x++) {
						route2[x]=route1[x];
					}
					length3=length1;				
//					route2=opt(route1,length1);	
//					for(int x=0;x<N-1;x++) {
//						length3=length3+dis[route2[x]-1][route2[x+1]-1];
//					}
//					length3=length3+dis[route2[N-1]-1][route2[0]-1];
				}
				length1=0;
			}
		}
		for(int x=0;x<N;x++) {
			route[x]=route2[x];
		}
		return route;
	}

}

class init_1{
	double alpha = 2;
	double beta = 50;
	double tao0 =2.07E-7;
	double rho = 0.4;
	double kes = 0.5;
}






