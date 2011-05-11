package org.fc.davannology.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fc.davannology.model.PreservationLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

@Controller
@RequestMapping("/preservationlocation")
public class PreservationLocationController {
	
	@Autowired 
	private ObjectifyFactory objectifyFactory;

	@RequestMapping(value = "/list")
	public String list(Model model) {
		Objectify objectify = objectifyFactory.begin();
		model.addAttribute("preservationLocations", objectify.query(PreservationLocation.class).list());
		return "preservationlocation/list";
	}
	
	@RequestMapping(value = "/edit") 
	public String create(Model model) {
		model.addAttribute("preservationLocation", new PreservationLocation());
		return "preservationlocation/edit";
	}
	
	@RequestMapping(value = "/edit/{_id}") 
	public String edit(@PathVariable("_id") Long id, Model model) {
		Objectify objectify = objectifyFactory.begin();
		model.addAttribute("preservationLocation", objectify.find(PreservationLocation.class, id));
		return "preservationlocation/edit";
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(@Valid PreservationLocation preservationLocation, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("preservationLocation", preservationLocation);
            return "preservationlocation/edit";
        }
		model.asMap().clear();
		Objectify objectify = objectifyFactory.begin();
		objectify.put(preservationLocation);
        return "redirect:/preservationlocation/list";
	}
	
	@RequestMapping(value = "/delete/{_id}") 
    public String delete(@PathVariable("_id") Long id, Model model) {
        Objectify objectify = objectifyFactory.begin();
        objectify.delete(PreservationLocation.class, id);
        return "redirect:/preservationlocation/list";
    }
}
