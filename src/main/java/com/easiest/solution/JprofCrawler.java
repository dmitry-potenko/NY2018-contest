package com.easiest.solution;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rx.Observable;
import rx.schedulers.Schedulers;

public class JprofCrawler extends WebCrawler {

  private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
      + "|png|mp3|mp4|zip|gz))$");

  private final Pattern javaPattern = Pattern.compile("java", Pattern.CASE_INSENSITIVE);

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase();
    return !FILTERS.matcher(href).matches()
        && href.contains("jprof.by");
  }


  @Override
  public void visit(Page page) {
    String url = page.getWebURL().getURL();
    logger.info("URL: " + url);

    if (page.getParseData() instanceof HtmlParseData) {
      HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
      String text = htmlParseData
          .getHtml();  //solution with html analyzing can be easily changed with htmlParseData.getText()

      Observable.just(text)
          .subscribeOn(Schedulers.computation())
          .subscribe(this::countJava);
    }
  }

  private void countJava(String content) {
    Matcher matcher = javaPattern.matcher(content);

    while (matcher.find()) {
      AnswerHolder.javaEntranceCount.incrementAndGet();
    }

  }
}
