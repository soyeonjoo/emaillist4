package kr.ac.sungkyul.emaillist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.sungkyul.emaillist.dao.EmailListDao;
import kr.ac.sungkyul.emaillist.vo.EmailListVo;

@Controller
public class EmaiListController {

	@Autowired
	private EmailListDao dao;
	
	/*@RequestMapping("/list")
	public ModelAndView list (){
		List<EmailListVo> list = dao.getList();
		System.out.println(list);
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("/WEB-INF/views/list.jsp");
		return mav;
	}*/
	
	@RequestMapping("/list")
	public String list(Model model){
		List<EmailListVo> list = dao.getList();
		model.addAttribute("list", list);
		return"/WEB-INF/views/list.jsp";
	}
	
	@RequestMapping("/form")
	public String form(){
		return"WEB-INF/views/form.jsp";
		
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute EmailListVo vo){
		dao.insert(vo);
		return "redirect:/list"; // redirect 시키기 
		
	}
}
