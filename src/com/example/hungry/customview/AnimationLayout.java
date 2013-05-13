/*
 * Copyright (C) 2012 0xlab - http://0xlab.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authored by Julian Chu <walkingice AT 0xlab.org>
 */

package com.example.hungry.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.hungry.R;

// update the package name to match your app

public class AnimationLayout extends ViewGroup {

    public final static int DURATION = 500;

    protected boolean mLeftOpened;
    protected boolean mRightOpened;
	protected boolean mTopOpened;

    protected View mSidebar;
    protected View mContent;
    protected View mTopbar;
    protected View mFilterbar;
    protected int mSidebarWidth = 150; /* assign default value. It will be overwrite
                                          in onMeasure by Layout xml resource. */
    protected int mTopbarHeight = 150;
    protected int mFilterWidth = 150;
    protected Animation mAnimation;
    protected LeftOpenListener    mLeftOpenListener;
    protected LeftCloseListener   mLeftCloseListener;
    protected RightOpenListener mRightOpenListener;
    protected RightCloseListener mRightCloseListener;
   // protected Listener mListener, mListener1;

    protected boolean mPressed = false;


    public AnimationLayout(Context context) {
        this(context, null);
    }

    public AnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mSidebar = findViewById(R.id.lt_sidebar);
        mContent = findViewById(R.id.lt_main);
        mTopbar = findViewById(R.id.lt_toplist);
        mFilterbar = findViewById(R.id.lt_Filterbar);

        if (mSidebar == null) {
            throw new NullPointerException("no view id = animation_sidebar");
        }

        if (mContent == null) {
            throw new NullPointerException("no view id = animation_content");
        }

        if (mTopbar == null) {
            throw new NullPointerException("no view id = animation_topbar");
        }
        if (mFilterbar == null) {
            throw new NullPointerException("no view id = animation_Filterbar");
        }
        
        mLeftOpenListener = new LeftOpenListener(mSidebar, mContent);
        mLeftCloseListener = new LeftCloseListener(mSidebar, mContent);
        mRightOpenListener = new RightOpenListener(mFilterbar, mContent);
        mRightCloseListener = new RightCloseListener(mFilterbar, mContent);
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        /* the title bar assign top padding, drop it */
      
       
        mSidebar.layout(l,
                0,
                l + mSidebarWidth,
                0 + mSidebar.getMeasuredHeight());
        mTopbar.layout(0,
        		t,
        		0 + mTopbar.getMeasuredWidth(),
        		t + mTopbarHeight);
        mFilterbar.layout(r - mFilterWidth,
        		0,
        		r,
        		0 + mFilterbar.getMeasuredHeight());

