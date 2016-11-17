package mypackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class BtoHTest {

	BtoHService bh=new BtoHService();
	String hex=bh.BtoHConvertion("1010101");
	String bin = "" + 55;
	
	
	@Test
	public void test() {
		System.out.println(hex+"="+bin);
		assertEquals(hex,bin);
		
	}

}
