package org.fc.davannology.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fc.davannology.dao.NegativeTechniqueDAO;
import org.fc.davannology.dao.PositiveTechniqueDAO;
import org.fc.davannology.dao.PreservationLocationDAO;
import org.fc.davannology.dao.QueryFilter;
import org.fc.davannology.dao.WorkDAO;
import org.fc.davannology.model.NegativeTechnique;
import org.fc.davannology.model.PositiveTechnique;
import org.fc.davannology.model.PreservationLocation;
import org.fc.davannology.model.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

@Controller
@RequestMapping("/work")
public class WorkController {
	
	@Autowired 
	private WorkDAO workDAO;	
	@Autowired 
	private PositiveTechniqueDAO positiveTechniqueDAO;	
	@Autowired 
	private NegativeTechniqueDAO negativeTechniqueDAO;	
	@Autowired 
	private PreservationLocationDAO preservationLocationDAO;
	
	@ModelAttribute("preservationLocations")
	public List<PreservationLocation> populatePreservationLocations() {
		return preservationLocationDAO.findAll();
	}
	
	@ModelAttribute("positiveTechniques")
	public List<PositiveTechnique> populatePositiveTechniques() {
		return positiveTechniqueDAO.findAll();
	}
	
	@ModelAttribute("negativeTechniques")
	public List<NegativeTechnique> populateNegativeTechniques() {
		return negativeTechniqueDAO.findAll();
	}

	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<Work> works = workDAO.findAll();
		for (Work currentWork : works) {
			addRelations(currentWork);
		}
		model.addAttribute("works", works);
		model.addAttribute("search", new Work());
		return "work/list";
	}
	
	@RequestMapping(value = "/edit") 
	public String create(Model model) {
		model.addAttribute("work", new Work());
		return "work/edit";
	}
	
	@RequestMapping(value = "/edit/{_id}") 
	public String edit(@PathVariable("_id") Long id, Model model) {
		model.addAttribute("work", workDAO.findById(id));
		return "work/edit";
	}
	
	@RequestMapping(value = "/view/{_id}") 
	public String view(@PathVariable("_id") Long id, Model model) {
		model.addAttribute("work", workDAO.findById(id));
		return "view/edit";
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(@Valid Work work, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			for (ObjectError error : bindingResult.getAllErrors()) {
				System.out.println("error " + error.getDefaultMessage() + " / " + error.getObjectName());
			}
			model.addAttribute("work", work);
            return "work/edit";
        }
		model.asMap().clear();
		workDAO.save(work);
        return "redirect:/work/list";
	}
	
	@RequestMapping(value = "/search", method=RequestMethod.POST)
    public String search(Work search, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
        
        List<QueryFilter> filters = Lists.newArrayList();
        if (search.getPreservationLocationKey() != null) {
        	filters.add(new QueryFilter("preservationLocationKey", search.getPreservationLocationKey()));
        }
        if (search.getPositiveTechniqueKey() != null) {
        	filters.add(new QueryFilter("positiveTechniqueKey", search.getPositiveTechniqueKey()));
        }
        if (search.getNegativeTechniqueKey() != null) {
        	filters.add(new QueryFilter("negativeTechniqueKey", search.getNegativeTechniqueKey()));
        }
        if (StringUtils.hasLength(search.getDescription())) {
        	filters.add(new QueryFilter("description >=", search.getDescription()));
        	filters.add(new QueryFilter("description <", search.getDescription() + "\uFFFD")); 
        }
        
        List<Work> works = workDAO.findByFilters(filters);
        for (Work currentWork : works) {
        	addRelations(currentWork);
            
        }
        model.addAttribute("works", works);        
        model.addAttribute("search", search);
        
        return "work/list";
    }
	
	void addRelations(Work work) {
		if (work.getNegativeTechniqueKeyAsLong() != null) {
			work.setNegativeTechnique(negativeTechniqueDAO.findById(work.getNegativeTechniqueKeyAsLong()));
        } 
        if (work.getPositiveTechniqueKeyAsLong() != null) {
        	work.setPositiveTechnique(positiveTechniqueDAO.findById(work.getPositiveTechniqueKeyAsLong()));
        } 
        if (work.getPreservationLocationKeyAsLong() != null) {
        	work.setPreservationLocation(preservationLocationDAO.findById(work.getPreservationLocationKeyAsLong()));
        } 
	}
}
