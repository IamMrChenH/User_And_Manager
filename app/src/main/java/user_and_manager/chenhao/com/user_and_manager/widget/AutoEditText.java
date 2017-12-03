package user_and_manager.chenhao.com.user_and_manager.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by chenhao on 17-3-22.
 */

public class AutoEditText extends android.support.v7.widget.AppCompatAutoCompleteTextView {


    public AutoEditText(Context context) {
        super(context);
    }

    public AutoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean enoughToFilter() {
        return true;
    }
}
