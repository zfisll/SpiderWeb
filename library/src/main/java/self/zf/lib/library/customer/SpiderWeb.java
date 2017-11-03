package self.zf.lib.library.customer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import self.zf.lib.library.R;

/**
 * Created by user_zf on 17/10/25.
 * 蜘蛛网：正多边形便是各种属性值
 */

public class SpiderWeb extends View {
    Paint mPaint;
    int mCenterX;  //中心点横坐标
    int mCenterY;  //中心点纵坐标
    int mRadiu;

    int mCount;   //层数
    int mPolygon; //多边形数
    int mPointColor; //苗点颜色
    int mDataColor;  //属性值覆盖区域颜色
    float[] rates = new float[]{
            0.6f, 0.5f, 0.3f, 0.9f, 0.8f, 0.6f, 0.55f, 0.47f, 0.83f, 0.28f
    };

    /**
     * 直接通过new出来
     */
    public SpiderWeb(Context context) {
        super(context);
        initPaint();
    }

    /**
     * 布局构造方法
     */
    public SpiderWeb(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpiderWeb);
        mCount = typedArray.getInt(R.styleable.SpiderWeb_count, 5);
        mPolygon = typedArray.getInt(R.styleable.SpiderWeb_polygon, 6);
        mPointColor = typedArray.getColor(R.styleable.SpiderWeb_pointColor, Color.BLUE);
        mDataColor = typedArray.getColor(R.styleable.SpiderWeb_dataColor, Color.argb(34, 0, 0, 255));

        typedArray.recycle();
    }

    /**
     * 设置绘制层数
     */
    public void setNewCount(int count) {
        this.mCount = count;
    }

    /**
     * 设置多边形数量
     */
    public void setmPolygon(int polygon) {
        this.mPolygon = polygon;
    }

    /**
     * 设置每个边的比重
     */
    public void setRates(float[] rates) {
        this.rates = rates;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //此方法可以获取view的宽高
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mRadiu = w > h ? h / 12 : w / 12;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mCenterX, mCenterY);
        canvas.rotate((float) ((180 - 360/mPolygon) / 2));
        drawPolygon(canvas);
        drawValue(canvas);
    }

    private void drawPolygon(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 1; i <= mCount; i++) {
            Path path = new Path();
            float curRadiu = i * mRadiu;
            path.moveTo(curRadiu, 0);
            float basicAngle = (float) (Math.PI * 2 / mPolygon);
            for (int j = 1; j < mPolygon; j++) {
                float curAngle = j * basicAngle;
                float x = (float) Math.cos(curAngle) * curRadiu;
                float y = (float) Math.sin(curAngle) * curRadiu;
                path.lineTo(x, y);
            }
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    private void drawValue(Canvas canvas) {
        if(rates.length < mPolygon) {
            throw new IllegalStateException("value's count cannot less than polygon count!");
        }
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        float mFullRadiu = mRadiu * mCount;
        Path path = new Path();
        for (int i = 0; i < mPolygon; i++) {
            //连接中心点到各顶点的线段
            mPaint.setColor(Color.GRAY);
            float currAngle = (float) Math.PI * 2 * (i - 1) / mPolygon;
            canvas.drawLine(0, 0, (float) (mFullRadiu * Math.cos(currAngle)), (float) (mFullRadiu * Math.sin(currAngle)), mPaint);
            float x = (float) (mFullRadiu * Math.cos(currAngle) * rates[i]);
            float y = (float) (mFullRadiu * Math.sin(currAngle) * rates[i]);
            mPaint.setColor(mPointColor);
            //绘制属性点
            canvas.drawCircle(x, y, 5, mPaint);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        mPaint.setColor(mDataColor);
        canvas.drawPath(path, mPaint);
    }
}
