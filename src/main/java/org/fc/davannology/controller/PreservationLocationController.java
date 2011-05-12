package org.fc.davannology.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fc.davannology.dao.PreservationLocationDAO;
import org.fc.davannology.model.PreservationLocation;
import org.fc.davannology.web.FlashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/preservationlocation")
public class PreservationLocationController {
	
	@Autowired 
	private PreservationLocationDAO preservationLocationDAO;

	@RequestMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("preservationLocations", preservationLocationDAO.findAll());
		return "preservationlocation/list";
	}
	
	@RequestMapping(value = "/edit") 
	public String create(Model model) {
		model.addAttribute("preservationLocation", new PreservationLocation());
		return "preservationlocation/edit";
	}
	
	@RequestMapping(value = "/edit/{_id}") 
	public String edit(@PathVariable("_id") Long id, Model model) {
		model.addAttribute("preservationLocation", preservationLocationDAO.findById(id));
		return "preservationlocation/edit";
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(@Valid PreservationLocation preservationLocation, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("preservationLocation", preservationLocation);
            return "preservationlocation/edit";
        }
		model.asMap().clear();
		preservationLocationDAO.save(preservationLocation);
		FlashMap.setSuccessMessage("Donnée sauvée");
        return "redirect:/preservationlocation/list";
	}
	
	@RequestMapping(value = "/delete/{_id}") 
    public String delete(@PathVariable("_id") Long id, Model model) {
		preservationLocationDAO.delete(id);
		FlashMap.setSuccessMessage("Donnée supprimée");
        return "redirect:/preservationlocation/list";
    }
}
