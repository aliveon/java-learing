package com.huanyu.doc.demo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.huanyu.common.utils.GenerateUUIDFactory;
import com.huanyu.doc.demo.pom.comm.BasketElement;
import com.huanyu.doc.demo.pom.comm.CaseElement;
import com.huanyu.doc.demo.pom.comm.ConditionElement;
import com.huanyu.doc.demo.pom.comm.DateRangeElement;
import com.huanyu.doc.demo.pom.comm.EntityElement;
import com.huanyu.doc.demo.pom.comm.ExcludedElement;
import com.huanyu.doc.demo.pom.comm.ExecutionOptionElement;
import com.huanyu.doc.demo.pom.comm.ExecutionSetElement;
import com.huanyu.doc.demo.pom.comm.OperandElement;
import com.huanyu.doc.demo.pom.comm.ProductElement;
import com.huanyu.doc.demo.pom.comm.RuleRoot;
import com.huanyu.doc.demo.pom.comm.StepElement;
import com.huanyu.doc.demo.pom.comm.XmlElement;
import com.huanyu.doc.demo.pom.coupon.ActivityElement;
import com.huanyu.doc.demo.pom.coupon.UsingExecutionElement;
import com.huanyu.doc.demo.pom.utils.XmlUtil;

/**
 * @author yangtao
 */
public class GeneratePomTest {


  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * xml文件输出路径
   */
  private static final String OUTPUT_FILE_PATH = "/temp/pomFiles/%s/%s.xml";

  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

  private static final String[] SPECIAL_REGX_CHARS =
    {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};

  /**
   * 综述：
   * <p>
   * 　　　下发规则分为：全场和可叠加两种分类，四种情况处理；
   * <p>
   * 　　　　主要区别在于：
   * 　　　　　　　　　　1.非全场比全场要多加 商品规则 product
   * 　　　　　　　　　　2.可叠加使用和不可叠加使用的级别条件运算符不同，
   * 　　　　　　　　　　　可叠加使用的运算符为{@link StepElement.EQUAL_OPERATOR},
   * 　　　　　　　　　　　不可叠加使用的运算符为{@link StepElement.RANGE_OPERATOR}
   */


  //  <!--情况1：全场商品满金额不可叠加使用-->
  @Test
  public void situation1() throws Exception {
    RuleRoot rule = new RuleRoot();
    generateRuleRoot(rule, true, StepElement.RANGE_OPERATOR);

    loggerAndOut2File(rule, true, true, "situation1");
  }


  //  <!--情况2：全场商品满金额可叠加使用-->
  //  情况2 和 情况1  的不同之处在于 StepElement.operator= {@link StepElement.EQUAL_OPERATOR}
  @Test
  public void situation2() throws Exception {
    RuleRoot rule = new RuleRoot();
    generateRuleRoot(rule, true, StepElement.EQUAL_OPERATOR);

    loggerAndOut2File(rule, true, true, "situation2");
  }


  //  <!--情况3：部分商品满金额不可叠加使用-->
  //  情况3 和 情况1  相比，多了 product 规则
  @Test
  public void situation3() throws Exception {
    RuleRoot rule = new RuleRoot();
    generateRuleRoot(rule, false, StepElement.RANGE_OPERATOR);

    loggerAndOut2File(rule, true, true, "situation3");
  }


  //    <!--情况4：部分商品满金额可叠加使用-->
  //  情况4 和 情况2  相比，多了 product 规则
  @Test
  public void situation4() throws Exception {
    RuleRoot rule = new RuleRoot();
    generateRuleRoot(rule, false, StepElement.EQUAL_OPERATOR);

    loggerAndOut2File(rule, true, true, "situation4");
  }


