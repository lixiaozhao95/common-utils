package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;


import com.lee.commonutils.R;

import java.util.ArrayList;
import java.util.List;

public class MarqueeView extends AppCompatTextView {
    //滚动方向
    //不滚动
    public static final int SCROLL_NO = 1;
    //从下往上
    public static final int SCROLL_BT = 2;
    //从右往左
    public static final int SCROLL_RL = 3;

    //垂直滚动需要的数据
    private float lineSpace;
    private float verticalSpeed = 0.1f;
    private List<String> textList = new ArrayList<>();
    private StringBuilder textBuilder = new StringBuilder();

    //水平滚动需要的数据
    private float horizontalSpeed = 2f;
    private Rect rect;

    private Paint paint;
    //默认不滚动
    private int scrollType;
    //每次更滚动的距离
    private float scrollStep = 0f;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MarqueeView);
        scrollType = array.getInt(R.styleable.MarqueeView_scrollType, SCROLL_NO);
        int color = array.getColor(R.styleable.MarqueeView_textColor, 0x000000);
        lineSpace = array.getInt(R.styleable.MarqueeView_lineSpace, 0);
        array.recycle();
        setSpeed(0, 1f);
        setSpeed(1, 5f);
        paint = getPaint();
        paint.setColor(color);
        rect = new Rect();
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        paint.setColor(color);
    }

    /**
     * 设置滚动方向
     *
     * @param scrollType 滚动方向
     */
    public void setScrollType(int scrollType) {
        this.scrollType = scrollType;
        invalidate();
    }

    /**
     * 设置滚动速度
     *
     * @param type 滚动方向 0垂直 1水平
     */
    public void setSpeed(int type, float speed) {
        if (0 == type) {
            verticalSpeed = speed;
        } else {
            horizontalSpeed = speed;
        }
        invalidate();
    }

    /**
     * 设置行高
     *
     * @param lineSpace 行高
     */
    public void setLineSpace(float lineSpace) {
        this.lineSpace = lineSpace;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        String text = getText().toString();
        if (!TextUtils.isEmpty(text) && scrollType == SCROLL_BT) {
            //由下往上滚动需要测量高度
            setTextList(widthMeasureSpec, text);
        }
    }

    /**
     * 根据TextView宽度和字体大小，计算显示的行数。
     *
     * @param widthMeasureSpec 测量模式
     * @param text             文本
     */
    private void setTextList(int widthMeasureSpec, String text) {
        textList.clear();
        float width = View.MeasureSpec.getSize(widthMeasureSpec);
        float length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (length <= width) {
                textBuilder.append(text.charAt(i));
                length += paint.measureText(text.substring(i, i + 1));
                if (i == text.length() - 1) {
                    if (length <= width) {
                        textList.add(textBuilder.toString());
                    } else {
                        if (textBuilder.toString().length() == 1) {
                            //每行最多显示一个字
                            textList.add(text.substring(text.length() - 1));
                        } else {
                            //去掉最后一个字，否则最后一个字显示不完整
                            textList.add(textBuilder.toString().substring(0, textBuilder.toString().length() - 1));
                            //最后一个字单独一行
                            textList.add(text.substring(text.length() - 1));
                        }
                    }
                }
            } else {
                if (textBuilder.toString().length() == 1) {
                    //每行最多显示一个字
                    textList.add(textBuilder.toString());
                    textBuilder.delete(0, textBuilder.length());
                    i--;
                    length = 0;
                } else {
                    //去掉最后一个字，否则最后一个字显示不完整
                    textList.add(textBuilder.toString().substring(0, textBuilder.toString().length() - 1));
                    textBuilder.delete(0, textBuilder.length() - 1);
                    i--;
                    length = paint.measureText(text.substring(i, i + 1));
                }
            }
        }
        //清空textBuilder
        textBuilder.delete(0, textBuilder.length());
    }

    @Override
    public void onDraw(Canvas canvas) {
        String text = getText().toString();
        if (TextUtils.isEmpty(text) || text.length() < 30) {
            super.onDraw(canvas);
            return;
        }
        switch (scrollType) {
            case SCROLL_NO:
                super.onDraw(canvas);
                break;
            case SCROLL_BT:
                //从下往上滚动，首次不显示文字，后续从下往上显示
                float textSize = paint.getTextSize();
                for (int i = 0; i < textList.size(); i++) {
                    float currentY = getHeight() + (i + 1) * textSize - scrollStep;
                    if (i > 0) {
                        currentY = currentY + i * lineSpace;
                    }
                    if (textList.size() > 1) {
                        canvas.drawText(textList.get(i), 0, currentY, paint);
                    } else {
                        canvas.drawText(textList.get(i), getWidth() / 2 - paint.measureText(text) / 2, currentY, paint);
                    }
                }
                scrollStep = scrollStep + verticalSpeed;
                if (scrollStep >= getHeight() + textList.size() * textSize + (textList.size() - 1) * lineSpace) {
                    scrollStep = 0;
                }
                invalidate();
                break;
            case SCROLL_RL:
                //从右向左滚动，首次不显示文字，后续每次往左偏移speed像素
                paint.getTextBounds(text, 0, text.length(), rect);
                int textWidth = rect.width();
                int viewWidth = getWidth();
                float currentX = viewWidth - scrollStep;
                canvas.drawText(text, currentX, getHeight() / 2 + (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / 2 - paint.getFontMetrics().descent, paint);
                scrollStep = scrollStep + horizontalSpeed;
                if (scrollStep >= viewWidth + textWidth) {
                    scrollStep = 0;
                }
                invalidate();
                break;
            default:
                break;
        }
    }
}