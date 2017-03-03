package com.huanyu.doc.demo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import com.huanyu.doc.interview.array.StoreQRCode;


/**
 * 查询门店二维码
 *
 * @author yangtao
 */
@XmlRootElement
public class QueryStoreQRCodeResponse extends ActionResult {

  private List<StoreQRCode> qrCodes = new ArrayList<StoreQRCode>();

  public static QueryStoreQRCodeResponse fail(String message) {
    QueryStoreQRCodeResponse response = new QueryStoreQRCodeResponse();
    response.setSuccess(false);
    response.setMessage(message);
    return response;
  }

  public QueryStoreQRCodeResponse() {
  }

  public QueryStoreQRCodeResponse(List<StoreQRCode> qrCodes) {
    this.qrCodes = qrCodes;
  }

  public List<StoreQRCode> getQrCodes() {
    return qrCodes;
  }

  public void setQrCodes(List<StoreQRCode> qrCodes) {
    this.qrCodes = qrCodes;
  }

}
