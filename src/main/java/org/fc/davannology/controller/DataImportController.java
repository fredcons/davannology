package org.fc.davannology.controller;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.fc.davannology.dao.NegativeTechniqueDAO;
import org.fc.davannology.dao.PositiveTechniqueDAO;
import org.fc.davannology.dao.PreservationLocationDAO;
import org.fc.davannology.dao.QueryFilter;
import org.fc.davannology.dao.WorkDAO;
import org.fc.davannology.model.NegativeTechnique;
import org.fc.davannology.model.PositiveTechnique;
import org.fc.davannology.model.PreservationLocation;
import org.fc.davannology.model.Work;
import org.fc.davannology.web.FlashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.com.bytecode.opencsv.CSVReader;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

@Controller
@RequestMapping("/dataimport")
public class DataImportController {
	
	@Autowired 
	private WorkDAO workDAO;	
	@Autowired 
	private PositiveTechniqueDAO positiveTechniqueDAO;	
	@Autowired 
	private NegativeTechniqueDAO negativeTechniqueDAO;	
	@Autowired 
	private PreservationLocationDAO preservationLocationDAO;
	
	@RequestMapping(value = "/form") 
	public String form(Model model) {
		return "dataimport/form";
	}
	
	@RequestMapping(value = "/done") 
	public String done(Model model) {
		return "dataimport/done";
	}
	
	@RequestMapping(value = "/deletedone") 
	public String deleteDone(Model model) {
		return "dataimport/deletedone";
	}
	
	@RequestMapping(value = "/confirmdelete") 
	public String confirmDelete(Model model) {
		return "dataimport/confirmdelete";
	}
	
	@RequestMapping(value = "/deleteAll", method=RequestMethod.POST) 
	public String deleteAll(Model model) {		
		workDAO.deleteAll();
		positiveTechniqueDAO.deleteAll();
		negativeTechniqueDAO.deleteAll();
		preservationLocationDAO.deleteAll();		
		return "redirect:/dataimport/deletedone";
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(HttpServletRequest httpServletRequest) {
		
		ServletFileUpload upload = new ServletFileUpload();
		try {
			FileItemIterator fileItemIterator = upload.getItemIterator(httpServletRequest);
			while (fileItemIterator.hasNext()) {
	            FileItemStream item = fileItemIterator.next();
	            String fieldName = item.getFieldName();
	            if (fieldName.equals("importFile")) {
	            	InputStream fileContent = item.openStream();
	            	CSVReader reader = new CSVReader(new InputStreamReader(fileContent, "UTF-8"), ',', '\"', 1);
	            	String[] nextLine;
	                while ((nextLine = reader.readNext()) != null) {	                	
	                    String workDescription = nextLine[0].trim();
	                    String positiveTechniqueName = nextLine[1].trim();
	                    String negativeTechniqueName = nextLine[2].trim();
	                    String workDates = nextLine[3].trim();
	                    String workHeight = nextLine[4].trim();
	                    String workWidth = nextLine[5].trim();
	                    String workDepth = nextLine[6].trim();
	                    String preservationLocationName = nextLine[7].trim();
	                    String workReference = nextLine[8].trim();
	                    String workComment = nextLine[9].trim();
	                    
	                    Work work = workDAO.findUniqueByFilters(Lists.newArrayList(new QueryFilter("description", workDescription)));
	                    if (work == null) {
	                    	work = new Work();
	                    }
	                    work.setDescription(workDescription);
	                    work.setDates(workDates);
	                    work.setHeight(workHeight);
	                    work.setWidth(workWidth);
	                    work.setDepth(workDepth);
	                    work.setReference(workReference);
	                    work.setComment(workComment);
	                    
	                    if (StringUtils.hasLength(positiveTechniqueName)) {
		                    PositiveTechnique positiveTechnique = positiveTechniqueDAO.findUniqueByFilters(Lists.newArrayList(new QueryFilter("name", positiveTechniqueName)));
		                    if (positiveTechnique == null) {
		                    	positiveTechnique = new PositiveTechnique();
		                    	positiveTechnique.setName(positiveTechniqueName);
		                    	positiveTechniqueDAO.save(positiveTechnique);		                    	
		                    }
		                    work.setPositiveTechniqueKeyAsLong(positiveTechnique.getId());
	                    }
	                    
	                    if (StringUtils.hasLength(negativeTechniqueName)) {
		                    NegativeTechnique negativeTechnique = negativeTechniqueDAO.findUniqueByFilters(Lists.newArrayList(new QueryFilter("name", negativeTechniqueName)));
		                    if (negativeTechnique == null) {
		                    	negativeTechnique = new NegativeTechnique();
		                    	negativeTechnique.setName(negativeTechniqueName);
		                    	negativeTechniqueDAO.save(negativeTechnique);		                    	
		                    }
		                    work.setNegativeTechniqueKeyAsLong(negativeTechnique.getId());
	                    }
	                    
	                    if (StringUtils.hasLength(preservationLocationName)) {
	                    	PreservationLocation preservationLocation = preservationLocationDAO.findUniqueByFilters(Lists.newArrayList(new QueryFilter("name", preservationLocationName)));
		                    if (preservationLocation == null) {
		                    	preservationLocation = new PreservationLocation();
		                    	preservationLocation.setName(preservationLocationName);
		                    	preservationLocationDAO.save(preservationLocation);		                    	
		                    }
		                    work.setPreservationLocationKeyAsLong(preservationLocation.getId());
	                    }
	                    
	                    workDAO.save(work);
	                }
	            }
	            
			}    
		} catch (Exception e) {
			return "dataimport/form";
		}
		FlashMap.setSuccessMessage("Fichier importé");
        return "redirect:/dataimport/done";
	}

}
