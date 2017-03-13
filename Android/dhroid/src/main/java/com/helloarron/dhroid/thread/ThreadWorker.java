package com.helloarron.dhroid.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Dialog;

import com.helloarron.dhroid.Const;
import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.IocContainer;

public class ThreadWorker {
    static ExecutorService executorService;

    /**
     * 线程池里跑runnable
     *
     * @param runnable
     * @return
     */
    public static Future<?> executeRunalle(Runnable runnable) {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(Const.net_pool_size);
        }
        return executorService.submit(runnable);
    }

    public static void execuse(boolean dialog, final Task task) {
        if (dialog) {
            IDialog diagloer = IocContainer.getShare().get(IDialog.class);
            Dialog pd = diagloer.showProgressDialog(task.mContext);
            pd.setCancelable(false);
            task.dialog = pd;
        }
        executeRunalle(new Runnable() {
            @Override
            public void run() {
                try {
                    task.doInBackground();
                } catch (Exception e) {
                    task.transfer(null, Task.TRANSFER_DOERROR);
                    return;
                }
                task.transfer(null, Task.TRANSFER_DOUI);
            }
        });
    }

}
