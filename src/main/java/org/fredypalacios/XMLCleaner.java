package org.fredypalacios;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class XMLCleaner {

    public XMLCleaner() {}

    // Method that parses the XML and removes the "Tipo de contrato" column along with its rows
    protected void removeContractTypeData(String xmlPath, String xmlPathOutput) throws Exception {
        File file = new File(xmlPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList rowList = document.getElementsByTagName("Row"); // Retrieve all <Row> nodes

        System.out.println("Deleting column \"Tipo de contrato\"...");

        for (int i = 0; i < rowList.getLength(); i++) { // Iterate through rows
            Node row = rowList.item(i);
            NodeList cellList = ((Element) row).getElementsByTagName("Cell");

            if (cellList.getLength() >= 8) { // Remove the <Cell> node at position 8
                Node cell = cellList.item(7); // Index 8 because Java uses 0-based indexing
                row.removeChild(cell);
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(xmlPathOutput)) { // Save the modified XML

            writeDocumentToStream(document, fileOutputStream);
            System.out.println("Successfully cleaned up XML file");
        }
    }

    // Method to write the XML document to a file output stream
    private static void writeDocumentToStream(Document document, FileOutputStream fileOutputStream) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(fileOutputStream));
    }
}