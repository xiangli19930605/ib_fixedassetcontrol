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

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idealbank.ib_secretassetcontrol.R;
import com.idealbank.ib_secretassetcontrol.mvp.ui.view.ExpandableViewHoldersUtil;
import com.idealbank.ib_secretassetcontrol.mvp.ui.view.HiddenAnimUtils;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.LogUtils;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;


/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AssetAdapter extends BaseQuickAdapter<AssetsBean, AssetAdapter.MyViewHolder> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    ExpandableViewHoldersUtil.KeepOneH<MyViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<>();
    public AssetAdapter(@Nullable List data) {
        super(R.layout.item_rv_asset, data);
    }

    @Override
    protected void convert(MyViewHolder helper, AssetsBean data) {

        ((SuperTextView) helper.getView(R.id.stv_id)).setCenterString(data.getAssetName()).setLeftString(data.getAssets());
        Drawable drawableLeft = mContext.getResources().getDrawable(
                R.mipmap.ic_gantan);

        if (data.getCheckState() == 0) {
            drawableLeft = mContext.getResources().getDrawable(
                    R.mipmap.ic_gantan);
            ((TextView) helper.getView(R.id.tv_state)).setText("未盘点");
            ((TextView) helper.getView(R.id.tv_state)).setTextColor(mContext.getResources().getColor(R.color.red));

        } else {
            drawableLeft = mContext.getResources().getDrawable(
                    R.mipmap.ic_yes);
            ((TextView) helper.getView(R.id.tv_state)).setText("已盘点");
            ((TextView) helper.getView(R.id.tv_state)).setTextColor(mContext.getResources().getColor(R.color.light_green));
        }
        ((TextView) helper.getView(R.id.tv_state)).setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null
                ,null ,null );

        ((SuperTextView) helper.getView(R.id.stv_typeName)).setCenterString(data.getTypeName());
        ((SuperTextView) helper.getView(R.id.stv_remark)).setCenterString(data.getRemark());
        ((SuperTextView) helper.getView(R.id.stv_brand)).setCenterString(data.getBrand());
        ((SuperTextView) helper.getView(R.id.stv_spec)).setCenterString(data.getSpec());
        ((SuperTextView) helper.getView(R.id.stv_location)).setCenterString(data.getLocation());
        //资产使用状态   0在库1在用2毁坏不能用3其他
        String AssetUserState="";
        if(data.getAssetUserState()==0){
            AssetUserState="在库";
        }else if(data.getAssetUserState()==1){
            AssetUserState="在用";
        }else if(data.getAssetUserState()==2){
            AssetUserState="毁坏不能用";
        }else if(data.getAssetUserState()==3){
            AssetUserState="其他";
        }
        ((SuperTextView) helper.getView(R.id.stv_checkState)).setCenterString(AssetUserState);
        ((SuperTextView) helper.getView(R.id.stv_belongName)).setCenterString(data.getBelongName());
        ((SuperTextView) helper.getView(R.id.stv_cueUser)).setCenterString(data.getCurName());

        helper.addOnClickListener(R.id.stv_remark).addOnLongClickListener(R.id.ll_content);

        helper.getView(R.id.ll_expand) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                keepOne.toggle(helper);
//                HiddenAnimUtils.newInstance(mContext,xxx,down,77).toggle();

                //item 隐藏展开效果
                LogUtils.warnInfo("data.getIsExand()"+data.getIsExand());
                if(data.getIsExand()){
//                     ExpandableViewHoldersUtil.closeH(helper,(LinearLayout) helper.getView(R.id.content),true);
//                    helper.getView(R.id.content).setVisibility(View.GONE);
                    HiddenAnimUtils.fadeOut( helper.getView(R.id.content));
                     data.setIsExand(false);
                    ((ImageView)helper.getView(R.id.img_arrow)).setImageResource(R.mipmap.ic_arrow_down);


                }else{
//                    ExpandableViewHoldersUtil.openH(helper,(LinearLayout) helper.getView(R.id.content),true);
//                    helper.getView(R.id.content).setVisibility(View.VISIBLE);
                    HiddenAnimUtils.fadeIn( helper.getView(R.id.content));
                    data.setIsExand(true);
                    ((ImageView)helper.getView(R.id.img_arrow)).setImageResource(R.mipmap.ic_arrow_up);
                }
            }
        });

        if(data.getIsExand()){
            HiddenAnimUtils.fadeIn( helper.getView(R.id.content));
            ((ImageView)helper.getView(R.id.img_arrow)).setImageResource(R.mipmap.ic_arrow_up);
        }else{
            HiddenAnimUtils.fadeOut( helper.getView(R.id.content));
            ((ImageView)helper.getView(R.id.img_arrow)).setImageResource(R.mipmap.ic_arrow_down);
        }

    }

    public class MyViewHolder extends BaseViewHolder implements ExpandableViewHoldersUtil.Expandable{
        private LinearLayout content;

        public MyViewHolder(View itemView) {
            super(itemView);
            content = ((LinearLayout) itemView.findViewById(R.id.content));
        }

        @Override
        public View getExpandView() {
            return content;
        }

    }




}