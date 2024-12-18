package org.fredypalacios;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParserToDB {

    public XMLParserToDB() {}

    // Method to parse the XML file and extract the data
    public static List<String[]> parseXML(String xmlPath) throws Exception {
        List<String[]> rowsData = new ArrayList<>();

        File file = new File(xmlPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList rowList = document.getElementsByTagName("Row"); //Retrieve all <Row> nodes.

        for (int i = 1; i < rowList.getLength(); i++) { // Iterate through rows
            Node row = rowList.item(i);
            if (row.getNodeType() == Node.ELEMENT_NODE) {
                Element rowElement = (Element) row;
                NodeList cellList = rowElement.getElementsByTagName("Cell");

                String[] values = new String[8];
                int currentIndex = 0;

                for (int j = 0; j < cellList.getLength(); j++) { // Iterate through cells
                    Element cell = (Element) cellList.item(j);

                    if (cell.hasAttribute("ss:Index")) { // Handling ss:Index to account for column skips.
                        currentIndex = Integer.parseInt(cell.getAttribute("ss:Index")) - 1;
                    }

                    if (currentIndex >= values.length) {
                        break;
                    }

                    Node dataNode = cell.getElementsByTagName("Data").item(0); // Retrieve the value of <Data>
                    String value = (dataNode != null) ? dataNode.getTextContent() : "";
                    values[currentIndex] = value;
                    currentIndex++;
                }
                rowsData.add(values); // Add row to the list
            }
        }
        return rowsData;
    }
}