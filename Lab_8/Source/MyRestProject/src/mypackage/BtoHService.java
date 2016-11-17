package mypackage;
import java.math.BigInteger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
 
@Path("/btohservice")
public class BtoHService {
 private String hex;
	  @GET
	  @Produces("application/xml")
	  public String BtoHConvertion(String b1) {
		  
 		hex=new BigInteger(b1,2).toString(16);
		
		String result = "" + hex;
		return result;
		
	  }
	  
	  
}