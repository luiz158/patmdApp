package com.bitjester.apps.jcr;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

@Stateless
public class JCRUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    JCRController jcrController;

    @Inject
    private Logger logger;


    public void createBiopsyRepo(String bcode) {
        Map<String, Object> options = new HashMap<>();
        options.put("nodeType", "nt:folder");
        String nodePath = "/biopsies/" + bcode;
        jcrController.createNode(nodePath, options);
    }

    public void removeBiopsyRepo(String bcode, String filename){
        String nodePath = "/biopsies/" + bcode;
        jcrController.removeNode(nodePath);
    }

    public void removeFile(String bcode, String filename){
        String nodePath = "/biopsies/" + bcode + "/" + filename;
        jcrController.removeNode(nodePath);
    }

    public void storeFile(String bcode, File file){
        String nodePath = "/biopsies/" + bcode + "/" + image.getName();
        Map<String, Object> options = new HashMap<>();
        options.put("nodeType", "nt:file");
        options.put("file", file);
        jcrController.createNode(nodePath, options);
    }

    public List<String> readDirectory(String bcode){
        String nodePath = "/biopsies/" + bcode;
        return (List<String>) jcrController.readNode(nodePath).get("children");
    }

    public byte[] readFile(String bcode, String filename) throws Exception {
        String nodePath = "/biopsies/" + bcode + "/" + filename;
        // Using org.modeshape.common.util.IoUtil;
        //return IoUtil.readBytes((InputStream) jcrController.readNode(nodePath).get("content"));

        // Using org.apache.commons.io.IOUtils;
        return IOUtils.toByteArray((InputStream) jcrController.readNode(nodePath).get("content"));
    }
}
