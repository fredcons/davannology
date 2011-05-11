package org.fc.davannology.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fc.davannology.dao.NegativeTechniqueDAO;
import org.fc.davannology.model.NegativeTechnique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/negativetechnique")
public class NegativeTechniqueController {
	
	@Autowired 
	private NegativeTechniqueDAO negativeTechniqueDAO;

	@RequestMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("negativeTechniques", negativeTechniqueDAO.findAll());
		return "negativetechnique/list";
	}
	
	@RequestMapping(value = "/edit") 
	public String create(Model model) {
		model.addAttribute("negativeTechnique", new NegativeTechnique());
		return "negativetechnique/edit";
	}
	
	@RequestMapping(value = "/edit/{_id}") 
	public String edit(@PathVariable("_id") Long id, Model model) {
		model.addAttribute("negativeTechnique", negativeTechniqueDAO.findById(id));
		return "negativetechnique/edit";
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(@Valid NegativeTechnique negativeTechnique, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("negativeTechnique", negativeTechnique);
            return "negativetechnique/edit";
        }
		model.asMap().clear();
		negativeTechniqueDAO.save(negativeTechnique);
        return "redirect:/negativetechnique/list";
	}
	
	@RequestMapping(value = "/delete/{_id}") 
    public String delete(@PathVariable("_id") Long id, Model model) {
		negativeTechniqueDAO.delete(id);
        return "redirect:/negativetechnique/list";
    }
}
