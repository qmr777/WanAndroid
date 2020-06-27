package com.qmr.base.view;

import android.view.View;
import android.widget.Toolbar;

public class ToolbarHelper {

    public static void setToolbar(Toolbar toolbar, String title) {
        setToolbar(toolbar, title, true);
    }

    public static void setToolbar(Toolbar toolbar, String title, boolean showBackIcon) {
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //doSomething();
                }
            });
        }
    }

}