  @Test
  public void test() throws Exception {
    String pom =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><pom class=\"com.huanyu.web.doc.PomDocument\"><element class=\"com.huanyu.web.doc.condition.general.DateRangeCondition\" start=\"2016-06-04 00:00:00.001\" finish=\"2016-09-04 23:59:59.100\"><element class=\"com.huanyu.web.doc.condition.product.BasketCondition\" overall=\"false\"><product mode=\"range\"><condition field=\"product\" operator=\"equals\"><operand><entity uuid=\"1611231479890152639d1e83a\" type=\"type0001\" code=\"code0001\" name=\"name0001\" qpc=\"3.0\" qpcStr=\"1*3\"/><entity uuid=\"1611231479890152878b351dc\" type=\"type0002\" code=\"code0002\" name=\"name0002\"/></operand></condition></product><element class=\"com.huanyu.web.doc.condition.member.voucher.VoucherActivityCondition\" name=\"100元丸子券\" isMgr=\"false\" logo=\"1.jpg\" no=\"00157\" parValue=\"300.12\" remark=\"remark\" restriction=\"\" form=\"amount\" sort=\"voucher\" uuid=\"\"><element class=\"com.huanyu.web.doc.condition.step.StepCondition\" valueType=\"amount\" operator=\"range\" priceType=\"actualPrice\"><case value=\"100.00\"><element class=\"com.huanyu.web.doc.PomExecutionSet\" inputMode=\"auto\"><element class=\"com.huanyu.web.doc.PomExecutionOption\" evaluation=\"70\"><element class=\"com.huanyu.web.doc.execution.member.voucher.VoucherUsingExecution\" count=\"1\" function=\"E#Member#VoucherUsing\"/></element></element></case></element></element></element></element></pom>";

    String regEx = "<entity uuid=\"([\\s\\S]*?)\" type=\"([\\s\\S]*?)\" code=\"([\\s\\S]*?)\"";
    Pattern pat = Pattern.compile(regEx);
    Matcher mat = pat.matcher(pom);
    int count = 1;
    while (mat.find()) {
      logger.info("字符串匹配次数：{}", count);
      for (int i = 1; i <= mat.groupCount(); i++) {
        logger.info("匹配结果：{}", mat.group(i));
      }

      count++;
    }
  }

  /**
   * pms 计算 调整 pom 中的 entity 的 uuid 值为 barcode的值,而下发 jpos的 规则为 Gid的值
   */
  @Test
  public void testPMSCalculatePom() {
    // 1. 有限制 entity 的规则
    String pom =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><pom class=\"com.huanyu.web.doc.PomDocument\"><element class=\"com.huanyu.web.doc.condition.general.DateRangeCondition\" start=\"2016-06-04 00:00:00.001\" finish=\"2016-09-04 23:59:59.100\"><element class=\"com.huanyu.web.doc.condition.product.BasketCondition\" overall=\"false\"><product mode=\"range\"><condition field=\"product\" operator=\"equals\"><operand><entity uuid=\"1611231479890152639d1e83a\" type=\"type0001\" code=\"code0001\" name=\"name0001\" qpc=\"3.0\" qpcStr=\"1*3\"/><entity uuid=\"1611231479890152878b351dc\" type=\"type0002\" code=\"code0002\" name=\"name0002\"/></operand></condition></product><element class=\"com.huanyu.web.doc.condition.member.voucher.VoucherActivityCondition\" name=\"100元丸子券\" isMgr=\"false\" logo=\"1.jpg\" no=\"00157\" parValue=\"300.12\" remark=\"remark\" restriction=\"\" form=\"amount\" sort=\"voucher\" uuid=\"\"><element class=\"com.huanyu.web.doc.condition.step.StepCondition\" valueType=\"amount\" operator=\"range\" priceType=\"actualPrice\"><case value=\"100.00\"><element class=\"com.huanyu.web.doc.PomExecutionSet\" inputMode=\"auto\"><element class=\"com.huanyu.web.doc.PomExecutionOption\" evaluation=\"70\"><element class=\"com.huanyu.web.doc.execution.member.voucher.VoucherUsingExecution\" count=\"1\" function=\"E#Member#VoucherUsing\"/></element></element></case></element></element></element></element></pom>";
    String temp = rebuild(pom);
    Assert.isTrue(pom.equals(temp) == false);


    // 2. 无限制 entity 的规则
    String pom2 =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><pom class=\"com.huanyu.web.doc.PomDocument\"><element class=\"com.huanyu.web.doc.condition.general.DateRangeCondition\" start=\"2016-06-04 00:00:00.001\" finish=\"2016-09-04 23:59:59.100\"><element class=\"com.huanyu.web.doc.condition.product.BasketCondition\" overall=\"true\"><element class=\"com.huanyu.web.doc.condition.member.voucher.VoucherActivityCondition\" name=\"100元丸子券\" isMgr=\"false\" logo=\"1.jpg\" no=\"00157\" parValue=\"300.12\" remark=\"remark\" restriction=\"\" form=\"amount\" sort=\"voucher\" uuid=\"\"><element class=\"com.huanyu.web.doc.condition.step.StepCondition\" valueType=\"amount\" operator=\"range\" priceType=\"actualPrice\"><case value=\"100.00\"><element class=\"com.huanyu.web.doc.PomExecutionSet\" inputMode=\"auto\"><element class=\"com.huanyu.web.doc.PomExecutionOption\" evaluation=\"70\"><element class=\"com.huanyu.web.doc.execution.member.voucher.VoucherUsingExecution\" count=\"1\" function=\"E#Member#VoucherUsing\"/></element></element></case></element></element></element></element></pom>";
    String temp2 = rebuild(pom2);
    Assert.isTrue(pom2.equals(temp2));
  }

