/*
 * http://nifty.stanford.edu/2014/nicholson-image-stacker/student.html
 * 
 * 
 */
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Driver {

	public static void main(String[] args) throws Exception {
		//TODO: Maybe add a GUI
		File ImageDir = new File("cone_nebula");
		Scanner[] scanners = new Scanner[ImageDir.listFiles().length];
		int idx = 0;
		for (File file : ImageDir.listFiles()) {
			scanners[idx] = new Scanner(file);
			idx++;
		}
		String headers = skipHeader(scanners);
		File outputFile = new File("output.ppm");
		PrintWriter pout = new PrintWriter(outputFile);
		pout.print(headers);
		while (scanners[0].hasNextLine()) {
			Map<Integer, RGB> values= readLine(scanners);
			int[] reds   = new int[values.size()];
			int[] blues  = new int[values.size()];
			int[] greens = new int[values.size()];
			for(int i=0;i<values.size();i++){
				RGB val = values.get(i);
				reds[i]   = val.getR();
				blues[i]  = val.getB();
				greens[i] = val.getG();
				
			}
			RGB rgb;
			rgb = new RGB(getAverage(reds), getAverage(greens), getAverage(blues));
			pout.print(rgb.toPPM());

			

			
		}
		pout.close();

	}
	public static int getAverage(int[] numArray){
		int average=0;
		for (int i = 0; i < numArray.length; i++) {
			average += numArray[i];
		}
		return (average/numArray.length);
	}
	public static String skipHeader(Scanner[] scanners) {
		String toRet = "";
		for (int i = 0; i < 3; i++) {
			toRet += scanners[0].nextLine() + "\n";
		}

		for (int i = 1; i < scanners.length; i++) {
			scanners[i].nextLine();
			scanners[i].nextLine();
			scanners[i].nextLine();
		}
		return toRet;
	}

	public static Map<Integer, RGB> readLine(Scanner[] scanners) {

		Map<Integer, RGB> map = new HashMap<Integer, RGB>();
		for (int i = 0; i < scanners.length; i++) {
			String line = scanners[i].nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt(split[0]);
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt(split[2]);
			RGB temp = new RGB(red, green, blue);
			map.put(i, temp);
		}

		return map;

	}

}

class RGB {
	private int r;
	private int g;
	private int b;

	public RGB(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public String toPPM() {
		return String.format("%3d %3d %3d\n", r, g, b);
	}

	@Override
	public String toString() {
		return String.format("RED: %d, GREEN:%d, BLUE:%d", r, g, b);
	}
}