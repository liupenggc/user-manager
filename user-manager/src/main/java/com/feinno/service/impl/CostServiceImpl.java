package com.feinno.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feinno.dao.CostDao;
import com.feinno.entity.Cost;
import com.feinno.service.CostService;

@Service
public class CostServiceImpl implements CostService{

	//注入dao
	@Resource
	private CostDao cDao;
	@Override
	public List<Cost> findAll() {
		cDao.scanResult("user");
		return cDao.scanResult("user");
	}

	@Override
	public void save(Cost cost) {
		String[] column = {"id","name","age","sex"};
		String[] value = {cost.getId(),cost.getName(),cost.getAge(),cost.getSex()};
		cDao.addData("user",cost.getId(),column,value);
	}

	@Override
	public Cost findByRowKey(String RowKey) {
		return cDao.getResult("user", RowKey);
	}
	
	@Override
	public void delete(String id) {
		cDao.deleteAllColumn("user",id);
	}

	@Override
	public void update(Cost cost) {
		String[] column = {"name","age","sex"};
		String[] value = {cost.getName(),cost.getAge(),cost.getSex()};
		cDao.addData("user",cost.getId(),column,value);
	}
}
