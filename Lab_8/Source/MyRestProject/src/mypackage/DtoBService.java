package mypackage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/dtobservice")
public class DtoBService {

	public String Decimal;
	@GET
	@Produces("application/xml")
	public String DtoBConvertion(int d) {
 
		Decimal = Integer.toBinaryString(d);
		
		String result1 = "" + Decimal;
		return result1;
		
	}
	
}