package se.starbox.util;

import java.net.MalformedURLException;
import java.util.List;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.DirectXmlRequest;
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
	
	public PipeToSolr(){
		initialize();
	}

	/**
	 * Initializes.
	 */
	public void initialize() {
		solrURL = "http://localhost:8983/solr";
		indexDataPath = "path till en mapp där allas indexData ligger"; //TODO kirra pathen
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
	public void processItem(Item item) throws PipelineException{
		String name = "";
		String url = "";
		String doctype = "";
		long size = 0;
		long timeStamp = 0;
		DocBinary docBinary = item.getDocBinary();
		
		if (docBinary != null && docBinary.getBinary().size() > 0) {
			url 		= docBinary.getName();
			doctype		= docBinary.getExtension();
			size 		= docBinary.getSize();
			timeStamp 	= docBinary.getTimestamp();
			name = new File(url).getName();
		
		
			boolean exists = (new File(indexDataPath + "/indexData.xml")).exists();
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
		 
				Element doc = new Element("doc");
				doc.addContent(new Element("id").setText("ee121111-51c8-4f11-b360-f9411e06fac2"));
				doc.addContent(new Element("name").setText(name));
				doc.addContent(new Element("url").setText(url));
				doc.addContent(new Element("docType").setText(doctype));
				doc.addContent(new Element("timeStamp").setText("" + timeStamp));
				doc.addContent(new Element("fileSize").setText("" + size));
				
	
				indexDataDocument.getRootElement().addContent(doc);
		 
				
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				try {
					xmlOutput.output(indexDataDocument, new FileWriter(indexDataPath + "/indexData.xml"));
				} catch (IOException e) {
					System.err.println("PipeToSolr - Error when trying to save update to IndexData.xml");
					e.printStackTrace();
				}
				
			} else {
				Element documents = new Element("documents");
				Document indexDataDocument = new Document(documents);
				indexDataDocument.setRootElement(documents);
				
				Element doc = new Element("doc");
				doc.addContent(new Element("id").setText("ee121111-51c8-4f11-b360-f9411e06fac2"));
				doc.addContent(new Element("name").setText(name));
				doc.addContent(new Element("url").setText(url));
				doc.addContent(new Element("docType").setText(doctype));
				doc.addContent(new Element("timeStamp").setText("" + timeStamp));
				doc.addContent(new Element("fileSize").setText("" + size));
		
				
				indexDataDocument.getRootElement().addContent(doc);
				
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

	public void toSolr(){

		try {
			File dir = new File(indexDataPath);
			 
			for (File child : dir.listFiles()) {
			    if (".".equals(child.getName()) || "..".equals(child.getName())) {
			      continue;  
			    }
			    
//			    Document doc = builder.build(child);
//			    Element root = doc.getRootElement();
//			    String s = root.toString();
//			    DirectXmlRequest xmlreq = new DirectXmlRequest( "/update", s); 
//			    solrServer.request(xmlreq);
			    
			    SolrInputDocument input = new SolrInputDocument();
				SAXBuilder builder = new SAXBuilder();
				
				Document document = null;
				try {
					document = builder.build(child);
				} catch (JDOMException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Element root = document.getRootElement();
				List row = root.getChildren("doc"); //Lista med docs
				
				for(int i = 0; i < row.size(); i++){
					Element docs = (Element) row.get(i);
					List column = docs.getChildren(); //Lista med field
					
					for(int j = 0; j < column.size(); j++){
						Element e = (Element) column.get(j);
						
						
						String name = e.getName();
						
						System.out.println(name);
						String value = e.getText();
						System.out.println(value);

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
	
	@Override
	public String getDescription() {
		return "Writes items to indexData in XML format and sends XML-files to Solr";
	}

	@Override
	public String getDisplayName() {
		return "PipeToSolr";
	}
	
}
