package CWCluster;

import java.util.ArrayList;

import Clustering.init;

public class CWTSP {
	
	public CWTSP(ArrayList<double[][]> list) {
		Find_center();
		
	}
	
	 public void Find_center() {
		 double avex=0;
		 double avey=0;
		 
		for(int i =1;i<init.N;i++) {
            avex = avex + init.locate[i][0];
            avey = avey + init.locate[i][0];
		}
        init.locate[0][0] = avex/init.N;      //这边是计算所有城市的横坐标的平均值，作为所有城市到这个点的依据。
        init.locate[0][1] = avey/init.N;
	}

}


