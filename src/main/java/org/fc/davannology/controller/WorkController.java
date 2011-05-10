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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

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
		model.addAttribute("works", objectify.query(Work.class).list());
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
		System.out.println("preservation location : " + work.getPreservationLocation());
		System.out.println("positive              : " + work.getPositiveTechnique());
		model.asMap().clear();
		Objectify objectify = objectifyFactory.begin();
		objectify.put(work);
        return "redirect:/work/list";
	}
}
