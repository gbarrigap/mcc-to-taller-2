/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package famipics.dao.jaxp;

import famipics.dao.InvalidLoginException;
import famipics.dao.RecordNotFoundException;
import famipics.dao.RepositoryConnectionException;
import famipics.dao.UniqueConstraintException;
import famipics.dao.UserDao;
import famipics.domain.User;
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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author guillermo
 */
public class UserDaoJaxp implements UserDao {

    /*private final String repositoryPath;

     public UserDaoJaxp() {
     this.repositoryPath = "/tmp/famipics-repo.xml";
     }

     public UserDaoJaxp(String repositoryPath) {
     this.repositoryPath = repositoryPath;
     }*/
    @Override
    public User authenticate(String email, String password) throws InvalidLoginException, RepositoryConnectionException {
        try {
            for (User user : retrieveAll()) {
                if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                    return user;
                }
            }
            throw new InvalidLoginException();
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void setRememberToken(User user) throws RecordNotFoundException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(JaxpFactory.repositoryPath);

            NodeList list = doc.getElementsByTagName("user");
            for (int i = 0; i < list.getLength(); i++) {
                Element el = (Element) list.item(i);
                if (user.getEmail().equals(el.getAttribute("email"))) {
                    el.setAttribute("famipics-remember-token", user.getRememberToken());

                    //Save the Created XML on Local Disc using Transformation APIs as Discussed
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                    transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(JaxpFactory.repositoryPath)));
                    System.out.println("XML File Created Succesfully");
                    return;
                }
            }
            throw new RecordNotFoundException();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User findByRememberToken(String token) throws RecordNotFoundException, RepositoryConnectionException {
        try {
            List<User> users = retrieveAll();
            for (User user : users) {
                if (token.equals(user.getRememberToken())) {
                    return user;
                }
            }
            throw new RecordNotFoundException();
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void create(User user) throws RepositoryConnectionException, UniqueConstraintException {
        // Look for email already registered.
        for (User u : retrieveAll()) {
            if (user.getEmail().equals(u.getEmail())) {
                throw new UniqueConstraintException();
            }
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(JaxpFactory.repositoryPath);
            
            // Get last pic id.
            String lastId = ((Element) doc.getElementsByTagName("uids").item(0)).getAttribute("last");
            int nextIdInt = Integer.parseInt(lastId);
            nextIdInt++;
            String nextId = Integer.toString(nextIdInt);
            
            ((Element) doc.getElementsByTagName("uids").item(0)).setAttribute("last", nextId);
            
            user.setUid(nextIdInt);

            //Element ele = doc.createElement("Movies");
            Element childElement = doc.createElement("user");
            childElement.setAttribute("display-name", user.getDisplayName());
            childElement.setAttribute("email", user.getEmail());
            childElement.setAttribute("password", user.getPassword());
            childElement.setAttribute("uid", Integer.toString(user.getUid()));

            //You can also use setTextContent() method to write between nodes
            doc.getElementsByTagName("users").item(0).appendChild(childElement);

            //Save the Created XML on Local Disc using Transformation APIs as Discussed
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
    public User retrieve(int id) throws RepositoryConnectionException, RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User t) throws RepositoryConnectionException, RecordNotFoundException, UniqueConstraintException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User t) throws RepositoryConnectionException, RecordNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> retrieveAll() throws RepositoryConnectionException {
        try {
            UsersHandler handler = new UsersHandler();
            SAXParserFactory.newInstance().newSAXParser().parse(JaxpFactory.repositoryPath, handler);
            return handler.getUsers();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw new RepositoryConnectionException();
        }
    }

    @Override
    public User findByEmail(String email) throws RecordNotFoundException, RepositoryConnectionException {
        try {
            List<User> users = retrieveAll();
            for (User user : users) {
                if (email.equals(user.getEmail())) {
                    return user;
                }
            }
            throw new RecordNotFoundException();
        } catch (RepositoryConnectionException ex) {
            Logger.getLogger(UserDaoJaxp.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private class UsersHandler extends DefaultHandler {

        private final List<User> users;

        UsersHandler() {
            this.users = new ArrayList<>();
        }

        public List<User> getUsers() {
            return users;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("user".equals(qName)) {
                User user = new User();
                user.setUid(Integer.parseInt(attributes.getValue("uid")));
                user.setEmail(attributes.getValue("email"));
                user.setDisplayName(attributes.getValue("display-name"));
                user.setPassword(attributes.getValue("password"));
                user.setAdmin(Boolean.parseBoolean(attributes.getValue("admin")));
                user.setRememberToken(attributes.getValue("famipics-remember-token"));
                getUsers().add(user);
            }
        }
    }
}
