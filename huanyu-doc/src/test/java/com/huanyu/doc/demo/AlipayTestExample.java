package com.huanyu.doc.demo;

import static com.alipay.api.AlipayConstants.CHARSET_UTF8;
import static com.alipay.api.AlipayConstants.FORMAT_JSON;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserAccountUseridBatchqueryRequest;
import com.alipay.api.request.KoubeiMarketingToolPrizesendAuthRequest;
import com.alipay.api.response.AlipayUserAccountUseridBatchqueryResponse;
import com.alipay.api.response.KoubeiMarketingToolPrizesendAuthResponse;

/**
 * @author yangtao
 */
public class AlipayTestExample {

  private static final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";
  private static final String ALIPAY_ISV_APPID = "ALIPAY_ISV_APPID";
  private static final String ALIPAY_ISV_PRIVATE_KEY = "ALIPAY_ISV_PRIVATE_KEY";
  private static final String ALIPAY_ISV_PUBLIC_KEY = "ALIPAY_ISV_PUBLIC_KEY";

  private static final Logger logger = LoggerFactory.getLogger(AlipayTestExample.class);

  private AlipayClient client;

  private static final String[] MOBILES = {"123456789", "987654321"};
  private static final String MOBILE = "156753789";

  @Before
  public void initAlipayClient() {
    client =
      new DefaultAlipayClient(ALIPAY_GATEWAY, ALIPAY_ISV_APPID, ALIPAY_ISV_PRIVATE_KEY, FORMAT_JSON,
        CHARSET_UTF8, ALIPAY_ISV_PUBLIC_KEY);
  }


  @Test
  public void getUserId() {
    queryUserIdByMobile(MOBILE);
  }

  @Test
  public void getUserIds() {
    for (String mobile : MOBILES) {
      queryUserIdByMobile(mobile);
    }
  }

  /**
   * 通过手机号获取支付宝用户ID
   *
   * @param mobile 手机号
   */
  private void queryUserIdByMobile(String mobile) {
    AlipayUserAccountUseridBatchqueryRequest batchQueryRequest =
      new AlipayUserAccountUseridBatchqueryRequest();
    AlipayUserAccountUseridBatchqueryResponse response = null;
    batchQueryRequest.setBizContent("{\"mobile_no\":\"" + mobile + "\"}");
    try {
      response = client.execute(batchQueryRequest);
    } catch (Exception e) {
      logger.error("", e);
    }

    logger.info("通过手机号查询支付宝用户Id查询响应：{}", JSON.toJSONString(response, true));
  }


  @Test
  public void tst() throws Exception {
    KoubeiMarketingToolPrizesendAuthRequest req = new KoubeiMarketingToolPrizesendAuthRequest();
    Map<String, String> bizContent = new HashMap<String, String>();
    bizContent.put("req_id", "1231321321");
    bizContent.put("prize_id", "123132132_1");
    bizContent.put("user_id", "123132132");
    String s = "{\"prize_id\":\"201612080010_1\",\"req_id\":\"20161208155955000000\"," +
      "\"user_id\":\"1237891596375687\"}";
    req.setBizContent(JSON.toJSONString(bizContent));
    req.setBizContent(s);
    logger.info("兑奖授权请求:{}", JSON.toJSONString(req, true));
    KoubeiMarketingToolPrizesendAuthResponse response = client.execute(req);
    logger.info("兑奖授权响应:{}", JSON.toJSONString(response, true));

  }

}
