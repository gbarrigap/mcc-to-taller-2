/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package famipics.dao.jaxp;

import famipics.dao.PicDao;
import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import famipics.domain.Pic;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author guillermo
 */
public class PicDaoJaxp implements PicDao {

    @Override
    public void create(Pic pic) throws RepositoryConnectionException, UniqueConstraintException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(JaxpFactory.repositoryPath);

            // Get last pic id.
            String lastId = ((Element) doc.getElementsByTagName("pids").item(0)).getAttribute("last");
            int nextIdInt = Integer.parseInt(lastId);
            nextIdInt++;
            String nextId = Integer.toString(nextIdInt);
            
            ((Element) doc.getElementsByTagName("pids").item(0)).setAttribute("last", nextId);
            
            pic.setPid(nextIdInt);
            
            Element childElement = doc.createElement("pic");
            childElement.setAttribute("pid", nextId);
            childElement.setAttribute("uid", Integer.toString(pic.getUser().getUid()));
            childElement.setAttribute("filename", pic.getFilename());
            childElement.setAttribute("comment", pic.getComment());

            //You can also use setTextContent() method to write between nodes
            doc.getElementsByTagName("pics").item(0).appendChild(childElement);

            //Save the Created XML on Local Disc using Transformation APIs as Discussed
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(JaxpFactory.repositoryPath)));
            System.out.println("XML File Created Succesfully");
        } catch (ParserConfigurationException | DOMException | IllegalArgumentException | TransformerException | SAXException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw new RepositoryConnectionException();
        } catch (IOException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw new RepositoryConnectionException();
        }
    }

    @Override
    public Pic retrieve(int pid) throws RecordNotFoundException, RepositoryConnectionException {
        try {
            for (Pic pic : this.retrieveAll()) {
                if (pid == pic.getPid()) {
                    return pic;
                }
            }
            throw new RecordNotFoundException();
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(PicDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void update(Pic t) throws RepositoryConnectionException, RecordNotFoundException, UniqueConstraintException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Pic t) throws RepositoryConnectionException, RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pic> retrieveAll() throws RepositoryConnectionException {
        try {
            PicsHandler handler = new PicsHandler();
            SAXParserFactory.newInstance().newSAXParser().parse(JaxpFactory.repositoryPath, handler);
            return handler.getPics();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw new RepositoryConnectionException();
        }
    }
    
    private class PicsHandler extends DefaultHandler {

        private final List<Pic> pics;

        PicsHandler() {
            this.pics = new ArrayList<>();
        }

        public List<Pic> getPics() {
            return pics;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("pic".equals(qName)) {
                Pic pic = new Pic();
                pic.setPid(Integer.parseInt(attributes.getValue("pid")));
                pic.setUid(Integer.parseInt(attributes.getValue("uid")));
                pic.setFilename(attributes.getValue("filename"));
                pic.setComment(attributes.getValue("comment"));
                pics.add(pic);
            }
        }
    }
}
