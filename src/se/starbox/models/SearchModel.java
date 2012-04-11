package se.starbox.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.DirectXmlRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.schema.UUIDField;
import org.xml.sax.SAXException;



/**
 * A Model class for searching files in your local index.
*
* @author Linus, Robin, Kim
*
*/
public class SearchModel {

	// Handle for Solr
	private static SolrServer solr = null;
	
	// URI to Solr
	private String SOLR_URI = "http://localhost:8080/starbox-solr-server/";

	/**
	* Initiate the model instance. Creates an instance of Solr on startup if
	* one has not been created already.
	* 
	* @throws MalformedURLException
	*/
	public SearchModel() {
		// Initilize Solr
		if (solr == null)
			getSolr();
		
		checkConnection();
		//testFill();
	}

	// Fill the db with test data.
	public void testFill() {
		File testIndexData = new File("exempelIndexData.xml");
		
		String doc = getStringFromDocument(testIndexData);
		System.out.println("Read file, content was: " + doc);
		
		DirectXmlRequest xmlreq = new DirectXmlRequest("/update", doc);
		try {
			solr.request(xmlreq);
		} catch (SolrServerException | IOException e) {
			System.err.println("Error updating solr");
			e.printStackTrace();
		}
	}

	/**
	 * Reads a file into a string.
	 * @param file
	 * @return a string with the content of file
	 */
	private String getStringFromDocument(File file) {
		BufferedReader br = null;
	
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException fe) {
			System.err.println("Error! Couldnt find file " + file.getName());
			fe.printStackTrace();
		}
		
		String line;
		StringBuilder sb = new StringBuilder();
		
		try {
			while((line=br.readLine()) != null)
				sb.append(line);
		} catch(IOException e) {
			System.err.println("Error reading line.");
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	private void checkConnection() {
		try {
			solr.ping();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * This method creates an embedded instance of Solr.
	 * If theres an exception solr will be set to <b>null</b>.
	 */
	private void getSolr() {
		try {
			solr = new CommonsHttpSolrServer(SOLR_URI);
		} catch(MalformedURLException mex) {
			System.err.println("Error (MalformedURLException) in SearchModel. Here is the stack trace:");
			mex.printStackTrace();
			solr = null;
		}
	}


	/**
	* Searches the index of Solr.
	* @param inputQuery The string which you are searching for
	* @param params Parameters to filter the search, on this format:
	* 		 "filetype:exe;minfilesize:20;maxfilesize:10"
	* 
	* @return Returns a JSON-formatted string.
	*/
	//public List<SearchResult> find(String inputQuery, String params){
	public List<String> query(String inputQuery, String params){
		SolrQuery solrQuery = new SolrQuery();
	    solrQuery.setQuery(inputQuery);
	    
	    // Update the search query with the chosen parameters
	    if(params != null)
	    	solrQuery = setParams(solrQuery, params);
	    
	    QueryResponse rsp;
	    try {
	    	rsp = solr.query(solrQuery);
	    } catch (SolrServerException sse) {
			System.err.println("SearchModel() - Caught a SolrServerException" +
								"inputQuery was " + inputQuery + 
								"params was " + params);
			sse.printStackTrace();	    	
	    }
	   
	    //List<SearchResult> resultList = rsp.getBeans(SearchResult.class);
	    List<String> resultList = new ArrayList<String>();
	    resultList.add("result1");
	    resultList.add("result2");
	    return resultList;
	}
	
	/**  NEW METHOD - Not defined in ADD  
	 *
	 * Parses the params string and sets parameters for the Solr-query based
	 * on that string.
	 * @param solrQuery the query for the solr search
	 * @param params parameters to filter the search, on this format:
	 * 		  "filetype:exe;minfilesize:20;maxfilesize:10"
	 * 
	 * @return returns a SolrQuery with the set parameters
	 */
	private SolrQuery setParams(SolrQuery solrQuery, String params){
		solrQuery.set("namn", )
		return solrQuery;
	}

}
