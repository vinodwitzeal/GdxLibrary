package library.gdx.utils.svg;

import com.badlogic.gdx.Gdx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;

public class SVGDoc {
    private DocumentBuilder dBuilder;
    private int maxWidth, maxHeight,cellWidth,cellHeight;
    private int width,height;

    private int cellX,cellY;

    private Document combinedDoc;
    private Element root;

    protected DOMSource source;
    private List<SVGCell> svgCells;
    public SVGDoc(DocumentBuilder dBuilder, int maxWidth, int height, int cellWidth, int cellHeight){
        this.dBuilder=dBuilder;
        this.maxWidth =maxWidth;
        this.maxHeight =height;
        this.width=maxWidth;
        this.height=cellHeight;
        this.cellWidth=cellWidth;
        this.cellHeight=cellHeight;
        this.cellX=0;
        this.cellY=0;
        this.svgCells=new ArrayList<>();
    }






    protected void begin(){
        svgCells.clear();
        combinedDoc = dBuilder.newDocument();
        root = combinedDoc.createElement("svg");
        root.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        root.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
        combinedDoc.appendChild(root);
    }

    public void addCell(SVGCell svgCell){
        svgCells.add(svgCell);
        appendCell(svgCell);
    }

    protected void appendCell(SVGCell cell){
        Element element=(Element) combinedDoc.importNode(cell.element, true);

        int x=cellX+(cellWidth-cell.width)/2;
        int y=cellY+(cellHeight-cell.height)/2;

        Gdx.app.error("Cell Begin","---------");
        Gdx.app.error("X",String.valueOf(x));
        Gdx.app.error("Y",String.valueOf(y));
        Gdx.app.error(" CELL WIDTH",String.valueOf(cellWidth));
        Gdx.app.error("CELL HEIGHT",String.valueOf(cellHeight));
        Gdx.app.error(" ELEMENT WIDTH",String.valueOf(cell.width));
        Gdx.app.error("ELEMENT HEIGHT",String.valueOf(cell.height));
        Gdx.app.error("Cell End","---------");

        element.setAttribute("x", Integer.toString(x));
        element.setAttribute("y", Integer.toString(y));
        element.removeAttribute("width");
        element.removeAttribute("height");
        cellX+=cellWidth;
        if (cellWidth> maxWidth -cellX){
            cellX=0;
            cellY+=cellHeight;
            height+=cellHeight;
        }
        Element svgGroupElement = combinedDoc.createElement("g");
        svgGroupElement.setAttribute("id",cell.name);
        svgGroupElement.appendChild(element);
        root.appendChild(svgGroupElement);
    }

    protected void finish(){
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));
        source=new DOMSource(combinedDoc);
    }
}
