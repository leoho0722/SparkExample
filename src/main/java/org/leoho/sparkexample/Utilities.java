package org.leoho.sparkexample;

public class Utilities {

    /**
     * @param strArray 字串陣列
     * @param start 開始位置
     * @param end 結束位置
     * @return
     */
    public static int strArrayPlusAll(String[] strArray, int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += Integer.parseInt(strArray[i]);
        }
        return sum;
    }
}
