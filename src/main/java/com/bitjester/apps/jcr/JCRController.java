package com.bitjester.apps.jcr;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jcr.*;

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
			logger.severe("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	private Binary bytesToBinary(byte[] bytes) {
		Binary value = null;
		Session session = null;
		try {
			session = getSession();
			InputStream istream = new ByteArrayInputStream(bytes);
			value = session.getValueFactory().createBinary(istream);
		} catch (Exception e) {
			logger.severe("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session)
				session.logout();
		}
		return value;
	}

	public boolean doesNodeExist(String path) {
		boolean value = false;
		Session session = null;
		try {
			session = getSession();
			value = session.itemExists(path);
		} catch (Exception e) {
			logger.severe("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session)
				session.logout();
		}
		return value;
	}

	public void createNode(String nodePath, Map<String, Object> options) {
		Session session = null;
		try {
			session = getSession();

			switch ((String) options.get("nodeType")) {
			case "nt:folder":
				tools.findOrCreateNode(session, nodePath, "nt:folder");
				break;

			case "nt:file":
				Node node = tools
						.findOrCreateNode(session, nodePath, "nt:file");
				// Upload the content to the node.
				Binary content = bytesToBinary((byte[]) options.get("bytes"));
				node.addNode("jcr:content", "nt:resource").setProperty(
						"jcr:data ", content);
				break;

			default:
				// TODO - use logger
				logger.severe("Unknown node type: "+ options.get("nodeType"));
				break;
			}
		} catch (Exception e) {
			logger.severe("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session)
				session.logout();
		}
	}

	public void updateNode(String nodePath, Map<String, Object> options) {
		Session session = null;
		try {
			session = getSession();
			if (null == session)
				System.out.println("Empty session.");
		} catch (Exception e) {
			logger.severe("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session)
				session.logout();
		}
	}

	public Map<String, Object> readNode(String nodePath) {
		Session session = null;
		try {
			session = getSession();

			// Prepare Map for return
			Map<String, Object> values = new HashMap<String, Object>();

			// Find the node that is to be read
			Node node = session.getNode(nodePath);
			switch (node.getPrimaryNodeType().getName()) {
			case "nt:file":
				values.put("name", node.getName());
				values.put("content",
						node.getNode("jcr:content").getProperty("jcr:data")
								.getBinary().getStream());
				values.put("size",
						node.getNode("jcr:content").getProperty("jcr:data")
								.getBinary().getSize());
				break;

			case "nt:folder":

				break;
			}

			return values;

		} catch (Exception e) {
			logger.severe("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if (null != session)
				session.logout();
		}
	}

	public void removeNode(String nodePath) {
		Session session = null;
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
			logger.severe("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session)
				session.logout();
		}
	}
}
