package org.klee.readview.page

import android.graphics.Paint

class DefaultBreaker: IBreaker {

    // 避免函数多次调用时不断创建ArrayList对象
    private val paras: MutableList<String> = ArrayList()
    private val lines: MutableList<String> = ArrayList()
    private val stringBuilder = StringBuilder()

    override fun breakParas(content: String): List<String> {
        paras.clear()
        val splits = content.split("\n")
        splits.forEach {
            val trim = it.trim()
            if (trim.isNotEmpty()) {
                paras.add(trim)
            }
        }
        return paras
    }

    override fun breakLines(
        para: String,
        width: Float, paint: Paint,
        textMargin: Float, offset: Float
    ): List<String> {
        lines.clear()
        var w = width - offset
        var dimen: Float
        para.forEach {
            dimen = paint.measureText(it.toString())
            if (w < dimen) {    // 剩余宽度已经不足以留给该字符
                lines.add(stringBuilder.toString())
                stringBuilder.clear()
                w = width
            }
            w -= dimen + textMargin
            stringBuilder.append(it)
        }
        val lastLine = stringBuilder.toString()
        if (lastLine.isNotEmpty()) {
            lines.add(lastLine)
        }
        stringBuilder.clear()       // 清空
        return lines
    }

    override fun recycle() {
        paras.clear()
        lines.clear()
    }
}