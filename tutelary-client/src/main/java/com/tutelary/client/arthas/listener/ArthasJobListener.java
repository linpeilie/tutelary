package com.tutelary.client.arthas.listener;

import com.taobao.arthas.core.shell.session.Session;
import com.taobao.arthas.core.shell.system.Job;
import com.taobao.arthas.core.shell.system.JobListener;

public class ArthasJobListener implements JobListener {
    private Session session;

    public ArthasJobListener(Session session) {
        this.session = session;
    }

    @Override
    public void onForeground(Job job) {
        session.setForegroundJob(job);
    }

    @Override
    public void onBackground(Job job) {
        if (session.getForegroundJob() == job) {
            session.setForegroundJob(null);
        }
    }

    @Override
    public void onTerminated(Job job) {
        if (session.getForegroundJob() == job) {
            session.setForegroundJob(null);
        }
    }

    @Override
    public void onSuspend(Job job) {
        if (session.getForegroundJob() == job) {
            session.setForegroundJob(null);
        }
    }
}
