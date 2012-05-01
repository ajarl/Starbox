package se.starbox.util;

import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import org.openpipeline.pipeline.item.DocBinary;
import org.openpipeline.pipeline.item.Item;
import org.openpipeline.pipeline.stage.Stage;
import org.openpipeline.scheduler.PipelineException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import se.starbox.models.SettingsModel;

/**
 * A class made as a bridge between OpenPipeline and Solr. PipeToSolr takes the
 * output from OpenPipeline and sends it to Solr.
 *
 * @author Linus, Robin
 *
 */
public class PipeToSolr extends Stage{
	CommonsHttpSolrServer solrServer;
	String solrURL;
	String indexDataPath;
	boolean first;
	public PipeToSolr(){
		initialize();
	}

	/**
	 * Initializes.
	 */
	public void initialize() {
		solrURL = "http://localhost:8080/starbox-solr-server";
		indexDataPath = SettingsModel.getProjectRootPath() + "/Index";
		first = true;

		try {
			solrServer = new CommonsHttpSolrServer(solrURL);
			
		} catch (MalformedURLException e) {
			System.err.println("PipeToSolr - Caught an exception when trying " +
								"to instantiate the solrServer.");
			e.printStackTrace();
		}	
	}

	
	
	/**
	 * Takes input from OpenPipeline, one file at a time as an item. 
	 * ProcessItem takes each item and adds it to IndexData.xml in the format: 
	 * <documents>
	 * 	<doc>
	 * 		<id> .. </id>
	 * 		<name> .. </name>
	 * 		..
	 * 	</doc>
	 * 	<doc>
	 * 		..
	 * 		..
	 * 	</doc>
	 * </documents>
	 * 
	 * 
	 * @param item Input from SimpleTokenizer
	 */
	@SuppressWarnings("static-access")
	public void processItem(Item item) throws PipelineException{
		SettingsModel sm = new SettingsModel();
		String userName = "";
		String fileName = "";
		String url = "";
		String doctype = "";
		long size = 0;
		long timeStamp = 0;
		UUID uuid = null;
		byte[] nameByteArray;
		
		DocBinary docBinary = item.getDocBinary();
		if(first){
			clearIndexData();
			try {
				solrServer.deleteByQuery( "*:*" );
			} catch (SolrServerException | IOException e) {
				System.err.println("Error while trying to delete the Solr index.");
				e.printStackTrace();
			}
			Thread t = new toSolr();
			t.start();
			first = false;
		}
		
		if (docBinary != null && docBinary.getBinary().size() > 0) {
			userName 	= sm.getDisplayName();
			url 		= docBinary.getName();
			doctype		= docBinary.getExtension();
			size 		= docBinary.getSize();
			timeStamp 	= docBinary.getTimestamp();
			fileName 	= new File(url).getName();
			nameByteArray = fileName.getBytes();
			uuid 		= uuid.nameUUIDFromBytes(nameByteArray);
			
			int i = url.indexOf("WebContent");
			url = url.substring(i);
		
			boolean exists = (new File(indexDataPath + "/indexData.xml")).exists();
			System.out.println(indexDataPath);
			if (exists) {
				SAXBuilder builder = new SAXBuilder();
				File indexData = new File(indexDataPath + "/indexData.xml");
		 
				Document indexDataDocument = null;
				try {
					indexDataDocument = (Document) builder.build(indexData);
				} catch (JDOMException | IOException e) {
					System.err.println("PipeToSolr - Error when trying to read IndexData for update.");
					e.printStackTrace();
				}
		 
				Element indexItem = new Element("item");
				indexItem.addContent(new Element("id").setText("" + uuid));
				indexItem.addContent(new Element("userName").setText(userName));
				indexItem.addContent(new Element("name").setText(fileName));
				indexItem.addContent(new Element("url").setText(url));
				indexItem.addContent(new Element("docType").setText(doctype));
				indexItem.addContent(new Element("timeStamp").setText("" + timeStamp));
				indexItem.addContent(new Element("fileSize").setText("" + size));
				
	
				indexDataDocument.getRootElement().addContent(indexItem);
		 
				
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				try {
					xmlOutput.output(indexDataDocument, new FileWriter(indexDataPath + "/indexData.xml"));
				} catch (IOException e) {
					System.err.println("PipeToSolr - Error when trying to save update to IndexData.xml");
					e.printStackTrace();
				}
				
			} else {
				Element indexItems = new Element("items");
				Document indexDataDocument = new Document(indexItems);
				indexDataDocument.setRootElement(indexItems);
				
				Element indexItem = new Element("item");
				indexItem.addContent(new Element("id").setText("" + uuid));
				indexItem.addContent(new Element("userName").setText(userName));
				indexItem.addContent(new Element("name").setText(fileName));
				indexItem.addContent(new Element("url").setText(url));
				indexItem.addContent(new Element("docType").setText(doctype));
				indexItem.addContent(new Element("timeStamp").setText("" + timeStamp));
				indexItem.addContent(new Element("fileSize").setText("" + size));
		
				
				indexDataDocument.getRootElement().addContent(indexItem);
				
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				try {
					xmlOutput.output(indexDataDocument, new FileWriter(indexDataPath + "/indexData.xml"));
				} catch (IOException e) {
					System.err.println("PipeToSolr - Error while trying to create IndexData.xml");
					e.printStackTrace();
				}
	
			}	
		}

	}

