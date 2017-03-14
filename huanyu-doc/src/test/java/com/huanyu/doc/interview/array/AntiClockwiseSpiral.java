package com.huanyu.doc.interview.array;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 逆时针螺旋二维数组输出
 *
 * @author yangtao
 */
public class AntiClockwiseSpiral {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private int generateRandom(int minRange, int maxRange) {
    return (int) (minRange + Math.random() * maxRange);
  }

  @Test
  public void exampleArray() {
    System.out.println("====================================\n");
    System.out.println("数字矩阵,请输入大于 0 的整数,进行查看生成的数字矩阵");
    System.out.println("例如：下述演示的 6 行矩阵");
    System.out.println("====================================\n");
    int count = 6;
    do {
      printArray(initMatrix(count));
      System.out.print("请输入：");
      count = generateRandom(-1, 20);
      System.out.println(count);
    } while (count > 0);

    System.out.println("谢谢！再见！");
  }

  // 按数字递增顺序初始化二维数组
  private String[][] initMatrix(int count) {
    String[][] matrix = new String[count][count];// 初始化二维数组大小
    int total = Double.valueOf(Math.pow(count, 2)).intValue();// 计算矩阵总数
    int length = String.valueOf(total).length();
    int start = 1, // 数字起始位
      horizontal = 0,// 数组行
      vertical = 0, // 数组列
      loop = 0, // 循环次数，逆时针方向(数组初始化绕行一周；如 3 行矩阵：m[0][0]..->m[2][0]..->m[2][2]..->m[0][2]..->m[0][1])
      flag = 0; // 单边循环结束标识(逆时针方向绕行时经过矩阵每一条边的循环结束标识)

    while (start < total) {
      flag = count - loop;// 避免在循环条件中计算表达式；java编码最佳实践
      while (horizontal < flag) {// 逆时针方向第一条边
        matrix[horizontal][vertical] = StringUtils.leftPad(String.valueOf(start++), length, "0");
        horizontal++;
      }

      horizontal--;
      vertical++;
      while (vertical < flag) {// 逆时针方向第二条边
        matrix[horizontal][vertical] = StringUtils.leftPad(String.valueOf(start++), length, "0");
        vertical++;
      }

      vertical--;
      while (horizontal > loop) {// 逆时针方向第三条边
        horizontal--;
        matrix[horizontal][vertical] = StringUtils.leftPad(String.valueOf(start++), length, "0");
      }

      flag = loop + 1;
      while (vertical > flag) {// 逆时针方向第四条边
        vertical--;
        matrix[horizontal][vertical] = StringUtils.leftPad(String.valueOf(start++), length, "0");
      }

      horizontal++;
      loop++;
    }

    if ((count & 1) != 0)// 奇数矩阵需要初始化最后一位；偶数矩阵则不需要
      matrix[horizontal][vertical] = StringUtils.leftPad(String.valueOf(start++), length, "0");
    return matrix;
  }

  private void printArray(String[][] tangle) {
    StringBuilder builder = new StringBuilder();
    //    for (int horizontal = 0; horizontal < tangle.length; horizontal++) {
    //  避免在循环条件中计算表达式；java编码最佳实践
    for (int horizontal = 0, total = tangle.length; horizontal < total; horizontal++) {
      //      for (int vertical = 0; vertical < tangle.length; vertical++) {
      for (int vertical = 0; vertical < total; vertical++) {
        // 减少 IO 处理,使用StringBuilder提高处理效率
        //        System.out.print(tangle[horizontal][vertical] + "  ");
        builder.append(tangle[horizontal][vertical]);
        builder.append("  ");
      }
      //      System.out.println("\n");
      builder.append("\n");
    }

    builder.append("\n");
    System.out.println(builder.toString());
  }

  @Test
  public void exampleAlgorithm() {
    System.out.println("====================================\n");
    System.out.println("数字矩阵,请输入大于 0 的整数,进行查看生成的数字矩阵");
    System.out.println("例如：下述演示的 6 行矩阵");
    System.out.println("====================================\n");
    int count = 6;
    do {
      algorithmGenerate(count);
      System.out.print("请输入：");
      count = generateRandom(-1, 20);
      System.out.println(count);
    } while (count > 0);

    System.out.println("谢谢！再见！");
  }


  private void algorithmGenerate(int count) {
    StringBuilder builder = new StringBuilder();
    int length = String.valueOf(Double.valueOf(Math.pow(count, 2)).intValue()).length();

    int horizontal = 0, vertical = 0;

    while (horizontal < count) {// 按行
      while (vertical < count) {// 按列
        builder.append(
          StringUtils.leftPad(String.valueOf(cal(count - 1, horizontal, vertical)), length, "0"));
        builder.append(" ");
        vertical++;
      }
      horizontal++;
      vertical = 0;
      builder.append("\n");
    }

    logger.info("result:\n{}", builder.toString());
  }

  private int cal(int base, int horizontal, int vertical) {
    // 判断内层矩阵边界； base > 1 来断定是否含有内层矩阵
    // 以及内层矩阵的边界，以防止修改了外层矩阵的值,判断条件为：
    // horizontal > 0 && vertical > 0  && base > horizontal && base > vertical
    if (base > 1 && base > horizontal && horizontal > 0 && base > vertical && vertical > 0) {
      // 外层矩阵基准值 base * 4 ,内层矩阵和外层矩阵相比 行数和列数（count） 相差 2; 因此, base - 2 作为内层矩阵的 base 值
      return base * 4 + cal(base - 2, horizontal - 1, vertical - 1);// 基准值加上内层循环矩阵值
    }

    return edge(base, horizontal, vertical);

  }

  // 位置落在矩阵边上的值计算；只考虑位置落在矩阵最外层边上的值
  private int edge(int base, int horizontal, int vertical) {
    if (vertical == 0) {// 逆时针方向第一条边上的值计算
      return base * 0 + horizontal + 1;
    } else {
      if (horizontal == base) {// 逆时针方向第二条边上的值计算
        return base * 1 + vertical + 1;
      } else {
        if (vertical == base)// 逆时针方向第三条边上的值计算
          return base * 2 + base + 1 - horizontal;
      }
    }

    return base * 3 + base + 1 - vertical;// 逆时针方向第四条边上的值计算
  }

}
