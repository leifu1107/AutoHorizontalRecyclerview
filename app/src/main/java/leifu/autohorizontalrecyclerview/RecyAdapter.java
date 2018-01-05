package leifu.autohorizontalrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Integer> datas;
    private OnItemClickListener onItemClickListener;

    public RecyAdapter(Context context, List<Integer> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iv_img.setImageResource(datas.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    interface OnItemClickListener {
        void onItemClick(View view, int tag);
    }
}

