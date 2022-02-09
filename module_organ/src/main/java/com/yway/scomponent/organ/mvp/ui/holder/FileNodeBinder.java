package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.UserTreeBean;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewBinder;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class FileNodeBinder extends TreeViewBinder<FileNodeBinder.ViewHolder> {

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        UserTreeBean userTreeBeanNode = (UserTreeBean) node.getContent();
        holder.tvName.setText(userTreeBeanNode.fileName);
        holder.tvUserOffice.setText(userTreeBeanNode.fileName);

    }

    @Override
    public int getLayoutId() {
        return R.layout.organ_address_item_user;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public AppCompatTextView tvName;
        public AppCompatTextView tvUserOffice;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (AppCompatTextView) rootView.findViewById(R.id.tv_user_name);
            tvUserOffice = rootView.findViewById(R.id.tv_user_office);
        }

    }
}
