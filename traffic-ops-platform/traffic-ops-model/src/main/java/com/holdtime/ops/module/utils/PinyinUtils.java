package com.holdtime.ops.module.utils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * 项目名称： jtzwfw-model
 * 功能描述： PinyinUtil概述：汉字转拼音工具类扩展
 * 创  建 人： yangli
 * 创建日期： 2024年3月16日
 * 修  改 人：
 * 修改时间：
 * 修改内容：
 * 版      本： 1.0.0
 */
@Slf4j
public class PinyinUtils {
    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     * @param inputStr 待转换字符串
     * @return 转换后字符串
     */
    public static String getPinyin2Uppercase(String inputStr) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuilder output = new StringBuilder();
        if (!StringUtils.hasText(inputStr)) {
            return null;
        }

        char[] inputs = inputStr.trim().toCharArray();
        try {
            for (char input : inputs) {
                if (Character.toString(input).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input, format);
                    output.append(temp[0]);
                } else
                    output.append(input);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
           log.error(e.getMessage(), e);
        }
        return output.toString();
    }

    /**
     * 获取中文串拼音首字母，英文字符不变
     * getFirstSpell
     * @param chinese 汉字串
     * @return java.lang.String 中文拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        return getFirstSpell(chinese, true);
    }

    /**
     * 获取中文串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return java.lang.String 中文拼音首字母
     */
    public static String getFirstSpell(String chinese, boolean isUppercase) {
        StringBuilder pybf = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        if (isUppercase) {
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        }
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                pybf.append(c);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    public static void main(String[] args) {
        System.out.println(PinyinUtils.getFirstSpell("内蒙古豪德天沐科技有限公司", false));
        System.out.println(new Random().nextInt(100));
    }
}
