package utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @ClassName KeyBoardUtils
 * @Description TODO
 * @Author lixiaozhao
 * @Date 2021/8/13 15:53
 * @Version 1.0
 */
public class KeyBoardUtils {
    // 隐藏输入法。
    public static void dismissKeyboard(Context context, EditText edit) {
        if (context == null || edit == null) {
            return;
        }
        InputMethodManager methodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isKeyShow = methodManager.isActive();
        if (isKeyShow) {
            methodManager.hideSoftInputFromWindow(
                    edit.getWindowToken(), 0);
        }
    }
    // 隐藏输入法。
    public static void dismissKeyboard(Context context, View v) {
        InputMethodManager methodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isKeyShow = methodManager.isActive();
        if (isKeyShow) {
            methodManager.hideSoftInputFromWindow(
                    v.getWindowToken(), 0);
        }
    }
    //显示键盘的方法。
    public static void showKeyboard(final EditText editText) {

        if (editText == null) {
            return;
        }

        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) editText.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        },180);

    }

    /**
     * 判断键盘是显示的方法。
     * @param context
     * @return
     */
    public static boolean isKeyboardShown(Context context) {
        InputMethodManager methodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return methodManager.isActive();
    }
}
