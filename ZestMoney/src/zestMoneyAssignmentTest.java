import static io.restassured.RestAssured.given;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class zestMoneyAssignmentTest {
	RequestSpecification resAPI1;
	RequestSpecification reqAPI1;
	RequestSpecification resAPI2;
	RequestSpecification reqAPI2;
	static String lineAPI1;
	static String lineAPI2;
	BufferedReader readerAPI1 ;
	BufferedReader readerAPI2 ;
  
  @BeforeTest
  public void setBaseURI() throws IOException {
	  
	  //set the project path, set the separator as per environment and load the file here
	  	String Projectpath = System.getProperty("user.dir");
		String separator = System.getProperty("file.separator");
		String filePathAPI1 = Projectpath + separator + "src" + separator + "APIRequest1";
		String filePathAPI2 = Projectpath + separator + "src" + separator + "APIRequest2";
		
		readerAPI1 = new BufferedReader(new FileReader(""+filePathAPI1+""));
		readerAPI2 = new BufferedReader(new FileReader(""+filePathAPI2+""));
		
		

		
  }
  @Test
  public void compareJSONResponses() throws IOException {
	  try {
		  //read line from two notepad files
		  lineAPI1 = readerAPI1.readLine();
		  lineAPI2 = readerAPI2.readLine();
		  
	  while(lineAPI1!= null || lineAPI2!=null) {
		  
		  //set two different URLs using RequestSpecification from each notepad file
		  reqAPI1 =new RequestSpecBuilder().setBaseUri(""+lineAPI1+"").build();
		  resAPI1=given().spec(reqAPI1);
		
		  reqAPI2 =new RequestSpecBuilder().setBaseUri(""+lineAPI2+"").build();
		  resAPI2=given().spec(reqAPI2);
			
		  String responseAPI1 = resAPI1.when().get("").then().extract().response().asString();
		  String responseAPI2 = resAPI2.when().get("").then().extract().response().asString();
	   
		  //compare the response here, if equal then print the JSON
	  if(responseAPI1.equals(responseAPI2)) {
		  System.out.println("Below are the API requests which gave same JSON responses");
		  System.out.println(responseAPI1);
		  System.out.println(responseAPI2);
		}
	  
	  	//to read the next line in the notepad file
	  	  lineAPI1 = readerAPI1.readLine();
	  	  lineAPI2 = readerAPI2.readLine();
  }
	  }catch(IOException e) {
			e.printStackTrace();
		}
		  
	  }
  
  @AfterTest
  public void closeFilses() throws IOException {
	  	readerAPI1.close();
		readerAPI2.close();
  }
  }

