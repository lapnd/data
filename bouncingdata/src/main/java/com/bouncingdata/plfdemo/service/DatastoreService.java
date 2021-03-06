package com.bouncingdata.plfdemo.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.bouncingdata.plfdemo.datastore.pojo.dto.SearchResult;
import com.bouncingdata.plfdemo.datastore.pojo.dto.UserInfo;
import com.bouncingdata.plfdemo.datastore.pojo.model.Activity;
import com.bouncingdata.plfdemo.datastore.pojo.model.Analysis;
import com.bouncingdata.plfdemo.datastore.pojo.model.AnalysisDataset;
import com.bouncingdata.plfdemo.datastore.pojo.model.AnalysisVote;
import com.bouncingdata.plfdemo.datastore.pojo.model.BcDataScript;
import com.bouncingdata.plfdemo.datastore.pojo.model.Comment;
import com.bouncingdata.plfdemo.datastore.pojo.model.CommentVote;
import com.bouncingdata.plfdemo.datastore.pojo.model.DataCollection;
import com.bouncingdata.plfdemo.datastore.pojo.model.Dataset;
import com.bouncingdata.plfdemo.datastore.pojo.model.DatasetVote;
import com.bouncingdata.plfdemo.datastore.pojo.model.ReferenceDocument;
import com.bouncingdata.plfdemo.datastore.pojo.model.Scraper;
import com.bouncingdata.plfdemo.datastore.pojo.model.Tag;
import com.bouncingdata.plfdemo.datastore.pojo.model.User;
import com.bouncingdata.plfdemo.datastore.pojo.model.Visualization;

/**
 * @author khiem
 *
 */
public interface DatastoreService {
  
  /**
   * Retrives list of dataset owe buy a specific user
   * @param userId the user id
   * @return <code>List</code> of <code>Dataset</code>s
   * @throws DataAccessException
   */
  public List<Dataset> getDatasetList(int userId) throws Exception;
  
  public Dataset getDatasetByName(String fullname) throws Exception;
  
  /**
   * Retrieves list of analysis by this user
   * @param userId the user id
   * @return <code>List</code> of <code>Analysis</code>s
   * @throws DataAccessException
   */
  public List<Analysis> getAnalysisList(int userId) throws Exception;
  
  /**
   * Gets the Analysis buy its id
   * @param appId
   * @return
   * @throws Exception
   */
  public Analysis getAnalysis(int appId) throws Exception;
  
  /**
   * Gets the Analysis buy its guid
   * @param guid
   * @return
   * @throws Exception
   */
  public Analysis getAnalysisByGuid(String guid) throws Exception;
  
  /**
   * @param guid
   * @return
   * @throws Exception
   */
  public Dataset getDatasetByGuid(String guid) throws Exception;
  
  /**
   * @param name
   * @param description
   * @param language
   * @param author
   * @param authorName
   * @param lineCount
   * @param isPublished
   * @param tags
   * @return the id of newly created Analysis
   * @throws Exception
   */
  public String createBcDataScript(BcDataScript script, String type) throws Exception;
  
  /**
   * @param Analysis
   * @throws Exception
   */
  public void updateAnalysis(Analysis Analysis) throws Exception;
  
  public void updateScraper(Scraper scraper) throws Exception;
  
  public boolean updateDataset(Dataset dataset) throws Exception;
  
  public void deleteAnalysis(int analysisId) throws Exception;
  
  /**
   * @param appId
   * @return
   * @throws Exception
   */
  public List<AnalysisDataset> getAnalysisDatasets(int appId) throws Exception;
  
  /**
   * @param appId
   * @return
   * @throws Exception
   */
  public List<Visualization> getAnalysisVisualizations(int appId) throws Exception;
    
  /**
   * @param query
   * @return
   * @throws Exception
   */
  public SearchResult search(String query) throws Exception;
  
  /**
   * Creates new user.
   * @param user the <code>User</code> instance
   * @throws Exception
   */
  public void createUser(User user) throws Exception;
  
