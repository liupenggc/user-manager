package com.feinno.dao;
import java.util.List;

import com.feinno.annotation.HBaseDao;
import com.feinno.entity.Cost;
//盖章:在dao的接口下
@HBaseDao
public interface CostDao {
	//查询所有
	List<Cost> scanResult(String tableName);
	//新增数据
	void addData(String tableName,String rowKey, String[] column, String[] value);
	//根据id查询
	Cost getResult(String tableName, String rowKey);
	//删除
	void deleteAllColumn(String tableName, String rowKey);
}