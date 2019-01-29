package com.xwc.coolweather.util

/**
 * Description：字符串工具类
 * author：Smoker
 * 2019/1/29 11:15
 */
object StringUtil {
    fun isEmpty(str: String?): Boolean = str.isNullOrEmpty()

    fun isNotEmpty(str: String?): Boolean = !isEmpty(str)

    fun isBlank(str: String?): Boolean = str.isNullOrBlank()

    fun isNotBlank(str: String?): Boolean = !isBlank(str)

    /**
     * 判断对象是否为空
     * */
    fun isBlankObject(obj: Any?): Boolean = obj == null || obj.toString().isBlank()

    /**
     * 判断两个字符串是否相等
     * */
    fun isEquals(str1: String?, str2: String): Boolean {
        return if (isEmpty(str1) || isEmpty(str2)) {
            false
        } else {
            str1.equals(str2)
        }
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     */
    fun isContainsEmoji(source: String): Boolean {
        val len = source.length
        for (i in 0 until len) {
            val codePoint = source[i]
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true
            }
        }
        return false
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     */
    fun isEmojiCharacter(codePoint: Char): Boolean {
        return codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA ||
                codePoint.toInt() == 0xD || codePoint.toInt() in 0x20..0xD7FF ||
                codePoint.toInt() in 0xE000..0xFFFD || codePoint.toInt() in 0x10000..0x10FFFF
    }
}