package se.starbox.models;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

// Link to SolrJ dependencies.
// http://repo1.maven.org/maven2/org/apache/solr/solr-solrj/1.4.0/solr-solrj-1.4.0.jar
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * A Model class for searching files in your local index.
*
* @author Linus, Robin, Kim
*
*/

public class SearchModel {
	/**
	* CommonsHttpSolrServer is thread-safe and if you are using the following constructor,
	* you *MUST* re-use the same instance for all requests.  If instances are created on
	* the fly, it can cause a connection leak. The recommended practice is to keep a
	* static instance of CommonsHttpSolrServer per solr server url and share it for all requests.
	* See https://issues.apache.org/jira/browse/SOLR-861 for more details	
	*/
	//private static CommonsHttpSolrServer solr;
	private String solrServer = "http://localhost:8983/solr";
	
	/**
	* Initiate the model instance. Creates an instance of Solr on startup if
	* one has not been created already.
	* 
	* @throws MalformedURLException
	*/
	public SearchModel() {
		/*
		if (solr == null)
			try {
				solr = new CommonsHttpSolrServer(solrServer);
			} catch (MalformedURLException e) {
				System.err.println("SearchModel() - Caught a MalformedURLException." +
									"URL was " + solrServer);
				e.printStackTrace();
			} catch (Exception e) {	
				System.err.println("SearchModel() - Caught an exception.");
				e.printStackTrace();
			}
			*/
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
		// TODO: Här ska params bakas in i solrQuery.
	    // solrQuery.set?
		return solrQuery;
	}

}
