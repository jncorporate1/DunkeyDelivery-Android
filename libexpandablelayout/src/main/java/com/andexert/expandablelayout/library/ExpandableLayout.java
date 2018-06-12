/***********************************************************************************
 * The MIT License (MIT)

 * Copyright (c) 2014 Robin Chutaux

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.andexert.expandablelayout.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandableLayout extends RelativeLayout {
	private Boolean isAnimationRunning = false;
	private Boolean isOpened = false;
	private Integer duration;
	private FrameLayout contentLayout;
	private FrameLayout headerLayout;
	private Animation animation;
	private Animation animationRotation;
	private View ivArrowUp;
	private View ivArrowDn;
	private TextView tvDesc;

	private OnExpandChangeListener mListener;

	public ExpandableLayout(Context context) {
		super(context);
	}

	public ExpandableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ExpandableLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(final Context context, AttributeSet attrs) {
		final View rootView = View.inflate(context, R.layout.view_expandable,
				this);
		headerLayout = (FrameLayout) rootView
				.findViewById(R.id.view_expandable_headerlayout);
		final TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.ExpandableLayout);
		final int headerID = typedArray.getResourceId(
				R.styleable.ExpandableLayout_el_headerLayout, -1);
		final int contentID = typedArray.getResourceId(
				R.styleable.ExpandableLayout_el_contentLayout, -1);
		contentLayout = (FrameLayout) rootView
				.findViewById(R.id.view_expandable_contentLayout);

		if (headerID == -1 || contentID == -1)
			throw new IllegalArgumentException(
					"HeaderLayout and ContentLayout cannot be null!");

		if (isInEditMode())
			return;

		duration = typedArray.getInt(
				R.styleable.ExpandableLayout_el_duration,
				getContext().getResources().getInteger(
						android.R.integer.config_shortAnimTime));
		final View headerView = View.inflate(context, headerID, null);
		headerView.setLayoutParams(new ViewGroup.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		headerLayout.addView(headerView);
		final View contentView = View.inflate(context, contentID, null);
		contentView.setLayoutParams(new ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		contentLayout.addView(contentView);
		contentLayout.setVisibility(GONE);
		headerLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isAnimationRunning) {
					if (contentLayout.getVisibility() == VISIBLE)
						collapse(contentLayout, true);
					else
						expand(contentLayout, true);

					isAnimationRunning = true;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							isAnimationRunning = false;
						}
					}, duration);
				}
			}
		});

		typedArray.recycle();
		animationRotation = AnimationUtils.loadAnimation(context, R.anim.rotate_around_center_point);
		animationRotation
				.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						refreshArrows();
					}
				});
	}

	private void expand(final View v, boolean animateArrow) {
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();
		v.getLayoutParams().height = 0;
		v.setVisibility(VISIBLE);
		if(tvDesc!=null)
			tvDesc.setVisibility(View.VISIBLE);

		animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				if (interpolatedTime == 1)
					isOpened = true;
				v.getLayoutParams().height = (interpolatedTime == 1) ? LayoutParams.WRAP_CONTENT
						: (int) (targetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		animation.setDuration(duration);
		v.startAnimation(animation);

		if (ivArrowDn != null) {
			//ivArrowUp.setVisibility(INVISIBLE);
			if(animateArrow) {
				ivArrowDn.startAnimation(animationRotation);
			} else {
				ivArrowDn.setVisibility(INVISIBLE);
				ivArrowUp.setVisibility(VISIBLE);
			}
		}
		if(mListener != null) mListener.elExpandChanged(true);
	}

	private void collapse(final View v, boolean animateArrow) {
		final int initialHeight = v.getMeasuredHeight();

		animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {

				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
					isOpened = false;
				} else {
					v.getLayoutParams().height = initialHeight
							- (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		animation.setDuration(duration);
		v.startAnimation(animation);

		if (ivArrowUp != null) {
			if(animateArrow) {
				ivArrowUp.startAnimation(animationRotation);
			} else {
				ivArrowUp.setVisibility(INVISIBLE);
				ivArrowDn.setVisibility(VISIBLE);
			}
		}
		if(mListener != null) mListener.elExpandChanged(false);
	}

	public void performClickOnHeader() {
		if (!isAnimationRunning) {
			if (contentLayout.getVisibility() == VISIBLE)
				collapse(contentLayout, true);
			else
				expand(contentLayout, true);

			isAnimationRunning = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					isAnimationRunning = false;
				}
			}, duration);
		}
	}

	public Boolean isOpened() {
		return isOpened;
	}

	public void show() {
		if (!isAnimationRunning) {
			expand(contentLayout, false);
			isAnimationRunning = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					isAnimationRunning = false;
				}
			}, duration);
		}
	}

	public FrameLayout getHeaderLayout() {
		return headerLayout;
	}

	public FrameLayout getContentLayout() {
		return contentLayout;
	}

	public void hide() {
		if (!isAnimationRunning) {
			collapse(contentLayout, false);
			isAnimationRunning = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					isAnimationRunning = false;
				}
			}, duration);
		}
	}

	@Override
	public void setLayoutAnimationListener(Animation.AnimationListener animationListener) {
		animation.setAnimationListener(animationListener);
	}

	public void setArrow(View ivArrowUp, View ivArrowDn) {
		this.ivArrowUp = ivArrowUp;
		this.ivArrowDn = ivArrowDn;
	}

	public void resetArrows() {
		ivArrowUp.setVisibility(View.INVISIBLE);
		ivArrowDn.setVisibility(View.VISIBLE);
	}

	public void setDescriptionView(TextView tvDesc) {
		this.tvDesc = tvDesc;
	}

	public void refreshArrows() {
		if (ivArrowUp != null && ivArrowDn != null) {
			if (ivArrowDn.getVisibility() == VISIBLE) {
				ivArrowUp.setVisibility(View.VISIBLE);
				ivArrowDn.setVisibility(View.INVISIBLE);
			} else {
				ivArrowUp.setVisibility(View.INVISIBLE);
				ivArrowDn.setVisibility(View.VISIBLE);
				if(tvDesc!=null)
					tvDesc.setVisibility(View.GONE);
			}
		}
	}

	public void setOnExpandChangeListener(OnExpandChangeListener listener) {
		mListener = listener;
	}

	public interface OnExpandChangeListener {
		void elExpandChanged(boolean isExpanded);
	}
}
