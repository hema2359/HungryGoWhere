package com.example.hungry.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView{

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	protected void onDraw(Canvas canvas){
	   // super.onDraw(canvas);

	 // init shader
	    BitmapShader shader;
	    Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
	    bitmap = Bitmap.createScaledBitmap(bitmap, 130, 130, true);
	    shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

	    // init paint
	    Paint paint = new Paint();
	    paint.setAntiAlias(true);
	    paint.setShader(shader);
	    int circleCenter =  getWidth() / 2;

	    // circleCenter is the x or y of the view's center
	    // radius is the radius in pixels of the cirle to be drawn
	    // paint contains the shader that will texture the shape

	    canvas.drawCircle(circleCenter, circleCenter, 60.0f, paint);
	}

}
