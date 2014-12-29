package com.bitjester.apps.jcr;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jcr.Repository;
import javax.jcr.*;
import javax.jcr.nodetype.NodeDefinition;

import org.modeshape.jcr.api.JcrTools;

@Stateless
public class JCRController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;

    @Resource(mappedName = "java:/jcr/mdJCR")
    private Repository repository;
    private JcrTools tools = new JcrTools();

    private Session getSession() {
        try {
            return repository.login("default");
        } catch (Exception e) {
            logger.severe("2Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Binary fileToBinary(File file){
        try {
            return getSession().getValueFactory().createBinary(new FileInputStream(file));
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean doesNodeExist(String path){
        try {
            return getSession().itemExists(path);
        }catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createNode( String nodePath, Map<String, Object> options) {
        Session session = null;
        // TODO: Check ACL for user

        try {
            session = getSession();

            switch ((String) options.get("nodeType")) {
                case "nt:folder":
                    tools.findOrCreateNode(session, nodePath, "nt:folder");
                    break;

                case "nt:file":
                    Node node = tools.findOrCreateNode(session, nodePath, "nt:file");
                    // Upload the content to the node.
                    Binary content = fileToBinary((File) options.get("file"));
                    node.addNode("jcr:content", "nt:resource").setProperty("jcr:data ", content);
                    break;

                default:
                    // TODO - use logger
                    System.out.println("Unknown node type: " + options.get("nodeType"));
            }
        } catch (Exception e) {
            logger.severe("4Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != session)
                session.logout();
        }
    }

    public void updateNode( String nodePath, Map<String, Object> options) {
        Session session = null;
        // TODO: Check ACL for user

        try {
            session = getSession();
            if (null == session)
                System.out.println("Empty session.");
        } catch (Exception e) {
            logger.severe("5Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != session)
                session.logout();
        }

    }

    public Map<String, Object> readNode( String nodePath) {
        Session session = null;
        // TODO: Check ACL for user

        try {
            session = getSession();

            // Prepare Map for return
            Map values = new HashMap();

            // Find the node that is to be read
            Node node = session.getNode(nodePath);
            switch (node.getPrimaryNodeType().getName()){
                case "nt:file":
                    values.put("name", node.getName());
                    values.put("content",node.getNode("jcr:content").getProperty("jcr:data").getBinary().getStream());
                    values.put("size",node.getNode("jcr:content").getProperty("jcr:data").getBinary().getSize());
                    break;

                case "nt:folder":

                    break;

            }

            return values;

        } catch (Exception e) {
            logger.severe("6Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (null != session)
                session.logout();
        }
    }

    public void removeNode( String nodePath) {
        Session session = null;
        // TODO: Check ACL for user

        try {
            session = getSession();

            // Find the node that is to be removed
            Node node = session.getNode(nodePath);
            tools.removeAllChildren(node);

            // Remove node's properties
            PropertyIterator pIt = node.getProperties();
            while (pIt.hasNext()) {
                Property property = pIt.nextProperty();
                property.remove();
            }

            // Finally remove the node
            node.remove();

            // Persists changes to repository
            session.save();
        } catch (Exception e) {
            logger.severe("7Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != session)
                session.logout();
        }
    }
}
