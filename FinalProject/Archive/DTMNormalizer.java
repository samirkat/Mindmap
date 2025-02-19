package Archive;

import java.util.ArrayList;


/*
 * This class reads in the Archive.DocumentTermMatrix and and normalizes
 * wordcounts on an article by article basis. 
 */
public class DTMNormalizer {
	FileIO io;
	ArrayList<String[]> normalizedArticles;
	double posSent;
	double negSent;
	double neutSent;
	
	
	/*
	 * constructor for the class 
	 */
	public DTMNormalizer(String filename){
		this.io = new FileIO(filename);
		
	}
	
	
	/*
	 * Normalizes the DTM
	 */
	public ArrayList<String[]> normalizeArticles(ArrayList<String[]> lines){
		//looping through each line and creating a word count for the article, 
		// and then normalizing each cell based off of that 
		normalizedArticles = new ArrayList<String[]>();
		String[] headers = lines.get(0);
		
		lines.remove(0);
		
		
		for (String[] line : lines) {
			int wordCount = 0;
			int posSentCount = Integer.valueOf(line[3]);
			int neutSentCount = Integer.valueOf(line[2]);
			int negSentCount = Integer.valueOf(line[1]);
			int totalSent = posSentCount + negSentCount + neutSentCount;
			
			double normPSC = (double) posSentCount/totalSent;
			double normNeutSC = (double) neutSentCount/totalSent;
			double normNegSC = (double) negSentCount/ (double) totalSent;
			
			line[3] = String.valueOf(normPSC);
			line[2] = String.valueOf(normNeutSC);
			line[1] = String.valueOf(normNegSC);
			
			//System.out.println("pos sent count: " + posSentCount + " and after normalization: " + normPSC);
			
			for (int i = 4; i < line.length -1; i++) {
				wordCount += Integer.valueOf(line[i]);
			}
			for (int i = 4; i < line.length -1; i++) {
				double newValue = (Double.valueOf(line[i]) / (double) wordCount);
				line[i] = String.valueOf(newValue);
				//System.out.println(line[i]);
		
			}
			
			
			
			//normalizing sentiments
			
			
			normalizedArticles.add(line);
			
		}
		
		
		return normalizedArticles;
	}
	

	
	
	
	public ArrayList<String[]> getNormalizedDTM() {
		
		ArrayList <String[]> lines = io.DTMfileReader("newsSourcesOut.csv");
		ArrayList <String[]> normalizedArticles = normalizeArticles(lines);
		return normalizedArticles;
		
	}
	
	
	
	public double getPositiveSentiment(int row) {
		String[] thisRow = normalizedArticles.get(row);
		posSent = Double.valueOf(thisRow[3]);
		//System.out.println("possent percent: " + posSent);
		return Math.round(posSent * 100);	
	}
	
	public double getNeutralSentiment(int row) {
		String[] thisRow = normalizedArticles.get(row);
		neutSent = Double.valueOf(thisRow[2]);
		return Math.round(neutSent * 100);	
		
	}
	
	public double getNegativeSentiment(int row) {
		String[] thisRow = normalizedArticles.get(row);
		negSent = Double.valueOf(thisRow[1]);
		return Math.round(negSent * 100) ;	
		
	}
	
	
	
	
	//main method
	
	public static void main(String[] args) {
		DTMNormalizer dtm = new DTMNormalizer("newsSourcesOut.csv");
		ArrayList <String[]> normalizedArticles = dtm.getNormalizedDTM();
		int rownum = 0;
		for (String[] row: normalizedArticles) {
			
			System.out.println(dtm.getPositiveSentiment(rownum));
			rownum++;
			
		}
		
	}
	
	
}
	
	
	
	
	
	
	



		
