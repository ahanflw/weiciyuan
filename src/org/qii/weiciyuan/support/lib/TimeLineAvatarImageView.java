package org.qii.weiciyuan.support.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import org.qii.weiciyuan.R;
import org.qii.weiciyuan.bean.UserBean;
import org.qii.weiciyuan.support.asyncdrawable.IWeiciyuanDrawable;
import org.qii.weiciyuan.support.utils.Utility;

/**
 * User: qii
 * Date: 12-12-19
 */
public class TimeLineAvatarImageView extends ImageView implements IWeiciyuanDrawable {

//    protected ImageView vImageView;
//    protected ImageView mImageView;

    private Paint paint = new Paint();

    private boolean showPressedState = true;
    private boolean pressed = false;

    private static final int V_TYPE_NONE = -1;
    private static final int V_TYPE_PERSONAL = 0;
    private static final int V_TYPE_ENTERPRISE = 1;

    private int vType = V_TYPE_NONE;


    public TimeLineAvatarImageView(Context context) {
        this(context, null);
    }

    public TimeLineAvatarImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineAvatarImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap;
        switch (vType) {
            case V_TYPE_PERSONAL:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_vip);
                canvas.drawBitmap(bitmap, getWidth() - bitmap.getWidth(), getHeight() - bitmap.getHeight(), paint);
                break;
            case V_TYPE_ENTERPRISE:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_enterprise_vip);
                canvas.drawBitmap(bitmap, getWidth() - bitmap.getWidth(), getHeight() - bitmap.getHeight(), paint);
                break;
            default:
                break;
        }

        if (pressed) {
            canvas.drawColor(getResources().getColor(R.color.transparent_cover));
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!showPressedState)
            return super.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                pressed = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                pressed = false;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    protected void initLayout(Context context) {
        setPadding(Utility.dip2px(5), Utility.dip2px(5), Utility.dip2px(5), Utility.dip2px(5));
//        LayoutInflater inflate = (LayoutInflater)
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflate.inflate(R.layout.timelineimageview_avatar_layout, this, true);
//        mImageView = (ImageView) v.findViewById(R.id.imageview);
//        vImageView = (ImageView) v.findViewById(R.id.imageview_v);
//        mImageView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    public ImageView getImageView() {
        return this;
    }

    @Override
    public void setProgress(int value, int max) {

    }

    @Override
    public ProgressBar getProgressBar() {
        return null;
    }

    @Override
    public void setGifFlag(boolean value) {

    }

    public void checkVerified(UserBean user) {
        if (user != null && user.isVerified() && !TextUtils.isEmpty(user.getVerified_reason())) {
            if (user.getVerified_type() == 0) {
                verifiedPersonal();
            } else {
                verifiedEnterprise();
            }
        } else {
            reset();
        }
    }

    private void verifiedPersonal() {
        if (vType != V_TYPE_PERSONAL) {
            vType = V_TYPE_PERSONAL;
            invalidate();
        }

//        vImageView.setImageDrawable(getResources().getDrawable(R.drawable.avatar_vip));
    }

    private void verifiedEnterprise() {
        if (vType != V_TYPE_ENTERPRISE) {
            vType = V_TYPE_ENTERPRISE;
            invalidate();
        }

//        vImageView.setImageDrawable(getResources().getDrawable(R.drawable.avatar_enterprise_vip));
    }

    private void reset() {
        if (vType != V_TYPE_NONE) {
            vType = V_TYPE_NONE;
            invalidate();
        }

//        vImageView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public void setPressesStateVisibility(boolean value) {
        if (showPressedState == value)
            return;
        showPressedState = value;
        invalidate();
    }
}
