function(zipfile) {
    
    var line = config_loader_path + '/delete.sh ' + zipfile
    var proc = karate.fork({useShell: true, line: line, workingDir: config_loader_path})
    proc.waitSync()
    return proc
  }
  
  