  /**
   * @param userId
   * @return
   * @throws Exception
   */
  public User getUser(int userId) throws Exception;
  
  /**
   * @param username
   * @return
   */
  public User findUserByUsername(String username);
  
  /**
   * @param email
   * @return
   */
  public User findUserByEmail(String email);
  
  /**
   * @param dataset
   * @return
   * @throws Exception
   */
  public String readDataset(String dataset) throws Exception;
  
  /**
   * @param appId
   * @return
   * @throws Exception
   */
  public Map<String, String> getDataSetMap(int appId) throws Exception;
  
  public void createVisualization(Visualization visualization) throws Exception;
  
  public void updateDashboard(String guid, String status) throws Exception;
  
  //public void createDashboard(String guid, String status) throws Exception;
  
  /**
   * @param Analysis
   * @throws Exception
   */
  public void invalidateViz(Analysis Analysis) throws Exception;
  
  /**
   * @param analysisId
   * @return
   * @throws Exception
   */
  public List<Comment> getComments(int analysisId) throws Exception;
  
  /**
   * @param userId
   * @param analysisId
   * @param comment
   * @throws Exception
   */
  public void addComment(int userId, int analysisId, Comment comment) throws Exception;
  
  /**
   * @param commentId
   * @return
   * @throws Exception
   */
  public Comment getComment(int commentId) throws Exception;
  
  /**
   * @param userId
   * @param commentId
   * @return
   * @throws Exception
   */
  public CommentVote getCommentVote(int userId, int commentId) throws Exception;
  
  /**
   * @param userId
   * @param commentId
   * @param commentVote
   * @throws Exception
   */
  public void addCommentVote(int userId, int commentId, CommentVote commentVote) throws Exception;
  
  /**
   * @param userId
   * @param commentId
   * @throws Exception
   */
  public void removeCommentVote(int userId, int commentId) throws Exception;
  
  /**
   * @param user
   * @param analysis
   * @throws Exception
   */
  public void publishAnalysis(User user, Analysis analysis, boolean value) throws Exception;
  
  /**
   * @param user
   * @param analysis
   * @throws Exception
   */
  public void doPublishAction(User user, BcDataScript script) throws Exception;
  
  public boolean addAnalysisVote(int usreId, int analysisId, AnalysisVote analysisVote) throws Exception;
  
  /**
   * @param userId
   * @return
   * @throws Exception
   */
  public List<Activity> getRecentFeed(int userId) throws Exception;
  
  public List<Activity> getMoreFeed(int userId, int lastId) throws Exception;
  
  /**
   * @param userId
   * @return
   * @throws DataAccessException
   */
  public List<UserInfo> getFollowers(int userId) throws Exception;
  
  /**
   * @param userId
   * @return
   * @throws Exception
   */
  public List<UserInfo> getFollowingUsers(int userId) throws Exception;
  
  public List<UserInfo> findFriends(User finder, String query) throws Exception;
  
  public void createFollowing(int followerId, int targetId) throws Exception;
  
  public void removeFollowing(int followerId, int targetId) throws Exception;
  
  public void createDataset(Dataset dataset) throws Exception;
  
  public void createDatasets(List<Dataset> datasets) throws Exception;
  
  public void invalidateDataset(Scraper scraper) throws Exception;
    
  public Scraper getScraperByGuid(String guid) throws Exception;
  
  public List<Scraper> getScraperList(int userId);
  
  public List<Scraper> getPublicScrapers(int userId) throws Exception;

  public List<Dataset> getScraperDataset(int scraperId) throws Exception;
  
  public void invalidateDatasets(Analysis analysis);
  
  public void createAnalysisDatasets(List<AnalysisDataset> anlsDts);

  List<AnalysisDataset> getRelatedAnalysis(int datasetId) throws Exception;

  void updateBcDataScript(BcDataScript script);

  void doUpdateAction(User user, BcDataScript script) throws Exception;

  SearchResult search(String query, int ownerId) throws Exception;
  
