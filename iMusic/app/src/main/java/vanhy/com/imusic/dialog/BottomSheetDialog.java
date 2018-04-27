package vanhy.com.imusic.dialog;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vanhy.com.imusic.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetDialog extends BottomSheetDialogFragment {


    public BottomSheetDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet, container, false);
    }

}
