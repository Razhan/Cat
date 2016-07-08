package com.ran.delta.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.MenuRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.ran.delta.widget.bottombar.BottomBarTab;

public final class MiscUtils {

    public static int dpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        try {
            return (int) (dp * (metrics.densityDpi / 160f));
        } catch (NoSuchFieldError ignored) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        }
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    public static BottomBarTab[] inflateMenuFromResource(Activity activity, @MenuRes int menuRes) {
        PopupMenu popupMenu = new PopupMenu(activity, null);
        Menu menu = popupMenu.getMenu();
        activity.getMenuInflater().inflate(menuRes, menu);

        int menuSize = menu.size();
        BottomBarTab[] tabs = new BottomBarTab[menuSize];

        for (int i = 0; i < menuSize; i++) {
            MenuItem item = menu.getItem(i);
            BottomBarTab tab = new BottomBarTab(item.getIcon(),
                    String.valueOf(item.getTitle()));
            tab.id = item.getItemId();
            tabs[i] = tab;
        }

        return tabs;
    }

}
