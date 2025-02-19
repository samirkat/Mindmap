
/**
 * This class makes charts that display levels and statistics, specifically for reading level and density.
 * More specifically, it can:
 * - create a chart that displays the z-scores for the average reading level by source
 * - create a chart that displays the z-score so lexical density by source
 * - create a chart displaying reading levels by source
 * - create a chart displaying lexical density by source
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

public class LevelAndDensityCategoryChart  extends GenericChart  {

	// Instance Variables
	private HashMap<String, Double[]> LevelsBySource;
	private HashMap<String, Double[]> ZDataSource;
	private HashMap<String, Double[]> Zdata;

	/**
	 * The constructor, which extracts the source data for averages and z-score charts. 
	 */
	public LevelAndDensityCategoryChart() {
		LevelsBySource = extractSourceLevelData();
		ZDataSource = extractSourceLevelData();
		Zdata = makeZs(ZDataSource);

	}

	/**
	 * Creates a HashMap with the source mapped to a Double[] containing the average 
	 * reading level and average lexical density for that source.
	 * @return readingLevelBySource
	 */
	public HashMap<String, Double[]> extractSourceLevelData() {

		HashMap<String, Double[]> readingAndZBySource = new HashMap<>();

		// populating the hashmap
		for (Article article : super.getArticles()) {
			String source = article.getSource().trim();
			double readingLevel = article.getReadingLevel();
			double density = article.getLexicalDensity();

			if (readingAndZBySource.containsKey(source)) {
				Double[] levels = readingAndZBySource.get(source);
				levels[0] += readingLevel;
				levels[1] += density;
				readingAndZBySource.replace(source, levels);

			}

			else {
				Double[] levels = { readingLevel, density };
				readingAndZBySource.put(source, levels);

			}

		}

		// converting sums to averages
		for (String source : readingAndZBySource.keySet()) {
			Double[] levels = readingAndZBySource.get(source);
			levels[0] = levels[0] / 100;
			levels[1] = levels[1] / 100;
			readingAndZBySource.replace(source, levels);

		}

		return readingAndZBySource;
	}

	/**
	 * takes in the hashmap containing {average reading level, average density} by source
	 * converts averages (on a source level) into z-scores (on a corpus level) for
	 * reading level and lexical density for each source.
	 * 
	 * @param Zdata
	 * @return Zdata
	 */
	public HashMap<String, Double[]> makeZs(HashMap<String, Double[]> averagesMap) {

		double corpusReadingAvg = 0;
		double corpusReadingStDev = 0;
		double corpusDensityAvg = 0;
		double corpusDensityStDev = 0;
		

		//  sum the reading levels and densities for each source
		for (String source : averagesMap.keySet()) {
			Double[] levels = averagesMap.get(source);
			corpusReadingAvg += levels[0];
			corpusDensityAvg += levels[1];
			
		}

		// compute the average for reading Level and density by dividing by the total number of sources
		corpusReadingAvg = corpusReadingAvg / 14;
		corpusDensityAvg = corpusDensityAvg / 14;

		// compute the standard deviation for reading Level and density, for the corpus 
		for (String source : averagesMap.keySet()) {
			Double[] levels = averagesMap.get(source);
			
			//get the source average - overall average squared, and sum it up 
			Double readingN = levels[0];
			Double densityN = levels[1];
			readingN = Math.pow((double) readingN - corpusReadingAvg, 2);
			corpusReadingStDev += readingN;
			densityN = Math.pow((double) densityN - corpusDensityAvg, 2);
			corpusDensityStDev += densityN;

		}
		
		//compute standard deviation by dividing by the number of sources and taking the square root
		corpusReadingStDev = Math.sqrt(corpusReadingStDev /14);
		corpusDensityStDev = Math.sqrt(corpusDensityStDev /14);
		
		
		// compute the z-score for each source
		for (String source : averagesMap.keySet()) {
			Double[] levels = averagesMap.get(source);
			Double readingN = levels[0];
			Double densityN = levels[1];
			Double readingZ = (double) ((readingN - corpusReadingAvg) / corpusReadingStDev);
			Double densityZ = (double) ((densityN - corpusDensityAvg) / corpusDensityStDev);

			levels[0] = readingZ;
			levels[1] = densityZ;

			averagesMap.replace(source, levels);

		}

		return averagesMap;
	}

	/**
	 * This method creates the chart object, either for density z-scires or reading level z-scores, depending on the argument.
	 * @param avgType - reading level or density
	 * @return the chart object 
	 */
	public CategoryChart makeZChart(String zType) {

		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600)
				.title( zType + " Z-Scores by Source").xAxisTitle("Source")
				.yAxisTitle("Z-Score").theme(ChartTheme.GGPlot2).build();

		// Series

		String[] xseries = Zdata.keySet().toArray(new String[0]);
		ArrayList<Double> y1 = new ArrayList<>();
		ArrayList<Double> y2 = new ArrayList<>();

		for (String source : Zdata.keySet()) {
			Double[] levels = Zdata.get(source);
			Double readingLevel = levels[0];
			Double density = levels[1];
			y1.add(readingLevel);
			y2.add(density);

		}

		if(zType.contains("Density")) {
			chart.addSeries("lexical density", new ArrayList<String>(Arrays.asList(xseries)), y2);	
		}
		
		else {
			chart.addSeries("reading level", new ArrayList<String>(Arrays.asList(xseries)), y1);	
		}
		
		
		chart.getStyler().setXAxisLabelRotation(45);
		return chart;
	}

	
	/**
	 * This method creates the chart object, either for average density or average reading level, depending on the argument.
	 * @param avgType - reading level or density
	 * @return the chart object 
	 */
	public CategoryChart makeAvgsChart(String avgType) {
		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title(avgType + " by Source")
				.xAxisTitle("Source").yAxisTitle("Level").theme(ChartTheme.GGPlot2).build();
		
		// Series
		String[] xseries = LevelsBySource.keySet().toArray(new String[0]);
		ArrayList<Double> y1 = new ArrayList<>();
		ArrayList<Double> y2 = new ArrayList<>();

		for (String source : LevelsBySource.keySet()) {
			Double[] levels = LevelsBySource.get(source);
			Double readingLevel = levels[0];
			Double density = levels[1];
			y1.add(readingLevel);
			y2.add(density);

		}

		if (avgType.contains("Density")) {
			chart.addSeries("lexical density", new ArrayList<String>(Arrays.asList(xseries)), y2);
		}

		else {
			chart.addSeries("reading level", new ArrayList<String>(Arrays.asList(xseries)), y1);

		}
		
		
		
		chart.getStyler().setXAxisLabelRotation(45);
		return chart;

	}



}
