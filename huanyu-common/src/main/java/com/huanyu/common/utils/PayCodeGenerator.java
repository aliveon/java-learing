package com.huanyu.common.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


/**
 * @author yangtao
 */
public class PayCodeGenerator {

  private static final String FORMATER = "yyyyMMddHHmmss";

  private static Map<String, Integer> CUR_FLOW_COUNT_MAP = new ConcurrentHashMap<String, Integer>();
  private static final Logger logger = LoggerFactory.getLogger(PayCodeGenerator.class);

  private int originalLength = 23;// 支付码总长度
  private int checkPartLength = 3;// 校验位长度

  private byte[] patch;
  private MessageDigest md;
  private long mask;

  public PayCodeGenerator() {
  }

  public PayCodeGenerator(int originalLength, int checkPartLength) throws Exception {
    this.originalLength = originalLength;
    this.checkPartLength = checkPartLength;

    patch = "环宇科技集团".getBytes("UTF-8");
    md = MessageDigest.getInstance("MD5");
    long maxd = (long) Math.pow(10, this.checkPartLength) - 1;
    int blen = Long.toBinaryString(maxd).length();
    mask = (long) Math.pow(2, blen) - 1;
  }

  public String next(String prefix) throws Exception {
    String code = generate(prefix,
      originalLength - checkPartLength - String.valueOf(prefix + FORMATER).length());
    byte[] plain = createPlain(code.substring(prefix.length()));// 待加密明文
    byte[] encode = encode(plain);// 校验码

    return code + new String(encode);
  }

  public boolean validate(String code, String prefix, int checkPartLength) throws Exception {
    if (StringUtils.isBlank(code))
      return false;

    String subCode = code.substring(prefix.length(), code.length() - checkPartLength);
    byte[] plain = createPlain(subCode);// 待加密明文
    byte[] encode = encode(plain);// 校验码
    if (code.equals(prefix + subCode + new String(encode)) == false)
      return false;

    return true;
  }

  private byte[] createPlain(String value) throws Exception {
    if (value.length() > originalLength) {
      throw new Exception("value too large: " + value);
    }
    byte[] vb = value.getBytes();
    byte[] part1 = new byte[originalLength];
    Arrays.fill(part1, (byte) '0');
    System.arraycopy(vb, 0, part1, originalLength - vb.length, vb.length);
    return part1;
  }

  private byte[] encode(byte[] part1) throws Exception {
    byte[] text = createText(part1);
    byte[] digest = md.digest(text);
    byte[] part2 = createCheckPart(digest);
    return part2;
  }

  private byte[] createText(byte[] input) {
    byte[] text = new byte[input.length + patch.length];
    System.arraycopy(input, 0, text, 0, input.length);
    System.arraycopy(patch, 0, text, input.length, patch.length);
    return text;
  }

  private byte[] createCheckPart(byte[] digest) throws Exception {
    long v = 0;
    for (int i = 0; i < 8; ++i) {
      v = v << 8 | ((long) (digest[i]) & 0x0ff);
    }
    v = v & mask;
    byte[] vb = Long.toString(v).getBytes();
    byte[] part2 = new byte[checkPartLength];
    if (vb.length < checkPartLength) {
      Arrays.fill(part2, (byte) '0');
      System.arraycopy(vb, 0, part2, checkPartLength - vb.length, vb.length);
    } else {
      System.arraycopy(vb, 0, part2, 0, checkPartLength);
    }
    return part2;
  }


  private synchronized String generate(String prefix, int flowLength) {
    double waitTimes = 128;// 约束执行的最大延迟不超过 255 ms
    while (true) {
      try {
        return doGenerate(prefix, flowLength);
      } catch (Exception e) {
        try {
          Thread.sleep((int) waitTimes);
          waitTimes = Math.log(waitTimes);
          return doGenerate(prefix, flowLength);
        } catch (Exception ex) {
          if (waitTimes == 0) {
            logger.error("", ex);
            throw new RuntimeException("生成流水出错。", ex);
          }
        }
      }
    }
  }

  private synchronized String doGenerate(String prefix, int flowLength) throws Exception {
    if (prefix == null)
      prefix = StringUtils.EMPTY;
    if (flowLength <= 0)
      flowLength = 1;

    prefix += new SimpleDateFormat(FORMATER).format(new Date());
    Integer startIndex = CUR_FLOW_COUNT_MAP.get(prefix);
    String startCode = "";
    if (startIndex != null) {
      startCode = StringUtils.leftPad(String.valueOf(startIndex), flowLength, "0");
      CUR_FLOW_COUNT_MAP.remove(prefix);// 先删除再修改
    } else {
      startCode = StringUtils.rightPad("0", flowLength, "0");
      startIndex = 0;
      CUR_FLOW_COUNT_MAP.clear();// 清理map
    }
    CUR_FLOW_COUNT_MAP.put(prefix, ++startIndex);// 修改值

    String nextCode = prefix + FLOW_MAP.get(startCode).getAndAdd(1L);

    return nextCode;
  }


  private static LoadingCache<String, AtomicLong> FLOW_MAP =
    CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(60, TimeUnit.SECONDS)
      .build(new CacheLoader<String, AtomicLong>() {
        public AtomicLong load(String key) throws Exception {
          return new AtomicLong(0);
        }
      });


  public static void main(String[] args) {
    try {
      PayCodeGenerator generator = new PayCodeGenerator(18, 1);
      for (int i = 0; i < 100000; i++) {
        String code = generator.next("98");
        logger.info("code:{},validate:{}", code, generator.validate(code, "98", 1));
      }
    } catch (Exception e) {
      logger.error("", e);
    }
  }

}
