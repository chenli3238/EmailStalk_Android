package CustomControl;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by User on 5/17/2017.
 */

public class EditTextLatoBolt extends EditText {
    public EditTextLatoBolt(Context context) {
        super(context);
        setTypeface(context);
    }

    public EditTextLatoBolt(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
        setTypeface(context);
    }

    public EditTextLatoBolt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(context);
    }

    public EditTextLatoBolt(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setTypeface(context);
        setTypeface(context);
    }
    private void setTypeface(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Font/Lato-Bold_4.ttf"));
    }
}
