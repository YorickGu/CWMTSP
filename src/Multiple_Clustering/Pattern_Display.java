package Multiple_Clustering;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Pattern_Display extends JPanel {
	final static double k = 10;
//	static ArrayList<double[][]> list = null;
//	static ArrayList<int[]> result = null;

//	public Pattern_Display(ArrayList<double[][]> list, ArrayList<int[]> result,double[][] zx) {
//		Pattern_Display.list = new ArrayList<double[][]>(list);
//		Pattern_Display.result = new ArrayList<int[]>(result);		
//
//	}

	public void paint(Graphics g) {

		super.paint(g);
		
		for (int m = 0; m < CWMTSP.list.size(); m++) {
			String[] str = new String[CWMTSP.list.get(m).length];
			for (int i = 0; i < CWMTSP.list.get(m).length; i++) {
				str[i] = String.valueOf(i);
				g.drawString(str[i], (int) (CWMTSP.list.get(m)[i][0] * k) + 2, (int) (CWMTSP.list.get(m)[i][1] * k) + 2);
			}
		}

		for (int m = 0; m < CWMTSP.list.size(); m++) {
			for (int i = 0; i < CWMTSP.result.get(m).length - 1; i++) {
				g.drawLine((int) (CWMTSP.list.get(m)[CWMTSP.result.get(m)[i]][0] * k), (int) (CWMTSP.list.get(m)[CWMTSP.result.get(m)[i]][1] * k),
						(int) (CWMTSP.list.get(m)[CWMTSP.result.get(m)[i + 1]][0] * k), (int) (CWMTSP.list.get(m)[CWMTSP.result.get(m)[i + 1]][1] * k));
			}
		}

	}

}
