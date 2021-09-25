package cn.com.cgh.encrypt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;

import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnalyMapUDTF extends GenericUDTF {
    private PrimitiveObjectInspector stringOI = null;

    @Override
    public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
        if (args.length != 1) {  //判断自定义函数传入参数是否为1
            throw new UDFArgumentException("NameParserGenericUDTF() takes exactly one argument");
        }

        if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                && ((PrimitiveObjectInspector) args[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            //判断参数是否是PRIMITIVE,LIST,MAP,STRUCT,UNION类型;
            throw new UDFArgumentException("NameParserGenericUDTF() takes a string as a parameter");
        }

        // input  {"ownerid":2915816952,"objtype":6,"objid":856188930314735616,"value":"{\"text\":\"晚安\"}","async":false,"strid":"856188930314735616"}
        stringOI = (PrimitiveObjectInspector) args[0];

        // output
        List<String> fieldNames = new ArrayList<String>(4); //生成表的字段名数组
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>(4); //生成表的字段对象监控器（object inspector）数组，即生成表的行对象每个字段的类型
        fieldNames.add("ownerid");
        fieldNames.add("objid");
        fieldNames.add("strid");
        fieldNames.add("text");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);  //类型是PRIMITIVE
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);  //返回对象监控器
    }

    public ArrayList<Object[]> processInputRecord(String json){
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        // ignoring null or empty input
        if (json == null || json.isEmpty()) {
            return list;
        }
        List<Map> jsonArray = JSONArray.parseArray(json,Map.class);

        for(int i=0;i<jsonArray.size();i++) {

            Object ownerid = jsonArray.get(i).get("ownerid");
            Object objid = jsonArray.get(i).get("objid");
            Object strid = jsonArray.get(i).get("strid");
            Object text = JSONObject.parseObject(jsonArray.get(i).get("value").toString()).get("text");
            list.add(new Object[] { ownerid, objid, strid, text });

        }

        return list;
    }

    @Override
    public void process(Object[] record) throws HiveException {
        final String name = stringOI.getPrimitiveJavaObject(record[0]).toString();



        ArrayList<Object[]> results = processInputRecord(name);

        Iterator<Object[]> it = results.iterator();

        while (it.hasNext()){
            Object[] r = it.next();
            forward(r); //转发行对象。注意，循环一次意味着返回一行
        }
    }

    @Override
    public void close() throws HiveException {
        // do nothing
    }
}
