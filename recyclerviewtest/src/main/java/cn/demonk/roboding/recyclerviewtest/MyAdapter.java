package cn.demonk.roboding.recyclerviewtest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ligs on 10/8/16.
 */
public class MyAdapter<T extends List<Pair<String, String>>> extends RecyclerView.Adapter<MyViewHolder> {
    T mDataList = null;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (mDataList != null) {
            Pair<String, String> data = mDataList.get(position);
            final String simple = data.first;
            final String hint = data.second;

//            if (position % 2 == 0) {
//                new AsyncTask<Void, Void, String>() {
//
//                    @Override
//                    protected String doInBackground(Void... params) {
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return hint;
//                    }
//
//                    protected void onPostExecute(String bitmap) {
//                        super.onPostExecute(bitmap);
//                        holder.mSimpleText.setText(simple);
//                        holder.mHintText.setText(hint);
//                    }
//                }.execute();
//            }else{
            holder.mSimpleText.setText(simple);
            holder.mHintText.setText(hint);

            Animator alphaAnimator = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0, 1);
            ViewCompat.setAlpha(holder.itemView, 0);
            alphaAnimator.setDuration(5000).setTarget(holder.itemView);
            alphaAnimator.start();

//            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public void setDataList(T list) {
        mDataList = list;
        notifyDataSetChanged();//reDraw可见部分
    }

    public void addItem(int position, Pair<String, String> item) {
        if (mDataList != null && position < mDataList.size()) {
            mDataList.add(position, item);
            notifyItemInserted(position);
//            notifyDataSetChanged();
        }
    }

    public void delItem(int position) {
        if (mDataList != null && position < mDataList.size()) {
            mDataList.remove(position);
            notifyItemRemoved(position);
//            notifyDataSetChanged();
        }
    }
}
