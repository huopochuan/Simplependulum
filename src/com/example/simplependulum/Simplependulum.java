package com.example.simplependulum;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Simplependulum extends View {

	private int radius = 20; // 半径
	private int l = 200;// 单摆摆动半径
	private int colors[] = { Color.RED, Color.GREEN, Color.BLUE };
	private double T; // 单摆运动周期
	private double jiange;
	private double W; // 角速率
	private int count = 0;
	private int width;
	private double angle; // 起始下落角度
	private boolean changecolor = false; // 是否颜色变换
	private Paint paintcircle1;
	private Paint paintcircle2;
	private Paint paintcircle3;

	public Simplependulum(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		T = 2 * Math.PI * Math.sqrt(l / 10); // 单摆运动周期
		jiange = T / 100;
		W = 2 * Math.PI / T;
		angle = Math.PI / 6;

		// 获取自定义属性
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Simplependulum, defStyleAttr, 0);
		radius = a.getDimensionPixelSize(R.styleable.Simplependulum_radius, radius);
		l = a.getDimensionPixelSize(R.styleable.Simplependulum_length, l);
		changecolor=a.getBoolean(R.styleable.Simplependulum_changecolor, changecolor);
		a.recycle();
		paintcircle1 = new Paint();
		paintcircle1.setAntiAlias(true);
		paintcircle1.setColor(colors[0]);

		paintcircle2 = new Paint();
		paintcircle2.setAntiAlias(true);
		paintcircle2.setColor(colors[1]);

		paintcircle3 = new Paint();
		paintcircle3.setAntiAlias(true);
		paintcircle3.setColor(colors[2]);
	}

	public Simplependulum(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public Simplependulum(Context context) {
		this(context, null);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = this.getMeasuredWidth();

	}

	@Override
	protected void onDraw(Canvas canvas) {

		// canvas.drawCircle(cx, cy, radius, paint);
		// super.onDraw(canvas);
		double currenttime = jiange * count;
		int x = width / 2 - 2 * radius;
		canvas.save();
		canvas.translate(x, 0);
		if (jiange * count >= T)
			count = 0;

		if ((jiange * count >= 0 && jiange * count <= T / 4) || jiange * count >= T * 3 / 4 && jiange * count <= T) {
			if (changecolor) {
				if ((jiange * count >= 0 && jiange * count <= T / 4)) {
					paintcircle1.setColor(colors[0]);
				} else {
					paintcircle1.setColor(colors[2]);
				}
			}
			int circlex = -(int) (l * Math.sin(angle) * Math.cos(W * jiange * count));
			int circley = (int) Math.sqrt(l * l - circlex * circlex);
			canvas.drawCircle(circlex, circley, radius, paintcircle1);
		} else {
			if (changecolor) {
				paintcircle1.setColor(colors[1]);
			}
			canvas.drawCircle(0, l, radius, paintcircle1);
		}
		canvas.restore();

		// 绘制第二个圆圈
		int x1 = width / 2;
		canvas.save();
		canvas.translate(x1, 0);
		canvas.drawCircle(0, l, radius, paintcircle2);
		if (changecolor) {
			if ((jiange * count >= 0 && jiange * count <= T / 4)) {
				paintcircle2.setColor(colors[1]);
			} else if ((jiange * count > T / 4 && jiange * count <= T * 3 / 4)) {
				paintcircle2.setColor(colors[2]);
			} else {
				paintcircle2.setColor(colors[0]);
			}
		}
		canvas.restore();

		// 绘制第三个园
		int x2 = width / 2 + 2 * radius;
		canvas.save();
		canvas.translate(x2, 0);
		if (changecolor) {
			if ((jiange * count >= 0 && jiange * count <= T / 4)) {
				paintcircle3.setColor(colors[2]);
			} else if ((jiange * count > T / 4 && jiange * count <= T * 3 / 4)) {
				paintcircle3.setColor(colors[0]);
			} else {
				paintcircle3.setColor(colors[1]);
			}
		}

		if (jiange * count >= T / 4 && jiange * count <= T * 3 / 4) {

			int circlex = -(int) (l * Math.sin(angle) * Math.cos(W * jiange * count));
			int circley = (int) Math.sqrt(l * l - circlex * circlex);

			canvas.drawCircle(circlex, circley, radius, paintcircle3);
		} else {
			canvas.drawCircle(0, l, radius, paintcircle3);
		}
		canvas.restore();

		count++;
		postInvalidate();
	}

}
