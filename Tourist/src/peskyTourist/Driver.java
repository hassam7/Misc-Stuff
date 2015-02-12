package peskyTourist;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		File images = new File("Images");
		Scanner[] scanners = new Scanner[images.listFiles().length];
		int index=0;
		for (File f : images.listFiles()) {
			if(f.getName().compareTo("output.ppm")==0)
				continue;
			scanners[index] = new Scanner(f);
			index++;
		}
		
		
		Scanner x = new Scanner(System.in);
		String headers = skipHeader(scanners);
				
		File outputFile = new File(images,"output.ppm");
		PrintWriter pout = new PrintWriter(outputFile);
		pout.print(headers);
		while(scanners[0].hasNextLine()){
			Map<Integer, RGB> values = readLine(scanners);
			int[] reds   = new int[values.size()];
			int[] blues  = new int[values.size()];
			int[] greens = new int[values.size()];
			for(int i=0;i<values.size();i++){
				RGB val = values.get(i);
				//System.out.println("File name: " + (i+1)+".ppm");
				//System.out.println("Values: "+val);
				reds[i]   = val.getR();
				blues[i]  = val.getB();
				greens[i] = val.getG();
				
			}
			RGB rgb;
			if(isSame(reds) && isSame(blues) && isSame(greens))
				rgb = new RGB(reds[0], greens[0], blues[0]);
			else
				rgb = new RGB(median(reds),median(blues),median(greens));
			
			pout.print(rgb.toPPM());
			//x.nextLine();
		}
		System.out.println("Done");
		pout.close();
	}
	public static boolean isSame(int[] numArray){
		int start = numArray[0];
		for(int i=1;i<numArray.length;i++){
			if(start!=numArray[i])
				return false;
		}
		return true;
	}
	public static int median(int[] numArray)
	{
		Arrays.sort(numArray);
		double median;
		if (numArray.length % 2 == 0)
		    median = ((double)numArray[numArray.length/2] + (double)numArray[numArray.length/2 - 1])/2;
		else
		    median = (double) numArray[numArray.length/2];
		return (int) median;
	}
	public static Map<Integer,RGB> readLine(Scanner[] scanners){
		
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
	public static String skipHeader(Scanner[] scanners){
		String toRet = "";
		for(int i=0;i<3;i++){
			toRet += scanners[0].nextLine()+"\n";
		}
		
		for (int i = 1; i < scanners.length; i++) {
			scanners[i].nextLine();
			scanners[i].nextLine();
			scanners[i].nextLine();
		}
		return toRet;
	}
	

}
class RGB{
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
	public String toPPM(){
		return String.format("%3d %3d %3d\n",r,g,b);
	}
	@Override
	public String toString(){
		return String.format("RED: %d, GREEN:%d, BLUE:%d", r,g,b);
	}
}