  public void createDataCollection(DataCollection collection);
  
  public void deleteDataCollection(int collectionId);
  
  public void updateDataCollection(DataCollection collection);
  
  public DataCollection getDataCollection(int collectionId);
  
  public List<DataCollection> getUserCollections(int userId);
  
  public void deleteUserCollections(int userId);
  
  public void addDatasetToCollection(int datasetId, int collectionId);

  void createAnalysisPost(User user, Analysis analysis, String message) throws Exception;

  List<Analysis> getMostPopularAnalyses();
  
  //----- Vinhpq : adding temporary functions for left menu ----
  public boolean removeAnalysis(int anlsId);
  
  public boolean removeDataset(int dsId);
  
  public boolean addDatasetVote(int userId, Dataset ds, DatasetVote dsVote) throws Exception;
  
  List<Dataset> getMostPopularDatasets(int maxNumber);
  
  List<Analysis> getAllAnalysesBySelf(int userId ,int startPoint ,int maxNumber);
  
  List<Dataset> getAllDatasetsBySelf(int userId ,int startPoint ,int maxNumber);
  
  List<Analysis> getPopularAnalysesBySelf(int userId, int startPoint, int maxNumber);

  List<Dataset> getPopularDatasetsBySelf(int userId, int startPoint, int maxNumber);
  
  List<Analysis> getAnalysesIn1Month(int startPoint, int numrows);
  
  List<Dataset> getDatasetsIn1Month(int startPoint, int numrows);
  
  List<Analysis> getPopularAnalysesIn1Month(int startPoint, int numrows);
  
  List<Dataset> getPopularDatasetsIn1Month(int startPoint, int numrows);
  
  List<Analysis> getRecentAnalysisStaffPick(int startPoint, int maxNumber);
  
  List<Dataset> getRecentDatasetsStaffPick(int startPoint, int maxNumber);

  List<Analysis> getPopularAnalysesStaffPick(int startPoint, int maxNumber);
  
  List<Dataset> getPopularDatasetsStaffPick(int startPoint, int maxNumber);
  
  List<Tag> get10Tags();
  
  void resetPassword(int userId, String newpass);
  
  void changeActiveRegisterStatus(int userId);
  
  void addSttResetPassword(int userId, String activecode, String expiredDate);
  
  List<Analysis> get20AuthorAnalysesRecent(int startPoint, int maxNumber);
  
  List<Dataset> get20AuthorDataSetRecent(int startPoint, int maxNumber);
  
  List<Analysis> get20AuthorAnalysesItemPopular(int startPoint, int maxNumber);
  
  List<Dataset> get20AuthorDataSetItemPopular(int startPoint, int maxNumber);
  //------------------
  
  List<Dataset> getMostPopularDatasets();

  List<Analysis> getStaffPickAnalyses();

  List<Dataset> getStaffPickDatasets();
  
  Tag getTag(String tag);

  void addAnalysisTags(int anlsId, List<Tag> tags);

  void removeAnalysisTag(Analysis anls, Tag tag);

  void removeAnalysisTags(Analysis anls, List<Tag> tags);

  List<Analysis> getAnalysesByTag(String tag);

  List<Analysis> getMostRecentAnalyses();

  List<Analysis> getMoreRecentAnalyses(int lastId);
  
  public void logUserAction(int userId, int action,String data);
  
  public void addDatasetRefDocument(int datasetId, ReferenceDocument refDoc);
  
  void addDatasetTags(int dtsId, List<Tag> tags);

  void removeDatasetTag(Dataset dts, Tag tag);

  void removeDatasetTags(Dataset dts, List<Tag> tags);

  void createTag(String tag);
  
  void addAnalysisTag(int anlsId, int tagId);
  
  void addDatasetTag(int datasetId, int tagId);
  
  /**
   * 
   * @param objectId
   * @param type
   * @return the total page view before increment
   */
  int increasePageView(int objectId, String type);
  
  int getPageView(int objectId, String type);
}
