package org.fc.davannology.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.fc.davannology.model.NegativeTechnique;
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
@RequestMapping("/negativetechnique")
public class NegativeTechniqueController {
	
	@Autowired 
	private ObjectifyFactory objectifyFactory;

	@RequestMapping(value = "/list")
	public String list(Model model) {
		Objectify objectify = objectifyFactory.begin();
		model.addAttribute("negativetechniques", objectify.query(NegativeTechnique.class).list());
		return "negativetechnique/list";
	}
	
	@RequestMapping(value = "/edit") 
	public String create(Model model) {
		model.addAttribute("negativeTechnique", new NegativeTechnique());
		return "negativetechnique/edit";
	}
	
	@RequestMapping(value = "/edit/{_id}") 
	public String edit(@PathVariable("_id") Long id, Model model) {
		Objectify objectify = objectifyFactory.begin();
		model.addAttribute("negativeTechnique", objectify.find(NegativeTechnique.class, id));
		return "negativetechnique/edit";
	}
	
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(@Valid NegativeTechnique negativeTechnique, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("negativeTechnique", negativeTechnique);
            return "negativetechnique/edit";
        }
		model.asMap().clear();
		Objectify objectify = objectifyFactory.begin();
		objectify.put(negativeTechnique);
        return "redirect:/negativetechnique/list";
	}
}