  /**
   * 替换含有特殊字符的字符串测试用例
   */
  @Test
  public void testSpecialExample() {
    String pom =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><pom class=\"com.huanyu.web.doc.PomDocument\"><element class=\"com.huanyu.web.doc.condition.general.DateRangeCondition\" start=\"2016-12-12 00:00:00.000\" finish=\"2016-12-14 00:00:00.000\"><element class=\"com.huanyu.web.doc.condition.product.BasketCondition\" overall=\"false\"><product mode=\"range\"><condition field=\"product\" operator=\"equals\"><operand><entity uuid=\"3006567\" type=\"product\" code=\"6923644266691\" name=\"杭州湖州—蒙牛奶特朱古力口味243ml/盒\" qpc=\"12.00\" qpcStr=\"1*12\"/><entity uuid=\"3021596\" type=\"product\" code=\"6923644267148\" name=\"杭州湖州—蒙牛奶特香草口味243ml/盒\" qpc=\"12.00\" qpcStr=\"1*12\"/><entity uuid=\"3046237\" type=\"product\" code=\"6923644277659\" name=\"杭州湖州—蒙牛奶特香蕉牛奶243ml/盒\" qpc=\"12.00\" qpcStr=\"1*12\"/></operand></condition></product><element class=\"com.huanyu.web.doc.condition.member.voucher.VoucherActivityCondition\" name=\"测试券\" isMgr=\"false\" logo=\"\" no=\"201612120008\" parValue=\"10.00\" remark=\"测试券\" restriction=\"\" form=\"amount\" sort=\"voucher\" uuid=\"4028809058e270fd0158f1b8bacc7913\"><element class=\"com.huanyu.web.doc.condition.step.StepCondition\" valueType=\"amount\" operator=\"range\" priceType=\"actualPrice\"><case value=\"0.10\"><element class=\"com.huanyu.web.doc.PomExecutionSet\" inputMode=\"auto\"><element class=\"com.huanyu.web.doc.PomExecutionOption\" evaluation=\"70\"><element class=\"com.huanyu.web.doc.execution.member.voucher.VoucherUsingExecution\" count=\"1\" function=\"E#Member#VoucherUsing\"/></element></element></case></element></element></element></element></pom>";
    String temp = rebuild(pom);
    Assert.isTrue(pom.equals(temp) == false);

    String result =
      "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><pom class=\"com.huanyu.web.doc.PomDocument\"><element class=\"com.huanyu.web.doc.condition.general.DateRangeCondition\" start=\"2016-12-12 00:00:00.000\" finish=\"2016-12-14 00:00:00.000\"><element class=\"com.huanyu.web.doc.condition.product.BasketCondition\" overall=\"false\"><product mode=\"range\"><condition field=\"product\" operator=\"equals\"><operand><entity uuid=\"6923644266691\" type=\"product\" code=\"6923644266691\" name=\"杭州湖州—蒙牛奶特朱古力口味243ml/盒\" qpc=\"12.00\" qpcStr=\"1*12\"/><entity uuid=\"6923644267148\" type=\"product\" code=\"6923644267148\" name=\"杭州湖州—蒙牛奶特香草口味243ml/盒\" qpc=\"12.00\" qpcStr=\"1*12\"/><entity uuid=\"6923644277659\" type=\"product\" code=\"6923644277659\" name=\"杭州湖州—蒙牛奶特香蕉牛奶243ml/盒\" qpc=\"12.00\" qpcStr=\"1*12\"/></operand></condition></product><element class=\"com.huanyu.web.doc.condition.member.voucher.VoucherActivityCondition\" name=\"测试券\" isMgr=\"false\" logo=\"\" no=\"201612120008\" parValue=\"10.00\" remark=\"测试券\" restriction=\"\" form=\"amount\" sort=\"voucher\" uuid=\"4028809058e270fd0158f1b8bacc7913\"><element class=\"com.huanyu.web.doc.condition.step.StepCondition\" valueType=\"amount\" operator=\"range\" priceType=\"actualPrice\"><case value=\"0.10\"><element class=\"com.huanyu.web.doc.PomExecutionSet\" inputMode=\"auto\"><element class=\"com.huanyu.web.doc.PomExecutionOption\" evaluation=\"70\"><element class=\"com.huanyu.web.doc.execution.member.voucher.VoucherUsingExecution\" count=\"1\" function=\"E#Member#VoucherUsing\"/></element></element></case></element></element></element></element></pom>";
    Assert.isTrue(result.equals(temp));


    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><pom " +
      "class=\"com.huanyu.web.doc.PomDocument\"><element class=\"com.huanyu.web.doc.condition" +
      ".general.DateRangeCondition\" start=\"2017-01-11 00:00:00.000\" finish=\"2017-01-30 00:00:00.000\"><element class=\"com.huanyu.web.doc.condition.product.BasketCondition\" overall=\"false\"><product mode=\"range\"><condition field=\"product\" operator=\"equals\"><operand><entity uuid=\"3012145\" type=\"product\" code=\"6920202866737\" name=\"强化型红牛250ml\" qpc=\"1.00\" qpcStr=\"1*1\"/><entity uuid=\"3003650\" type=\"product\" code=\"6920202888883\" name=\"红牛250ml\" qpc=\"1.00\" qpcStr=\"1*1\"/><entity uuid=\"3015625\" type=\"product\" code=\"6925303754884\" name=\"统一小茗同学冰橘绿茶480ml\" qpc=\"1.00\" qpcStr=\"1*1\"/><entity uuid=\"3015626\" type=\"product\" code=\"6925303754952\" name=\"统一小茗同学青柠红茶480ml\" qpc=\"1.00\" qpcStr=\"1*1\"/><entity uuid=\"3015627\" type=\"product\" code=\"6925303755461\" name=\"统一小茗同学溜溜哒茶480ml\" qpc=\"1.00\" qpcStr=\"1*1\"/></operand></condition></product><element class=\"com.huanyu.web.doc.condition.member.voucher.VoucherActivityCondition\" name=\"商品满额发圈\" isMgr=\"false\" logo=\"\" no=\"201701110004\" parValue=\"1.00\" remark=\"会员专属商品优惠券\" restriction=\"\" form=\"amount\" sort=\"voucher\" uuid=\"4028809059403f2e01598c573c040129\"><element class=\"com.huanyu.web.doc.condition.step.StepCondition\" valueType=\"amount\" operator=\"range\" priceType=\"actualPrice\"><case value=\"5.00\"><element class=\"com.huanyu.web.doc.PomExecutionSet\" inputMode=\"auto\"><element class=\"com.huanyu.web.doc.PomExecutionOption\" evaluation=\"70\"><element class=\"com.huanyu.web.doc.execution.member.voucher.VoucherUsingExecution\" count=\"1\" function=\"E#Member#VoucherUsing\"/></element></element></case></element></element></element></element></pom>";
    String res = rebuild(xml);


  }