        if (mLeftOpened) {
            mContent.layout(l + mSidebarWidth, 0, r + mSidebarWidth, b);
        }else if(mRightOpened){
            mContent.layout(l - mFilterWidth, 0, r - mFilterWidth, b);
        }else {
            mContent.layout(0, 0, r, b);
        }
        
    }

    @Override
    public void onMeasure(int w, int h) {
        super.onMeasure(w, h);
        super.measureChildren(w, h);
        mSidebarWidth = mSidebar.getMeasuredWidth();
        mTopbarHeight = mTopbar.getMeasuredHeight();
        mFilterWidth = mFilterbar.getMeasuredWidth();
    }

    @Override
    protected void measureChild(View child, int parentWSpec, int parentHSpec) {
        /* the max width of Sidebar is 90% of Parent */
        if (child == mSidebar) {
            int mode = MeasureSpec.getMode(parentWSpec);
            int width = (int)(getMeasuredWidth() * 0.9);
            super.measureChild(child, MeasureSpec.makeMeasureSpec(width, mode), parentHSpec);
        }else if(child == mTopbar){
        	 int mode = MeasureSpec.getMode(parentHSpec);
             int height = (int)(getMeasuredHeight() * 0.5);
            super.measureChild(child, MeasureSpec.makeMeasureSpec(height, mode), parentWSpec);
        }else if(child == mFilterbar){
        	int mode = MeasureSpec.getMode(parentWSpec);
            int width = (int)(getMeasuredWidth() * 0.9);
            super.measureChild(child, MeasureSpec.makeMeasureSpec(width, mode), parentHSpec);
        }else{
            super.measureChild(child, parentWSpec, parentHSpec);
        }
    }

 

   /* public void setListener(Listener l) {
        mListener = l;
    }*/

    /* to see if the Sidebar is visible */
    public boolean isOpening() {
        return mLeftOpened;
    }
    
    public boolean isTopOpening(){
    	return mRightOpened;
    }

    public void toggleLeftbar() {
        if (mContent.getAnimation() != null) {
            return;
        }

        if (mLeftOpened) {
            /* opened, make close animation*/
            mAnimation = new TranslateAnimation(0, -mSidebarWidth, 0, 0);
            mAnimation.setAnimationListener(mLeftCloseListener);
        } else {
            /* not opened, make open animation */
        	mAnimation = new TranslateAnimation(0, mSidebarWidth, 0, 0);
            mAnimation.setAnimationListener(mLeftOpenListener);
        }
        mAnimation.setDuration(DURATION);
        mAnimation.setFillAfter(true);
        mAnimation.setFillEnabled(true);
        mContent.startAnimation(mAnimation);
    }

   /* public void openSidebar() {
        if (!mOpened) {
            toggleSidebar();
        }
    }

    public void closeSidebar() {
        if (mOpened) {
            toggleSidebar();
        }
    }*/

    class LeftOpenListener implements Animation.AnimationListener {
        View iSidebar;
        View iContent;

        LeftOpenListener(View sidebar, View content) {
            iSidebar = sidebar;
            iContent = content;
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            iSidebar.setVisibility(View.VISIBLE);
        }

        public void onAnimationEnd(Animation animation) {
            iContent.clearAnimation();
            mLeftOpened = !mLeftOpened;
            requestLayout();
           /* if (mListener != null) {
                mListener.onSidebarOpened();
            }*/
        }
    }

    class LeftCloseListener implements Animation.AnimationListener {
        View iSidebar;
        View iContent;

        LeftCloseListener(View sidebar, View content) {
            iSidebar = sidebar;
            iContent = content;
        }

        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            iContent.clearAnimation();
            iSidebar.setVisibility(View.INVISIBLE);
            mLeftOpened = !mLeftOpened;
            requestLayout();
          /*  if (mListener != null) {
                mListener.onSidebarClosed();
            }*/
        }
    }

    public interface Listener {
        public void onSidebarOpened();
        public void onSidebarClosed();
      //  public boolean onContentTouchedWhenOpening();
    }
    public void toggleRightbar() {
        if (mContent.getAnimation() != null) {
            return;
        }

        if (mRightOpened) {
            /* opened, make close animation*/
            mAnimation = new TranslateAnimation(0, mFilterWidth,0, 0 );
            mAnimation.setAnimationListener(mRightCloseListener);
        } else {
            /* not opened, make open animation */
        	mAnimation = new TranslateAnimation(0, -mFilterWidth,0, 0);
            mAnimation.setAnimationListener(mRightOpenListener);
        }
        mAnimation.setDuration(DURATION);
        mAnimation.setFillAfter(true);
        mAnimation.setFillEnabled(true);
        mContent.startAnimation(mAnimation);
    }
    class RightOpenListener implements Animation.AnimationListener {
        View iFilterbar;
        View iContent;

        RightOpenListener(View topbar, View content) {
        	iFilterbar = topbar;
            iContent = content;
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        	//iTopbar.setVisibility(View.VISIBLE);
        }

        public void onAnimationEnd(Animation animation) {
        	iFilterbar.setVisibility(View.VISIBLE);

            iContent.clearAnimation();
            mRightOpened = !mRightOpened;
            requestLayout();
            /*if (mListener != null) {
                mListener.onSidebarOpened();
            }*/
        }
    }
    class RightCloseListener implements Animation.AnimationListener {
        View iFilterbar;
        View iContent;

        RightCloseListener(View topbar, View content) {
            iFilterbar = topbar;
            iContent = content;
        }

        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
            iFilterbar.setVisibility(View.INVISIBLE);

        }

        public void onAnimationEnd(Animation animation) {
            iContent.clearAnimation();
           // iTopbar.setVisibility(View.INVISIBLE);
            mRightOpened = !mRightOpened;
            requestLayout();
            /*if (mListener != null) {
                mListener.onSidebarClosed();
            }*/
        }
    }
    public void toggleMenu(){
    	if(mTopOpened)
    		mTopbar.setVisibility(View.GONE);
    	else
    		mTopbar.setVisibility(View.VISIBLE);
    	mTopOpened = !mTopOpened;
    }
    
}
