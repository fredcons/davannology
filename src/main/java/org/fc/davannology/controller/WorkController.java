package org.fc.davannology.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

@Controller
@RequestMapping("/work")
public class WorkController {
	
	@ModelAttribute("preservationLocations")
	public Collection<PreservationLocation> populatePreservationLocations() {
		Objectify objectify = objectifyFactory.begin();
		return objectify.query(PreservationLocation.class).list();
	}
	
	@ModelAttribute("positiveTechniques")
	public Collection<PositiveTechnique> populatePositiveTechniques() {
		Objectify objectify = objectifyFactory.begin();
		return objectify.query(PositiveTechnique.class).list();
	}
	
	@ModelAttribute("negativeTechniques")
	public Collection<NegativeTechnique> populateNegativeTechniques() {
		Objectify objectify = objectifyFactory.begin();
		return objectify.query(NegativeTechnique.class).list();
	}

	
	@Autowired 
	private ObjectifyFactory objectifyFactory;

	@RequestMapping(value = "/list")
	public String list(Model model) {
		Objectify objectify = objectifyFactory.begin();
		Collection<Work> works = objectify.query(Work.class).list();
		for (Work work : works) {
		    if (work.getNegativeTechniqueKeyAsLong() != null) {
		        work.setNegativeTechnique(objectify.find(NegativeTechnique.class, work.getNegativeTechniqueKeyAsLong()));
		    } 
		    if (work.getPositiveTechniqueKeyAsLong() != null) {
                work.setPositiveTechnique(objectify.find(PositiveTechnique.class, work.getPositiveTechniqueKeyAsLong()));
            } 
		    if (work.getPreservationLocationKeyAsLong() != null) {
                work.setPreservationLocation(objectify.find(PreservationLocation.class, work.getPreservationLocationKeyAsLong()));
            } 
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
		Objectify objectify = objectifyFactory.begin();
		model.addAttribute("work", objectify.find(Work.class, id));
		return "work/edit";
	}
	
	@RequestMapping(value = "/view/{_id}") 
	public String view(@PathVariable("_id") Long id, Model model) {
		Objectify objectify = objectifyFactory.begin();
		model.addAttribute("work", objectify.find(Work.class, id));
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
		Objectify objectify = objectifyFactory.begin();
		objectify.put(work);
        return "redirect:/work/list";
	}
	
	@RequestMapping(value = "/search", method=RequestMethod.POST)
    public String search(Work search, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
        Objectify objectify = objectifyFactory.begin();
                
        Query<Work> query = objectify.query(Work.class);
        if (search.getPreservationLocationKey() != null) {
            query.filter("preservationLocationKey", search.getPreservationLocationKey());
        }
        if (search.getPositiveTechniqueKey() != null) {
            query.filter("positiveTechniqueKey", search.getPositiveTechniqueKey());
        }
        if (search.getNegativeTechniqueKey() != null) {
            query.filter("negativeTechniqueKey", search.getNegativeTechniqueKey());
        }
        if (StringUtils.hasLength(search.getDescription())) {
            query.filter("description >=", search.getDescription()).filter("description <", search.getDescription() + "\uFFFD"); 
        }
        
        Collection<Work> works = query.list();
        for (Work currentWork : works) {
            if (currentWork.getNegativeTechniqueKeyAsLong() != null) {
                currentWork.setNegativeTechnique(objectify.get(NegativeTechnique.class, currentWork.getNegativeTechniqueKeyAsLong()));
            } 
            if (currentWork.getPositiveTechniqueKeyAsLong() != null) {
                currentWork.setPositiveTechnique(objectify.get(PositiveTechnique.class, currentWork.getPositiveTechniqueKeyAsLong()));
            } 
            if (currentWork.getPreservationLocationKeyAsLong() != null) {
                currentWork.setPreservationLocation(objectify.get(PreservationLocation.class, currentWork.getPreservationLocationKeyAsLong()));
            } 
        }
        model.addAttribute("works", works);        
        model.addAttribute("search", search);
        
        return "work/list";
    }
}