  private String rebuild(String pom) {
    String regEx = "<entity uuid=\"([\\s\\S]*?)\" type=\"([\\s\\S]*?)\" code=\"([\\s\\S]*?)\"";
    Pattern pat = Pattern.compile(regEx);
    Matcher mat = pat.matcher(pom);
    int step = 0;
    while (mat.find()) {
      //      String temp = pom.substring(mat.start() + step, mat.end());
      String temp = pom.substring(mat.start() + step, mat.end() + step);  // 实现相对位移
      logger.info("temp:{},replace:{}", temp, mat.group(3));
      String newStr = temp.substring(0, temp.indexOf("=\"") + 2) + mat.group(3) +
        temp.substring(temp.indexOf("\" type=\""));
      pom = pom.replaceAll(escapeExprSpecialWord(temp), newStr);
      //      logger.info("after temp str:{}  ->  Pom:{}", temp, pom);
      //      step = newStr.length() - temp.length();
      step += newStr.length() - temp.length(); // 做累加计算目的：累计位移量,即新位置相对原位置的累计位移偏量
    }

    logger.info("最终替换结果：{}", pom);
    return pom;
  }

  /**
   * 转义正则特殊字符 （$()*+.[]?\^{},|）
   *
   * @param keyword
   * @return
   */
  public static String escapeExprSpecialWord(String keyword) {
    if (StringUtils.isNotBlank(keyword)) {
      for (String key : SPECIAL_REGX_CHARS) {
        if (keyword.contains(key)) {
          keyword = keyword.replace(key, "\\" + key);
        }
      }
    }
    return keyword;
  }

