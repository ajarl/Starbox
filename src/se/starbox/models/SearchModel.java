package se.starbox.models;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;



/**
 * A Model class for searching files in your local index.
*
* @author Linus, Robin, Kim
*
*/

public class SearchModel {

	
	private static EmbeddedSolrServer solr;
	// Får inte den här att fungera med en path relativ till projektet.
	private final String SOLR_HOME = "C:/solr/";

	/**
	* Initiate the model instance. Creates an instance of Solr on startup if
	* one has not been created already.
	* 
	* @throws MalformedURLException
	*/
	public SearchModel() {
	
		//Initilize Solr
		if (solr == null) {
			//detta krashar sidan
			getSolr();
		}
		
			
		try {
			solr.deleteById("*");
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * This method creates an embedded instance of Solr.
	 */
	private void getSolr() {
		File home = new File(SOLR_HOME);
		File f = new File(home, "conf/solr.xml");
		
//		System.out.println("PATH: " + f.getParentFile().getAbsolutePath());
		
		CoreContainer container = new CoreContainer();
		try {
			container.load(SOLR_HOME, f);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		solr  = new EmbeddedSolrServer(container,
				"core");
		try {
			System.setProperty("solr.solr.home", SOLR_HOME);
		} catch (SecurityException se) {
			System.err.println("SearchModel caught a SecurityException. Couldn't set system property!");
			se.toString();
		}
	
		CoreContainer coreContainer = new CoreContainer();
		CoreContainer.Initializer initiliazer = new CoreContainer.Initializer();
		solr = new EmbeddedSolrServer(coreContainer, "core"); 
	}



	/**
	* Searches the index of Solr.
	* @param inputQuery The string which you are searching for
	* @param params Parameters to filter the search, on this format:
	* 		 "filetype:exe;minfilesize:20;maxfilesize:10"
	* 
	* @return Returns a list of SearchResults.
	*/
	//public List<SearchResult> find(String inputQuery, String params){
	public List<String> find(String inputQuery, String params){
		/*
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
	    return  resultList;
	    */
		return null;
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
		// TODO: HÃ¤r ska params bakas in i solrQuery.
	    // solrQuery.set?
		return solrQuery;
	}

}
