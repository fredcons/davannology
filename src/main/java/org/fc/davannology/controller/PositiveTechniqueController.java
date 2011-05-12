package org.fc.davannology.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fc.davannology.dao.PositiveTechniqueDAO;
import org.fc.davannology.model.PositiveTechnique;
import org.fc.davannology.web.FlashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/positivetechnique")
public class PositiveTechniqueController {
	
	@Autowired 
	private PositiveTechniqueDAO positiveTechniqueDAO;
	
	@RequestMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("positiveTechniques", positiveTechniqueDAO.findAll());
		return "positivetechnique/list";
	}
	
	@RequestMapping(value = "/edit") 
	public String create(Model model) {
		model.addAttribute("positiveTechnique", new PositiveTechnique());
		return "positivetechnique/edit";
	}
	
	@RequestMapping(value = "/edit/{_id}") 
	public String edit(@PathVariable("_id") Long id, Model model) {
		model.addAttribute("positiveTechnique", positiveTechniqueDAO.findById(id));
		return "positivetechnique/edit";
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(@Valid PositiveTechnique positiveTechnique, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("positiveTechnique", positiveTechnique);
            return "positivetechnique/edit";
        }
		model.asMap().clear();
		positiveTechniqueDAO.save(positiveTechnique);
		FlashMap.setSuccessMessage("Donnée sauvée");
		return "redirect:/positivetechnique/list";
	}
	
	@RequestMapping(value = "/delete/{_id}") 
    public String delete(@PathVariable("_id") Long id, Model model) {
        positiveTechniqueDAO.delete(id);
        FlashMap.setSuccessMessage("Donnée supprimée");
		return "redirect:/positivetechnique/list";
    }
}
