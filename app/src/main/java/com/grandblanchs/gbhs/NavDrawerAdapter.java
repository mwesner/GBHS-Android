package com.grandblanchs.gbhs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];

    static Context c;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setFocusable(true);
            itemView.setFocusableInTouchMode(true);
            textView = (TextView) itemView.findViewById(R.id.rowText);
            imageView = (ImageView) itemView.findViewById(R.id.imgBackground);
        }

    }

    NavDrawerAdapter(String Titles[],int Icons[], Context context){
        mNavTitles = Titles;
        mIcons = Icons;
        c = context;
    }

    @Override
    public NavDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_row,parent,false);

            return new ViewHolder(v);

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_header,parent,false);

            return new ViewHolder(v);


        }
        return null;

    }

    @Override
    public void onBindViewHolder(final NavDrawerAdapter.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(mIcons[position - 1], 0, 0, 0);
        } else {
            //Randomize the header image.
            Random rand = new Random();

            switch (rand.nextInt(4)) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.flag);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.hswest);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.hsfinal1);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.gbgym);
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}