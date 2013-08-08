library(RCurl)
library(rjson)

BASE_URL = "http://localhost:8080/bouncingdata/client"

# try to retrieve credential from file
getCredential = function() {
  cfg_file <- '~/.bcdataclient'
  if (file.exists(cfg_file)) {
    return(readChar(cfg_file, file.info(cfg_file)$size))
  }
  return(NULL)
}

list = function(type, credential=NULL) {
  if (is.null(credential)) {
    credential <- getCredential()
  }
  
  if (is.null(credential)) {
    print('Credential is null')
    return(NULL)
  }

  res = getURL(paste(BASE_URL, "/list?type=", type, sep=''), userpwd=credential)
  jsonObj <- fromJSON(res)
  # print result as a dataframe
  print(jsonObj$analyses)
  return(jsonObj$analyses)
}

getAnalysis = function(guid, credential=NULL) {
  if (is.null(credential)) {
    credential <- getCredential()
  }
  
  if (is.null(credential)) {
    print('Credential is null')
    return(NULL)
  }
  
  url <- paste(BASE_URL, "/anls/getsource/", guid, sep='')
  res = getURL(url, userpwd=credential)
  print(res)
  return(res)
}

# upload script to bouncingdata
postAnalysis = function(script_path, name, credential=NULL) {
  
  if (is.null(credential)) {
    credential <- getCredential()
  }
  
  if (is.null(credential)) {
    print('Credential is null')
    return(NULL)
  }
  
  # read script file
  if (!file.exists(script_path)) {
    print(paste("Can't find script file ", script_path, sep=''))
    return(NULL)
  }
  
  code <- readChar(script_path, file.info(script_path)$size)
  
  # make the POST request
  opts = curlOptions(verbose = TRUE, userpwd = credential, netrc = FALSE, followlocation = TRUE)
  postForm(paste(BASE_URL, "/anls/up", sep=''), "code" = code, "name" = name, "description" = "Upload from bcdataclient", .opts=opts)  

  # wait for response: guid if upload is successful, otherwise print error message
}

getAnalysisInfo = function(guid, credential=NULL) {
  if (is.null(credential)) {
    credential <- getCredential()
  }
  
  if (is.null(credential)) {
    print('Credential is null')
    return(NULL)
  }
  
  url <- paste(BASE_URL, "/anls/info/", guid, sep='')
  res <- getURL(url, userpwd=credential)
  jsonObj <- fromJSON(res)
  # print result as a dataframe
  print(jsonObj)
  return(jsonObj)
}

getDatasetInfo = function(guid, credential=NULL) {
  
}

# upload dataset to bouncingdata
postDataset = function(dataframe, name, credential=NULL) {
  
}

x <- getAnalysis('615a3dfa-effb-4f64-87f7-c8c59ac1a6d4', 'demo:demo')
