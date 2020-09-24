/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.armscomponent.commonsdk.core;

import org.simple.eventbus.EventBus;

/**
 * ================================================
 * 放置 {@link EventBus} 的 Tag ,便于检索
 * ================================================
 */


public interface EventBusTags {
    //首页跳转界面
    String JUMP_PAGE = "jump_page";
    String ZERO = "0";
    String ONE = "1";
    String TWO = "2";
    String THREE = "3";
    //日夜切换
    String DAY_NIGHT = "day_night";

    //搜索
    String SEARCH_RFID = "search_rfid";
    String SEARCH_RFID2 = "search_rfid2";
    String INFO_SEARCH_RFID_LIST = "search_rfid_list";
//盘点 界面刷新
    String CHECK = "check";
    //home 当前 、首页、历史界面刷新
    String REFRESH = "refresh";

    //扫描标签
    String SCANRFID = "SCANRFID";

}
