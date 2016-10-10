package cn.demonk.roboding.recyclerviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collections;

/**
 * Created by ligs on 10/8/16.
 */
public class MyRecyclerView extends RecyclerView {

    private ItemTouchHelper helper = null;
    private MyAdapter adapter = null;

    public MyRecyclerView(Context context) {
        super(context);
        init();
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private int lastVisibleItem = 0;

    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.adapter = (MyAdapter) adapter;
    }

    private void init() {
        helper = new ItemTouchHelper(touchHelperCallback);
        helper.attachToRecyclerView(this);

        this.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        this.setLayoutManager(new GridLayoutManager(this.getContext(),4));
//        this.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));

        this.setItemAnimator(new DefaultItemAnimator());

        this.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            }
        });

        this.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 2 >= recyclerView.getLayoutManager().getItemCount()) {
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    private ItemTouchHelper.Callback touchHelperCallback = new ItemTouchHelper.Callback() {

        //用于设置拖拽和滑动的方向
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0, swipeFlags = 0;
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager || recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                //网格式布局有4个方向
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                //线性式布局有2个方向
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END; //设置侧滑方向为从两个方向都可以
            }
            return makeMovementFlags(dragFlags, swipeFlags);//swipeFlags 为0的话item不滑动
        }

        //长摁item拖拽时会回调这个方法
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            Collections.swap(adapter.mDataList, from, to);//交换数据链表中数据的位置
            adapter.notifyItemMoved(from, to);//更新适配器中item的位置

            DLog.d("drag from " + from + ", to " + to);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //这里处理滑动删除
            DLog.d("delete:" + direction);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;//返回true则为所有item都设置可以拖拽
        }

        //当item拖拽开始时调用
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);//拖拽时设置背景色为灰色
            }
        }

        //当item拖拽完成时调用
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(Color.WHITE);//拖拽停止时设置背景色为白色
        }

        //当item视图变化时调用
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            //根据item滑动偏移的值修改item透明度。screenwidth是我提前获得的屏幕宽度
            int screenwidth = 1080;
            viewHolder.itemView.setAlpha(1 - Math.abs(dX) / screenwidth);
        }
    };
}
