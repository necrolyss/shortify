package lv.devternity.shortity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.veripacks.VeripacksBuilder$;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortityApplicationTests {

	@Test
	public void verifyPackaging() {
		VeripacksBuilder$.MODULE$.build()
			.verify("lv.devternity.shortity")
			.throwIfNotOk();
	}

}
