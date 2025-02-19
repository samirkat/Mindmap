import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.knowm.xchart.*;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.internal.chartpart.Chart;

/**
 * In this class, we run the project.
 *
 */
public class ProjectRunner extends JFrame {

	// Instance Variables
	private LevelAndDensityCategoryChart ldCat = new LevelAndDensityCategoryChart();
	private CategoryChart zChartReading;
	private CategoryChart zChartDensity;
	private CategoryChart readingLevel;
	private CategoryChart densityChart;

	private LengthDensityAndLevelXYCharts ldlXY = new LengthDensityAndLevelXYCharts();
	private XYChart lengthDensity;
	private XYChart lengthReadingLevel;
	private XYChart readingLevelDensity;

	private FrequencyChart fc = new FrequencyChart();
	private XYChart personfreq;

	private SentimentChart sc = new SentimentChart();
	private CategoryChart sentsource;
	private PieChart pos;
	private PieChart neg;
	private PieChart neut;

	private ArrayList<Chart> charts = new ArrayList<>();

	/**
	 * Constructor, which makes all the chart objects we need to display
	 */
	public ProjectRunner() {
		this.zChartReading = ldCat.makeZChart("Reading Level");
		this.zChartDensity = ldCat.makeZChart("Density");
		this.readingLevel = ldCat.makeAvgsChart("Reading Level");
		this.densityChart = ldCat.makeAvgsChart("Density");
		this.pos = sc.makeSentimentPieChart("pos");
		this.neg = sc.makeSentimentPieChart("neg");
		this.neut = sc.makeSentimentPieChart("neut");
		this.lengthDensity = ldlXY.makeReadingandLengthChart(0, 2, "Article Length", "Density of Article");
		this.lengthReadingLevel = ldlXY.makeReadingandLengthChart(0, 1, "Article Length", "Reading Level of Article");
		this.readingLevelDensity = ldlXY.makeReadingandLengthChart(1, 2, "Reading Level of Article",
				"Density of Article");
		this.personfreq = fc.makeFrequencyChart();
		this.sentsource = sc.makeSentimentBySourceChart();

	}

	/**
	 * This method saves a matrix of the charts to a jpeg file, which can then be
	 * displayed.
	 */
	public void makeChartsMatrix() {
		charts.add(readingLevel);
		charts.add(zChartReading);
		charts.add(densityChart);
		charts.add(zChartDensity);

		charts.add(sentsource);
		charts.add(pos);
		charts.add(neg);
		charts.add(neut);

		charts.add(lengthDensity);
		charts.add(lengthReadingLevel);
		charts.add(readingLevelDensity);

		charts.add(personfreq);

		try {
			BitmapEncoder.saveBitmap(charts, 12, 1, "chartsMatrix", BitmapFormat.GIF);
		} catch (IOException e) {
			System.out.println(
					"Hmmm...something went wrong. Please ensure that you have a fully up-to-date pom.xml file for this project.");
			e.printStackTrace();
		}

	}

	/**
	 * This method displays the matrix jpeg to a scrollable swing panel.
	 */
	public void displayCharts() {
		ImageIcon ii = new ImageIcon("chartsMatrix.gif");
		JScrollPane jsp = new JScrollPane(new JLabel(ii));

		getContentPane().add(jsp);

		setSize(1300, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {

		Scanner myScanner = new Scanner(System.in);
		System.out.println("Hello! You are about to run the analysis on data that has been processed on the full data set.\n"
				+ "If you would like to see the data processing part of this project, type Y to process a small subset.\n"
				+ "To continue the analysis on the full dataset, enter any other key:");
		

		String input = myScanner.nextLine().toString();

		if (input.contains("Y") || input.contains("y")) {
			System.out.println(
					"Okay! Creating an .ser with 14 fully annotated article objects using the default NOP logger.");
			System.out.println("This will just take a few minutes.");
			RawDocumentReader rdr = new RawDocumentReader("newsSourcesSAMPLED1.csv");
			System.out.println("A test .ser has been created and saved! It is called 'atricleMetricsAray.ser'.");
			System.out.println();
		}
		
		

		System.out.println("Running the full data analysis... ");
		ProjectRunner pr = new ProjectRunner();
		System.out.println("Making a matrix of all of the charts to display...");
		System.out.println("Please wait one moment - the charts will pop up in a Swing window shortly.");
		pr.makeChartsMatrix();
		pr.displayCharts();

	}

}
