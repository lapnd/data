import MySQLdb as mysqldb
import os
import sys
import uuid

_log_dir = '/tmp/bouncingdata'

def _initialize():
  global _execution_id
  global _initialized
  try:    
    _execution_id = sys.argv[1]
    _initialized = True
  except:
    print "Failed to initialize visualization module. Reason: Invalid parameters"
  

def save_html(v_name, code, description):
  if not _initialized:
    print 'Visualization module was not initialized properly.'
    return
  
  try:
    # store html file to log dir
    filename = _log_dir + "/" + _execution_id + "/" + v_name + ".html"
    dirname = os.path.dirname(filename)
    if not os.path.exists(dirname):
      os.makedirs(dirname)
    output_file = open(filename, "w")
    output_file.write(code)
    output_file.close()
    
    print "Successfully save visualization"
  
    # update to database
    #conn = mysqldb.connect (host='localhost', user='root', passwd='a', db='bouncingdata')
    #cursor = conn.cursor(mysqldb.cursors.DictCursor)
    
    #print 'Saving visualization..'
    # save visualization
    #guid = str(uuid.uuid4())
    #filename = _app_store + "/" + _app_guid + "/v/" + guid + ".html"
    #dirname = os.path.dirname(filename)
    #if not os.path.exists(dirname):
    #  os.makedirs(dirname)
    #output_file = open(filename, "w")
    #output_file.write(code)
    #output_file.close()
    
    # invalidate old viz.
    #query = "update `visualizations` set `is_active` = false where `app_id` = " + _app_id + " and `is_active` = true"
    #try:
    #  cursor.execute(query)
    #  conn.commit()
    #except:
    #  print "Failed to invalidate old visualizations"
    #  conn.rollback()
    
    # update db
    #print 'Updating database..'
    #query = "insert into `visualizations`(`name`, `app_id`, `type`, `description`, `author`, `guid`, `is_active`) values('" + v_name + "'," + _app_id + ", 'html', '" +  description + "'," + _user_id + ", '" + guid + "', true)" 
    #try:
    #  cursor.execute(query)
    #  conn.commit() 
    #except:
    #  print "Failed to update database"
    #  conn.rollback()
    #cursor.close()
    #conn.close()
    
  except Exception as e:
    print "There're something wrong when save visualization"
    print e

def main():
  #do something
  x = 1

if __name__ == "__main__":
  main()
elif __name__ == "visualization":
  _initialize()
