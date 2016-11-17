package mypackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class DtoBTest {
	DtoBService db=new DtoBService();
	String dec=db.DtoBConvertion(85);
	String  bin= "" + 1010101;
	
	
	@Test
	public void test() {
		System.out.println(dec+"="+bin);
		assertEquals(dec,bin);
		
	}

}
