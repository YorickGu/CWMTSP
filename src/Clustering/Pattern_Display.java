package Clustering;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Pattern_Display extends JPanel {
	final static double k = 10;
//	static double[][] locate = null;
//	static int[] route = null;
//	public static double[][][] locate = null;
//	public static int[][] route  = null;
	static ArrayList<double[][]> list = null;
	static ArrayList<int[]> result = null;

	public Pattern_Display(ArrayList<double[][]> list, ArrayList<int[]> result,double[][] zx) {
//		list = new ArrayList<double[][]>();
//		result = new ArrayList<int[]>();
		Pattern_Display.list = new ArrayList<double[][]>(list);
		Pattern_Display.result = new ArrayList<int[]>(result);
//		for(int i =0;i<list.size();i++)
//			Pattern_Display.list.add(list.get(i));
//		
//		for(int i=0;i<result.size();i++)
//			Pattern_Display.result.add(result.get(i));
//		locate = new double[list.size()][20][2];
//		route = new int[result.size()][20];
//		for(int i =0;i<list.size();i++) {
//			for(int j =0;j<list.get(i).length;j++) {
//				locate[i][j][0] = list.get(i)[j][0];
//				locate[i][j][1] = list.get(i)[j][1];
//			}
//		}
//		
//		for(int i =0;i<result.size();i++) {
//			for(int j=0;j<result.get(i).length;j++) {
//				route[i][j] = result.get(i)[j];
//			}
//		}
		

	}

	public void paint(Graphics g) {

		super.paint(g);
		
//		for(int i =0;i<locate.length;i++) {
//			int length = locate[0].length;
//			String[] str = new String[length];
//			for(int j=0;j<length;j++) {
//				str[j] = String.valueOf(j);
//				g.drawString(str[j], (int)(locate[i][j][0]*k)+2, (int)(locate[i][j][1]*k)+2);
//			}
//		}
//		
//		for(int i =0;i<locate.length;i++) {
//			int length = route[0].length;
//			for(int j=0;j<length-1;j++) {
//				g.drawLine((int)(locate[i][route[i][j]][0]*k), 
//						   (int)(locate[i][route[i][j]][1]*k), 
//						   (int)(locate[i][route[i][j+1]][0]*k),
//						   (int)(locate[i][route[i][j+1]][1]*k));
//			}
//		}

		for (int m = 0; m < list.size(); m++) {
			String[] str = new String[list.get(m).length];
			for (int i = 0; i < list.get(m).length; i++) {
				str[i] = String.valueOf(i);
				g.drawString(str[i], (int) (list.get(m)[i][0] * k) + 2, (int) (list.get(m)[i][1] * k) + 2);
			}
		}

		for (int m = 0; m < list.size(); m++) {
			for (int i = 0; i < result.get(m).length - 1; i++) {
				g.drawLine((int) (list.get(m)[result.get(m)[i]][0] * k), (int) (list.get(m)[result.get(m)[i]][1] * k),
						(int) (list.get(m)[result.get(m)[i + 1]][0] * k), (int) (list.get(m)[result.get(m)[i + 1]][1] * k));
			}
		}

	}

}
