package app.com.dunkeydelivery.activities;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class Activities {


    public static void selectTab(Context context, int position) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setSelectedTab(position);
        }
    }

    public static void lockDrawer(Context context) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).lockDrawer();
        }
    }

    public static void unLockDrawer(Context context) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).unlockDrawer();
        }
    }

    public static void goBackFragment(Context context, int count) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).goBackFragment(count);
        }
    }

    public static Fragment getVisibleFragment(Context context) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            return ((MainActivity) activity).getVisibleFragment(activity);
        }
        return null;
    }

    public static Fragment getCurrentFragment(Context context) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            return ((MainActivity) activity).getCurrentFragment();
        }
        return null;
    }

    public static void goBackFragmentWithAnimation(Context context, int count) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).goBackFragmentWithAnimation(count);
        }
    }

    public static void removeAllFragments(Context context) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).removeAllFragments();
        }
    }

    public static void removeAllFragmentsImmediate(Context context) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).removeAllFragmentsImmediate();
        }
    }

    public static void gotoNextFragment(Context context, Fragment fragment) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
//            ((MainActivity) activity).hideBottomNavigation();
            ((MainActivity) activity).gotoNextFragment(fragment);
        }
    }

    public static void gotoNextFragmentWithTransition(Context context, Fragment fragment) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
//            ((MainActivity) activity).hideBottomNavigation();
            ((MainActivity) activity).gotoNextFragmentWithAnimationTransition(fragment);
        }
    }

    public static void gotoNextWithAddFragment(Context context, Fragment fragment) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
//            ((MainActivity) activity).hideBottomNavigation();
            ((MainActivity) activity).gotoNextWithAddFragment(fragment);
        }
    }

    public static void gotoNextFragmentNoAnimation(Context context, Fragment fragment) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).gotoNextFragmentNoAnimation(fragment);
        }
    }


    public static void gotoNextFragmentWithAnimation(Context context, Fragment fragment,
                                                     int enterAnim, int exitAnim, int popEnter,
                                                     int popExit) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).gotoNextFragmentWithAnimation(fragment, enterAnim, exitAnim, popEnter, popExit);
        }
    }

    public static void gotoSkipFragment(Context context, Fragment fragment) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).gotoSkipFragment(fragment);
        }
    }

    public static void removeFragment(Context context, Fragment fragment) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).removeFragment(fragment);
        }
    }


    // this method is used to change status bar color and navigation bar color
    public static void changeThemeColor(Context context, int navigationBarColor, int statusBarColor) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(navigationBarColor);
            window.setStatusBarColor(statusBarColor);
        }

    }

    public static void hideBottomNavigation(Context context, boolean isHide) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            if (isHide) {
                ((MainActivity) activity).hideBottomNavigation();
            } else {
                ((MainActivity) activity).showBottomNavigation();
            }
        }
    }


    public static void setSelectedTab(Context context, int pageIndex) {
        AppCompatActivity activity = ((AppCompatActivity) context);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setSelectedTab(pageIndex);
        }
    }


}
