package com.github.limboc.sample.presenter.iview;

import java.util.List;

/**
 * Created by Chen on 2016/3/15.
 */
public interface IMainView extends IBaseView{

    void onLoadDataSuccess(List<Object> objectList);

}
