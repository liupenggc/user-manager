package com.feinno.controller;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.feinno.entity.Cost;
import com.feinno.service.CostService;
@Controller
@RequestMapping("cost")
public class CostController {

	//注入service
	@Resource
	private CostService cService;
	
	@RequestMapping("findCost.do")
	public String find(Model model){
		List<Cost> costs = cService.findAll();
		model.addAttribute("costs", costs);
		return "cost/cost_list";
	}
	
	//先跳转到添加页面
	@RequestMapping("toAddCost.do")
	public String toAdd(){
		return "cost/add_cost";
	}
	@RequestMapping("addCost.do")
	public String add(Cost cost){
		cService.save(cost);
		return "redirect:findCost.do";
	}
	
	//跳转到修改页面
	@RequestMapping("toUpdateCost.do")
	public String toUpdate(String id,Model model){
		//查询
		Cost cost=cService.findByRowKey(id);
		//绑
		model.addAttribute("cost", cost);
		return "cost/update_cost";
	}
	
	//回到列表页面
	@RequestMapping("updateCost.do")
	public String update(Cost cost){
		cService.update(cost);
		return "redirect:findCost.do";
	}
	//删除
	@RequestMapping("deleteCost.do")
	public String delete(@RequestParam("id") String id){
		cService.delete(id);
		return "redirect:findCost.do";
	}
		
}