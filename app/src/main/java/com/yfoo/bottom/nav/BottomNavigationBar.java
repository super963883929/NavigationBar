package com.yfoo.bottom.nav;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class BottomNavigationBar extends LinearLayout implements View.OnClickListener {

    private Paint paint;
    private Path path;
    /**
     * 设备屏幕宽度
     */
    private float width;

    private onBottomNavClickListener listener;
    private TextView tv_first;
    private TextView tv_second;
    private TextView tv_third;
    private TextView tv_fourth;

    /***
     * 设置图标
     * @param selectIcon 选中图标
     * @param normalIcon 未选中图标
     * @param titles 标题
     */
    public void setIcon(int[] selectIcon,int[] normalIcon,String[] titles) {
        this.selectIcon = selectIcon;
        this.normalIcon = normalIcon;
        this.titles = titles;
    }

    private int[] selectIcon = {R.drawable.nav_like_on, R.drawable.nav_web_on, R.drawable.nav_download_on, R.drawable.nav_me_on};
    private String[] titles = {"收藏","网页","下载","我的"};

    public void setNormalIcon(int[] normalIcon) {
        this.normalIcon = normalIcon;
    }

    //选中时icon
    private int[] normalIcon = {R.drawable.nav_like_off, R.drawable.nav_web_off, R.drawable.nav_download_off, R.drawable.nav_me_off};

    private int textColor = Color.parseColor("#5fce30");
    private ImageButton img1, img2, img3, img4;
    private ImageButton imgCenter;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    private int currentPosition = 0; //上次的页面

    private PointF b;
    private PointF c;
    private PointF a;
    private PointF a2;
    private PointF b2;
    private PointF c2;
    private PointF a3;
    private PointF b3;
    private PointF c3;
    private ViewPager viewPager;

    public BottomNavigationBar(Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void setTextColor(int color){
        this.textColor = color;
        tv_first.setTextColor(textColor);
        tv_second.setTextColor(textColor);
        tv_third.setTextColor(textColor);
        tv_fourth.setTextColor(textColor);
    }


    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG); //初始化油漆画笔
        path = new Path();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_nav_bar, this);

        img1 = view.findViewById(R.id.first);
        img2 = view.findViewById(R.id.second);
        imgCenter = view.findViewById(R.id.centerIcon);
        img3 = view.findViewById(R.id.third);
        img4 = view.findViewById(R.id.fourth);
        setWillNotDraw(false);

        //2、通过Resources获取
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels; //设备屏幕宽度

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        imgCenter.setOnClickListener(this);

        tv_first = findViewById(R.id.tv_first);
        tv_second = findViewById(R.id.tv_second);
        tv_third = findViewById(R.id.tv_third);
        tv_fourth = findViewById(R.id.tv_fourth);

        tv_first.setTextColor(textColor);
        tv_second.setTextColor(textColor);
        tv_third.setTextColor(textColor);
        tv_fourth.setTextColor(textColor);


        a = new PointF(0, 0);
        b = new PointF(0, 0);
        c = new PointF(0, 0);

        a2 = new PointF(0, 0);
        b2 = new PointF(0, 0);
        c2 = new PointF(0, 0);

        a3 = new PointF(0, 0);
        b3 = new PointF(0, 0);
        c3 = new PointF(0, 0);

        setCurrentPage(0);
    }




    @Override
    protected void onDraw(Canvas canvas) {

        int high = 90;//底部导航的高度
        int marginTop = 30;//剧顶边高度

        a.x = dip2px(110);
        a.y = dip2px(marginTop);
        b.x = dip2px(130);
        b.y = dip2px(marginTop);
        c.x = dip2px(140);
        c.y = dip2px(marginTop-10);

        a2.x = c.x;
        a2.y = c.y;
        b2.x = width / 2;
        b2.y = dip2px(-10);
        c2.x = width - dip2px(140);
        c2.y = dip2px(marginTop-10);

        a3.x =  c2.x;
        a3.y =  c2.y;
        b3.x =  width - dip2px(130);
        b3.y = dip2px(marginTop);
        c3.x = width-dip2px(110);
        c3.y = dip2px(marginTop);

        paint.setColor(Color.WHITE);
        paint.setShadowLayer(30, 0, 20, Color.parseColor("#d4d5d9"));

        //moveTo 用来移动画笔
        path.moveTo(0, dip2px(marginTop));//设置下一个轮廓线的起始点(x,y)。第一个点

        path.lineTo(a.x, a.y); //绘制到贝塞尔曲线第一个点 也就是a1点

        path.quadTo(b.x, b.y, c.x, c.y);//第左边曲线
        path.quadTo(b2.x, b2.y, c2.x, c2.y);//中间曲线
        path.quadTo(b3.x , b3.y, c3.x, c3.y );//第右边曲线

        path.lineTo(width, dip2px(marginTop)); //画线
        path.lineTo(width, dip2px(high));//画线
        path.lineTo(0, dip2px(high));

        path.close();
        canvas.drawPath(path, paint); //绘制路径 使用指定的油漆绘制指定的路径。路径将根据绘画的风格被填充或框起来。

        super.onDraw(canvas);
    }




    /**
     * 根据屏幕的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.first){
            setUnSelect(currentPosition);
            if (listener!=null){
                listener.onIconClick(v.getId(),0);
            }
            setCurrentPage(0);

        }else if (v.getId() == R.id.second){
            setUnSelect(currentPosition);
            if (listener!=null){
                listener.onIconClick(v.getId(),1);
            }
            setCurrentPage(1);

        }else if (v.getId() == R.id.third){
            setUnSelect(currentPosition);
            if (listener!=null){
                listener.onIconClick(v.getId(),2);
            }
            setCurrentPage(2);

        }else if (v.getId() == R.id.fourth){
            setUnSelect(currentPosition);
            if (listener!=null){
                listener.onIconClick(v.getId(),3);
            }
            setCurrentPage(3);

        }else if (v.getId() == R.id.centerIcon){
            if (listener!=null){
                listener.onCenterIconClick();
            }

        }
        if (viewPager!=null){
            viewPager.setCurrentItem(currentPosition,true);
        }
    }


    public void setCurrentPage(int page){
        setUnSelect(currentPosition);
        currentPosition = page;
        if (page==0){
            img1.setImageResource(selectIcon[currentPosition]);
            tv_first.setVisibility(VISIBLE);
        }else if (page==1){
            img2.setImageResource(selectIcon[currentPosition]);
            tv_second.setVisibility(VISIBLE);
        }else if (page==2){
            img3.setImageResource(selectIcon[currentPosition]);
            tv_third.setVisibility(VISIBLE);
        }else if (page==3){
            img4.setImageResource(selectIcon[currentPosition]);
            tv_fourth.setVisibility(VISIBLE);
        }


    }





    private void setUnSelect(int position) {
        switch (position) {
            case 0:
                img1.setImageResource(normalIcon[0]);
                tv_first.setVisibility(GONE);
                break;
            case 1:
                img2.setImageResource(normalIcon[1]);
                tv_second.setVisibility(GONE);
                break;
            case 2:
                img3.setImageResource(normalIcon[2]);
                tv_third.setVisibility(GONE);
                break;
            case 3:
                img4.setImageResource(normalIcon[3]);
                tv_fourth.setVisibility(GONE);
                break;
        }
    }

    public interface onBottomNavClickListener {
        void onIconClick(int viewId,int i);
        void onCenterIconClick();
    }


    public void setOnListener(onBottomNavClickListener listener) {
        this.listener = listener;
    }


}
