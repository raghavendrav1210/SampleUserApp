package com.tn.tnparty.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.tn.tnparty.R;

/**
 * Created by PH052323 on 12/30/2017.
 */

public class GridButtonsAdapter extends BaseAdapter {

    private static final String[] buttonNames = new String[]{"Add User", "Search User", "DemoButton1", "DemoButton2"};
    private static final int[] buttonImages = new int[]{R.drawable.add_user_icon, R.drawable.user_search, 0, 0};
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public GridButtonsAdapter(Context context, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return buttonNames.length;
    }

    @Override
    public Object getItem(int i) {
        return buttonNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row = convertView;
        Button button = null;
        if (null == row) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.buttons_layout, parent, false);
        }
        button = row.findViewById(R.id.optionButton);
        button.setOnClickListener(mOnClickListener);
        button.setText(String.valueOf(buttonNames[i]));
        button.setCompoundDrawablesWithIntrinsicBounds(0, buttonImages[i], 0, 0);
        return row;
    }
}
