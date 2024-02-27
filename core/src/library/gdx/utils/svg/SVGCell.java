package library.gdx.utils.svg;

import com.badlogic.gdx.files.FileHandle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

public class SVGCell {
    private DocumentBuilder dBuilder;
    private FileHandle svgFile;
    protected int width,height;
    protected Element element;
    protected String name;
    public SVGCell(FileHandle svgFile, DocumentBuilder dBuilder) throws Exception{
        this.svgFile=svgFile;
        this.dBuilder=dBuilder;
        this.name=svgFile.nameWithoutExtension();
        read();
    }

    private void read() throws Exception{
        Document doc = dBuilder.parse(svgFile.file());
        doc.getDocumentElement().normalize();
        doc.getDocumentElement();

        element=doc.getDocumentElement();

        // Assuming width and height are directly set on the svg element
        width= Integer.parseInt(element.getAttribute("width").replaceAll("px", ""));
        height= Integer.parseInt(element.getAttribute("height").replaceAll("px", ""));
    }
}
