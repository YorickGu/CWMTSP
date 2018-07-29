package pso;

/***
 *  计算y=-x(x-1)的最大值
 *  取值范围x--[-2,2]
 * @author BreezeDust
 *
 */
public class PSOTest {
    int n=2; //粒子个数，这里为了方便演示，我们只取两个，观察其运动方向
    double[] y;
    double[] x;
    double[] v;
    double c1=2;
    double c2=2;
    double pbest[];
    double gbest;
    double vmax=0.1;
    public void fitnessFunction(){//适应函数
        for(int i=0;i<n;i++){
            y[i]=-1*x[i]*(x[i]-2);
        }
    }
    public void init(){ //初始化
        x=new double[n];
        v=new double[n];
        y=new double[n];
        pbest=new double[n];
        /***
         * 本来是应该随机产生的，为了方便演示，我这里手动随机落两个点，分别落在最大值两边
         */
        x[0]=-0.5;
        x[1]=2.6;
        v[0]=0.01;
        v[1]=0.02;
        fitnessFunction();
        //初始化当前个体极值，并找到群体极值
        for(int i=0;i<n;i++){
            pbest[i]=y[i];
            if(y[i]>gbest) gbest=y[i];
        }
        System.out.println("start gbest:"+gbest);
    }
    public double getMAX(double a,double b){
        return a>b?a:b;
    }
    //粒子群算法
    public void PSO(int max){
        for(int i=0;i<max;i++){
            double w=0.4;
            for(int j=0;j<n;j++){
                //更新位置和速度
                v[j]=w*v[j]+c1*Math.random()*(pbest[j]-x[j])+c2*Math.random()*(gbest-x[j]);
                if(v[j]>vmax) v[j]=vmax;
                x[j]+=v[j];
                //越界判断
                if(x[j]>2) x[j]=2;
                if(x[j]<-2) x[j]=-2;
                
            }
            fitnessFunction();
            //更新个体极值和群体极值
            for(int j=0;j<n;j++){
                pbest[j]=getMAX(y[j],pbest[j]);
                if(pbest[j]>gbest) gbest=pbest[j];
                System.out.println(x[j]+"  "+v[j]);
            }
            System.out.println("======"+(i+1)+"======gbest:"+gbest);
        }
        
        
    }
    public static void main(String[] args){
        PSOTest ts=new PSOTest();
        ts.init();
        ts.PSO(100);
    }

    

}