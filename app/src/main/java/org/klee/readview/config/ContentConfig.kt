package org.klee.readview.config

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import org.klee.readview.page.DefaultPageFactory
import org.klee.readview.page.IPageFactory

/**
 * ContentView绘制参数配置
 */
object ContentConfig {

    // contentView的宽高
    var contentDimenInitialized = false         // contentView的尺寸是否完成了初始化
        private set
    var contentWidth = 0
        private set
    var contentHeight = 0
        private set

    fun setContentDimen(width: Int, height: Int) {
        contentDimenInitialized = true
        contentWidth = width
        contentHeight = height
    }

    private var pageFactory: IPageFactory? = null

    fun getPageFactory(): IPageFactory {
        if (pageFactory == null) {
            pageFactory = DefaultPageFactory()
        }
        return pageFactory!!
    }

    fun setPageFactory(pageFactory: IPageFactory) {
        this.pageFactory = pageFactory
    }

    val contentPaint: Paint by lazy { Paint().apply {
        textSize = 54F
        color = Color.parseColor("#2B2B2B")
    } }

    val titlePaint: Paint by lazy { Paint().apply {
        typeface = Typeface.DEFAULT_BOLD
        textSize = 72F
        color = Color.BLACK
    } }

    val contentColor get() = contentPaint.color
    val contentSize get() = contentPaint.textSize
    val titleColor get() = titlePaint.color
    val titleSize get() = titlePaint.textSize

    /************** - contentView内部尺寸参数 - *****************/
    val lineOffset get() = contentPaint.measureText("来自") // 段落首行的偏移
    var titleMargin = 150F                                      // 章节标题与章节正文的间距
    var textMargin = 0F                                         // 字符间隔
    var lineMargin = 25F                                        // 行间隔
    var paraMargin = 35F                                        // 段落的额外间隔

    private var bgBitmap: Bitmap? = null

    /**
     * 获取一个和当前contentView的大小一样的透明bitmap
     * 每次调用该函数都会复制产生一个透明bitmap
     */
    fun getBgBitmap(): Bitmap {
        val bitmap = bgBitmap ?: let {
            bgBitmap = Bitmap.createBitmap(contentWidth, contentHeight, Bitmap.Config.ARGB_8888)
            bgBitmap!!
        }
        return bitmap.copy(Bitmap.Config.ARGB_8888, true)
    }
}