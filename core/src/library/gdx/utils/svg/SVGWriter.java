package library.gdx.utils.svg;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class SVGWriter {
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private TransformerFactory transformerFactory;
    private Transformer transformer;
    private int maxWidth,maxHeight;
    private int cellWidth,cellHeight, docMaxWidth, docMaxHeight;
    private List<SVGDoc> svgDocs;
    private SVGDoc currentDoc;

    public SVGWriter() throws Exception{
        dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        svgDocs=new ArrayList<>();
        setParameters(1024,1024);
    }

    public void setParameters(int maxWidth,int maxHeight){
        this.maxWidth=maxWidth;
        this.maxHeight=maxHeight;
    }


    public void combineSVG(List<FileHandle> svgFiles) throws Exception{
        List<SVGCell> svgCells=new ArrayList<>();
        for (FileHandle svgFile:svgFiles){
            SVGCell svgCell=new SVGCell(svgFile,dBuilder);
            cellWidth=Math.max(svgCell.width,cellWidth);
            cellHeight=Math.max(svgCell.height,cellHeight);
            svgCells.add(new SVGCell(svgFile,dBuilder));
        }

        int columnCount=maxWidth/cellWidth;
        int rowCount=maxHeight/cellHeight;

        docMaxWidth =columnCount*cellWidth;
        docMaxHeight =rowCount*cellHeight;
        int filesInOneDoc=columnCount*rowCount;

        int totalSVGFiles=svgFiles.size();
        int totalDoc=totalSVGFiles/filesInOneDoc+(totalSVGFiles%filesInOneDoc>0?1:0);

        svgDocs.clear();

        newDoc();

        int fileIndex=0;
        while (fileIndex<totalSVGFiles){
            currentDoc.addCell(svgCells.get(fileIndex));
            fileIndex++;
            if (fileIndex>=totalSVGFiles){
                finishDoc();
            }
            if (fileIndex>=filesInOneDoc){
                finishDoc();
                if (fileIndex<totalSVGFiles){
                    newDoc();
                }
            }
        }
        currentDoc.finish();
    }


    private SVGDoc newDoc(){
        SVGDoc svgDoc=new SVGDoc(dBuilder, docMaxWidth, docMaxHeight,cellWidth,cellHeight);
        svgDoc.begin();
        currentDoc=svgDoc;
        svgDocs.add(svgDoc);
        return svgDoc;
    }

    private void finishDoc(){
        currentDoc.finish();
    }

    public List<FileHandle> saveDocs(FileHandle outputDirectory,String name) throws Exception{
        List<FileHandle> fileHandles=new ArrayList<>();
        for (int i=0;i<svgDocs.size();i++){
            SVGDoc svgDoc=svgDocs.get(i);
            FileHandle fileHandle;
            if (i==0){
                fileHandle=outputDirectory.child(name+".svg");
            }else {
                fileHandle=outputDirectory.child(name+i+".svg");
            }
            fileHandles.add(fileHandle);
            saveDoc(svgDoc,fileHandle);
        }
        return fileHandles;
    }

    private void saveDoc(SVGDoc doc,FileHandle outputFile) throws Exception{
        StreamResult result = new StreamResult(outputFile.file());
        transformer.transform(doc.source, result);
    }


}
