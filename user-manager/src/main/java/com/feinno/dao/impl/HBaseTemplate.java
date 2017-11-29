package com.feinno.dao.impl;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.feinno.dao.CostDao;
import com.feinno.entity.Cost;

/**
 * Created with IntelliJ IDEA.
 * User: 刘鹏
 * Date: 16/6/29
 * Time: 下午12:15
 * DESCIPTION: Hbase 1.2.0 API
 */
@Repository
public class HBaseTemplate implements CostDao{
    public static Configuration config = null;
    public static Logger log = Logger.getLogger(HBaseTemplate.class); 
    static {
        config = HBaseConfiguration.create();
        //config.set("hbase.zookeeper.quorum", "master:2181");
    }
 
    /**
     * 删除rowkey
     *
     * @param tableName 表名
     * @param rowKey
     */
    public void deleteAllColumn(String tableName, String rowKey) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Delete delAllColumn = new Delete(Bytes.toBytes(rowKey));
            table.delete(delAllColumn);
            System.out.println("Delete AllColumn Success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 删除指定列
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列族
     * @param columnName 列名
     */
    public static void deleteColumn(String tableName, String rowKey, String familyName, String columnName) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Delete delColumn = new Delete(Bytes.toBytes(rowKey));
            delColumn.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
            table.delete(delColumn);
            System.out.println("Delete Column Success!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 查询多个版本的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列族
     * @param columnName 列名
     */
    public static void getResultByVersion(String tableName, String rowKey, String familyName, String columnName) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
            get.setMaxVersions(5);
//          Result result = table.get(get);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 更新某一列的值
     *
     * @param tableName  表名
     * @param rowKey     rowkey
     * @param familyName 列族
     * @param columnName 列名
     * @param value      值
     */
    public static void updateTable(String tableName, String rowKey, String familyName, String columnName, String value) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
            table.put(put);
            System.out.println("Update Table Success!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 查询某一列数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列族
     * @param columnName 列名
     */
    public void getResultByColumn(String tableName, String rowKey, String familyName, String columnName) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
//            Result result = table.get(get);
//            Cost cost = new Cost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 范围查询数据
     *
     * @param tableName   表名
     * @param beginRowKey startRowKey
     * @param endRowKey   stopRowKey
     */
    public static void scanResult(String tableName, String beginRowKey, String endRowKey) {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(beginRowKey));
        scan.setStopRow(Bytes.toBytes(endRowKey));
        scan.setMaxVersions(1);
        scan.setCaching(20);
        scan.setBatch(10);
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName));
             ResultScanner rs = table.getScanner(scan)) {
            for (Result result : rs) {
                System.out.println(Bytes.toString(result.getRow()));
                //以下是打印内容
                for (Cell cell : result.listCells()) {
                    System.out.println("family:" + Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()));
                    System.out.println("qualifier:" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()));
                    System.out.println("value:" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                    System.out.println("Timestamp:" + cell.getTimestamp());
                    System.out.println("---------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 全表扫描数据
     * 查询全部
     * @param tableName 表名
     */
    public List<Cost> scanResult(String tableName) {
    	List<Cost> list = new ArrayList<>();
    	Cost cost = null;
    	Scan scan = new Scan();
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName));
             ResultScanner rs = table.getScanner(scan)) {
        	 for (Result res : rs) {
				cost = result2Bean(res, "data");
				list.add(cost);
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
 
    /**
     * 根据rowkey查询数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return
     */
    public Cost getResult(String tableName, String rowKey) {
        Result result = null;
        Cost cost = null;
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName))
        ) {
            Get get = new Get(Bytes.toBytes(rowKey));
            result = table.get(get);
            cost = result2Bean(result,"data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cost;
    }
 
    /**
     * 添加数据
     *
     * @param rowKey    rowKey
     * @param tableName 表名
     * @param column    列名
     * @param value     值
     */
    public void addData(String tableName,String rowKey, String[] column, String[] value) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Put put = new Put(Bytes.toBytes(rowKey));
            HColumnDescriptor[] columnFamilies = table.getTableDescriptor().getColumnFamilies();
            for (int i = 0; i < columnFamilies.length; i++) {
                String familyName = columnFamilies[i].getNameAsString();
                if (familyName.equals("data")) {
                    for (int j = 0; j < column.length; j++) {
                        put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(column[j]), Bytes.toBytes(value[j]));
                    }
                }
                table.put(put);
                System.out.println("Add Data Success!!!-");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 删除表
     *
     * @param tableName 表名
     */
    public static void deleteTable(String tableName) {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
            System.out.println(tableName + " is deleted!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 创建Table
     *
     * @param tableName 表名
     * @param family    列族
     */
    public static void createTable(String tableName, String[] family) {
        HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {
 
            for (int i = 0; i < family.length; i++) {
                table.addFamily(new HColumnDescriptor(family[i]));
            }
            if (admin.tableExists(TableName.valueOf(tableName))) {
                System.out.println("Table Exists!!");
                System.exit(0);
            } else {
                admin.createTable(table);
                System.out.println("Create Table Success!!! Table Name :[ " + tableName + " ]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 将结果集中的数据封装到JavaBean中
     *
     * @param result     HbaseResult
     * @param familyName 列族
     * @return
     */
//    public static <T> T parsePbFrom(byte[] buffer, T t) {
//        try {
//            Builder<T> builder = getSuperPojoBuilder(t);
//            builder.parsePbFrom(CodedInputStream.newInstance(buffer));
//            return builder.getData();
//        } catch (Exception var3) {
//            throw new RuntimeException(var3);
//        }
//    }
    public static Cost result2Bean(Result result, String familyName) {
        Cost cost = new Cost();
//        获取rowkey的值
        cost.setId(Bytes.toString(result.getRow()));
        cost.setName(Bytes.toString(result.getValue(Bytes.toBytes(familyName), Bytes.toBytes("name"))));
        cost.setAge(Bytes.toString(result.getValue(Bytes.toBytes(familyName), Bytes.toBytes("age"))));
        cost.setSex(Bytes.toString(result.getValue(Bytes.toBytes(familyName), Bytes.toBytes("sex"))));
        return cost;
    }
 
 
    public static void main(String[] args) throws IOException {
 
//        String[] family = {"userinfo"};
//        createTable("user4", family);
 
    	
//    	String[] column = {"id", "name", "age", "sex"};
//    	String[] value = {"03", "liup", "11", "female"};
//        addData("user", "u03", column, value);
        
//        Cost cost = getResult("user", "u02");
//        System.out.println(cost);
        
 
        
        //scanResult("user");
       	
 
 
        //scanResult("ott_message", "2016070110355700001", "2016070110355700007");
        //scanResult("user", "rowkey1", "rowkey3");
 
 
 
        /*
        getResultByColumn("user1", "rowkey1", "userinfo", "name");
        updateTable("user1", "rowkey1", "userinfo", "name", "zs");
        getResultByColumn("user1", "rowkey1", "userinfo", "name");
        */
 
        /*
        getResultByVersion("user1", "rowkey1", "userinfo", "name");
        */
 
        /*
        deleteColumn("user1","rowkey1","userinfo","email");
        */
 
        /*
        deleteAllColumn("user1","rowkey1");
        */
 
    }
}
