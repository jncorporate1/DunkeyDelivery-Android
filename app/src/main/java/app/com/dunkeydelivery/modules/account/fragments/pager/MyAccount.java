package app.com.dunkeydelivery.modules.account.fragments.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.items.UserBO;
import app.com.dunkeydelivery.modules.account.fragments.ChangePassword;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyAccount extends Fragment implements View.OnClickListener {

    private Context context;
    Unbinder unbinder;

    @BindView(R.id.tv_change_paswd)
    TextView tvChangePaswd;

    @BindView(R.id.view_separator)
    View view_separator;

    @BindView(R.id.tv_firstname)
    TextView tvFirstName;

    @BindView(R.id.tv_lastname)
    TextView tvLastName;

    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.btn_continue)
    Button btnContinue;

    private UserBO userBO;

    public static MyAccount newInstance() {
        Bundle args = new Bundle();
        MyAccount fragment = new MyAccount();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_myaccount, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // Initialize all views
        setListener();

        setUpUserDetail();

        setViews();
    }

    //show hide change password option
    private void setViews() {

        try {
            if (userBO != null && !TextUtils.isEmpty(userBO.getRole()) && (Integer.parseInt(userBO.getRole()) == EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Facebook) || Integer.parseInt(userBO.getRole()) == EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Gmail))) {
                tvChangePaswd.setVisibility(View.GONE);
                view_separator.setVisibility(View.GONE);
            } else {
                tvChangePaswd.setVisibility(View.VISIBLE);
                view_separator.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //setup user details
    private void setUpUserDetail() {
        userBO = UserSharedPreference.readUserBO();
        if (userBO != null) {
            if(!userBO.getFirstName().isEmpty() && !userBO.getLastName().isEmpty()) {
                tvFirstName.setText(userBO.getFirstName());
                tvLastName.setText(userBO.getLastName());
            }
            else
            {
                tvFirstName.setText(userBO.getFullName().split(" ")[0]);
                tvLastName.setText(userBO.getFullName().split(" ")[1]);
            }
            tvEmail.setText(userBO.getEmail());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void setListener() {
        tvChangePaswd.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_change_paswd:
                Activities.gotoNextFragment(context, ChangePassword.newInstance());
                break;
            case R.id.btn_continue:
                Fragment fragment = Activities.getCurrentFragment(context);
                if (fragment instanceof AccountViewPager) {
                    ((AccountViewPager) fragment).setPageSelected(1);
                }
                break;
        }
    }
}