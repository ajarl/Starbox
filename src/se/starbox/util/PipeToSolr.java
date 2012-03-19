package se.starbox.util;

import java.net.MalformedURLException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import org.openpipeline.pipeline.item.DocBinary;
import org.openpipeline.pipeline.item.Item;
import org.openpipeline.pipeline.stage.Stage;
import org.openpipeline.scheduler.PipelineException;

import org.jdom.Attribute;
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
		indexDataPath = "path till en mapp där allas indexData ligger/minIndexData.xml";  //TODO Namnge indexData efter användaren
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
	 * ProcessItem takes each item and adds it to IndexData.xml 
	 * <creator = "creator">
	 * 		<items>
	 * 			<name>"filename"</name>
	 * 			<url>"url"</url>
	 * 			..
	 * 		</items>
	 * 		<items>
	 * 		</items>
	 * 		..
	 * </creator>
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
		}
		
		boolean exists = (new File(indexDataPath)).exists();
		if (exists) {
			SAXBuilder builder = new SAXBuilder();
			File indexData = new File(indexDataPath);
	 
			Document doc = null;
			try {
				doc = (Document) builder.build(indexData);
			} catch (JDOMException | IOException e) {
				System.err.println("PipeToSolr - Error when trying to read IndexData for update.");
				e.printStackTrace();
			}
	 
			Element items = new Element("items");
			
			items.addContent(new Element("name").setText(name));
			items.addContent(new Element("url").setText(url));
			items.addContent(new Element("doctype").setText(doctype));
			items.addContent(new Element("timeStamp").setText("" + timeStamp));
			items.addContent(new Element("filesize").setText("" + size));

			doc.getRootElement().addContent(items);
	 
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			try {
				xmlOutput.output(doc, new FileWriter(indexDataPath));
			} catch (IOException e) {
				System.err.println("PipeToSolr - Error when trying to save update to IndexData.xml");
				e.printStackTrace();
			}
			
		} else {
			Element creator = new Element("creator");
			creator.setAttribute("filecreator", "asd"); //TODO Hur se vem som skapat?
			Document doc = new Document(creator);
			doc.setRootElement(creator);
			
			Element items = new Element("items");
			items.addContent(new Element("name").setText(name));
			items.addContent(new Element("url").setText(url));
			items.addContent(new Element("doctype").setText(doctype));
			items.addContent(new Element("timeStamp").setText("" + timeStamp));
			items.addContent(new Element("filesize").setText("" + size));
	
			
			doc.getRootElement().addContent(items);
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			try {
				xmlOutput.output(doc, new FileWriter(indexDataPath));
			} catch (IOException e) {
				System.err.println("PipeToSolr - Error while trying to create IndexData.xml");
				e.printStackTrace();
			}

		}		 
	
	}
	
	public void toSolr(){
		
	}

	/**
	 * 
	 * Deletes IndexData.xml
	 * 
	 */
	
	public void clearIndexData (){
		
		    try {
		    	
		      File target = new File(indexDataPath);

		      if (!target.exists()) {
		        System.err.println("IndexData.xml doesn't exist.");
		        return;
		      }

		      if (target.delete())
		        System.err.println("** Deleted IndexData.xml **");
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
