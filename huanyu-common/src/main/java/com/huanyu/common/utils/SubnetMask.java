package com.huanyu.common.utils;

/**
 * @author yangtao
 */
public class SubnetMask {
  public static int toSubnetMask(int flag) {
    if (flag >= 1 && flag <= 32) {
      return 0x80000000 >> flag - 1;
    } else {
      return 0;
    }
  }

  public static int toNetworkFlag(int mask) {
    // @Thx http://www.oschina.net/code/snippet_264635_21446#cmt_58387_5970
    int count = 1;
    for (; mask > 0x80000000; count++, mask &= mask - 1)
      ;
    return count;
  }
}
