package cn.com.cgh.util.sql;

import com.google.common.collect.Maps;
import org.apache.hadoop.hive.ql.lib.*;
import org.apache.hadoop.hive.ql.parse.*;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <p> TODO
 * @author Haidar
 * @date 2020/7/29 15:54
 **/
public class SqlUtil {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cn.com.dwsoft");
        String sql = "select partb.month_id,partb.prov_id,partb.prov_name,partb.area_id,partb.area_name,partb.city_code,partb.city_name,partb.serial_number,partb.user_id,partb.cust_name,partb.net_type_code_desc,partb.open_date,partb.use_product_id,partb.use_product_name,partb.user_state,partb.remove_tag_desc,partb.last_stop_time,partb.open_depart_id,partb.open_depart_name,partb.open_staff_id,partb.open_staff_name,partb.hy_product_id,partb.hy_product_name,partb.product_type_name,partb.hy_start_date,partb.hy_end_date,partb.hy_staff_id,partb.hy_staff_name,partb.hy_depart_id,partb.hy_depart_name,partb.hy_depart_kind,partb.assure_type,partb.end_assure_date,partb.fee2,partb.owe_monthx,partb.owe_monthd,partb.mon,partb.sumfee,partb.is_good_no,parta.product_id,parta.product_name from 000ad34d.vra__res_43a2452_tmp0 parta  inner  join 000ad34d.user_base_info partb on parta.user_id = partb.user_id where  partb.month = '202005' and partb.user_state != '开通' and substr( partb.last_stop_time ,1,6) < '202005'";
        String sql2 = "SELECT deposit_code as aaaaa,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003  " +
//                "where deposit_code not in ('1050','1051','1141','1231','1232','1260','1293','1313','1680','17','30','4012') " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1050' and deposit_name='靓号账本' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_s where deposit_code='1051' and deposit_name='宽带包一年-包月费' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1141' and deposit_name='23转4赠款账本-2G升3G流量赠款账本' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1231' and deposit_name='(河南)来显预交' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1232' and deposit_name='(河南)来显预交' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1260' and deposit_name='亲情付账本' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1293' and deposit_name='IP精灵专款专用账本(山东)' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1313' and deposit_name='固话维系预存普通赠款(分月解冻赠款)' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='1680' and deposit_name='沃家云视专款账本' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='17' and deposit_name='AOP缴费接口账本' " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='30' and deposit_name='积分充值账本' and deposit_type_code=0 " +
                "union all " +
                "SELECT deposit_code,deposit_name,deposit_type_code FROM C01012_DEPOSIT_202003_z where deposit_code='4012' and deposit_name='账后调帐现金账本，可退预存款'";
        try {
            parser(sql);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static String unescapeIdentifier(String val) {
        if (val == null) {
            return null;
        }
        if (val.charAt(0) == '`' && val.charAt(val.length() - 1) == '`') {
            val = val.substring(1, val.length() - 1);
        }
        return val;
    }

    public static void parser(String sql) throws ParseException {
        try {
            ParseDriver parseDriver = new ParseDriver();
            ASTNode parse = parseDriver.parse(sql);
            System.out.println(parse.toStringTree());
            final NodeProcessor processor = new NodeProcessor() {
                private int num = 0;
                @Override
                public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx, Object... nodeOutputs) {
                    System.out.println("start:  "+nd.toString());
                    
                    if (num > 0) {
                        return null;
                    }
//                    System.out.println("num " + num);
                    ASTNode ast = (ASTNode) nd;
                    switch (ast.getToken().getType()) {
                        case HiveParser.TOK_QUERY:
                            num++;
                            break;
                        case HiveParser.TOK_TABLE_PARTITION:
                            if (ast.getChildCount() != 2) {
                                String table = BaseSemanticAnalyzer
                                        .getUnescapedName((ASTNode) ast.getChild(0));
                                System.out.println("TOK_TABLE_PARTITION = "+table);
                            }
                            break;

                        case HiveParser.TOK_TAB:// outputTable
                            String tableTab = BaseSemanticAnalyzer
                                    .getUnescapedName((ASTNode) ast.getChild(0));
                            System.out.println("TOK_TAB tableTab:" + tableTab);
                            break;
                        case HiveParser.TOK_TABREF:// inputTable
                            ASTNode tabTree = (ASTNode) ast.getChild(0);
                            String tableName = (tabTree.getChildCount() == 1) ? BaseSemanticAnalyzer
                                    .getUnescapedName((ASTNode) tabTree.getChild(0))
                                    : BaseSemanticAnalyzer
                                    .getUnescapedName((ASTNode) tabTree.getChild(0))
                                    + "." + tabTree.getChild(1);
                            System.out.println("tableName " + tableName);
                            if (ast.getChild(1) != null) {
                                String alia = ast.getChild(1).getText().toLowerCase();
                                System.out.println("alisa " + alia);
                            }
                            break;
                        case HiveParser.TOK_ALLCOLREF:
                            System.out.println("TOK_ALLCOLREF *");
                            break;
                        case HiveParser.TOK_SUBQUERY:
                            if (ast.getChildCount() == 2) {
                                String tableAlias = unescapeIdentifier(ast.getChild(1)
                                        .getText());
                                System.out.println("TOK_SUBQUERY " + tableAlias);
                            }
                            break;

                        case HiveParser.TOK_SELEXPR:
                            ArrayList<Node> children = ast.getChildren();
                            for (int i = 0; i < children.size(); i++) {
                                if (i == 0){
                                    System.out.println(" column " + ((ASTNode)children.get(i)).getChild(0).getText()+"   ");
                                }else {
                                    System.out.println(" alisa " + ((ASTNode)children.get(i)).getText());
                                }
                            }
                            break;
                        case HiveParser.DOT:
                            if (ast.getType() == HiveParser.DOT) {
                                if (ast.getChildCount() == 2) {
                                    if (ast.getChild(0).getType() == HiveParser.TOK_TABLE_OR_COL
                                            && ast.getChild(0).getChildCount() == 1
                                            && ast.getChild(1).getType() == HiveParser.Identifier) {
                                        String alia = BaseSemanticAnalyzer
                                                .unescapeIdentifier(ast.getChild(0)
                                                        .getChild(0).getText()
                                                        .toLowerCase());
                                        String column = BaseSemanticAnalyzer
                                                .unescapeIdentifier(ast.getChild(1)
                                                        .getText().toLowerCase());
                                        System.out.println("DOT alia "+ alia);
                                        System.out.println("DOT column "+ column);
                                    }
                                }
                            }
                            break;
                        case HiveParser.TOK_ALTERTABLE_ADDPARTS:
                        case HiveParser.TOK_ALTERTABLE_RENAME:
                        case HiveParser.TOK_ALTERTABLE_ADDCOLS:
                            ASTNode alterTableName = (ASTNode) ast.getChild(0);
                            System.out.println("TOK_ALTERTABLE_ADDCOLS alterTableName " + alterTableName);
                            break;
                        default:

                    }
                    return null;
                }
            };

            DefaultGraphWalker walker = new DefaultGraphWalker(
                    new DefaultRuleDispatcher(processor,
                            Maps.<Rule, NodeProcessor>newLinkedHashMap(),
                            null)
            );

            walker.startWalking(parse.getChildren(), null);

        } catch (ParseException e) {
            throw e;
        } catch (SemanticException e) {
            e.printStackTrace();
        }

    }


}
