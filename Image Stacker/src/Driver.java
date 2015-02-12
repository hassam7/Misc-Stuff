import java.io.*;
import java.util.Scanner;


public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		//TODO: maybe add a gui
		File image = new File("images","3.ppm");
		
	}
	public static void horizontalBlur(File f) throws FileNotFoundException{
		Scanner imageScanner = new Scanner(f);
		String header = skipHeader(imageScanner);
		File output = new File("flipped.ppm");
		PrintWriter pw = new PrintWriter(output);
		pw.print(header);
		int i=1;
		int[] reds = new int[3];
		int[] blues = new int[3];
		int[] greens = new int[3];
		while(imageScanner.hasNextLine()){
			String line = imageScanner.nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt(split[0]);
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt(split[2]);
			reds[(i-1)%3] = red;
			greens[(i-1)%3] = green;
			blues[(i-1)%3] = blue;
			System.out.println((i-1)%3);
			int nRed=0,nGreen=0,nBlue=0;
			if(i%3==0){
				System.out.println("Avreaged");
				for(int j=0;j<3;j++){
					nRed += reds[j];
					nGreen += greens[j];
					nBlue += blues[j];
				}
				nRed   /=3;
				nBlue  /=3;
				nGreen /=3;
			}
			
			pw.print(String.format("%d %d %d\n",nRed,nGreen,nBlue));
			i++;
		}
		pw.close();
	}
	
	public static void flipHorizontal(File f) throws FileNotFoundException{
		Scanner imageScanner = new Scanner(f);
		String header = skipHeader(imageScanner);
		File output = new File("flipped" + f.getName());
		PrintWriter pw = new PrintWriter(output);
		pw.print(header);

		while(imageScanner.hasNextLine()){
			String line = imageScanner.nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt( reverse(split[0]) );
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt( reverse(split[2]));
			
			pw.print(String.format("%d %d %d\n",blue,green,red));
		}
		String s = "asd";
		
		pw.close();
	}
	public static String reverse(String s){
		String toRet = "";
		for(int i=s.length()-1;i>-1;i--){
			toRet += s.charAt(i);
		}
		return toRet;
	}
	public static void flattenRed(File f) throws FileNotFoundException{
		Scanner imageScanner = new Scanner(f);
		String header = skipHeader(imageScanner);
		File output = new File("flattenRed" + f.getName());
		PrintWriter pw = new PrintWriter(output);
		pw.print(header);

		while(imageScanner.hasNextLine()){
			String line = imageScanner.nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt(split[0]);
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt(split[2]);
			red = 0;
			pw.print(String.format("%d %d %d\n",red,green,blue));
		}
		pw.close();
	}
	public static void flattenGreen(File f) throws FileNotFoundException{
		Scanner imageScanner = new Scanner(f);
		String header = skipHeader(imageScanner);
		File output = new File("flattenGreed" + f.getName());
		PrintWriter pw = new PrintWriter(output);
		pw.print(header);

		while(imageScanner.hasNextLine()){
			String line = imageScanner.nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt(split[0]);
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt(split[2]);
			green = 0;
			pw.print(String.format("%d %d %d\n",red,green,blue));
		}
		pw.close();
	}
	public static void flattenBlue(File f) throws FileNotFoundException {
		Scanner imageScanner = new Scanner(f);
		String header = skipHeader(imageScanner);
		File output = new File("flattenBlue" + f.getName());
		PrintWriter pw = new PrintWriter(output);
		pw.print(header);

		while(imageScanner.hasNextLine()){
			String line = imageScanner.nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt(split[0]);
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt(split[2]);
			blue = 0;
			pw.print(String.format("%d %d %d\n",red,green,blue));
		}
		pw.close();
	}
	public static void extremeContrast(File f) throws FileNotFoundException{
		Scanner imageScanner = new Scanner(f);
		String header = skipHeader(imageScanner);
		File output = new File("extremeContrast" + f.getName());
		PrintWriter pw = new PrintWriter(output);
		pw.print(header);

		while(imageScanner.hasNextLine()){
			String line = imageScanner.nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt(split[0]);
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt(split[2]);
			if (red > 128) red = 255; else if (red < 128) red = 0;
			if(green > 128 ) green = 255; else if(green < 128) green = 0;
			if(blue > 128) blue = 255; else if(blue<128)blue=0;
			
			pw.print(String.format("%d %d %d\n",red,green,blue));
		}
		pw.close();
	}
	public static void toGreyScale(File f) throws FileNotFoundException{
		Scanner imageScanner = new Scanner(f);
		String header = skipHeader(imageScanner);
		File output = new File("images_greyScale.ppm");
		PrintWriter pw = new PrintWriter(output);
		pw.print(header);
		
		while(imageScanner.hasNextLine()){
			String line = imageScanner.nextLine();
			String[] split = line.split(" ");
			int red = Integer.parseInt(split[0]);
			int green = Integer.parseInt(split[1]);
			int blue = Integer.parseInt(split[2]);
			int average = (red+green+blue)/3;
			
			pw.print(String.format("%d %d %d\n",average,average,average));
		}
		pw.close();
	}
	public static String skipHeader(Scanner scanners){
		String toRet = "";
		for(int i=0;i<3;i++){
			toRet += scanners.nextLine()+"\n";
		}
		return toRet;
	}
}
