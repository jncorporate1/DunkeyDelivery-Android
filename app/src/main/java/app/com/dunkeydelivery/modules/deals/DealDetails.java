package app.com.dunkeydelivery.modules.deals;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.deals.items.OfferItem;
import app.com.dunkeydelivery.modules.deals.items.Package;

public class DealDetails extends Fragment {

    private static String ARG_PARAM1="offerItem";
    private static String ARG_PARAM2="package";

    public DealDetails() {
        // Required empty public constructor
    }
    public static DealDetails newInstance(OfferItem offerItem,Package packages) {
        DealDetails fragment = new DealDetails();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,offerItem);
        args.putParcelable(ARG_PARAM2,packages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deal_details2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null)
        {
        }

    }
}
