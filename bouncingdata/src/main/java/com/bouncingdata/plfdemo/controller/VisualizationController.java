package com.bouncingdata.plfdemo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.bouncingdata.plfdemo.datastore.pojo.model.Analysis;
import com.bouncingdata.plfdemo.datastore.pojo.model.User;
import com.bouncingdata.plfdemo.datastore.pojo.model.UserActionLog;
import com.bouncingdata.plfdemo.service.ApplicationStoreService;
import com.bouncingdata.plfdemo.service.DatastoreService;

@Controller
@RequestMapping(value = { "/visualize", "/public" })
public class VisualizationController {

  private Logger                  logger = LoggerFactory.getLogger(VisualizationController.class);

  @Autowired
  private DatastoreService        datastoreService;

  @Autowired
  private ApplicationStoreService appStoreService;

  @RequestMapping(value = "/app/{guid}/{vGuid}/{type}", method = RequestMethod.GET)
  public String get(@PathVariable String guid, @PathVariable String vGuid, @PathVariable String type, ModelMap model) {
    try {
      String content = appStoreService.getVisualization(guid, vGuid, type);
      model.addAttribute("content", content);
    } catch (IOException e) {
      // log: file not found
      logger.debug("Failed to find visualization file, appGuid: {}, vGuid: {}", guid, vGuid);
      logger.debug("", e);
    } catch (Exception e) {
      logger.debug("", e);
    }
    return "visualize";
  }

  @RequestMapping(value = "/temp/{executionId}/{name}/{type}", method = RequestMethod.GET)
  public String temp(@PathVariable String executionId, @PathVariable String name, @PathVariable String type,
      ModelMap model) {
    try {
      String content = appStoreService.getTemporaryVisualization(executionId, name, type);
      model.addAttribute("content", content);
    } catch (Exception e) {
      logger.debug("", e);
    }
    return "visualize";
  }

  @RequestMapping(value = "/replot/{guid}/{vGuid}/{type}", method = RequestMethod.GET)
  public @ResponseBody String resizeViz(@PathVariable String guid, @PathVariable String vGuid, @PathVariable String type, WebRequest request,
      ModelMap model, Principal principal) {
    User user = (User) ((Authentication) principal).getPrincipal();

    try {
      ObjectMapper logmapper = new ObjectMapper();
      String data = logmapper.writeValueAsString(new String[] { "2", vGuid, type });
      datastoreService.logUserAction(user.getId(), UserActionLog.ActionCode.RESIZE_VIZ, data);
    } catch (Exception e) {
      logger.debug("Failed to log action", e);
    }

    if (!"png".equalsIgnoreCase(type)) {
      return null;
    }

    Map<String, String[]> params = request.getParameterMap();
    if (!params.containsKey("w") || !params.containsKey("h")) {
      return null;
    }

    Analysis anls;
    try {
      anls = datastoreService.getAnalysisByGuid(guid);
      if (anls.getUser().getId() != user.getId()) {
        return null;
      }
    } catch (Exception e) {
      logger.debug("Failed to get analysis {}", guid);
      logger.debug("Exception detail", e);
      return null;
    }

    int w = 0;
    int h = 0;
    try {
      w = Integer.parseInt(params.get("w")[0]);
      h = Integer.parseInt(params.get("h")[0]);
    } catch (NumberFormatException e) {
      if (logger.isDebugEnabled()) {
        logger.debug("Resize viz. {}/{} failed. Reason: Cannot parse width and height.", guid, vGuid);
      }
      return null;
    }
    try {
      appStoreService.resizeRPlot(guid, vGuid, w, h);
      return appStoreService.getVisualization(guid, vGuid, type);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    // determine the viz. snapshot file
    // initiate a process to replay the plot
    // return when replay process finish
  }

}
