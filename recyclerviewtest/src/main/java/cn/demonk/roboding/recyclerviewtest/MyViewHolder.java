package cn.demonk.roboding.recyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ligs on 10/8/16.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    private static int count = 0;

    private int curNum;
    @BindView(R.id.rec_simple)
    TextView mSimpleText;

    @BindView(R.id.rec_hint)
    TextView mHintText;

    @BindView(R.id.rec_img)
    ImageView mImageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        curNum = count++;

        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.rec_simple)
    void simpleClick(View v) {
        DLog.d("Simple click");
    }

    @OnClick(R.id.rec_hint)
    void hintClick(View v) {
        DLog.d("Hint click");
    }

    public String toString() {
        return "viewholder num=" + curNum;
    }
}