  /**
   * 生成下发规则
   *
   * @param rule         下发规则
   * @param isOverall    是否全场
   * @param stepOperator 级别条件运算符
   * @throws Exception
   */
  private void generateRuleRoot(RuleRoot rule, boolean isOverall, String stepOperator)
    throws Exception {
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    DateRangeElement dateRangeElement = new DateRangeElement();
    dateRangeElement.setStart(format.parse("2016-6-4 00:00:00.001"));
    dateRangeElement.setFinish(format.parse("2016-9-4 23:59:59.100"));
    List<XmlElement> dateRangeElementList = new ArrayList<XmlElement>();

    BasketElement basketElement = new BasketElement();
    basketElement.setOverall(isOverall);
    List<XmlElement> basketElementList = new ArrayList<XmlElement>();

    ActivityElement couponActivityElement = new ActivityElement();
    generateActivityRule(couponActivityElement, stepOperator);
    basketElementList.add(couponActivityElement);

    if (!isOverall) {
      ProductElement productElement = new ProductElement();
      generateProductRule(productElement);
      List<ProductElement> productsRule = new ArrayList<ProductElement>();
      productsRule.add(productElement);
      basketElement.setProducts(productsRule);
    }
    basketElement.setChildrends(basketElementList);

    dateRangeElementList.add(basketElement);
    dateRangeElement.setChildrends(dateRangeElementList);

    List<XmlElement> list = new ArrayList<XmlElement>();
    list.add(dateRangeElement);
    rule.setChildrends(list);
  }

  /**
   * 生成券活动规则
   *
   * @param couponActivityElement 券活动规则
   * @param stepConditionOperator 级别条件运算符
   */
  private void generateActivityRule(ActivityElement couponActivityElement,
    String stepConditionOperator) {
    couponActivityElement.setLogo("1.jpg");
    couponActivityElement.setMgr(false);
    couponActivityElement.setNo("00157");
    couponActivityElement.setName("100元丸子券");
    couponActivityElement.setParValue(new BigDecimal(300.12).setScale(2, BigDecimal.ROUND_HALF_UP));
    couponActivityElement.setRemark("remark");
    couponActivityElement.setRestriction("");
    couponActivityElement.setUuid("");

    List<XmlElement> couponActivityElementList = new ArrayList<XmlElement>();
    StepElement stepElement = new StepElement();
    stepElement.setValueType(StepElement.AMOUNT_VALUETYPE);
    stepElement.setOperator(stepConditionOperator);


    List<CaseElement> stepElementList = new ArrayList<CaseElement>();
    CaseElement caseElement = new CaseElement();
    caseElement.setValue(new BigDecimal(100.00).setScale(2, BigDecimal.ROUND_HALF_UP));


    ExecutionSetElement executionSetElement = new ExecutionSetElement();
    executionSetElement.setInputMode(ExecutionSetElement.AUTO_MODE);

    ExecutionOptionElement executionOptionElement = new ExecutionOptionElement();
    UsingExecutionElement usingExecutionElement = new UsingExecutionElement();
    usingExecutionElement.setCount(1);
    usingExecutionElement.setFunction("E#Member#VoucherUsing");

    List<XmlElement> executionOptionElementList = new ArrayList<XmlElement>();

    executionOptionElementList.add(usingExecutionElement);
    executionOptionElement.setChildrends(executionOptionElementList);
    List<XmlElement> executionSetElementList = new ArrayList<XmlElement>();

    executionSetElementList.add(executionOptionElement);
    executionSetElement.setChildrends(executionSetElementList);

    caseElement.setElement(executionSetElement);

    stepElementList.add(caseElement);
    stepElement.setCasz(stepElementList);

    couponActivityElementList.add(stepElement);
    couponActivityElement.setChildrends(couponActivityElementList);
  }

