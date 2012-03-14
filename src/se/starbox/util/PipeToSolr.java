//package se.starbox.util;
//
//import java.io.File;
//import java.net.MalformedURLException;
//
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
//import org.openpipeline.pipeline.item.DocBinary;
//import org.openpipeline.pipeline.item.Item;
//import org.openpipeline.pipeline.stage.Stage;
//import org.openpipeline.scheduler.PipelineException;
//
///**
// * A class made as a bridge between OpenPipeline and Solr. PipeToSolr takes the
// * output from OpenPipeline and sends it to Solr.
// *
// * @author Linus, Robin
// *
// */
//public class PipeToSolr extends Stage{
//	CommonsHttpSolrServer solrServer;
//	String solrURL;
//	String indexDataPath;
//	
//	public PipeToSolr(){
//		initialize();
//	}
//
//	/**
//	 * Initializes.
//	 */
//	public void initialize() {
//		solrURL = "http://localhost:8983/solr";
//		indexDataPath = "path till en mapp där allas indexData ligger/minIndexData.xml";
//		try {
//			solrServer = new CommonsHttpSolrServer(solrURL);
//		} catch (MalformedURLException e) {
//			System.err.println("PipeToSolr - Caught an exception when trying " +
//								"to instantiate the solrServer.");
//			e.printStackTrace();
//		}	
//	}
//
//	
//	
//	/**
//	 * Takes input from OpenPipeline, one file at a time as an item. 
//	 * ProcessItem takes each item and adds it to IndexData.xml 
//	 * 
//	 * @param item Input from SimpleTokenizer
//	 */
//	public void processItem(Item item) throws PipelineException{
//	
//		DocBinary docBinary = item.getDocBinary();
//		
//		if (docBinary != null && docBinary.getBinary().size() > 0) {
//			String name 	= docBinary.getName();
//			String extension= docBinary.getExtension();
//			long size 		= docBinary.getSize();
//			long timeStamp 	= docBinary.getTimestamp();
//		}
//	}
//	
//	
//	
//	/**
//	 * 
//	 */
//	/*
//	private void printToFile(List<Item>){
//		
//	}
//	*/
//	
//	@Override
//	public String getDescription() {
//		return "Writes items to indexData in XML format and sends XML-files to Solr";
//	}
//
//	@Override
//	public String getDisplayName() {
//		return "PipeToSolr";
//	}
//	
//}
