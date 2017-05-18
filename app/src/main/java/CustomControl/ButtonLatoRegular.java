package CustomControl;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by navneet on 5/18/2017.
 */

public class ButtonLatoRegular extends Button {
    public ButtonLatoRegular(Context context) {
        super(context);
        setTypeface(context);

    }
    public ButtonLatoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public ButtonLatoRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(context);
    }
    private void setTypeface(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(),"Font/Lato-Regular_4.ttf"));
    }
}
