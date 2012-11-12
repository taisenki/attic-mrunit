package org.apache.hadoop.mrunit.internal.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.types.Pair;

public class StringUtils {
  
  /**
   * Split "key \t val" into Pair(Text(key), Text(val))
   * 
   * @param tabSeparatedPair
   * @return
   */
  public static Pair<Text, Text> parseTabbedPair(final String tabSeparatedPair) {
    final int split = tabSeparatedPair.indexOf('\t');
    if (split == -1) {
      throw new IllegalArgumentException("String pair missing a tab separator");
    }
    return new Pair<Text, Text>(new Text(tabSeparatedPair.substring(0, split)),
        new Text(tabSeparatedPair.substring(split + 1)));
  }

  /**
   * Split "val,val,val,val..." into a List of Text(val) objects.
   * 
   * @param commaDelimList
   *          A list of values separated by commas
   */
  public static List<Text> parseCommaDelimitedList(
      final String commaDelimList) {
    final ArrayList<Text> outList = new ArrayList<Text>();

    final int len = commaDelimList.length();
    int curPos = 0;
    int curComma = commaDelimList.indexOf(',');
    if (curComma == -1) {
      curComma = len;
    }

    while (curPos < len) {
      outList.add(new Text(commaDelimList.substring(curPos, curComma).trim()));
      curPos = curComma + 1;
      curComma = commaDelimList.indexOf(',', curPos);
      if (curComma == -1) {
        curComma = len;
      }
    }

    return outList;
  }
  
  /**
   * Transform a list with elements a and b into a string "(a,b)".
   */
  public static void formatValueList(final List<?> values,
      final StringBuilder sb) {
    sb.append("(");

    boolean first = true;
    for (final Object val : values) {
      if (!first) {
        sb.append(", ");
      }
      first = false;
      sb.append(val);
    }

    sb.append(")");
  }

}
