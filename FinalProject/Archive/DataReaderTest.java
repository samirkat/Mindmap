package Archive;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataReaderTest {

	DataReader reader = new DataReader();
	ArrayList<Article> articles = reader.readArray("articleMetricsArray.ser");

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	
	@Test
	void testReadArrayFunction() {	
	int corpusSize = articles.size();
	assertEquals(1400, corpusSize);		
	}
	
	@Test
	void articleSpotCheck() {	
		int idx = 1;
	    String source = (articles.get(idx).getSource().trim());
	    System.out.println(source);
		assertEquals("CNN", source);		
	}
	
	
	
	
	

}
