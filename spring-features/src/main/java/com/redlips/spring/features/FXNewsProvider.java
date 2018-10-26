package com.redlips.spring.features;

import com.redlips.spring.features.ioc01.DowJonesNewsListener;
import com.redlips.spring.features.ioc01.DowJonesNewsPersistent;
import org.apache.commons.lang3.ArrayUtils;

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
public class FXNewsProvider {
    // FXNewsProvider需要依赖IFXNewsListener来抓取新闻，依赖IFXNewsPersistent来存储新闻

    // 监听新闻
    private IFXNewsListener newsListener;
    // 同步新闻到库的接口
    private IFXNewsPersistent newsPersistent;

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
     * 获取并存储新闻
     */
    private void getAndPersistNews() {
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
