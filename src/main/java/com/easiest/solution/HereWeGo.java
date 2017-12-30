package com.easiest.solution;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HereWeGo {

  private static final Logger logger = LoggerFactory.getLogger(HereWeGo.class);


  public static void main(String[] args) throws Exception {
    String crawlStorageFolder = System.getProperty("java.io.tmpdir");
    int numberOfCrawlers = 7;

    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(crawlStorageFolder);
    config.setCleanupDelaySeconds(2);
    config.setThreadShutdownDelaySeconds(2);
    config.setThreadMonitoringDelaySeconds(2);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    controller.addSeed("https://jprof.by/");

    controller.start(JprofCrawler.class, numberOfCrawlers);

    controller.waitUntilFinish();

    logger.error("\n!!!--------------------------------\n\nHERE IS THE ANSWER: Java entrance count = " +
        AnswerHolder.javaEntranceCount +
        "\n\n--------------------------------!!!"
    );
  }
}