  /**
   * 生成部分商品条件规则
   *
   * @param productElement
   */
  private void generateProductRule(ProductElement productElement) {
    productElement.setMode(ProductElement.RANGE_MODE);
    List<ConditionElement> conditions = new ArrayList<ConditionElement>();
    ConditionElement productCondition = new ConditionElement();
    productCondition.setOperator(ConditionElement.equals_operator);
    productCondition.setField(ConditionElement.PRODUCT_FIELD);
    OperandElement productOperand = new OperandElement();
    List<EntityElement> productList = new ArrayList<EntityElement>();
    EntityElement entityElement1 = new EntityElement();
    entityElement1.setQpc(BigDecimal.valueOf(3.00));
    entityElement1.setQpcStr("1*3");
    entityElement1.setCode("code0001");
    entityElement1.setName("name0001");
    entityElement1.setType("type0001");
    entityElement1.setUuid(GenerateUUIDFactory.getInstance().generateUUID());

    EntityElement entityElement2 = new EntityElement();
    entityElement2.setCode("code0002");
    entityElement2.setName("name0002");
    entityElement2.setType("type0002");
    entityElement2.setUuid(GenerateUUIDFactory.getInstance().generateUUID());

    productList.add(entityElement1);
    productList.add(entityElement2);

    productOperand.setEntitys(productList);
    productCondition.setOperand(productOperand);

    conditions.add(productCondition);
    productElement.setConditions(conditions);
  }

  /**
   * logger打印下发规则
   *
   * @param rule 下发规则
   * @throws Exception
   */
  private void loggerPomStruct(RuleRoot rule) throws Exception {
    loggerAndOut2File(rule, true, false, null);
  }

  /**
   * 下发规则打印并输出到文件
   *
   * @param rule       下发规则
   * @param isLogger   是否输出日志
   * @param isOut2File 是否输出文件
   * @param fileName   输出文件名
   * @throws Exception
   */
  private void loggerAndOut2File(RuleRoot rule, boolean isLogger, boolean isOut2File,
    String fileName) throws Exception {
    if (isLogger)
      logger.info(XmlUtil.Obj2Xml(rule,
        new Class[]{XmlElement.class, RuleRoot.class, DateRangeElement.class, ActivityElement.class,
          BasketElement.class, ExecutionSetElement.class, ExecutionOptionElement.class,
          UsingExecutionElement.class, ExcludedElement.class, StepElement.class, CaseElement.class,
          EntityElement.class, ProductElement.class, ConditionElement.class,
          OperandElement.class}));

    if (isOut2File)
      XmlUtil.toXMLFile(rule,
        new Class[]{XmlElement.class, RuleRoot.class, DateRangeElement.class, ActivityElement.class,
          BasketElement.class, ExecutionSetElement.class, ExecutionOptionElement.class,
          UsingExecutionElement.class, ExcludedElement.class, StepElement.class, CaseElement.class,
          EntityElement.class, ProductElement.class, ConditionElement.class, OperandElement.class},
        String
          .format(OUTPUT_FILE_PATH, new SimpleDateFormat("yyyyMMdd").format(new Date()), fileName));
  }


}
