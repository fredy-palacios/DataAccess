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

    public XMLCleaner() {
    }

    // Method that parses the XML and removes the "Tipo de contrato" column along with its rows
    protected void removeContractTypeData(String xmlPath, String xmlPathOutput) throws Exception {
        File file = new File(xmlPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        // Retrieve all <Row> nodes
        NodeList rowList = document.getElementsByTagName("Row");

        // Iterate through rows
        for (int i = 0; i < rowList.getLength(); i++) {
            Node row = rowList.item(i);
            NodeList cellList = ((Element) row).getElementsByTagName("Cell");

            // Remove the <Cell> node at position 8
            if (cellList.getLength() >= 8) {
                Node cell = cellList.item(7); // Index 8 because Java uses 0-based indexing
                row.removeChild(cell);
            }
        }

        // Save the modified XML
        try (FileOutputStream fos = new FileOutputStream(xmlPathOutput)) {
            writeDocumentToStream(document, fos);
        }
    }

    // Write the XML document to a file output stream
    private static void writeDocumentToStream(Document document, FileOutputStream fos) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(fos));
    }
}