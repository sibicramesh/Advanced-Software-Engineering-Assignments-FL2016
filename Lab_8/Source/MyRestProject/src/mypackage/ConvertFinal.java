package mypackage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/convertfinal")
public class ConvertFinal {
public static void main(String[] args){
	
}
	
public ConvertFinal(){
	
}
public String a;

		@GET
		@Path("/dtobservice1")
		@Produces("application/xml")
		public String convertDtoB(int f) {
	 DtoBService n=new DtoBService();
	 a=n.DtoBConvertion(f);
	  return a;
		
		}
			
		  @GET
		  @Path("/btohservice1")
		  @Produces("application/xml")
		  
		  public String convertBtoH() {
			  			  
			  BtoHService a2=new BtoHService();
				 String c=a2.BtoHConvertion(a);
					
			String result3 = "" + c;
			return result3;
			
		  }
						
		@Path("{f}")
		  @GET
		  @Produces("application/json")
		  public Response convertFtoCfromInput(@PathParam("f") int f) throws JSONException {
			convertDtoB(f);
			convertBtoH();
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("Decimal", f);
			jsonObject.put("Binary", convertDtoB(f));
			jsonObject.put("Hexadecimal", convertBtoH());
	 
			String result = "" + jsonObject;
			return Response.status(200).entity(result).build();
		  }
		   
		 }
	


