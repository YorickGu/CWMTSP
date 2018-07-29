package testfun;


public class TestFun {
	 public static int[] arr = {0,6,6,2,7,4,1,8,8,3,5,6,4,
				1,1,5,3,4,1,7,2,3,6,8,1,1,8,
				6,2,3,3,8,6,5,3,2,2,4,3,5,7,
				7,7,8,7,5,4,4,8,5,3,4};

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		paixu();
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"  ");
        }
        System.out.println();

	}
	
    public static void paixu(){
        boolean flag = true;
        while(flag){
        	flag = false;
            int temp;
            for(int i =0;i<arr.length-1;i++){
                for(int j=0;j<arr.length-i-1;j++){
                    if(arr[j+1]<arr[j]){
                        temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                        flag = true;

                    }
                }
                if(!flag)
                	break;
            }
        }
    }

}
