/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idealbank.ib_secretassetcontrol.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.idealbank.ib_secretassetcontrol.R;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;


/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>当前盘查
 * <p>
 * ================================================
 */
public class HistoryAdapter extends BaseQuickAdapter<TaskBean, BaseViewHolder> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;
    public HistoryAdapter(@Nullable List data) {
        super(R.layout.item_rv_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskBean data) {

        if (data.getCheckType() == 0) {
            //责任人
            ((ImageView) helper.getView(R.id.img_checkType)).setImageResource(R.mipmap.ic_type_person);
            ((SuperTextView) helper.getView(R.id.stv_curUser)).setLeftString("负责人：").setCenterString(data.getCurUser()).setLeftTvDrawableLeft(mContext.getResources().getDrawable(
                    R.mipmap.ic_person));
        } else {
            //部门
            ((ImageView) helper.getView(R.id.img_checkType)).setImageResource(R.mipmap.ic_type_department);
            ((SuperTextView) helper.getView(R.id.stv_curUser)).setLeftString("部    门：").setCenterString(data.getCurUser()).setCenterString(data.getBelongDeptName()).setLeftTvDrawableLeft(mContext.getResources().getDrawable(
                    R.mipmap.department));
        }
        ((SuperTextView) helper.getView(R.id.stv_id)).setCenterString(data.getId());
        ((SuperTextView) helper.getView(R.id.stv_name)).setLeftString(data.getCheckName());
        ((SuperTextView) helper.getView(R.id.stv_createName)).setCenterString(data.getCreateName());
        ((SuperTextView) helper.getView(R.id.stv_total)).setLeftString("" + data.getCheckNum()).setCenterString("/" + data.getTotal());

        if(data.getTotal()>data.getCheckNum()){
            ((SuperTextView) helper.getView(R.id.stv_total)).setLeftTvDrawableLeft(mContext.getResources().getDrawable(
                    R.mipmap.ic_exclamatory));
        }else{
            ((SuperTextView) helper.getView(R.id.stv_total)).setLeftTvDrawableLeft(mContext.getResources().getDrawable(
                    R.mipmap.ic_ok));
        }

        if (mEditMode == MYLIVE_MODE_CHECK) {
            ((ImageView) helper.getView(R.id.check_box)).setVisibility(View.GONE);
        } else {
            ((ImageView) helper.getView(R.id.check_box)).setVisibility(View.VISIBLE);
            if (data.isSelect()) {
                ((ImageView) helper.getView(R.id.check_box)).setImageResource(R.mipmap.ic_checked);
            } else {
                ((ImageView) helper.getView(R.id.check_box)).setImageResource(R.mipmap.ic_uncheck);
            }
        }
        helper.addOnClickListener(R.id.content_main).addOnClickListener(R.id.right_menu_1).addOnClickListener(R.id.check_box);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

}