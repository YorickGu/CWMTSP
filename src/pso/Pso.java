package pso;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.swing.*;
import javax.swing.event.*;
public class Pso extends Frame implements Runnable
{
    private static int particleNumber;  //粒子的数量
    private static int iterations;      //迭代的次数
    private static int k=1;             //记录迭代的次数
    final private static float C1=2;    //学习因子
    final private static float C2=2;
    final private static float WMIN=-200;
    final private static float WMAX=200;
    final private static float VMAX=200;
    private static float r1;           //随机数0-1之间
    private static float r2;
    private static float x[][];
    private static float v[][];
    private static float xpbest[][];
    private static float pbest[];      
    private static float gbest=0;
    private static float xgbest[];
    private static float w;           //惯性因子
    private static float s;
    private static float h;
    private static float fit[];
//    public Sounds sound;
    
    //粒子群的迭代函数
public void lzqjs()
{
	  
		w=(float)(0.9-k*(0.9-0.4)/iterations);
        for(int i=0;i<particleNumber;i++)
        {
                   fit[i]= (float)(1/(Math.pow(x[i][0],2)+Math.pow(x[i][1],2))); //求适值函数最大值
                   System.out.print("粒子"+i+"本次适应值函数f为：" + fit[i]);
                   System.out.println();
                   if(fit[i]>pbest[i])
                   {
                   	pbest[i]=fit[i];
                   	xpbest[i][0]=x[i][0];
                   	xpbest[i][1]=x[i][1];
                   }
                   if(pbest[i]>gbest)
                   {
                   	gbest=pbest[i];
                   	xgbest[0]=xpbest[i][0];
                   	xgbest[1]=xpbest[i][1];
                   }
         }
         for(int i=0;i<particleNumber;i++)
         {
                   for(int j=0;j<2;j++)
                   {
                	   //粒子速度和位置迭代方程:
                   	v[i][j]=(float)(w*v[i][j]+C1*Math.random()*(xpbest[i][j]-x[i][j])+C2*Math.random()*(xgbest[j]-x[i][j]));
                   
                   	x[i][j]=(float)(x[i][j]+v[i][j]);
                   
                   }
               	System.out.print("粒子"+i+"本次X1的速度变化幅度:"+v[i][0]+";本次X2的速度变化幅度:"+v[i][1]);
                System.out.println();
            	System.out.print("粒子"+i+"本次X1为："+x[i][0]+";本次X2为："+x[i][1]);
                System.out.println();
         }
}
	public static void main(String[] args)
	{
		
		particleNumber=Integer.parseInt(JOptionPane.showInputDialog("请输入粒子个数1-500）"));
		iterations=Integer.parseInt(JOptionPane.showInputDialog("请输入迭代次数"));
		x=new float [particleNumber][2];
		v=new float [particleNumber][2];
		fit=new float [particleNumber];    //存储适值函数值
		pbest=new float [particleNumber];  //存储整个粒子群的最有位置
		xpbest=new float [particleNumber][2];
		xgbest=new float [2];
		for(int i=0;i<particleNumber;i++)
		{
			
			//对数组的初始化操作
			pbest[i]=0;
			xpbest[i][0]=0;
			xpbest[i][1]=0;
		}
		xgbest[0]=0;
		xgbest[1]=0;
		 System.out.println("开始初始化：");
		for(int i=0;i<particleNumber;i++)
		{
			
			for(int j=0;j<2;j++)
			{
				//任意给定每个位置一定的位置值和速度值
				x[i][j]=(float)(WMAX*Math.random()+WMIN);
				v[i][j]=(float)(VMAX*Math.random());
			}
			System.out.print("粒子"+i+"本次X1的变化幅度:"+v[i][0]+";本次X2的变化幅度:"+v[i][1]);
		 	 System.out.println();
		 	System.out.print("粒子"+i+"本次X1为："+x[i][0]+";本次X2为："+x[i][1]);
			 System.out.println();
		}
		System.out.println("初始化数据结束，开始迭代.....");
	Pso threada=new Pso();
	threada.setTitle("基于粒子群的粒子位置动态显示");
	threada.setSize(800,800);
	threada.addWindowListener(new gbck());
	threada.setVisible(true);
        Thread threadc=new Thread(threada);
        threadc.start();
	}
	static class gbck extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}
	
	//开启的额外线程用于声音的播放
//	public void run()
//	{
//       
//		repaint();
//        
//        for(int i=0;i<iterations;i++){
//        	sound();
//        }
//	}
	public void paint(Graphics g)
	{
		 
		   g.setColor(new Color(0,0,0));
	       for(int i=0;i<particleNumber;i++)
	       {
	       	g.drawString("*",(int)(x[i][0]+200),(int)(x[i][1]+200));
	       }
	       g.setColor(new Color(255,0,0));
	       g.drawString("全局最优适应度函数值："+gbest+"      参数1："+xgbest[0]+"     参数2："+xgbest[1]+"    迭代次数："+ k,50,725);

    try
	{
	lzqjs();  //开始迭代
	
	if(k>=iterations)
	{
		
		Thread.sleep((int)(5000));
		System.exit(0);
	}
	k=k+1;  //每次迭代一次加1操作
	Thread.sleep((int)(1000));
	}
    catch(InterruptedException e)
    {
		 System.out.println(e.toString());
    }
    repaint();
	}
//	public  void sound(){
//		  sound =new Sounds("050.wav");
//		  InputStream stream =new ByteArrayInputStream(sound.getSamples());
//		  // play the sound
//		  sound.play(stream);
//		  // exit
//
//	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}