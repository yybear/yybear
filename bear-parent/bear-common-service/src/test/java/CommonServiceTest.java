import org.apache.avro.AvroRemoteException;
import org.bear.api.Query;
import org.bear.api.SearcherService;
import org.bear.api.ShortUrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test.xml")
@ActiveProfiles("test")
public class CommonServiceTest {
	@Autowired
	private ShortUrlService shortUrlService;
	
	@Autowired
	private SearcherService searcherService;
	
	@Test
	public void shortUrlTest() throws AvroRemoteException {
		String s = shortUrlService.generate("xx", 0);
		
		System.out.println("shortUrlTest:" + s);
	}
	
	@Test
	public void searcherTest() throws AvroRemoteException {
		Query query = new Query();
		query.setId("id");
		searcherService.searcher(query);
	}
}
