package se.starbox.util;

import java.net.MalformedURLException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.DirectXmlRequest;

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
	 * <add>
	 * 		<doc>
	 * 			<field name="name">"filename"</field>
	 * 			<field name="url">"url"</field>
	 * 			..
	 * 		</doc>
	 * 		<doc>
	 * 		</doc>
	 * 		..
	 * </add>
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
		 
				Document document = null;
				try {
					document = (Document) builder.build(indexData);
				} catch (JDOMException | IOException e) {
					System.err.println("PipeToSolr - Error when trying to read IndexData for update.");
					e.printStackTrace();
				}
		 
				Element doc = new Element("doc");
				doc.addContent(new Element("field").setText(name).setAttribute("name", "name"));
				doc.addContent(new Element("field").setText(url).setAttribute("name", "url"));
				doc.addContent(new Element("field").setText(doctype).setAttribute("name", "docType"));
				doc.addContent(new Element("field").setText("" + timeStamp).setAttribute("name", "timeStamp"));
				doc.addContent(new Element("field").setText("" + size).setAttribute("name", "fileSize"));
				
	
				document.getRootElement().addContent(doc);
		 
				
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				try {
					xmlOutput.output(document, new FileWriter(indexDataPath + "/indexData.xml"));
				} catch (IOException e) {
					System.err.println("PipeToSolr - Error when trying to save update to IndexData.xml");
					e.printStackTrace();
				}
				
			} else {
				Element add = new Element("add");
				Document document = new Document(add);
				document.setRootElement(add);
				
				Element doc = new Element("doc");
				doc.addContent(new Element("field").setText(name).setAttribute("name", "name"));
				doc.addContent(new Element("field").setText(url).setAttribute("name", "url"));
				doc.addContent(new Element("field").setText(doctype).setAttribute("name", "docType"));
				doc.addContent(new Element("field").setText("" + timeStamp).setAttribute("name", "timeStamp"));
				doc.addContent(new Element("field").setText("" + size).setAttribute("name", "fileSize"));
		
				
				document.getRootElement().addContent(doc);
				
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				try {
					xmlOutput.output(document, new FileWriter(indexDataPath + "/indexData.xml"));
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
		SAXBuilder builder = new SAXBuilder();

		try {
			File dir = new File(indexDataPath);
			 
			for (File child : dir.listFiles()) {
			    if (".".equals(child.getName()) || "..".equals(child.getName())) {
			      continue;  
			    }
			    
			    Document doc = builder.build(child);
			    Element root = doc.getRootElement();
			    String s = root.toString();
			    DirectXmlRequest xmlreq = new DirectXmlRequest( "/update", s); 
			    solrServer.request(xmlreq);
			  }
		} catch (SolrServerException e) {
			System.err.println("PipeToSolr caught a SolrServerException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("PipeToSolr caught an IOException");
			e.printStackTrace();
		}catch (JDOMException e) {
			System.err.println("PipeToSolr caught an JDOMException");
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
