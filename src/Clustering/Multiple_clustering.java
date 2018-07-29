package Clustering;

public class Multiple_clustering {
	
	public static int Total_cycle = 500;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int Cycle = 0;
		while(Cycle<Total_cycle) {
			Cycle++;
			CWMTSP cwmtsp= new CWMTSP();
			Test(cwmtsp);
			
		}

	}
	
	public static void Test(CWMTSP cwmtsp) {
		
	}

}
