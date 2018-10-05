package com.jinhui.irecyclerview.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jinhui.irecyclerview.R;
import com.jinhui.irecyclerview.model.Image;
import com.jinhui.irecyclerview.ui.activity.MainActivity;
import com.jinhui.library.IViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jh on 2018/10/4.
 * Email: 1004260403@qq.com
 */
public class ImageAdapter extends RecyclerView.Adapter<IViewHolder> {

    private List<Image> mImages;

    private OnItemClickListener<Image> mOnItemClickListener;

    public ImageAdapter() {
        mImages = new ArrayList<>();
    }

    @NonNull
    @Override
    public IViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_item, parent, false);
        final ViewHolder holder = new ViewHolder(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Note:
                 * in order to get the right position, you must use the method with i- prefix in
                 * {@link IViewHolder} eg:
                 * {@code IViewHolder.getIPosition()}
                 * {@code IViewHolder.getILayoutPosition()}
                 * {@code IViewHolder.getIAdapterPosition()}
                 */
//                int position = holder.getAdapterPosition();  // 位置?
                int position = holder.getIAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    Image image = mImages.get(position);
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(position, image, view);
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.itemView;
        Image image = mImages.get(position);
        // 3.8.0
//        Glide.with(imageView.getContext()).load(image.image).dontAnimate().into(imageView);
        // 4.8.0
        Glide.with(imageView.getContext()).load(image.image).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public void setOnItemClickListener(OnItemClickListener<Image> listener) {
        this.mOnItemClickListener = listener;
    }

    public void clear() {
        mImages.clear();
        notifyDataSetChanged();
    }

    public void setList(List<Image> images) {
        mImages.clear();
        append(images);
    }

    public void append(List<Image> images) {
        int positionStart = mImages.size();
        int itemCount = images.size();
        mImages.addAll(images);
        if (positionStart > 0 && itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mImages.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends IViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
