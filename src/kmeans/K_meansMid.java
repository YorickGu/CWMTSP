package kmeans;
//基于质心的k－means聚类算法

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


public class K_meansMid {

    private static int M=351;             //需要设置的参数为4行(包括本行)
    private static int k=15;               //类数
    private static int N=34;                //原数据的维数,属性个数
    private static String str="C:\\Users\\guyao\\Desktop\\k-means\\Iono.txt";     //测试数据地址

    private static int Z=0;   //迭代次数
    private static float[][] arr;  //存放原数据
    float zx[][];    //存放质心
    float ozx[][];   //保存质心旧值
    boolean r=true; //停止计算阈值
    double P1,P2=0;



	/*public void readfile(String str){//读文件函数
		initParam(str);
        readFile(str);
	}*/

    void Normalize(){     //归一化
        float max[]=new float[N];
        float min[]=new float[N];
        int i,j;

        for(i=0;i<N;i++){
            max[i]=min[i]=arr[0][i];
        }

        for(i=0;i<M;i++)//找每维的最大最小值
            for(j=0;j<N;j++){
                if(max[j]<arr[i][j])
                    max[j]=arr[i][j];
                if(min[j]>arr[i][j])
                    min[j]=arr[i][j];
            }

        for(i=0;i<M;i++)
            for(j=0;j<N;j++){
                if(max[j]==min[j])
                    ;
                else
                    arr[i][j]=(arr[i][j]-min[j])/(max[j]-min[j]);
            }
        //for(i=0;i<M;i++)
        //for(j=0;j<N;j++)
        //System.out.print(max[j]+"\t"+min[j]+"\n");
		/*for(i=0;i<M;i++){
            for(j=0;j<N;j++){
                System.out.print(arr[i][j]+",");
            }
            System.out.println();
        }*/
    }

    public void init(){

        arr=new float[M][N+1];      //分配数据存储空间
        zx= new float[k][N];        //分配质心存储空间
        ozx=new float[k][N];

        readFile(str);
        int i,j;                    //打印聚类原数据
		/*for(i=0;i<M;i++){

			System.out.print(i+"\t");
			for(j=0;j<4;j++){
				System.out.print(arr[i][j]);
				System.out.print("\t");
				if(j==3)
					System.out.print("\n");
			}
		}*/
        Normalize();
        calculate();
        for(i=0;i<M;i++)//打印聚类结果
            System.out.print((arr[i][N]+1)+"\n");
        System.out.println();

        for(i=0;i<zx.length;i++)//打印质心
            for(j=0;j<zx[i].length;j++){
                System.out.print(zx[i][j]+"\t");
                if(j==zx[i].length-1)
                    System.out.print("\n");
            }
        System.out.print("迭代次数:"+Z);
        System.out.print("\n"+P1+"\n"+P2);

    }





		/*public void initParam(String fileName){//读文件,不是很相关
	        int count = 0;
	        try{
	            FileReader fr = new FileReader(fileName);
	            BufferedReader br = new BufferedReader(fr);
	            while(br.readLine()!=null){
	                count++;
	            }
	            fr.close();
	            br.close();
	        }
	        catch(IOException e){
	            System.out.println("IOException");
	        }
	        M = count;
	    }*/


    public void readFile(String fileName){	  //读文件
        int count = 0,i,j;
        String str[] = new String[M];
        String str1 = null;
        String str2[] = new String[4];
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while((str1=br.readLine())!=null){
                str[count] = str1;
                count++;
            }
            fr.close();
            br.close();
        }
        catch(IOException e){
            System.out.println("IOException");
        }
        for(i=0;i<M;i++){
            str2=str[i].split(",");
            for(j=0;j<N;j++){
                Float ff = new Float(str2[j]);
                arr[i][j]=ff.floatValue();//存入数组
            }
        }
        //打印测试数据
	        /*for(i=0;i<M;i++){
	            for(j=0;j<N;j++){
	                System.out.print(arr[i][j]+",");
	            }
	            System.out.println();
	        }*/
    }




    public void calculate(){
        int i,j;
        Random R= new Random();
        for(i=0;i<M;i++){               //随机划分组
            j=Math.abs(R.nextInt())%k;
            arr[i][N]=j;
        }

        //计算质心
        calulatezhixin();
        while(r){
            Z++;
            distance();
            calulatezhixin();
            if(Math.abs(P1-P2)<0.000001) break;//控制循环结束条件
            for(int a=0;a<k;a++)//作用同一行
                for(int w=0;w<N;w++){
                    if(zx[a][w]!=ozx[a][w]){
                        r=true;
                        break;
                    }
                    else
                        r=false;

                }


        }



    }



    public void calulatezhixin(){//计算质心
        float s[]=new float[N];
        int i,j,l;
        for(i=0;i<k;i++)//保存原来的质心
            for(j=0;j<N;j++){
                ozx[i][j]=zx[i][j];
            }

        for(i=0;i<k;i++){

            for(l=0;l<N;l++) s[l]=0;
            l=0;

            for(j=0;j<M;j++)
                if(arr[j][N]==i){//为每一类计算质心
                    l++;
                    for(int z=0;z<N;z++)
                        s[z]+=arr[j][z];
                }
            for(int x=0;x<N;x++)
                zx[i][x]=s[x]/l;
        }
    }

    public void distance(){   //距离计算函数
        int i,j,w;
        double dis,z;
        P1=P2;
        P2=0;
        for(i=0;i<M;i++){
            dis=Double.MAX_VALUE;
            for(j=0;j<k;j++){
                z=0;
                for(w=0;w<N;w++){//欧氏距离
                    //z=z+Math.abs(zx[j][w]-arr[i][w]);
                    //if(w==0||w==1) z=z+0.0937*(zx[j][w]-arr[i][w])*(zx[j][w]-arr[i][w]);
                    z=z+(zx[j][w]-arr[i][w])*(zx[j][w]-arr[i][w]);
                }
                z=Math.sqrt(z);
                if(dis>z) {//到当前类近,那么归到当前类
                    dis=z;
                    arr[i][N]=j;
                    P2+=z;
                }
            }
        }

    }

    public static void main(String []args){
        K_meansMid k= new K_meansMid();
        k.init();


    }

}
