package cn.demonk.roboding.recyclerviewtest;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @BindView(R.id.rec_btn_add)
    Button mBtnAdd;

    @BindView(R.id.rec_btn_del)
    Button mBtnDel;

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAdapter = new MyAdapter();
        ArrayList<Pair<String, String>> list = genData(20);
        mAdapter.setDataList(list);


        mRecyclerView.setAdapter(mAdapter);
    }


    @OnClick(R.id.rec_btn_add)
    void addItem(View v) {
        Pair<String, String> item = new Pair<>("newItem simple", "newItem hint");
        mAdapter.addItem(2, item);
    }

    @OnClick(R.id.rec_btn_del)
    void delItem(View v) {
        mAdapter.delItem(0);
    }

    private ArrayList<Pair<String, String>> genData(int size) {
        ArrayList<Pair<String, String>> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Pair<String, String> pair = new Pair<>("" + i, "This's " + i);
            list.add(pair);
        }
        return list;
    }
}
