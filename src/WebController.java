


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author Harish Puli
 *
 */

@Path("/webservice")
public class WebController {

	@GET
	@Path("/hello")
	@Produces("text/plain")
	public String hello() {
		return "Hello World!!! dineshonjava";
	}

	@GET
	@Path("/apps")
	@Produces("application/json")
	public String listAppsJSON(@Context HttpServletRequest req) {
		try {

			boolean isLtpaExists = false;

			HttpSession httpSession = req.getSession(true);
			//String userID= "uccuid1";
			//String userID= req.getHeader("SM_USER");
			String userID=req.getHeader("SM_UNIVERSALID");
			

			if (httpSession.getAttribute("LTPAToken") != null) {
				isLtpaExists = true;
			}

			Client client = Client.create();
			
			//handle if LTPAT Doesnt exist
			/*
			if (!isLtpaExists)
			{
				client.addFilter(new HTTPBasicAuthFilter("uccuid1", "Secret1234"));
			}
			*/
			WebResource webResource = client.resource("http://192.168.2.140:9080/rest/bpm/wle/v1/exposed/process");
			
			ClientResponse response  = null;
			////-----------handle if LPTA Exists;
			if (isLtpaExists)
			{
				response = webResource.header("Cookie", httpSession.getAttribute("LTPAToken").toString()).accept("application/json").get(ClientResponse.class);
			}
			else 
				//The following will be fired with username /password
			{
				 response = webResource.header("SM_UNIVERSALID", userID).accept("application/json").get(ClientResponse.class);
				 String LtpaToken = response.getHeaders().get("Set-Cookie").get(1).toString().concat(",").concat(response.getHeaders().get("Set-Cookie").get(0).toString());
				 httpSession.setAttribute("LTPAToken", LtpaToken);
			}			

			//Handle response
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			// String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(response.getHeaders().toString());
			System.out.println("Output from header.... \n");
			// String userName = (String) session.getAttribute("userName");
			//System.out.println(session.getAttribute("userName"));
			//System.out.println(session.getAttribute("password"));
			return output;
		} catch (Exception e) {

			e.printStackTrace();

		}
		return "Something Went weong";
	}

	@PUT
	@Path("/getTasks")
	@Produces("application/json")
	public String listTasksJSON(@Context HttpServletRequest req) {
		try {

			boolean isLtpaExists = false;

			HttpSession httpSession = req.getSession(true);
			//String userID= "uccuid1";
			//String userID= req.getHeader("SM_USER");
			String userID=req.getHeader("SM_UNIVERSALID");
			System.out.println("The logged in user is"+userID);
			if (httpSession.getAttribute("LTPAToken") != null) {
				isLtpaExists = true;
			}

			Client client = Client.create();
			MultivaluedMap queryParams = new MultivaluedMapImpl();
			queryParams.add("condition", "taskActivityName|Equals|Search Request");
			queryParams.add("condition", "taskStatus|Equals|Received");
			queryParams.add("organization", "byInstance");
			
			/*
			if (!isLtpaExists)
			{
				client.addFilter(new HTTPBasicAuthFilter("uccuid1", "Secret1234"));
				
			}
			*/
			WebResource webResource = client.resource("http://192.168.2.140:9080/rest/bpm/wle/v1/search/query/?");

			ClientResponse response = null;
			
			if (isLtpaExists)
			{
				response = webResource.queryParams(queryParams).header("Cookie", httpSession.getAttribute("LTPAToken").toString()).accept("application/json").put(ClientResponse.class);
			}
			else 
			{
				 response = webResource.queryParams(queryParams).header("SM_UNIVERSALID", userID).accept("application/json").put(ClientResponse.class);
				 String LtpaToken = response.getHeaders().get("Set-Cookie").get(1).toString().concat(",").concat(response.getHeaders().get("Set-Cookie").get(0).toString());
				 httpSession.setAttribute("LTPAToken", LtpaToken);
			}	

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			// String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(response.getHeaders().toString());
			System.out.println("Output from header.... \n");
			// String userName = (String) session.getAttribute("userName");
			//System.out.println(session.getAttribute("userName"));
			//System.out.println(session.getAttribute("password"));
			return output;
		} catch (Exception e) {

			e.printStackTrace();

		}
		return "Something Went weong";
	}
	
	//start Instance post method
	
	@POST
	@Path("/startInst")
	@Produces("application/json")
	public String sendStartInstJSON(@Context HttpServletRequest req, @QueryParam("input") String input ) {
		try {

			boolean isLtpaExists = false;

			HttpSession httpSession = req.getSession(true);
			//String userID= "uccuid1";
			//String userID= req.getHeader("SM_USER");
			String userID=req.getHeader("SM_UNIVERSALID");

			if (httpSession.getAttribute("LTPAToken") != null) {
				isLtpaExists = true;
			}

			Client client = Client.create();
			MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
			queryParams.add("action", "start");
			queryParams.add("bpdId", "25.a1f4ce57-4310-4deb-8eff-ad947b634dec");
			queryParams.add("branchId", "2063.1191f66b-a473-45d5-b956-5e1affc45bc0");
			queryParams.add("params", input);
			queryParams.add("parts", "all");
			/*
			if (!isLtpaExists)
			{
				client.addFilter(new HTTPBasicAuthFilter("uccuid1", "Secret1234"));
			}
			*/

			WebResource webResource = client.resource("http://192.168.2.140:9080/rest/bpm/wle/v1/process?");

			ClientResponse response = null;
			
			if (isLtpaExists)
			{
				response = webResource.queryParams(queryParams).header("Cookie", httpSession.getAttribute("LTPAToken").toString()).accept("application/json").post(ClientResponse.class);
			}
			else 
			{
				 response = webResource.queryParams(queryParams).header("SM_UNIVERSALID", userID).accept("application/json").post(ClientResponse.class);
				 String LtpaToken = response.getHeaders().get("Set-Cookie").get(1).toString().concat(",").concat(response.getHeaders().get("Set-Cookie").get(0).toString());
				 httpSession.setAttribute("LTPAToken", LtpaToken);
			}	

			if (response.getStatus() != 200) {
				System.out.print(response);
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			// String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(response.getHeaders().toString());
			//System.out.println("Output from header.... \n");
			// String userName = (String) session.getAttribute("userName");
			//System.out.println(session.getAttribute("userName"));
			//System.out.println(session.getAttribute("password"));
			return output;
		} catch (Exception e) {

			e.printStackTrace();

		}
		return "Something Went weong";
	}

}
