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

import java.util.List;

import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.utils.KeywordUtil;


/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class SearchAssetAdapter extends BaseQuickAdapter<AssetsBean, BaseViewHolder> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    String keyword;

    public SearchAssetAdapter(@Nullable List data) {
        super(R.layout.item_rv_search_asset, data);
    }

    public void setKey(String key) {
        keyword = key;
    }

    @Override
    protected void convert(BaseViewHolder helper, AssetsBean data) {
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
        ((TextView) helper.getView(R.id.tv_state)).setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null
                , null, null);


        ((SuperTextView) helper.getView(R.id.stv_typeName)).setCenterString(data.getTypeName());
        ((SuperTextView) helper.getView(R.id.stv_remark)).setCenterString(data.getRemark());
        ((SuperTextView) helper.getView(R.id.stv_brand)).setCenterString(data.getBrand());
        ((SuperTextView) helper.getView(R.id.stv_spec)).setCenterString(data.getSpec());
        ((SuperTextView) helper.getView(R.id.stv_location)).setCenterString(data.getLocation());
        ((SuperTextView) helper.getView(R.id.stv_belongName)).setCenterString(data.getBelongName());
        ((SuperTextView) helper.getView(R.id.stv_cueUser)).setCenterString(data.getCueUser());
        ((SuperTextView) helper.getView(R.id.stv_checkState)).setCenterString("" + data.getCheckState());

        if (KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getAssets(), keyword) != null) {
            ((SuperTextView) helper.getView(R.id.stv_id)).setLeftString(KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getAssets(), keyword));
        } else {
            ((SuperTextView) helper.getView(R.id.stv_id)).setLeftString(data.getAssets());
        }
        ((SuperTextView) helper.getView(R.id.stv_id)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getAssetName(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getAssetName(), keyword) : data.getAssetName());

        ((SuperTextView) helper.getView(R.id.stv_typeName)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getTypeName(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getTypeName(), keyword) : data.getTypeName());
        ((SuperTextView) helper.getView(R.id.stv_remark)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getRemark(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getRemark(), keyword) : data.getRemark());
        ((SuperTextView) helper.getView(R.id.stv_brand)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getBrand(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getBrand(), keyword) : data.getBrand());
        ((SuperTextView) helper.getView(R.id.stv_spec)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getSpec(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getSpec(), keyword) : data.getSpec());
        ((SuperTextView) helper.getView(R.id.stv_location)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getLocation(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getLocation(), keyword) : data.getLocation());
        ((SuperTextView) helper.getView(R.id.stv_belongName)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getBelongName(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getBelongName(), keyword) : data.getBelongName());
        ((SuperTextView) helper.getView(R.id.stv_cueUser)).setCenterString((KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getCueUser(), keyword) != null) ? KeywordUtil.matcherSearchTitle(R.color.colorPrimary, data.getCueUser(), keyword) : data.getCueUser());

        helper.addOnClickListener(R.id.stv_remark);

    }


}