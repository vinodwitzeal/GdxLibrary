package library.gdx.utils.svg;

import com.badlogic.gdx.files.FileHandle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SVGUtils {
    public static void combineSVGFiles(List<FileHandle> svgFiles, FileHandle outputFile) {
        try {
            // Initialize Document Builder Factory

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Create a new Document for the combined SVG
            Document combinedDoc = dBuilder.newDocument();
            Element svgRoot = combinedDoc.createElement("svg");
            svgRoot.setAttribute("xmlns", "http://www.w3.org/2000/svg");
            svgRoot.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
            combinedDoc.appendChild(svgRoot);

            int maxWidth = 0;
            int totalHeight = 0;

            for (FileHandle svgFile : svgFiles) {
                Document doc = dBuilder.parse(svgFile.file());
                doc.getDocumentElement().normalize();

                Element svgElement = (Element) combinedDoc.importNode(doc.getDocumentElement(), true);

                // Assuming width and height are directly set on the svg element
                int width = Integer.parseInt(svgElement.getAttribute("width").replaceAll("px", ""));
                int height = Integer.parseInt(svgElement.getAttribute("height").replaceAll("px", ""));

                maxWidth = Math.max(maxWidth, width);
                totalHeight += height;

                svgElement.setAttribute("y", Integer.toString(totalHeight - height));
                svgElement.removeAttribute("width");
                svgElement.removeAttribute("height");

                Element svgGroupElement = combinedDoc.createElement("g");
                svgGroupElement.setAttribute("id",svgFile.nameWithoutExtension());

                svgGroupElement.appendChild(svgElement);

                svgRoot.appendChild(svgGroupElement);
            }

            svgRoot.setAttribute("width", Integer.toString(maxWidth));
            svgRoot.setAttribute("height", Integer.toString(totalHeight));

            // Write the combined SVG to a file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(combinedDoc);
            StreamResult result = new StreamResult(outputFile.file());
            transformer.transform(source, result);

            System.out.println("Combined SVG has been saved to " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
