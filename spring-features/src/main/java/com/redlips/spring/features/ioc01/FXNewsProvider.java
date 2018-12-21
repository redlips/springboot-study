package com.redlips.spring.features.ioc01;

import com.redlips.spring.features.ioc01.service.impl.DowJonesNewsListener;
import com.redlips.spring.features.ioc01.service.impl.DowJonesNewsPersistent;
import com.redlips.spring.features.ioc01.service.IFXNewsListener;
import com.redlips.spring.features.ioc01.service.IFXNewsPersistent;
import com.redlips.spring.features.ioc01.pojo.FXNewsBean;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 花落孤忆
 * @create 2018-10-26 16:34
 * @description FX项目的简单示例，FX全称(Foreign Exchange)外汇交易，FX通常作为交易的中间商，与上游服务商（如：花旗、道琼斯等各大银行）合作
 * 为顾客提供保证金交易服务，顾客只需要缴纳很少的保证金就可以进行大额的外汇交易。保证金交易的杠杆可以让顾客"以小博大"，只需很少的保证金，顾客就可
 * 以享受到因汇率变动而带来的收益（当然，评估方向错误也可能导致损失）
 *
 * FX系统通常需要近乎实时地为客户提供外汇新闻。通常情况下，都是先从不同的新闻社订阅新闻来源，然后通过批处理程序定时地到指定的新闻服务器抓取最新的
 * 外汇新闻，接着讲这些新闻存入本地数据库，最后在FX系统的前台页面展示。
 *
 * 假设该类是做以上工作的
 */
@Component
public class FXNewsProvider {
    public IFXNewsListener getNewsListener() {
        return newsListener;
    }

    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersistent getNewsPersistent() {
        return newsPersistent;
    }

    public void setNewsPersistent(IFXNewsPersistent newsPersistent) {
        this.newsPersistent = newsPersistent;
    }

    public String getNewsListenerBeanName() {
        return newsListenerBeanName;
    }

    public void setNewsListenerBeanName(String newsListenerBeanName) {
        this.newsListenerBeanName = newsListenerBeanName;
    }

    public String getNewsPersistentBeanName() {
        return newsPersistentBeanName;
    }

    public void setNewsPersistentBeanName(String newsPersistentBeanName) {
        this.newsPersistentBeanName = newsPersistentBeanName;
    }

    // FXNewsProvider需要依赖IFXNewsListener来抓取新闻，依赖IFXNewsPersistent来存储新闻

    // 监听新闻
    @Autowired
    private IFXNewsListener newsListener;
    // 同步新闻到库的接口
    @Autowired
    private IFXNewsPersistent newsPersistent;

    // Bean的名称
    private String newsListenerBeanName;
    // Bean的名称
    private String newsPersistentBeanName;
    /**
     * 假设默认使用（道琼斯新闻社）的新闻，我们需要提供上面两个接口的实现类，通常需要在构造函数中构造FXNewsProvider依赖的这两个类，这种被依赖
     * 的类、或对象，简称为“依赖类”、“依赖对象”。
     * 下面是构造道琼斯的两个依赖类
     */
    public FXNewsProvider() {
        newsListener = new DowJonesNewsListener();
        newsPersistent = new DowJonesNewsPersistent();
    }

    /**
     * 引入IOC模式后的：构造方法注入，这样FXNewsProvider就可以重用，就算有新的新闻来源只需要给出特定的IFXNewsListener实现即可。
     */
    public FXNewsProvider(IFXNewsListener newsListener, IFXNewsPersistent newsPersistent) {
        this.newsListener = newsListener;
        this.newsPersistent = newsPersistent;
        System.out.println("FXNewsProvider 通过构造方法构建成功了");
    }

    /**
     * 获取并存储新闻
     */
    public void getAndPersistNews() {
        // 获取可用的新闻
        String[] newsIds = newsListener.getAvailableNewsIds();
        if (ArrayUtils.isEmpty(newsIds)) {
            return;
        }

        // 存入本地数据库
        for (String newsId : newsIds) {
            FXNewsBean newsBean = newsPersistent.getNewsByPK(newsId);
            newsPersistent.persistNews(newsBean);
        }
    }
}
