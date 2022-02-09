package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.OrganTreeBean;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewBinder;

/**
 * 通讯录 - 组织机构
 */

public class DirectoryNodeBinder extends TreeViewBinder<DirectoryNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        holder.ivArrow.setRotation(0);
        holder.ivArrow.setImageResource(R.mipmap.ic_keyboard_arrow_right_black_18dp);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.ivArrow.setRotation(rotateDegree);
        OrganTreeBean organTreeBeanNode = (OrganTreeBean) node.getContent();
        holder.tvName.setText(organTreeBeanNode.dirName);
        if (node.isLeaf())
            holder.ivArrow.setVisibility(View.INVISIBLE);
        else holder.ivArrow.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.organ_address_item_org;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        private AppCompatImageView ivArrow;
        private AppCompatTextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = (AppCompatImageView) rootView.findViewById(R.id.iv_arrow);
            this.tvName = (AppCompatTextView) rootView.findViewById(R.id.tv_tree_name);
        }

        public ImageView getIvArrow() {
            return ivArrow;
        }

        public TextView getTvName() {
            return tvName;
        }
    }
}
