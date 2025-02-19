import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelAndDensityCategoryChartTest {
	LevelAndDensityCategoryChart tester = new LevelAndDensityCategoryChart();
	HashMap<String, Double[]> ZdataTest = new HashMap<>();
	
	
	

	
	

	@BeforeEach
	void setUp() throws Exception {
		
	
		
		
		
		
			
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testZScoreFunction() {
	
		Double[] testValues1 = {1.0, 2.0};
		Double[] testValues2 = {2.0, 2.0};
		Double[] testValues3 = {3.0, 2.0};
		Double[] testValues4 = {1.0, 2.0};
		Double[] testValues5 = {2.0, 2.0};
		Double[] testValues6 = {3.0, 2.0};
		Double[] testValues7 = {1.0, 2.0};
		Double[] testValues8 = {2.0, 2.0};
		Double[] testValues9 = {3.0, 2.0};
		Double[] testValues10 = {1.0, 2.0};
		Double[] testValues11 = {2.0, 2.0};
		Double[] testValues12 = {3.0, 2.0};
		Double[] testValues13 = {2.0, 2.0};
		Double[] testValues14 = {2.0, 2.0};
		ZdataTest.put("Source 1", testValues1 );
		ZdataTest.put("Source 2", testValues2 );
		ZdataTest.put("Source 3", testValues3 );
		ZdataTest.put("Source 4", testValues4 );
		ZdataTest.put("Source 5", testValues5 );
		ZdataTest.put("Source 6", testValues6 );
		ZdataTest.put("Source 7", testValues7 );
		ZdataTest.put("Source 8", testValues8 );
		ZdataTest.put("Source 9", testValues9 );
		ZdataTest.put("Source 10", testValues10 );
		ZdataTest.put("Source 11", testValues11 );
		ZdataTest.put("Source 12", testValues12 );
		ZdataTest.put("Source 13", testValues13 );
		ZdataTest.put("Source 14", testValues14 );
		HashMap<String, Double[]> zScores = tester.makeZs(ZdataTest);
		int size = ZdataTest.size();
		boolean zScores1 = ZdataTest.containsKey("Source 1");
		Double[] zScoresArray1 = ZdataTest.get("Source 1");
		assertEquals(14, size);
		assertTrue(zScores1);
		System.out.println(zScoresArray1[0]);
		assertEquals(-1.3228756555322954, zScoresArray1[0]);
		
	}
	
	

}