	/**
	 * 
	 * Reads all index files in index folder and sends sends them to Solr.
	 * 
	 */

	@SuppressWarnings("rawtypes")
	private class toSolr extends Thread{
		public void run(){
	        
			//TODO Kolla om OP �r klar ist�llet f�r att v�nta.
			long t0,t1;
	        t0=System.currentTimeMillis();
	        t1=System.currentTimeMillis();
	        while (t1-t0<10000){
	            t1=System.currentTimeMillis();
	        }
	        
			try {
				File dir = new File(indexDataPath);
				 
				for (File child : dir.listFiles()) {
				    if (".".equals(child.getName()) || "..".equals(child.getName())) {
				      continue;  
				    }
				    
				    SolrInputDocument input = new SolrInputDocument();
					SAXBuilder builder = new SAXBuilder();
					
					Document document = null;
					try {
						document = builder.build(child);
					} catch (JDOMException e1) {
						System.err.println("Error while building a document in toSolr()");
						e1.printStackTrace();
					} catch (IOException e1) {
						System.err.println("Error while reading file in toSolr()");
						e1.printStackTrace();
					}
					Element root = document.getRootElement();
					List row = root.getChildren("item"); //Lista med item
					
					for(int i = 0; i < row.size(); i++){
						Element docs = (Element) row.get(i);
						List column = docs.getChildren(); //Lista med field
						
						for(int j = 0; j < column.size(); j++){
							Element e = (Element) column.get(j);
			
							String name = e.getName();
							String value = e.getText();
	
							input.addField(name, value);			
						}
						solrServer.add(input);
						solrServer.commit();
						input.clear();
					}
				  }
			} catch (SolrServerException e) {
				System.err.println("PipeToSolr caught a SolrServerException");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("PipeToSolr caught an IOException");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * Deletes IndexData.xml
	 * 
	 */
	public void clearIndexData (){
		
	    try {
	    	
	      File target = new File(indexDataPath + "/indexData.xml");

	      if (!target.exists()) {
	        System.err.println("IndexData.xml doesn't exist.");
	        return;
	      }

	      if (target.delete())
	        System.out.println("** Deleted IndexData.xml **");
	      else
	        System.err.println("Failed to delete IndexData.xml");
	    } catch (SecurityException e) {
	      System.err.println("Unable to delete IndexData.xml ("
	          + e.getMessage() + ")");
	    }
		  
	}
	
	/**
	 * Adds a specified IP-address to a specific indecData.xml-file. 
	 * 
	 * @param ip The IP address to add.
	 * @param indexFile The file to add the IP to.
	 */
	@SuppressWarnings("rawtypes")
	public static void addIp(String ip, String indexFile){
		SAXBuilder builder = new SAXBuilder();
		String indexFilePath = SettingsModel.getProjectRootPath() + "/Index";

		File indexData = new File(indexFilePath + "/indexFile");
		Document indexDataDocument = null;
		try {
			indexDataDocument = (Document) builder.build(indexData);
		} catch (JDOMException | IOException e) {
			System.err.println("PipeToSolr - Error when trying to read IndexData for update.");
			e.printStackTrace();
		}
		
		Element root = indexDataDocument.getRootElement();
		List row = root.getChildren("item"); //Lista med item
		
		for(int i = 0; i < row.size(); i++){
			Element docs = (Element) row.get(i);
			List column = docs.getChildren(); //Lista med field
			
			for(int j = 0; j < column.size(); j++){
				Element e = (Element) column.get(j);

				String name = e.getName();
				if(name.equals("url")){
					String value = e.getText();
					e.setText(ip + ":" + value);
				}	
			}
		}

	}
	
	@Override
	public String getDescription() {
		return "Writes items to indexData in XML format and sends XML-files to Solr";
	}

	@Override
	public String getDisplayName() {
		return "PipeToSolr";
	}
	
}
