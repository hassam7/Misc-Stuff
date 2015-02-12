/**
 * Code from http://nifty.stanford.edu/2014/nicholson-the-pesky-tourist/
 */
package peskyTourist;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImageToPpmProcessor {

	private File sourceDir;
	private File destDir;

	/**
	 * @param args
	 * @throws IOException
	 */
//	public static void main(String[] args) throws IOException {
//		ImageToPpmProcessor converter = new ImageToPpmProcessor();
//		ImageToPpmProcessor.Chooser chooser = converter.new Chooser();
//		chooser.setVisible(true);
//	}

	private void processFiles() throws IOException {
		File[] originalImages = sourceDir.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				String tmp = name.toLowerCase();
				return tmp.endsWith("jpg") || tmp.endsWith("gif")
						|| tmp.endsWith("bmp") || tmp.endsWith("png");
			}
		});

		System.out.println("Processing directory: " + sourceDir);

		for (File original : originalImages) {
			String filename = original.getName();
			filename = filename.substring(0, filename.length() - 4);
			convert(original);
		}

		JOptionPane.showMessageDialog(null, "Done.");

	}

	public void convert(File originalFile) throws IOException {
		System.out.println("Processing file: "
				+ originalFile.getCanonicalPath());
		String filename = originalFile.getName();
		filename = filename.substring(0, filename.length() - 4);

		BufferedImage image = ImageIO.read(originalFile);

		int height = image.getHeight();
		int width = image.getWidth();

		String ppmFilename = filename + ".ppm";
		File destFile = new File(destDir, ppmFilename);

		PrintWriter output = new PrintWriter(destFile);
		output.println("P3");
		output.println(width + " " + height);
		output.println("255");
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color pixel = new Color(image.getRGB(x, y));
				output.println(pixel.getRed() + " " + pixel.getGreen() + " "
						+ pixel.getBlue());
			}
		}
		output.close();
	}

	class Chooser extends JFrame implements ActionListener {
		private static final long serialVersionUID = -8389660714995462075L;

		private JTextField sourceDirTF;
		private JTextField destDirTF;
		private JButton sourceButton;
		private JButton destButton;

		public Chooser() throws IOException {
			JPanel panel = new JPanel(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();

			// source
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = 0;
			constraints.gridy = 0;
			panel.add(new JLabel("Source: "), constraints);

			sourceDir = new File(".");
			sourceDirTF = new JTextField();
			sourceDirTF.setText(sourceDir.getCanonicalPath());
			constraints.gridx++;
			panel.add(sourceDirTF, constraints);

			sourceButton = new JButton("Browse");
			sourceButton.addActionListener(this);
			constraints.gridx++;
			panel.add(sourceButton, constraints);

			// destination
			constraints.gridx = 0;
			constraints.gridy++;
			panel.add(new JLabel("Destination: "), constraints);

			destDir = new File(".");
			destDirTF = new JTextField();
			destDirTF.setText(destDir.getCanonicalPath());
			constraints.gridx++;
			panel.add(destDirTF, constraints);

			destButton = new JButton("Browse");
			destButton.addActionListener(this);
			constraints.gridx++;
			panel.add(destButton, constraints);

			// process
			JButton processButton = new JButton("Process");
			processButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					try {
						String sourcePath = sourceDirTF.getText().trim();
						sourceDir = new File(sourcePath);

						String destPath = destDirTF.getText().trim();
						destDir = new File(destPath);

						if (isDirOK(sourceDir) && isDirOK(destDir)) {
							processFiles();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});
			constraints.gridx = 0;
			constraints.gridy++;
			panel.add(processButton, constraints);

			add(panel);

			pack();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationByPlatform(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			File current;
			String title;
			if (e.getSource() == sourceButton) {
				current = sourceDir;
				title = "Choose source folder";
			} else {
				current = destDir;
				title = "Choose destination folder";
			}

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle(title);
			chooser.setCurrentDirectory(current);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			int result = chooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File dir = chooser.getSelectedFile();
				try {
					if (e.getSource() == sourceButton) {
						sourceDir = dir;
						sourceDirTF.setText(sourceDir.getCanonicalPath());
					} else {
						destDir = dir;
						destDirTF.setText(destDir.getCanonicalPath());
					}
				} catch (IOException ex) {
				}
			}
		}

	}

	private boolean isDirOK(File dir) throws IOException {
		if (!dir.exists()) {
			JOptionPane.showMessageDialog(null,
					"Error: the directory " + dir.getCanonicalPath()
							+ " does not exist.");
			return false;
		} else if (!dir.isDirectory()) {
			JOptionPane
					.showMessageDialog(null, "Error: " + dir.getCanonicalPath()
							+ " is not a directory.");
			return false;

		} else {
			return true;
		}
	}

}
