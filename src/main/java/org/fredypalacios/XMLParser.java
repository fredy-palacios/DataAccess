package org.fredypalacios;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    // Method to parse the XML file and extract the data
    public static List<String[]> parseXML(String xmlPath) throws Exception {
        List<String[]> rowsData = new ArrayList<>();

        File file = new File(xmlPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList rowList = document.getElementsByTagName("Row");

        // Iterate through rows
        for (int i = 1; i < rowList.getLength(); i++) {
            Node row = rowList.item(i);
            if (row.getNodeType() == Node.ELEMENT_NODE) {
                Element rowElement = (Element) row;
                NodeList cellList = rowElement.getElementsByTagName("Cell");

                String[] values = new String[8];
                int currentIndex = 0;

                // Iterate through cells
                for (int j = 0; j < cellList.getLength(); j++) {
                    Element cell = (Element) cellList.item(j);

                    // Handling ss:Index to account for column skips.
                    if (cell.hasAttribute("ss:Index")) {
                        currentIndex = Integer.parseInt(cell.getAttribute("ss:Index")) - 1;
                    }

                    if (currentIndex >= values.length) {
                        break;
                    }

                    // Retrieve the value of <Data>
                    Node dataNode = cell.getElementsByTagName("Data").item(0);
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