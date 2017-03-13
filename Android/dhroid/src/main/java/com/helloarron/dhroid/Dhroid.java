package com.helloarron.dhroid;

import com.helloarron.dhroid.dialog.DialogImpl;
import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.Instance;
import com.helloarron.dhroid.ioc.IocContainer;

/**
 * 完成一些系统的初始化的工作
 * @author Administrator
 *
 */
public class Dhroid {
	public void init(){
		//对话框的配置
		IocContainer.getShare().bind(DialogImpl.class).to(IDialog.class).scope(Instance.InstanceScope.SCOPE_PROTOTYPE);
	}
}
