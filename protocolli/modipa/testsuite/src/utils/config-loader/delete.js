function(zipfile) {
  
  var commandName = 'delete'

  if (platform == 'windows') {
    commandName = commandName + ".cmd"
  } else {
    commandName = commandName + ".sh"
  }


  var line = config_loader_path + '/' + commandName + ' '  + zipfile
  var proc = karate.fork({useShell: true, line: line, workingDir: config_loader_path})
  proc.waitSync()
  return proc
}
  
  