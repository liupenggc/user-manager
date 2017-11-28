package com.feinno.service;
import java.util.List;

import com.feinno.annotation.HBaseDao;
import com.feinno.entity.Cost;
//盖章:在dao的接口下
@HBaseDao
public interface CostService {
	//查询所有
	List<Cost> findAll();
	//新增数据
	void save(Cost cost);
	//根据id查询
	Cost findByRowKey(String id);
	//修改
	void update(Cost cost);
	//删除
	void delete(String id);
}